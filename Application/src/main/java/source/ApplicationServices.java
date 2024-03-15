package source;

import CatServices.CatService;
import Contracts.ICatService;
import Contracts.IOwnerService;
import Managers.HibernateConnectionSetUper;
import Migrations.InitialMigration;
import Models.Enums.CatColor;
import OwnerServices.OwnerService;
import Repositories.CatRepository;
import RepositoriesInterfaces.OwnerTransactable;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ApplicationServices {
    public static void main (String[] args) {
        var initialMigration = new InitialMigration();
        try {
            initialMigration.migrate();
        } catch (IOException e) {
            System.out.println("Migration failed");
        }

        var hibernateConnectionSetUper = new HibernateConnectionSetUper();
        var entityManagerFactory = hibernateConnectionSetUper.setUpWithHibernate();

        IOwnerService ownerService = new OwnerService(entityManagerFactory);
        ICatService catService = new CatService(entityManagerFactory);

        ownerService.addOwnerToContext(LocalDate.of(2000, 1, 1), "John Doe");
        catService.addCatToContext("Comaru", "Manchkin", LocalDate.of(2019, 1, 1), CatColor.semi_color, "John Doe");
        catService.addCatToContext("Cocoa", "Manchkin", LocalDate.of(2018, 2, 2), CatColor.black, "John Doe");
        catService.addCatToContext("Comugi", "Manchkin", LocalDate.of(2019, 3, 3), CatColor.grey, "John Doe");

        catService.mapCatToOwner("Comaru", "John Doe");
        catService.mapCatToOwner("Cocoa", "John Doe");
        catService.mapCatToOwner("Comugi", "John Doe");

        List<String> catsNames = new ArrayList<>();
        catsNames.add("Cocoa");
        catsNames.add("Comugi");

        catService.addFriendsToCat("Comaru", catsNames);
    }
}
