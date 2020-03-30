package handler;

import types.AccountMode;

public interface AuthenticationHandler {
    void HandleLogin(AccountMode mode, String uid, String pwd);
}
