package Repositories;

import Managers.HibernateConnectionSetUper;
import Models.CatsMainInfo;
import Models.Owner;
import Models.OwnersWithCats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
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

    @Override
    public void deleteOwner(EntityManagerFactory entityManagerFactory, Integer ownerId) {
        var entityManager = entityManagerFactory.createEntityManager();

        try {
            var transaction = entityManager.getTransaction();
            transaction.begin();

            Owner owner = entityManager.find(Owner.class, ownerId);

            if (owner != null) {
                entityManager.remove(owner);
            }

            transaction.commit();
        }
        catch (RuntimeException e) {
            logger.error("An error occurred", e);
        }
        finally {
            entityManager.close();
        }
    }
}
