package CatServices;

import Contracts.ICatService;
import Models.Enums.CatColor;
import Repositories.CatRepository;
import Repositories.OwnerRepository;
import RepositoriesInterfaces.CatTransactable;
import RepositoriesInterfaces.OwnerTransactable;
import ResultTypes.TransactionResult;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.Collection;

public class CatService implements ICatService {
    private final EntityManagerFactory entityManagerFactory;
    private static final Logger logger = LoggerFactory.getLogger(CatService.class);
    private final CatTransactable catRepository;
    private final OwnerTransactable ownerRepository;

    public CatService(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        this.catRepository = new CatRepository(entityManagerFactory);
        this.ownerRepository = new OwnerRepository(entityManagerFactory);
    }
    @Override
    public TransactionResult addCatToContext(String catName, String catBreed, LocalDate catBirthday, CatColor catColor, String ownerName) {
        try {
            catRepository.addCatToMainInfo(catName, catBreed, catBirthday, catColor);
        } catch (RuntimeException e) {
            logger.error("An error occurred in service layer", e);

            return new TransactionResult.Failure("An error occurred in service layer");
        }

        return new TransactionResult.Success();
    }

    @Override
    public TransactionResult mapCatToOwner(String ownerName, String catName) {
        try {
            var owners = ownerRepository.listOwners().stream().toList();
            var cats = catRepository.listCatsFromMainInfo().stream().toList();

            var targetOwner = owners.stream().filter(owner -> owner.getName().equals(ownerName)).findFirst().orElse(null);
            var targetCat = cats.stream().filter(cat -> cat.getName().equals(catName)).findFirst().orElse(null);

            if (targetCat == null) {
                logger.error("Target cat not found");
                return new TransactionResult.Failure("Target cat not found");
            }

            if (targetOwner == null) {
                logger.error("Target owner not found");
                return new TransactionResult.Failure("Target owner not found");
            }

            catRepository.insertCatAndItFriendToFriendsTable(targetCat.getCatId(), targetOwner.getId());

        }
        catch (RuntimeException e) {
            logger.error("An error occurred in service layer", e);
            return new TransactionResult.Failure("An error occurred in service layer");
        }

        return new TransactionResult.Success();
    }

    @Override
    public TransactionResult addFriendsToCat(String targetCatName, Collection<String> friendCatNames) {
        var catsList = catRepository.listCatsFromMainInfo().stream().toList();
        var targetCatClass = catsList.stream().filter(cat -> cat.getName().equals(targetCatName)).findFirst().orElse(null);

        if (targetCatClass == null) {
            logger.error("Target cat not found");
            return new TransactionResult.Failure("Target cat not found");
        }

        try {
            for (var friendCatName : friendCatNames) {
                var friendCatClass = catsList.stream().filter(cat -> cat.getName().equals(friendCatName)).findFirst().orElse(null);

                if (friendCatClass == null) {
                    logger.error("Friend cat not found");
                    return new TransactionResult.Failure("Friend cat not found");
                }

                catRepository.insertCatAndItFriendToFriendsTable(targetCatClass.getCatId(), friendCatClass.getCatId());
            }
        } catch (RuntimeException e) {
            logger.error("An error occurred in service layer", e);

            return new TransactionResult.Failure("An error occurred in service layer");
        }

        return new TransactionResult.Success();
    }
}
