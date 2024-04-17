package back.infrastructure.out.storage;

import back.infrastructure.qualifiers.Product;
import jakarta.enterprise.inject.Produces;

public class PostgreFactory {
    private static IDataBase instance = null;

    @Produces
    @Product
    public static IDataBase createPostgreStorage() {
        if (instance == null) {
            instance = new DataBase();
        }
        return instance;
    }
}
