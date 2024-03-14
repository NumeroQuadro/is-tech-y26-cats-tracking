import Managers.HibernateConnectionSetUper;
import Migrations.InitialMigration;
import Models.CatsMainInfo;
import Models.Enums.CatColor;
import Models.Owner;
import Models.OwnersWithCats;
import Repositories.CatRepository;
import Repositories.OwnerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;

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


        var hibernateConnectionSetUper = new HibernateConnectionSetUper();
        var entityManagerFactory = hibernateConnectionSetUper.setUpWithHibernate();

        var ownerRepo = new OwnerRepository();
        ownerRepo.addOwner(entityManagerFactory, dimon);
        catRepo.addCatToMainInfo(entityManagerFactory, comaru);
        var cats = catRepo.listCatsFromMainInfo(entityManagerFactory).stream().toList();
        var cat = cats.stream().findFirst().orElse(null);

        var comaruWithDimon = new OwnersWithCats();
        comaruWithDimon.setOwnerId(dimon.getId());

        if (cat == null) {
            logger.error("No cats found");
            return;
        }

        var catId = cat.getCat_id();

        comaruWithDimon.setCatId(catId);
        catRepo.addCatToOwnersWithCats(entityManagerFactory, comaruWithDimon);
        catRepo.deleteCatFromMainInfo(entityManagerFactory, catId, dimon.getId());

    }
}