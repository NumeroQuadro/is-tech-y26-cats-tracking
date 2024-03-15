package Scenarios;

import ScenarioResults.ScenarioResult;

public interface IScenario {
    ScenarioResult run();
    String getScenarioName();
}
