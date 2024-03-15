package Scenarios;

import CatServices.CatService;
import Contracts.ICatService;
import Contracts.IOwnerService;
import OwnerServices.OwnerService;
import ResultTypes.TransactionResult;
import ScenarioResults.ScenarioResult;
import jakarta.persistence.EntityManagerFactory;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AddingFriendsToCat implements IScenario {
    private static final Logger logger = LoggerFactory.getLogger(AddingFriendsToCat.class);
    private final EntityManagerFactory entityManagerFactory;
    private final ICatService catService;
    private final IOwnerService ownerService;

    @Getter
    private String scenarioName = "Add friends to Cat";

    public AddingFriendsToCat(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        this.catService = new CatService(entityManagerFactory);
        this.ownerService = new OwnerService(entityManagerFactory);
    }

    @Override
    public ScenarioResult run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Only existing cats can be added as friends.");
        System.out.println("Please enter the name of the target cat:");
        String targetCatName = scanner.nextLine();

        List<String> friendNames = new ArrayList<>();
        String friendName;
        while (true) {
            System.out.println("Enter a friend cat's name (or type 'done' to finish):");
            friendName = scanner.nextLine();
            if ("done".equalsIgnoreCase(friendName)) {
                break;
            }
            friendNames.add(friendName);
        }

        // Assuming your catService has a method to add cat friends
        try {
            var result = catService.addFriendsToCat(targetCatName, friendNames);
            if (result instanceof TransactionResult.Success) {
                System.out.println("Friends were added successfully!");
                return new ScenarioResult.Success();
            } else {
                System.out.println("It was not possible to add friends.");
                return new ScenarioResult.Failure("Failed to add friends.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred while adding friends.");
            return new ScenarioResult.Failure(e.getMessage());
        }
    }
}
