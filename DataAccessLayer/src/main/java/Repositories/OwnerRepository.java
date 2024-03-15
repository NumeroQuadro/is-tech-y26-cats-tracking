package Repositories;

import Managers.HibernateConnectionSetUper;
import Models.Owner;
import RepositoriesInterfaces.OwnerTransactable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public class OwnerRepository implements OwnerTransactable {
    private static final Logger logger = LoggerFactory.getLogger(OwnerRepository.class);
    private final HibernateConnectionSetUper hibernateConnectionSetUper = new HibernateConnectionSetUper();
    private final EntityManagerFactory entityManagerFactory;

    public OwnerRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public Owner addOwner(LocalDate birthday, String ownerName) {
        var entityManager = entityManagerFactory.createEntityManager();
        try {
            var transaction = entityManager.getTransaction();

            var owner = new Owner();
            owner.setBirthDate(birthday);
            owner.setName(ownerName);

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
    public Collection<Owner> listOwners() {
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
    public void deleteOwner(Integer ownerId) {
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
