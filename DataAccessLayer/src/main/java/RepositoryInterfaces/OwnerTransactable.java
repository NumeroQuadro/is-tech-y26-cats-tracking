package RepositoryInterfaces;

import Models.Owner;

import jakarta.persistence.EntityManagerFactory;
import java.util.Collection;

public interface OwnerTransactable {
    Owner addOwner(EntityManagerFactory entityManagerFactory, Owner owner);
    Collection<Owner> listOwners(EntityManagerFactory entityManagerFactory);

}
