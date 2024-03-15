package ScenarioResultHandlers;

import ScenarioResults.ScenarioResult;

public class ConsoleScenarioResultHandler implements IScenarioResultHandler {
    @Override
    public void handle(ScenarioResult result) {
        if (result == null) {
            System.out.println("Fail to run scenario, because result is null.");
            return;
        }

        if (result instanceof ScenarioResult.Failure) {
            System.out.println("Fail to run scenario" + ((ScenarioResult.Failure) result).getMessage());
        } else {
            System.out.println("");
        }
    }
}
