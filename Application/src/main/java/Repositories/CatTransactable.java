package Repositories;

import Models.CatsMainInfo;
import Models.CatsWithFriends;
import Models.OwnersWithCats;
import jakarta.persistence.EntityManagerFactory;

import java.util.Collection;

public interface CatTransactable {
    CatsMainInfo addCatToMainInfo(EntityManagerFactory entityManagerFactory, CatsMainInfo catsMainInfo);
    Collection<CatsMainInfo> listCatsFromMainInfo(EntityManagerFactory entityManagerFactory);
    void deleteCatFromMainInfo(EntityManagerFactory entityManagerFactory, Integer catId, Integer ownerId);
    void insertCatAndItFriendToFriendsTable(EntityManagerFactory entityManagerFactory, CatsWithFriends catsWithFriends);


    OwnersWithCats addCatToOwnersWithCats(EntityManagerFactory entityManagerFactory, Integer ownerId, Integer catId);
}
