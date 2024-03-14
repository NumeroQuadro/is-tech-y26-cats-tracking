package Repositories;

import Managers.HibernateConnectionSetUper;
import Models.Owner;
import RepositoryInterfaces.OwnerTransactable;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.List;

public class OwnerRepository implements OwnerTransactable {
    private static final Logger logger = LoggerFactory.getLogger(OwnerRepository.class);
    private final HibernateConnectionSetUper hibernateConnectionSetUper = new HibernateConnectionSetUper();
    @Override
    public Owner addOwner(EntityManagerFactory entityManagerFactory, Owner owner) {
        var entityManager = entityManagerFactory.createEntityManager();
        try {
            var transaction = entityManager.getTransaction();

            transaction.begin();
            entityManager.persist(owner);
            transaction.commit();

            return owner;
        } catch (RuntimeException e) {
            logger.error("An error occurred", e);
        } finally {
            entityManager.close();
        }

        return null;
    }


    @Override
    public Collection<Owner> listOwners(EntityManagerFactory entityManagerFactory) {
        var entityManager = entityManagerFactory.createEntityManager();
        try {
            var transaction = entityManager.getTransaction();
            transaction.begin();

            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Owner> cq = cb.createQuery(Owner.class);

            Root<Owner> owner = cq.from(Owner.class);
            cq.select(owner);

            TypedQuery<Owner> query = entityManager.createQuery(cq);
            List<Owner> owners = query.getResultList();

            for (Owner o : owners) {
                System.out.println(o.toString());
            }
            return owners;

        } catch (RuntimeException e) {
            logger.error("An error occurred", e);
        }
        finally {
            entityManager.close();
        }

        return null;
    }
}
