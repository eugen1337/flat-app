package back.infrustructure.storage;

import back.infrustructure.qualifiers.Product;
import jakarta.enterprise.inject.Produces;

public class PostgreFactory {
    private static IDataBase instance = null;

    @Produces
    @Product
    public static IDataBase createPostgreStorage() {
        if (instance == null) {
            instance = new PostgreDB();
        }
        return instance;
    }
}
