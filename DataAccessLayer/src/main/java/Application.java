import Managers.HibernateConnectionSetUper;
import Migrations.InitialMigration;
import Models.CatsMainInfo;
import Models.CatColor;
import Models.Owner;
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
        comaru.setOwnerId(dimon);

        var hibernateConnectionSetUper = new HibernateConnectionSetUper();
        var entityManagerFactory = hibernateConnectionSetUper.setUpWithHibernate();

        var ownerRepo = new OwnerRepository();
        ownerRepo.addOwner(entityManagerFactory, dimon);
        catRepo.addCatToMainInfo(entityManagerFactory, comaru);
        catRepo.deleteCatFromMainInfo(entityManagerFactory, 1);
        catRepo.listCatsFromMainInfo(entityManagerFactory).forEach(catsMainInfo -> System.out.println(catsMainInfo.getName()));
    }
}