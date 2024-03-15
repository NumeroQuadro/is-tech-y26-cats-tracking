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
        var dimon = new Owner();
        dimon.setName("Dimon");
        dimon.setBirthDate(LocalDate.of(2004, 4, 13));

        var catRepo = new CatRepository();
        var comaru = new CatsMainInfo();
        comaru.setName("Comaru");
        comaru.setBirthDate(LocalDate.of(2019, 1, 1));
        comaru.setBreed("Manchkin");
        comaru.setColor(CatColor.semi_color);

        var cocoa = new CatsMainInfo();
        cocoa.setName("Cocoa");
        cocoa.setBirthDate(LocalDate.of(2020, 2, 3));
        cocoa.setBreed("Manchkin");
        cocoa.setColor(CatColor.black);

        var hibernateConnectionSetUper = new HibernateConnectionSetUper();
        var entityManagerFactory = hibernateConnectionSetUper.setUpWithHibernate();

        var ownerRepo = new OwnerRepository();
        ownerRepo.addOwner(entityManagerFactory, dimon);

        catRepo.addCatToMainInfo(entityManagerFactory, comaru);
        catRepo.addCatToMainInfo(entityManagerFactory, cocoa);
        var cats = catRepo.listCatsFromMainInfo(entityManagerFactory).stream().toList();
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

        var catsWithFriends = new CatsWithFriends();
        catsWithFriends.setCatId(comaruId);
        catsWithFriends.setFriendId(cocoaId);

        comaruWithDimon.setOwner(dimon);
        catRepo.addCatToOwnersWithCats(entityManagerFactory, dimon.getId(), comaruId);
        catRepo.insertCatAndItFriendToFriendsTable(entityManagerFactory, catsWithFriends);
        ownerRepo.deleteOwner(entityManagerFactory, dimon.getId());
        //catRepo.deleteCatFromMainInfo(entityManagerFactory, catId, dimon.getId());
    }
}