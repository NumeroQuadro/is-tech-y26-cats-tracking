package RepositoryInterfaces;

import Models.CatsMainInfo;
import Models.OwnersWithCats;

import jakarta.persistence.EntityManagerFactory;
import java.util.Collection;

public interface CatTransactable {
    CatsMainInfo addCatToMainInfo(EntityManagerFactory entityManagerFactory, CatsMainInfo catsMainInfo);
    Collection<CatsMainInfo> listCatsFromMainInfo(EntityManagerFactory entityManagerFactory);
    void deleteCatFromMainInfo(EntityManagerFactory entityManagerFactory, Integer catId, Integer ownerId);
    OwnersWithCats addCatToOwnersWithCats(EntityManagerFactory entityManagerFactory, OwnersWithCats ownersWithCats);
}
