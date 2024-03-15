package Repositories;

import Models.Owner;

import jakarta.persistence.EntityManagerFactory;
import java.util.Collection;

public interface OwnerTransactable {
    Owner addOwner(EntityManagerFactory entityManagerFactory, Owner owner);
    Collection<Owner> listOwners(EntityManagerFactory entityManagerFactory);
    void deleteOwner(EntityManagerFactory entityManagerFactory, Integer ownerId);

}
