package cnam.myapplication;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.widget.TextView;

import com.xee.sdk.api.XeeApi;
import com.xee.sdk.api.model.Signal;
import com.xee.sdk.api.model.Trip;
import com.xee.sdk.api.model.Vehicle;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cnam.myapplication.Classes.ApiService;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class StatActivity extends AppCompatActivity {
    /**
     *   Données affichées sur l'activitée
     *   Format d'affichage :
     *   NomVariable         =       Information
     *
     *   timeSpendData       =       Temps d'utilisation
     *   timeMeanTripData    =       Temps moyens des parcours
     *   kmMadeData          =       Nombre de kilomètre parcouru avec le boîtier
     *   speedMeanData       =       Vitesse moyenne
     * */
    private List<String> idList = new ArrayList<String>();

    private List<String> idTrip = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat);
        this.initStatActivity();
        // this.getCars();
        // this.getVehiclesId();


    }

    /**
     * Fonction d'initialisation des variables d'affichage (EN DEV)
     * Normalement en PROD c'est un appel de fonction pour récupérer ces données
     */
    public void initStatActivity(){
        TextView timeSpendData = (TextView) findViewById(R.id.timeSpendData);
        timeSpendData.setText("1 H 17 min 29 sec");

        TextView timeMeanTripData = (TextView) findViewById(R.id.timeMeanTripData);
        timeMeanTripData.setText("0 H 15 min 51 sec");

        TextView kmMadeData = (TextView) findViewById(R.id.kmMadeData);
        kmMadeData.setText("28,02 Km");

        TextView speedMeanData = (TextView) findViewById(R.id.speedMeanData);
        speedMeanData.setText("21,7 Km/H");
    }

    /**
     * Fonction pour obtenir l'identifiant de chaque voyage parcouru
     * @param ID : identifiant de la voiture
     */
    public void getVehicleTrip(String ID){
        ApiService.getInstance().getXapi().getVehicleTrips(ID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<List<Trip>>() {
                    @Override
                    public void accept(List<Trip> trips) throws Exception {
                        for(int i=0;i<trips.size();i++) {
                            Log.i("TRIP_INFO", "TRIP NUMBER");
                            Log.i("TRIP_INFO", trips.get(i).getId());
                            //StatActivity.this.idTrip.add(trips.get(i).getId());
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
     * Fonction pour obtenir les signaux de la voiture
     * @param ID : identifiant de la voiture
     */
    public void getVehicleSignal(String ID){
        Map<String, Object> optionalParametersSignals = new ArrayMap<>();
        optionalParametersSignals.put("signals", "LockSts");
        Calendar cal = Calendar.getInstance();
        cal.set(2017,10,15);
        Date dd = cal.getTime();
        cal.set(2017,11,31);
        Date df = cal.getTime();
        optionalParametersSignals.put("from", "2017-10-15T14:11:29+02:00");
        optionalParametersSignals.put("to", "2017-10-31T14:11:29+02:00");
        ApiService.getInstance().getXapi().getVehicleSignals(ID,optionalParametersSignals)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<List<Signal>>() {
                    @Override
                    public void accept(List<Signal> signals) throws Exception {
                        for(int i=0;i<signals.size();i++) {
                            Log.i("DEVICE_INFO", "TRIP NUMBER");
                            Log.i("DEVICE_INFO", signals.get(i).getName());
                            //StatActivity.this.idTrip.add(trips.get(i).getId());
                        }
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("FAIL", throwable.getMessage());

                    }
                });


    }

}
