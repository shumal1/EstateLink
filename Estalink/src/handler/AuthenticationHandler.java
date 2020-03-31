package handler;

import types.AccountMode;

public interface AuthenticationHandler {
    boolean HandleLogin(AccountMode mode, String uid, String pwd, String db_uid, String db_pwd);
}
