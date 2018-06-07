import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import tmmdl.collector.Collector;
import tmmdl.util.Converter;

public class Main {

    public static void main(String[] args) {


        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        System.out.println("session open");
        session.close();
        System.out.println("closed");


        Converter converter = new Converter();
        converter.scheduleIt();
    }
}
