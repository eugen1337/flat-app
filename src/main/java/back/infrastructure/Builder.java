package back.infrastructure;

import back.app.api.IApp;
import back.app.api.IDBUsing;
import back.app.api.ITMUsing;
import back.app.api.ITransporterAssign;
import back.infrastructure.out.storage.IDataBase;
import back.infrastructure.qualifiers.Built;
import back.infrastructure.qualifiers.Product;
import back.infrastructure.tokenManager.ITokenManager;
import back.infrastructure.websocket.ITransporter;
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

    @Inject
    @Default
    private ITransporter transporter;

    @Produces
    @Built
    public IApp buildApp() {
        ((IDBUsing) app).useDB(db);
        ((ITMUsing) app).useTM(tm);
        ((ITransporterAssign) app).useTransporter(transporter);

        return app;
    }
}