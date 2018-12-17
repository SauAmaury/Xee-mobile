package cnam.myapplication;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xee.sdk.api.model.Vehicle;

import java.util.List;

import cnam.myapplication.Classes.ApiService;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class VehicleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle);
        this.getCars();
    }

    @SuppressLint("CheckResult")
    public void getCars() {
        ApiService.getInstance().getXapi().getUserVehicles()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<List<Vehicle>>() {
                    @Override
                    public void accept(List<Vehicle> vehicles) throws Exception {
                        VehicleActivity.this.addCarToUi(vehicles);
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("FAIL", throwable.getMessage());

                    }
                });
    }

    public void addCarToUi(List<Vehicle> v)
    {
        LinearLayout ll = (LinearLayout)findViewById(R.id.content);

        for(int i=0;i<v.size();i++) {
            LinearLayout lv = new LinearLayout(this);
            lv.setOrientation(LinearLayout.VERTICAL);
            LinearLayout lh = new LinearLayout(this);
            lh.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout lh2 = new LinearLayout(this);
            lh2.setOrientation(LinearLayout.HORIZONTAL);

            TextView txlabel = new TextView(this);
            txlabel.setText("Marque : ");
            TextView txcontent = new TextView(this);
            txcontent.setText(v.get(i).getBrand());

            lh.addView(txlabel);lh.addView(txcontent);
            lv.addView(lh);

            TextView txlabel2 = new TextView(this);
            txlabel2.setText("Modèle : ");
            TextView txcontent2 = new TextView(this);
            txcontent2.setText(v.get(i).getModel());

            lh2.addView(txlabel2);lh2.addView(txcontent2);
            lv.addView(lh2);

            ll.addView(lv);
        }



    }

}
