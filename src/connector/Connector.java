package connector;

import interact.EstateDatabaseManager;
import types.AccountMode;

public class Connector {
    protected EstateDatabaseManager manager;
    protected String lasterr;
    public Connector(){
        this.manager = EstateDatabaseManager.getInstance();
    }

    public String getLastError() {
        return lasterr;
    }

    protected boolean checkMode(AccountMode mode) {
        return this.manager.getCurrentMode() == mode;
    }
}
