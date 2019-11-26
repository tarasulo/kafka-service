package dao;

import model.FinalCar;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HibernateUtil;

public class CarDao {

    private final static Logger logger = LoggerFactory.getLogger(CarDao.class);

    public FinalCar add(FinalCar finalCar) {
        Long id = null;
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            id = (Long) session.save(finalCar);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        finalCar.setId(id);
        return finalCar;
    }

    public FinalCar update(FinalCar finalCar) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(finalCar);
            transaction.commit();
            session.close();
        } catch (Exception e) {
            logger.error("Can't update Car " + finalCar, e);
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return finalCar;
    }

    public FinalCar get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            FinalCar finalCar = session.get(FinalCar.class, id);
            session.close();
            return finalCar;
        } catch (Exception e) {
            logger.error("Can't get car by id=" + id, e);
        }
        return null;
    }

    public void delete(FinalCar finalCar) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(finalCar);
            transaction.commit();
            session.close();
        } catch (Exception e) {
            logger.error("Can't delete car " + finalCar, e);
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}
