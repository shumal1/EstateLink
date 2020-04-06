import handler.AuthenticationHandler;
import interact.EstateTransactionHandler;
import types.AccountMode;
import ui.EstateAuthenticator;
import ui.EstateUI;

public class EstateLink implements AuthenticationHandler {
    private boolean authenticated;
    private EstateTransactionHandler transactionHandler;
    private EstateAuthenticator au;
    private EstateUI ui;
    private AccountMode mode;
    public static void main(String[] args){
        // Performs logon and stuff
            // Change mode and get a new event handler upon changing account type.
        // EstateDatabaseManager manager = EstateDatabaseManager.getInstance();
        // First display a login panel, have three account types and allows user to input username and password
        // After login display the main panel which have set of buttons for different datasets
            // listing
            // resources
        // Each of those page should also have associated ui's that allows user to perform search immediately using
        // retrieved value for other datasets.
        EstateLink link = new EstateLink();
        link.start();
    }

    public void start(){
        transactionHandler = new EstateTransactionHandler();
        authenticate();
    }

    public void authenticate() {
        au = new EstateAuthenticator(this);
        au.authenticate();
    }

    public boolean HandleLogin(AccountMode mode, String uid, String pwd, String db_uid, String db_pwd) {
        if (transactionHandler.dbLogon(db_uid, db_pwd)) {
            authenticated = transactionHandler.changeMode(mode, uid, pwd);
            if (authenticated) {
                // show main menu
                this.mode = mode;
                au.dispose();
                interact();
                return true;
            }
        }
        return false;
    }

    public void interact(){
        // handler.getCurrentMode()
        ui = new EstateUI(transactionHandler, mode);
        ui.showUI();
    }
}
