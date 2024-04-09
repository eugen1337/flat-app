package back.app;

import back.infrustructure.storage.IDataBase;

public interface IDBUsing {
    void useDB(IDataBase db);
}
