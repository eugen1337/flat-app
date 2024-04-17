package back.app.api;

import back.infrastructure.out.storage.IDataBase;

public interface IDBUsing {
    void useDB(IDataBase db);
}
