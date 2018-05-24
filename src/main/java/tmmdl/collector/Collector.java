package tmmdl.collector;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import tmmdl.model.Job;
import tmmdl.parser.Parser;

import java.util.ArrayList;

public class Collector {

    ArrayList<Job> jobs = new ArrayList<Job>();

    public void run() {

        Parser parser = new Parser();
        parser.indeed(jobs);
        parser.monster(jobs);
        parser.glassdoor(jobs);

        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        for (int i = 0; i < jobs.size(); i++) {
            if(session.find(Job.class, jobs.get(i).getId()) == null) {
                session.save(jobs.get(i));
            }
        }
        session.getTransaction().commit();
        session.close();
    }
}
