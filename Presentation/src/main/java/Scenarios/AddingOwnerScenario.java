package Scenarios;

import CatServices.CatService;
import Contracts.ICatService;
import Contracts.IOwnerService;
import Models.Enums.CatColor;
import OwnerServices.OwnerService;
import ResultTypes.TransactionResult;
import ScenarioResults.ScenarioResult;
import jakarta.persistence.EntityManagerFactory;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class AddingOwnerScenario implements IScenario {
    private static final Logger logger = LoggerFactory.getLogger(AddingOwnerScenario.class);
    private final EntityManagerFactory entityManagerFactory;
    private final ICatService catService;
    private final IOwnerService ownerService;

    @Getter
    private String scenarioName = "Add Owner";

    public AddingOwnerScenario(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        this.catService = new CatService(entityManagerFactory);
        this.ownerService = new OwnerService(entityManagerFactory);
    }

    @Override
    public ScenarioResult run() {
        try {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Please enter the your name (it will be written as owner's name):");
            String ownerName = scanner.nextLine();

            System.out.println("Please enter the your birthday (YYYY-MM-DD):");
            String birthdayStr = scanner.nextLine();
            LocalDate birthday = LocalDate.parse(birthdayStr, DateTimeFormatter.ISO_LOCAL_DATE);

            var result = ownerService.addOwnerToContext(birthday, ownerName);

            if (result instanceof TransactionResult.Failure) {
                System.out.println("It is not possible to add owner to the context :(((");
            } else {
                System.out.println("Owner was added to the context successfully! 🤓");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());

            return new ScenarioResult.Failure(e.getMessage());
        }

        return new ScenarioResult.Success();
    }
}
