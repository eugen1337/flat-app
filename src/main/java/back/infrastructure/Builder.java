package back.infrastructure;

import back.app.api.IApp;
import back.app.api.IDBUsing;
import back.app.api.ITMUsing;
import back.infrastructure.out.storage.IDataBase;
import back.infrastructure.qualifiers.Built;
import back.infrastructure.qualifiers.Product;
import back.infrastructure.tokenManager.ITokenManager;
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