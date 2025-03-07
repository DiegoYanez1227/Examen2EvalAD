package edu.examen.marzo2025.persistencia;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import edu.examen.marzo2025.modelo.Examen;
import edu.examen.marzo2025.modelo.Pregunta;
import edu.examen.marzo2025.modelo.PreguntaDeExamen;
import edu.examen.marzo2025.util.HibernateUtil;

public class ExamenesDAOImpl implements ExamenesDAO {

	//private static final Logger Logger = LogManager.getLogger(ExamenesDAOImpl.class);
	

    @Override
    public List<Examen> recuperarExamenes() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Examen", Examen.class).list();
        } catch (Exception e) {
            e.printStackTrace();
          //  Logger.error("Error al recuperar exámenes", e);
            return null;
        }
    }

    @Override
    public List<Pregunta> recuperarPreguntas() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Pregunta", Pregunta.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Integer numeroPreguntasDelExamen(int idExamen) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Long count = session.createQuery(
                "SELECT COUNT(p.id.pregunta) FROM PreguntaDeExamen p WHERE p.id.examen = :idExamen",
                Long.class)
                .setParameter("idExamen", idExamen)
                .uniqueResult();
            return count != null ? count.intValue() : 0;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean aniadirPregunta(Pregunta pregunta) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(pregunta);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean aniadirExamen(Examen examen) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(examen);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean aniadirPreguntaAExamen(int idExamen, int idPregunta) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            Examen examen = session.get(Examen.class, idExamen);
            Pregunta pregunta = session.get(Pregunta.class, idPregunta);

            if (examen == null || pregunta == null) {
                if (tx != null) tx.rollback();
                //logger
                return false;
            }

            // Buscar la posición máxima actual
            Integer maxPosicion = session.createQuery(
                "SELECT COALESCE(MAX(p.posicion), 0) FROM PreguntaDeExamen p WHERE p.examen.id = :idExamen",
                Integer.class)
                .setParameter("idExamen", idExamen)
                .uniqueResult();

            PreguntaDeExamen pde = new PreguntaDeExamen(examen, pregunta, 0); 
            session.persist(pde);

            tx.commit();
           // Logger.info("Pregunta añadida al examen con éxito");
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean quitarPreguntaAExamen(int idExamen, int idPregunta) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            PreguntaDeExamen pde = session.createQuery(
                    "FROM PreguntaDeExamen p WHERE p.examen.id = :idExamen AND p.pregunta.id = :idPregunta",
                    PreguntaDeExamen.class)
                    .setParameter("idExamen", idExamen)
                    .setParameter("idPregunta", idPregunta)
                    .uniqueResult();

                if (pde == null) {
                   // Logger warn
                    return false;
                }

                session.remove(pde);
                tx.commit();
               // Logger
                return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean eliminarPregunta(int idPregunta) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Pregunta pregunta = session.get(Pregunta.class, idPregunta);
            if (pregunta != null) {
                session.remove(pregunta);
                tx.commit();
                return true;
            }
            if (tx != null) tx.rollback();
            return false;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean eliminarExamen(int idExamen) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Examen examen = session.get(Examen.class, idExamen);
            if (examen != null) {
                session.remove(examen);
                tx.commit();
                return true;
            }
            if (tx != null) tx.rollback();
            return false;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        }
    }
}