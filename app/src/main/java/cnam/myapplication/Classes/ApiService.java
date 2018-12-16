package cnam.myapplication.Classes;

import android.support.v7.app.AppCompatActivity;

import com.xee.sdk.api.XeeApi;
import com.xee.sdk.core.auth.OAuth2Client;
import com.xee.sdk.core.auth.XeeAuth;
import com.xee.sdk.core.common.XeeEnv;

import java.util.Arrays;

public final class ApiService {

    private static OAuth2Client oac;
    private static XeeEnv xenv;
    private static XeeAuth xeau;
    private static XeeApi xapi;
    private static ApiService instance = null;

    private final static boolean log = true;

    private ApiService() {
        ApiService.oac = new OAuth2Client.Builder()
                .clientId("b3707f5bd3ab8bc767fc7f9982acac85")
                .clientSecret("b0aeab4367810e479a4166c1f6668ccffcb1b0a492d8eb92a28699516c2f3751")
                .redirectUri("http://localhost")
                .scopes(Arrays.asList("account.read"))
                .build();
    }

    public static void initEnv(AppCompatActivity ap) {
        xenv = new XeeEnv(ap, oac, "api.xee.com/v4");
        xapi = new XeeApi(xenv, ApiService.log);
        xeau = new XeeAuth(xenv, ApiService.log);
    }

    public static void updateEnv(AppCompatActivity ap) {
        xenv = new XeeEnv(ap, oac, "api.xee.com/v4");
    }

    public static XeeEnv getXenv() {
        return xenv;
    }

    public static XeeAuth getXeau() {
        return xeau;
    }

    public static XeeApi getXapi() {
        return xapi;
    }

    public static ApiService getInstance() {
        if (instance == null) {
            instance = new ApiService();
        }
        return instance;
    }
}
