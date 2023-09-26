package com.example3;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import com.example3.model.*;

public class App {

    public static void main(String[] args) {
        // Create a Hibernate configuration and session factory
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml"); // Update this with your Hibernate configuration file
        SessionFactory sessionFactory = configuration.buildSessionFactory();

        // Create a session
        Session session = sessionFactory.openSession();

        try {
            // Create a new person
            Person person = new Person();
            person.setPersonName("John Doe");

            // Create a passport for the person
            Passport passport = new Passport();
            passport.setName("US Passport");
            passport.setPerson(person);

            // Start a transaction
            session.beginTransaction();

            // Save the person and passport
            session.save(person);
            session.save(passport);

            // Commit the transaction
            session.getTransaction().commit();

            // Retrieve the person and passport
            session.beginTransaction();

            Person retrievedPerson = session.get(Person.class, person.getId());
            Passport retrievedPassport = session.get(Passport.class, passport.getPassportId());

            // Print the retrieved data
            System.out.println("Retrieved Person: " + retrievedPerson.getPersonName());
            System.out.println("Retrieved Passport: " + retrievedPassport.getName());

            // Commit the transaction
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            // Rollback the transaction if an exception occurs
            session.getTransaction().rollback();
        } finally {
            // Close the session and session factory
            session.close();
            sessionFactory.close();
        }
    }
}
