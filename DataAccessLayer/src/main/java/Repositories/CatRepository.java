package Repositories;

import Managers.HibernateConnectionSetUper;
import Models.CatsMainInfo;
import Models.CatsWithFriends;
import Models.OwnersCatsPrimaryKey;
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
    public void deleteCatFromMainInfo(EntityManagerFactory entityManagerFactory, Integer catId, Integer ownerId) {
        var entityManager = entityManagerFactory.createEntityManager();
        try {
            var transaction = entityManager.getTransaction();
            transaction.begin();

            OwnersCatsPrimaryKey pk = new OwnersCatsPrimaryKey(ownerId, catId);
            OwnersWithCats ownersWithCats = entityManager.find(OwnersWithCats.class, pk);
            var catsMainInfo = entityManager.find(CatsMainInfo.class, catId);

            if (ownersWithCats != null) {
                entityManager.remove(ownersWithCats);
            }
            if (catsMainInfo != null) {
                entityManager.remove(catsMainInfo);
            }

            transaction.commit();
        } catch (RuntimeException e) {
            logger.error("An error occurred", e);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public OwnersWithCats addCatToOwnersWithCats(EntityManagerFactory entityManagerFactory, OwnersWithCats ownersWithCats) {
        var entityManager = entityManagerFactory.createEntityManager();

        try {
            var transaction = entityManager.getTransaction();
            transaction.begin();

            entityManager.persist(ownersWithCats);

            transaction.commit();

            return ownersWithCats;
        } catch (RuntimeException e) {
            logger.error("An error occurred", e);
        } finally {
            entityManager.close();
        }

        return null;
    }

    @Override
    public void insertCatAndItFriendToFriendsTable(EntityManagerFactory entityManagerFactory, CatsWithFriends catsWithFriends) {
        var entityManager = entityManagerFactory.createEntityManager();

        try {
            var transaction = entityManager.getTransaction();
            transaction.begin();

            var catsWithFriendsReverse = new CatsWithFriends();
            catsWithFriendsReverse.setCatId(catsWithFriends.getFriendId());
            catsWithFriendsReverse.setFriendId(catsWithFriends.getCatId());

            entityManager.persist(catsWithFriends);
            entityManager.persist(catsWithFriendsReverse);

            transaction.commit();
        } catch (RuntimeException e) {
            logger.error("An error occurred", e);
        } finally {
            entityManager.close();
        }
    }
}
