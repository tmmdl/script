package tmmdl.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import tmmdl.model.Job;
import tmmdl.util.Converter;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class Parser {

    String url;
    String title;
    String id;
    String location;
    String company;
    String date;
    String link;
    java.sql.Date created;


    public Parser() {
    }

    public void indeed(ArrayList list) {

        for (int i = 0; i < 110; i+=10) {
            url = "https://de.indeed.com/jobs?q=Junior+Softwareentwickler&sort=date&start=()".replace("()", ""+i);
            try {
                Document document = Jsoup.connect(url)
                        .timeout(0)
                        .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
                        .referrer("http://www.google.com")
                        .get();
                for (Element element: document.select(".row")) {
                    id = element.select("a[href]").attr("href");
                    title = element.select(".jobTitle").text();
                    location = element.select(".location").text();
                    company = element.select(".company").text();
                    date = element.select("span[class~=date]").text();
                    link = "https://www.indeed.de" + id;
                    String formatted = Converter.convert(date);
                    try {
                        created = Converter.toDate(formatted);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Job job = new Job(id, title, location, company, created, link, " ");
                    list.add(job);
                }
                try {
                    Thread.sleep(5000);
                }
                catch (Exception e) {
                    e.fillInStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void monster(ArrayList list) {

        for (int i = 0; i < 6; i++) {
            url = "https://www.monster.de/jobs/suche/?cy=de&q=junior&kwdv=13765,14644&page=()".replace("()", "+i");
            try {
                Document document = Jsoup.connect(url)
                        .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
                        .referrer("http://www.google.com")
                        .get();
                for (Element element: document.select(".js_result_row")) {
                    id = element.select(".jobTitle a[href]").attr("href");
                    title = element.select(".jobTitle").text();
                    location = element.select(".job-specs-location").text();
                    company = element.select(".company").text();
                    date = element.select("time[datetime]").attr("datetime").substring(0, 10);
                    System.out.println(date);
                    link = id;
                    try {
                        created = Converter.toDate(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Job job = new Job(id, title, location, company, created, link, " ");
                    list.add(job);
                }
                try {
                    Thread.sleep(5000);
                }
                catch (Exception e) {
                    e.fillInStackTrace();
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    public void glassdoor(ArrayList list) {

        for (int i = 1; i < 15; i++) {

            url = "https://www.glassdoor.de/Job/deutschland-junior-java-jobs-SRCH_IL.0,11_IN96_KO12,23_IP().htm?fromAge=30".replace("()", "" + i);
            try {
                Document document = Jsoup.connect(url)
                        .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
                        .referrer("http://www.google.com")
                        .timeout(0)
                        .get();
                for (Element element : document.select(".jl")) {
                    id = element.select("a[href]").attr("href");
                    link = "https://www.glassdoor.de" + id;
                    title = element.select("a[href]").text();
                    location = element.select(".subtle").text();
                    String temp = element.select("div[class$=empLoc]").text();
                    company = temp.substring(0, temp.indexOf("– "));
                    date = element.select(".hotListing").text();
                    String formatted = Converter.convert(date);
                    try {
                        created = Converter.toDate(formatted);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Job job = new Job(id, title, location, company, created, link, " ");
                    list.add(job);
                }
                try {
                    Thread.sleep(5000);
                } catch (Exception e) {
                    e.fillInStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}