package handler;

import types.AccountMode;

public interface AuthenticationHandler {
    public void HandleLogin(AccountMode mode, String uid, String pwd);
}
