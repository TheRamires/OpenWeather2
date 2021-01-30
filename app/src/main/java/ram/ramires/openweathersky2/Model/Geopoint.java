package ram.ramires.openweathersky2.Model;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import javax.inject.Inject;

import ram.ramires.openweathersky2.All_Adapters.GeoJCoder;
import ram.ramires.openweathersky2.App;
import ram.ramires.openweathersky2.R;

import static ram.ramires.openweathersky2.MainActivity.LOG;

public class Geopoint {
    private LocationManager locationManager;
    @Inject
    public Context context;
    @Inject
    public GeoJCoder geoJCoder;
    private String context2 = Context.LOCATION_SERVICE;
    private double lat=0;
    private double lon=0;
    private Model model;
    private String city;

    public void setModel(Model model) { this.model=model; }

    public Geopoint(Context mcontext, GeoJCoder geoJCoder) {
        App.getGeoComponent().injectsGeoPoint(this);
    }

    public void daterminate() {
        locationManager = (LocationManager) context.getSystemService(context2);
        LocationListener locationListener = new MyLocationListener();

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000*10, 100000, locationListener);
    }

    class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location loc) {
            lat=loc.getLatitude();
            lon=loc.getLongitude();
            city=geoJCoder.getCityName(loc);
            model.getWether(lat,lon,city);
            if (city!=null) {
                Toast.makeText(context, city, Toast.LENGTH_SHORT).show();
                Log.d(LOG, "city "+city);
            }
        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.d(LOG,"Geopoint Disable");
            context.startActivity(new Intent(
                    android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            Toast.makeText(context, R.string.no_internet, Toast.LENGTH_LONG).show();
        }

        @Override
        public void onProviderEnabled(String provider) {}

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    }
}