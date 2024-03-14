package RepositoryInterfaces;

import Models.Owner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Collection;

public interface OwnerTransactable {
    Owner addOwner(EntityManagerFactory entityManagerFactory, Owner owner);
    Collection<Owner> listOwners(EntityManagerFactory entityManagerFactory);

}
