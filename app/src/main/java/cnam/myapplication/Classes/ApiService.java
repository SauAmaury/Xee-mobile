package cnam.myapplication.Classes;

import android.support.v7.app.AppCompatActivity;

import com.xee.sdk.api.XeeApi;
import com.xee.sdk.core.auth.OAuth2Client;
import com.xee.sdk.core.auth.XeeAuth;
import com.xee.sdk.core.common.XeeEnv;

import java.util.Arrays;
import java.util.List;

public final class ApiService {

    private static OAuth2Client oac;
    private static XeeEnv xenv;
    private static XeeAuth xeau;
    private static XeeApi xapi;
    private static ApiService instance = null;

    private final static boolean log = true;
    private final List<String> scopes = Arrays.asList("vehicles.read", "vehicles.management", "vehicles.signals.read", "vehicles.locations.read", "vehicles.accelerometers.read", "vehicles.privacies.read", "vehicles.privacies.management", "vehicles.trips.read", "vehicles.loans.read", "vehicles.loans.management", "account.read", "vehicles.devices-data.read", "vehicles.events.read", "vehicles.gyroscopes.read", "fleets.read");

    private ApiService() {
        ApiService.oac = new OAuth2Client.Builder()
                .clientId("b3707f5bd3ab8bc767fc7f9982acac85")
                .clientSecret("b0aeab4367810e479a4166c1f6668ccffcb1b0a492d8eb92a28699516c2f3751")
                .redirectUri("http://localhost")
                .scopes(this.scopes)
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
