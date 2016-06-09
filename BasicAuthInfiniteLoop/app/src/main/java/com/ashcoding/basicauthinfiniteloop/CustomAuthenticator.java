package com.ashcoding.basicauthinfiniteloop;

import android.util.Log;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

/**
 * Created by msamah on 9/6/16.
 */
public class CustomAuthenticator extends Authenticator {
    String domain;
    String user;
    String password;

    public CustomAuthenticator(String user, String password) {
        super();
        this.domain = null;
        this.user = user;
        this.password = password;
    }

    public CustomAuthenticator(String domain, String user, String password) {
        super();
        this.domain = domain;
        this.user = user;
        this.password = password;
    }

    @Override
    public PasswordAuthentication getPasswordAuthentication() {
        if (domain != null && !domain.isEmpty()) {
            user = domain + "\\" + user;
        }
        Log.d(MainActivity.TAG,"U:"+user+" P:"+password);
        if (password != null) {
            return new PasswordAuthentication(user, password.toCharArray());
        }
        return new PasswordAuthentication(user, null);
    }
}
