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

    public void HandleLogin(AccountMode mode, String uid, String pwd) {
        authenticated = transactionHandler.changeMode(mode, uid, pwd);
        au.displayResult(authenticated);
        if (authenticated) {
            // show main menu
            this.mode = mode;
            au.dispose();
            interact();
        } else {
            // login failed, try again
        }
    }

    public void interact(){
        // handler.getCurrentMode()
        ui = new EstateUI(transactionHandler, mode);
        ui.showUI();
    }
}
