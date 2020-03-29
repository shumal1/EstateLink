package interact;

import handler.AuthenticationHandler;
import types.AccountMode;
import ui.EstateAuthenticator;
import ui.EstateUI;

public class EstateLink implements AuthenticationHandler {
    private boolean authenticated;
    private EstateTransactionHandler transactionHandler;
    private EstateAuthenticator au;
    private EstateUI ui;
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
            au.dispose();
            interact(uid);
        } else {
            // login failed, try again
        }
    }

    public void interact(String uid){
        // handler.getCurrentMode()
        ui = new EstateUI(transactionHandler, AccountMode.ADMIN , uid);
        ui.showUI();
    }
}
