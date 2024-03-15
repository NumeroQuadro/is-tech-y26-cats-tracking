package ScenarioProviders;

import ScenarioRunners.ScenarioHolder;
import Scenarios.IScenario;

public interface IScenarioProvider {
    boolean tryGetScenario(ScenarioHolder<IScenario> scenarioHolder);
}
