import Migrations.InitialMigration;
import Models.Owner;
import OwnerTransactions.AddNewOwner;
import OwnerTransactions.FetchingOwners;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) throws IOException {
        System.out.println("Hello World");
        var addNewOwner = new AddNewOwner();
        var migration = new InitialMigration();
        migration.migrate();
        var denis = new Owner();
        var dimon = new Owner();
        denis.setName("Denis");
        dimon.setName("Dimon");
        addNewOwner.addOwner(denis);
        addNewOwner.addOwner(dimon);

        var fetchingOwners = new FetchingOwners();
        fetchingOwners.ListOwners();
        logger.info("NUMERO QUADRO");
    }
}
