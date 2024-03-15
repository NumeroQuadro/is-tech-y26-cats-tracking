package ScenarioRunners;

import ScenarioResultHandlers.ConsoleScenarioResultHandler;
import ScenarioResultHandlers.IScenarioResultHandler;
import ScenarioResults.ScenarioResult;
import Scenarios.IScenario;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import ScenarioProviders.*;

public class ScenarioRunner {
    private final List<IScenarioProvider> providers;

    public ScenarioRunner(List<IScenarioProvider> providers) {
        this.providers = providers;
    }

    public void run() {
        List<IScenario> scenarios = getScenarios();

        System.out.println("Select action:");
        for (int i = 0; i < scenarios.size(); i++) {
            System.out.println((i + 1) + ". " + scenarios.get(i).getScenarioName());
        }

        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        if (choice < 1 || choice > scenarios.size()) {
            System.out.println("Invalid selection.");
            return;
        }

        IScenario scenario = scenarios.get(choice - 1);
        ScenarioResult result = scenario.run();

        IScenarioResultHandler handler = new ConsoleScenarioResultHandler();
        handler.handle(result);
    }

    private List<IScenario> getScenarios() {
        List<IScenario> scenarios = new ArrayList<>();
        ScenarioHolder<IScenario> scenarioHolder = new ScenarioHolder<IScenario>(null);

        for (IScenarioProvider provider : providers) {
            if (provider.tryGetScenario(scenarioHolder)) {
                scenarios.add(scenarioHolder.getValue());
            }
        }
        return scenarios;
    }
}
