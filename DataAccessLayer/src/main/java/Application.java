import Managers.HibernateConnectionSetUper;
import Migrations.InitialMigration;

import Models.CatsMainInfo;
import Models.CatsWithFriends;
import Models.Enums.CatColor;
import Models.Owner;
import Models.OwnersWithCats;
import Repositories.CatRepository;
import Repositories.OwnerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDate;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        var initialMigration = new InitialMigration();
        try {
            initialMigration.migrate();
        } catch (IOException e) {
            logger.error("An error occurred", e);
        }


        var hibernateConnectionSetUper = new HibernateConnectionSetUper();
        var entityManagerFactory = hibernateConnectionSetUper.setUpWithHibernate();

        var catRepo = new CatRepository(entityManagerFactory);

        var ownerRepo = new OwnerRepository(entityManagerFactory);
        ownerRepo.addOwner(LocalDate.of(2004, 4, 13), "Dimon");

        catRepo.addCatToMainInfo("Comaru", "Manchkin", LocalDate.of(2019, 1, 1), CatColor.semi_color);
        catRepo.addCatToMainInfo("Cocoa", "Manchkin", LocalDate.of(2020, 2, 3), CatColor.black);
        var cats = catRepo.listCatsFromMainInfo().stream().toList();
        var comaruCat = cats.get(0);
        var cocoaCat = cats.get(1);

        var comaruWithDimon = new OwnersWithCats();
        comaruWithDimon.setCat(comaruCat);

        if (comaruCat == null || cocoaCat == null) {
            logger.error("No cats found");
            return;
        }

        var comaruId = comaruCat.getCatId();
        var cocoaId = cocoaCat.getCatId();

        var dimon = ownerRepo.listOwners().stream().findFirst().orElse(null);

        comaruWithDimon.setOwner(dimon);
        catRepo.addCatToOwnersWithCats(dimon.getId(), comaruId);
        catRepo.insertCatAndItFriendToFriendsTable(comaruId, cocoaId);
        ownerRepo.deleteOwner(dimon.getId());
        //catRepo.deleteCatFromMainInfo(entityManagerFactory, catId, dimon.getId());
    }
}