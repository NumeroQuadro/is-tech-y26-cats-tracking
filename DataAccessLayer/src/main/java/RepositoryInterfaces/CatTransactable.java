package RepositoryInterfaces;

import Models.CatsMainInfo;

import javax.persistence.EntityManagerFactory;
import java.util.Collection;

public interface CatTransactable {
    CatsMainInfo addCatToMainInfo(EntityManagerFactory entityManagerFactory, CatsMainInfo catsMainInfo);
    Collection<CatsMainInfo> listCatsFromMainInfo(EntityManagerFactory entityManagerFactory);
    void updateInfoAboutCatInMainInfo(EntityManagerFactory entityManagerFactory, CatsMainInfo oldCatsMainInfo, CatsMainInfo newCatsMainInfo);
    void deleteCatFromMainInfo(EntityManagerFactory entityManagerFactory, Integer catId);
}
