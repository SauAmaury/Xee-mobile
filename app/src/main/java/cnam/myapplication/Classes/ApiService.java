package cnam.myapplication.Classes;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.xee.sdk.api.XeeApi;
import com.xee.sdk.api.model.Vehicle;
import com.xee.sdk.core.auth.OAuth2Client;
import com.xee.sdk.core.auth.XeeAuth;
import com.xee.sdk.core.common.XeeEnv;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public final class ApiService {

    private static OAuth2Client oac;
    private static XeeEnv xenv;
    private static XeeAuth xeau;
    private static XeeApi xapi;
    private static ApiService instance = null;

    private final static boolean log = true;
    private final List<String> scopes = Arrays.asList("vehicles.read", "vehicles.management", "vehicles.signals.read", "vehicles.locations.read", "vehicles.accelerometers.read", "vehicles.privacies.read", "vehicles.privacies.management", "vehicles.trips.read", "vehicles.loans.read", "vehicles.loans.management", "account.read", "vehicles.devices-data.read", "vehicles.events.read", "vehicles.gyroscopes.read", "fleets.read");
    private static List<String> userVehicleId = new ArrayList<String>();

    /**
     * Constructeur privé (principe du Singleton)
     */
    private ApiService() {
        ApiService.oac = new OAuth2Client.Builder()
                .clientId("b3707f5bd3ab8bc767fc7f9982acac85")
                .clientSecret("b0aeab4367810e479a4166c1f6668ccffcb1b0a492d8eb92a28699516c2f3751")
                .redirectUri("http://localhost")
                .scopes(this.scopes)
                .build();
    }

    /**
     * Fonction qui permet d'initialiser les objets pour le bon fonctionnement
     * de l'application
     * @param ap
     */
    public static void initEnv(AppCompatActivity ap) {
        xenv = new XeeEnv(ap, oac, "api.xee.com/v4");
        xapi = new XeeApi(xenv, ApiService.log);
        xeau = new XeeAuth(xenv, ApiService.log);
    }

    /**
     * Fonction qui permet de changer l'environnement si l'activité change
     * @param ap
     */
    public static void updateEnv(AppCompatActivity ap) {
        xenv = new XeeEnv(ap, oac, "api.xee.com/v4");
    }

    /**
     * Fonction qui permet d'obtenir les id des véhicules de l'utilisateur
     */
    public static void getVehiclesId() {
        xapi.getUserVehicles()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<List<Vehicle>>() {
                    @Override
                    public void accept(List<Vehicle> vehicles) throws Exception {
                        for(int i=0;i<vehicles.size();i++) {
                            userVehicleId.add(vehicles.get(i).getId());
                        }
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("FAIL", throwable.getMessage());

                    }
                });
    }

    /**
     * Getter
     */

    public static XeeEnv getXenv() {
        return xenv;
    }

    public static XeeAuth getXeau() {
        return xeau;
    }

    public static XeeApi getXapi() {
        return xapi;
    }

    public static List<String> getUserVehicleId() {return userVehicleId;}


    public static ApiService getInstance() {
        if (instance == null) {
            instance = new ApiService();
        }
        return instance;
    }
}
