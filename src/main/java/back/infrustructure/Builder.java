package back.infrustructure;

import back.infrustructure.storage.IDataBase;
import back.infrustructure.storage.PostgreDB;

public class Builder {
    private IDataBase dataBase = new PostgreDB();

}