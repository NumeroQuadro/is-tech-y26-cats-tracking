package Repositories;

import Managers.HibernateConnectionSetUper;
import Models.*;
import Models.Enums.CatColor;
import RepositoriesInterfaces.CatTransactable;
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

public class CatRepository implements CatTransactable {
    private static final Logger logger = LoggerFactory.getLogger(OwnerRepository.class);
    private final HibernateConnectionSetUper hibernateConnectionSetUper = new HibernateConnectionSetUper();
    private final EntityManagerFactory entityManagerFactory;

    public CatRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public CatsMainInfo addCatToMainInfo(String catName, String catBredd, LocalDate birthday, CatColor catColor) {
        var entityManager = entityManagerFactory.createEntityManager();

        try {
            var transaction = entityManager.getTransaction();
            transaction.begin();

            var catsMainInfo = new CatsMainInfo();
            catsMainInfo.setName(catName);
            catsMainInfo.setBreed(catBredd);
            catsMainInfo.setBirthDate(birthday);
            catsMainInfo.setColor(catColor);

            entityManager.persist(catsMainInfo);

            transaction.commit();

            return catsMainInfo;
        } catch (RuntimeException e) {
            logger.error("An error occurred", e);
            throw e;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Collection<CatsMainInfo> listCatsFromMainInfo() {
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
            throw e;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void deleteCatFromMainInfo(Integer catId, Integer ownerId) {
        var entityManager = entityManagerFactory.createEntityManager();
        try {
            var transaction = entityManager.getTransaction();
            transaction.begin();

            OwnerWithCatID pk = new OwnerWithCatID(ownerId, catId);
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
            throw e;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public OwnersWithCats addCatToOwnersWithCats(Integer ownerId, Integer catId) {
        var entityManager = entityManagerFactory.createEntityManager();

        try {
            var transaction = entityManager.getTransaction();
            transaction.begin();

            Owner existingOwner = entityManager.find(Owner.class, ownerId);
            CatsMainInfo existingCat = entityManager.find(CatsMainInfo.class, catId);
            OwnerWithCatID id = new OwnerWithCatID();
            id.setOwnerId(existingOwner.getId());
            id.setCatId(existingCat.getCatId());

            var ownerWithCat = new OwnersWithCats();
            ownerWithCat.setOwner(existingOwner);
            ownerWithCat.setCat(existingCat);
            ownerWithCat.setId(id);

            entityManager.persist(ownerWithCat);

            transaction.commit();

            return ownerWithCat;
        } catch (RuntimeException e) {
            logger.error("An error occurred", e);
            throw e;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void insertCatAndItFriendToFriendsTable(Integer targetCatId, Integer friendId) {
        var entityManager = entityManagerFactory.createEntityManager();

        try {
            var transaction = entityManager.getTransaction();
            transaction.begin();

            var catsWithFriends = new CatsWithFriends();
            catsWithFriends.setCatId(targetCatId);
            catsWithFriends.setFriendId(friendId);

            var catsWithFriendsReverse = new CatsWithFriends();
            catsWithFriendsReverse.setCatId(catsWithFriends.getFriendId());
            catsWithFriendsReverse.setFriendId(catsWithFriends.getCatId());

            entityManager.persist(catsWithFriends);
            entityManager.persist(catsWithFriendsReverse);

            transaction.commit();
        } catch (RuntimeException e) {
            logger.error("An error occurred", e);
            throw e;
        } finally {
            entityManager.close();
        }
    }
}
