package OwnerTransactions;

import Managers.HibernateConnectionSetUper;
import Models.Owner;
import OwnerTransactions.Interfaces.OwnerTransactable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.Session;

public class AddNewOwner implements OwnerTransactable {
    private static final Logger logger = LoggerFactory.getLogger(AddNewOwner.class);
    private final HibernateConnectionSetUper hibernateConnectionSetUper = new HibernateConnectionSetUper();
    public Owner addOwner(Owner owner) {
        try {
            var sessionFactory = hibernateConnectionSetUper.setUp();

            System.out.println("sessionFactory: " + sessionFactory);

            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();
                session.save(owner);

                session.getTransaction().commit();

                session.disconnect();

                return owner;
            } catch (Exception e) {
                logger.error("An error occurred", e);
                System.out.println("some troubles with session: " + e.getMessage());
            }
            sessionFactory.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return null;
    }
}
