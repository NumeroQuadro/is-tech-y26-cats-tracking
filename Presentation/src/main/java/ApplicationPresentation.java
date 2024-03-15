import Managers.HibernateConnectionSetUper;
import Migrations.InitialMigration;
import Repositories.CatRepository;
import ScenarioRunners.ScenarioRunner;
import ScenarioProviders.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ApplicationPresentation {
    public static void main(String[] args) {
        var initialMigration = new InitialMigration();
        try {
            initialMigration.migrate();
        } catch (IOException e) {
            System.out.println("An error occurred" + e);
        }

        var hibernateConnectionSetUper = new HibernateConnectionSetUper();
        var entityManagerFactory = hibernateConnectionSetUper.setUpWithHibernate();

        List<IScenarioProvider> providers = new ArrayList<IScenarioProvider>();
        providers.add(new AddingCatScenarioProvider(entityManagerFactory));
        providers.add(new AddingFriendsToCatProvider(entityManagerFactory));
        providers.add(new AddingOwnerScenarioProvider(entityManagerFactory));

        var scenarioRunner = new ScenarioRunner(providers);
        while (true) {
            scenarioRunner.run();
        }
    }
}
