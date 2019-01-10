package cnam.myapplication;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.xee.sdk.api.XeeApi;
import com.xee.sdk.api.model.Location;
import com.xee.sdk.api.model.Vehicle;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cnam.myapplication.Classes.ApiService;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LocationActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        this.getVehicleLocation();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public void getVehicleLocation(){
        Calendar cal = Calendar.getInstance();
        cal.set(2017,10,15);
        Date dd = cal.getTime();
        cal.set(2017,11,31);
        Date df = cal.getTime();

        for(int i=0;i<ApiService.getInstance().getUserVehicleId().size();i++) {
            ApiService.getInstance().getXapi().getVehicleLocations(ApiService.getUserVehicleId().get(i))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new io.reactivex.functions.Consumer<List<Location>>() {
                        @Override
                        public void accept(List<Location> locations) throws Exception {
                                Log.i("LOCATION",String.valueOf(locations.size()));
                        }
                    }, new io.reactivex.functions.Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            Log.e("FAIL", throwable.getMessage());

                        }
                    });
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng voiture = new LatLng(49.258329, 4.031696000000011);
        mMap.addMarker(new MarkerOptions().position(voiture).title("Voiture 1"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(voiture));
    }
}
