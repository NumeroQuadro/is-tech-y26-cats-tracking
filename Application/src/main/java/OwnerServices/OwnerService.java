package OwnerServices;

import CatServices.CatService;
import Contracts.IOwnerService;
import Repositories.CatRepository;
import Repositories.OwnerRepository;
import RepositoriesInterfaces.CatTransactable;
import RepositoriesInterfaces.OwnerTransactable;
import ResultTypes.TransactionResult;
import jakarta.persistence.EntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

public class OwnerService implements IOwnerService {
    private static final Logger logger = LoggerFactory.getLogger(CatService.class);
    private final EntityManagerFactory entityManagerFactory;
    private final OwnerTransactable ownerRepository;
    private final CatTransactable catRepository;

    public OwnerService(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        this.ownerRepository = new OwnerRepository(entityManagerFactory);
        this.catRepository = new CatRepository(entityManagerFactory);
    }


    @Override
    public TransactionResult addOwnerToContext(LocalDate birthday, String ownerName) {
        try {
            ownerRepository.addOwner(birthday, ownerName);
        } catch (RuntimeException e) {
            logger.error("An error occurred in service layer", e);
            return new TransactionResult.Failure("An error occurred in service layer");
        }

        return new TransactionResult.Success();
    }

    @Override
    public TransactionResult deleteOwnerFromContext(String ownerName) {
        return null;
    }
}
