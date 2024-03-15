package ScenarioProviders;

import CatServices.CatService;
import Contracts.ICatService;
import Contracts.IOwnerService;
import OwnerServices.OwnerService;
import ScenarioRunners.ScenarioHolder;
import Scenarios.AddingOwnerScenario;
import Scenarios.IScenario;
import jakarta.persistence.EntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddingOwnerScenarioProvider implements IScenarioProvider {
    private static final Logger logger = LoggerFactory.getLogger(AddingOwnerScenarioProvider.class);
    private final EntityManagerFactory entityManagerFactory;
    private final ICatService catService;
    private final IOwnerService ownerService;

    public AddingOwnerScenarioProvider(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        this.catService = new CatService(entityManagerFactory);
        this.ownerService = new OwnerService(entityManagerFactory);
    }

    @Override
    public boolean tryGetScenario(ScenarioHolder<IScenario> scenarioHolder) {
        if (entityManagerFactory == null) {
            scenarioHolder.setValue(null);
            logger.error("Entity manager factory is not set.");

            return false;
        }

        scenarioHolder.setValue(new AddingOwnerScenario(entityManagerFactory));

        return true;
    }
}
