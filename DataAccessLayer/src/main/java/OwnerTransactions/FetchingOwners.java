package OwnerTransactions;

import Managers.HibernateConnectionSetUper;
import Models.Owner;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;

@Slf4j
public class FetchingOwners {
    private final HibernateConnectionSetUper hibernateConnectionSetUper = new HibernateConnectionSetUper();
    public void ListOwners() {
        try {
            var sessionFactory = hibernateConnectionSetUper.setUp();

            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();

                List<Owner> owners = session.createQuery("select u from Owner u", Owner.class).list();
                owners.forEach(owner -> System.out.println(owner.getName()));

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("some troubles with session: " + e.getMessage());
            }
            sessionFactory.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
