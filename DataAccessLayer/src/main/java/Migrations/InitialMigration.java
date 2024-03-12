package Migrations;

import org.flywaydb.core.Flyway;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class InitialMigration implements Migratable {
    private final MigrationConfigurationFilePaths migrationConfigurationFilePaths = new MigrationConfigurationFilePaths();
    public void migrate() throws IOException {
        System.out.println("Migrating...");
        Properties properties = new Properties();
        properties.load(new FileInputStream(migrationConfigurationFilePaths.FLYWAY_CONFIGURATIONS)); // DataAccessLayer\src\main\resources\db\migration\V0__create_user_table.sql
        Flyway flyway = Flyway.configure()
                .configuration(properties)
                .load();

        flyway.clean();
        flyway.migrate();
    }
}
