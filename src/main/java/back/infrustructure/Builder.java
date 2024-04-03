package back.infrustructure;

import back.app.IApp;
import back.infrustructure.qualifiers.Built;
import back.infrustructure.qualifiers.Product;
import back.infrustructure.storage.IDataBase;
import back.infrustructure.storage.PostgreDB;
import jakarta.enterprise.inject.Default;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;

public class Builder {
    private IDataBase dataBase = new PostgreDB();

    // @Inject
    // @Default
    // private IApp app;

    // @Inject
    // @Product
    // private IDataBase db;

    // @Inject
    // @Default
    // private ITokenManager tm;


    // @Produces
    // @Built
    // public IApp buildApp() {
    //     ((IDataBaseUsing) app).useDB(db);
    //     ((ITokenManagerUsing) app).useTM(tm);

    //     return app;
    // }
}