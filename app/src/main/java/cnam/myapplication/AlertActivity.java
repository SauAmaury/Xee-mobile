package cnam.myapplication;

import android.icu.util.Calendar;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.xee.sdk.api.XeeApi;
import com.xee.sdk.api.model.Signal;
import com.xee.sdk.api.model.Vehicle;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cnam.myapplication.Classes.ApiService;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AlertActivity extends AppCompatActivity {

    private List<String> idList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);
        this.getVehiclesId();
    }

    public void getVehiclesId() {
        ApiService.getInstance().getXapi().getUserVehicles()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<List<Vehicle>>() {
                    @Override
                    public void accept(List<Vehicle> vehicles) throws Exception {
                        for (int i = 0; i < vehicles.size(); i++) {
                            AlertActivity.this.idList.add(vehicles.get(i).getId());
                        }
                        AlertActivity.this.sortData();
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("FAIL", throwable.getMessage());

                    }
                });
    }

    public void getSignal(String id) {
        Map<String, Object> optionalParametersSignals = new ArrayMap<>();
        optionalParametersSignals.put("signals", "LockSts");
        Calendar cal = Calendar.getInstance();
        cal.set(2016, 1, 1);
        optionalParametersSignals.put("from", XeeApi.DATE_FORMATTER.format(cal.getTime()));
        optionalParametersSignals.put("to", XeeApi.DATE_FORMATTER.format(new Date()));

        ApiService.getInstance().getXapi().getVehicleSignals(id, optionalParametersSignals)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<List<Signal>>() {
                    @Override
                    public void accept(List<Signal> signals) throws Exception {
                        for (int i = 0; i < signals.size(); i++) {
                            Log.i("Signal", signals.get(i).getName());
                        }
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("FAIL", throwable.getMessage());

                    }
                });
    }

    public void sortData() {
        this.getSignal(this.idList.get(1));
    }
}
