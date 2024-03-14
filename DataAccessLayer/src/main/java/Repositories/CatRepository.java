package Repositories;

import Managers.HibernateConnectionSetUper;
import Models.CatsMainInfo;
import RepositoryInterfaces.CatTransactable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.List;

public class CatRepository implements CatTransactable {
    private static final Logger logger = LoggerFactory.getLogger(OwnerRepository.class);
    private final HibernateConnectionSetUper hibernateConnectionSetUper = new HibernateConnectionSetUper();
    @Override
    public CatsMainInfo addCatToMainInfo(EntityManagerFactory entityManagerFactory, CatsMainInfo catsMainInfo) {
        var entityManager = entityManagerFactory.createEntityManager();

        try {
            var transaction = entityManager.getTransaction();
            transaction.begin();

            entityManager.persist(catsMainInfo);

            transaction.commit();

            return catsMainInfo;
        } catch (RuntimeException e) {
            logger.error("An error occurred", e);
        } finally {
            entityManager.close();
        }

        return null;
    }

    @Override
    public Collection<CatsMainInfo> listCatsFromMainInfo(EntityManagerFactory entityManagerFactory) {
        var entityManager = entityManagerFactory.createEntityManager();

        try {
            var transaction = entityManager.getTransaction();
            transaction.begin();

            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<CatsMainInfo> cq = cb.createQuery(CatsMainInfo.class);

            Root<CatsMainInfo> catsMainInfoRoot = cq.from(CatsMainInfo.class);
            cq.select(catsMainInfoRoot);

            TypedQuery<CatsMainInfo> query = entityManager.createQuery(cq);
            List<CatsMainInfo> cats = query.getResultList();

            return cats;

        } catch (RuntimeException e) {
            logger.error("An error occurred", e);
        } finally {
            entityManager.close();
        }

        return null;
    }

    @Override
    public void updateInfoAboutCatInMainInfo(EntityManagerFactory entityManagerFactory, CatsMainInfo oldCatsMainInfo, CatsMainInfo newCatsMainInfo) {
        var entityManager = entityManagerFactory.createEntityManager();

        if (!oldCatsMainInfo.getCat_id().equals(newCatsMainInfo.getCat_id())) {
            String message = "Cannot update cat's info, because the id of the old cat and the new cat are different";
            logger.error(message);
            throw new IllegalArgumentException(message);
        }
        // TODO: implement this method
    }

    @Override
    public void deleteCatFromMainInfo(EntityManagerFactory entityManagerFactory, Integer catId) {
        // TODO: firstly delete this cat from another tables (where are foreign keys to this cat) and then delete this cat from the main table
        var entityManager = entityManagerFactory.createEntityManager();
        try {
            var transaction = entityManager.getTransaction();
            transaction.begin();

            CatsMainInfo cat = entityManager.find(CatsMainInfo.class, catId);
            if (cat != null) {
                entityManager.remove(cat);
            } else {
                throw new IllegalArgumentException("There is no cat with id " + catId);
            }

            transaction.commit();
        } catch (RuntimeException e) {
            logger.error("An error occurred", e);
        } finally {
            entityManager.close();
        }
    }
}
