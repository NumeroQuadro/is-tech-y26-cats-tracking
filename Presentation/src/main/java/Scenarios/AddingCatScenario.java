package Scenarios;

import CatServices.CatService;
import Contracts.ICatService;
import Contracts.IOwnerService;
import Models.Enums.CatColor;
import OwnerServices.OwnerService;
import Repositories.OwnerRepository;
import ResultTypes.TransactionResult;
import ScenarioResults.ScenarioResult;
import jakarta.persistence.EntityManagerFactory;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class AddingCatScenario implements IScenario {
    private static final Logger logger = LoggerFactory.getLogger(AddingCatScenario.class);
    private final EntityManagerFactory entityManagerFactory;
    private final ICatService catService;
    private final IOwnerService ownerService;

    @Getter
    private String scenarioName = "Add Cat";

    public AddingCatScenario(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        this.catService = new CatService(entityManagerFactory);
        this.ownerService = new OwnerService(entityManagerFactory);
    }

    @Override
    public ScenarioResult run() {
        try {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Please enter the cat's name:");
            String catName = scanner.nextLine();

            System.out.println("Please enter the cat's birthday (YYYY-MM-DD):");
            String birthdayStr = scanner.nextLine();
            LocalDate birthday = LocalDate.parse(birthdayStr, DateTimeFormatter.ISO_LOCAL_DATE);

            System.out.println("Please enter the cat's breed:");
            String breed = scanner.nextLine();

            System.out.println("Please choose the cat's color:");
            for (CatColor color : CatColor.values()) {
                System.out.println("- " + color);
            }
            String colorStr = scanner.nextLine();
            CatColor color;
            try {
                color = CatColor.valueOf(colorStr);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid color selected. Defaulting to undefined.");
                color = CatColor.undefined;
                logger.error("Invalid color selected. Defaulting to undefined.");
            }

            System.out.println("Please enter name of the owner of cat");
            String ownerName = scanner.nextLine();

            var result = catService.addCatToContext(catName, breed, birthday, color, ownerName);
            var linkingResult = catService.mapCatToOwner(ownerName, catName);

            if (result instanceof TransactionResult.Failure) {
                System.out.println("It is not possible to add cat to the context :(((");
            } else {
                System.out.println("Cat was added to the context successfully! 🐈");
            }

            if (linkingResult instanceof TransactionResult.Failure) {
                System.out.println("It is not possible to map cat to owner :((((");
            } else {
                System.out.println("Cat was mapped to owner " + ownerName);
            }

        } catch (Exception e) {
            logger.error(e.getMessage());

            return new ScenarioResult.Failure(e.getMessage());
        }

        return new ScenarioResult.Success();
    }
}
