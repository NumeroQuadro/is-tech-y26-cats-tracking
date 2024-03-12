package Migrations;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface Migratable {
    void migrate() throws IOException;
}
