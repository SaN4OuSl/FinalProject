package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        Configuration configuration = new Configuration().configure();

        try(SessionFactory sessionFactory = configuration.buildSessionFactory();
            Session session = sessionFactory.openSession()){
        }
    }
}
