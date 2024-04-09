package back.infrustructure;

import back.app.IApp;
import back.app.IDBUsing;
import back.app.ITMUsing;
import back.infrustructure.qualifiers.Built;
import back.infrustructure.qualifiers.Product;
import back.infrustructure.storage.IDataBase;
import back.infrustructure.tokenManager.ITokenManager;
import jakarta.enterprise.inject.Default;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;

public class Builder {
    @Inject
    @Default
    private IApp app;

    @Inject
    @Product
    private IDataBase db;

    @Inject
    @Default
    private ITokenManager tm;

    @Produces
    @Built
    public IApp buildApp() {
        ((IDBUsing) app).useDB(db);
        ((ITMUsing) app).useTM(tm);

        return app;
    }
}