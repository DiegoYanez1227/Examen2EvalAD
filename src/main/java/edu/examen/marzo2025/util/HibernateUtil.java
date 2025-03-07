package edu.examen.marzo2025.util;

import org.hibernate.SessionFactory;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import edu.examen.marzo2025.modelo.Examen;
import edu.examen.marzo2025.modelo.Pregunta;
import edu.examen.marzo2025.modelo.PreguntaDeExamen;

public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            Configuration conf = new Configuration();
            conf.configure("hibernate.cfg.xml");
            conf.addAnnotatedClass(Examen.class);
            conf.addAnnotatedClass(Pregunta.class);
            conf.addAnnotatedClass(PreguntaDeExamen.class);

            ServiceRegistry sr = new StandardServiceRegistryBuilder()
                .applySettings(conf.getProperties()).build();

            return conf.buildSessionFactory(sr);
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }
}