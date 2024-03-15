package RepositoriesInterfaces;

import Models.Owner;

import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDate;
import java.util.Collection;

public interface OwnerTransactable {
    Owner addOwner(LocalDate birthday, String ownerName);
    Collection<Owner> listOwners();
    void deleteOwner(Integer ownerId);

}
