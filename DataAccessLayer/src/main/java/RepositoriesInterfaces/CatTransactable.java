package RepositoriesInterfaces;

import Models.CatsMainInfo;
import Models.CatsWithFriends;
import Models.Enums.CatColor;
import Models.OwnersWithCats;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDate;
import java.util.Collection;

public interface CatTransactable {
    CatsMainInfo addCatToMainInfo(String catName, String catBredd, LocalDate birthday, CatColor catColor);
    Collection<CatsMainInfo> listCatsFromMainInfo();
    void deleteCatFromMainInfo(Integer catId, Integer ownerId);
    void insertCatAndItFriendToFriendsTable(Integer targetCatId, Integer friendId);
    OwnersWithCats addCatToOwnersWithCats(Integer ownerId, Integer catId);
}
