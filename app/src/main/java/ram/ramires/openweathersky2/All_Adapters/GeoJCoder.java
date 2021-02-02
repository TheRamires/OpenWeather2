package ram.ramires.openweathersky2.All_Adapters;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static ram.ramires.openweathersky2.MainActivity.LOG;

/* GeoCoder
в андроид эмуляторе не работате, только на телефоне проверяется*/
public class GeoJCoder {
    private Context mcontext;
    private Geocoder gcd;
    private String city;
    private List<Address> addresses;

    public GeoJCoder(Context mcontext){
        this.mcontext=mcontext;
        gcd= new Geocoder(mcontext, Locale.getDefault());
    }
    /*------- Geocoder getCoordinate ---------------------------------------------------- */

    public double getLat(String city){
        return getCoordinate(city)[0];
    }
    public double getLon(String city){
        return getCoordinate(city)[1];
    }

    public double[] getCoordinate(String cityName) {
        double [] lat_lon=new double[2];
        try {
            addresses = gcd.getFromLocationName(cityName, 1);
            if (addresses.size() > 0) {
                lat_lon[0]=addresses.get(0).getLatitude();
                lat_lon[1]=addresses.get(0).getLongitude();
                Log.d(LOG,""+lat_lon[0]+" "+lat_lon[1]);
                addresses.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lat_lon;
    }
    /*------- Geocoder getCityName ----------------------------------------------------- */

    public String getCityName(Location loc){
        Geocoder gcd = new Geocoder(mcontext, Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = gcd.getFromLocation(loc.getLatitude(),
                    loc.getLongitude(), 1);
            if (addresses.size() > 0) {
                city = addresses.get(0).getLocality();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        /*if (city!=null){
            Toast.makeText(mcontext,city, Toast.LENGTH_SHORT).show();
        }*/
        return city;
    }
}