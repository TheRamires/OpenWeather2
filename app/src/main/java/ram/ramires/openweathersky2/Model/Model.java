package ram.ramires.openweathersky2.Model;

import android.util.Log;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import javax.inject.Inject;

import ram.ramires.openweathersky2.App;
import ram.ramires.openweathersky2.pojo.curent.WeathersALL;
import ram.ramires.openweathersky2.pojo.daily.WeatherALL_Daily;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static ram.ramires.openweathersky2.MainActivity.LOG;

public class Model {
    @Inject
    public  OpenWeatherApi openWeatherApi;
    @Inject
    public Storage storage;
    @Inject
    public Geopoint geopoint;
    public ObservableField<WeathersALL> weatherCurent;
    public MutableLiveData<WeatherALL_Daily> dailyLiveData;
    public ObservableField<Boolean> progressbarObservable;
    private WeathersALL bd_Curent;
    private WeatherALL_Daily bd_daily;
    @Inject
    public String lang;

    public Model(Retrofit retrofit,
                 Storage storage,
                 Geopoint geopoint,
                 String lang) {
        App.getGeoComponent().injectsModel(this);
    }

    public void setArguments( ObservableField<WeathersALL> weatherCurent,
                              MutableLiveData<WeatherALL_Daily> dailyLiveData,
                              ObservableField<Boolean> progressbarObservable){
        this.weatherCurent=weatherCurent;
        this.dailyLiveData=dailyLiveData;
        this.progressbarObservable=progressbarObservable;
    }

    public void getWether(double lat, double lon, String city){
        progressbarObservable.set(true);
        Call<WeathersALL> weatherByCity=openWeatherApi.weathersByCity(city, lang);
        weatherByCity.enqueue(new Callbacker(city));

        if (lat==0.0 && lon==0.0){
            Log.d(LOG, "lat "+lat+" lon "+lon+" RETURN");
            return;
        }


        Call<WeatherALL_Daily> daily = openWeatherApi.dailyByCoordinates(lat, lon, lang);
        daily.enqueue(new Callbacker_Daily(city));
    }
    public void geoLocation(){
        geopoint.setModel(this);
        geopoint.daterminate();
    }
    public void storageData() {
        try {
            weatherCurent.set(storage.query_last_dt());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
//-----------------------------The Calbackers!-------------------------------------------//
//---------------------------------------------------------------------------------------//
//------------------------------Calbaker Curent----------------//

    class Callbacker implements Callback<WeathersALL> {
        private String city;
        public Callbacker(String city){
            this.city=city;
        }

        @Override
        public void onResponse(Call<WeathersALL> call, final Response<WeathersALL> response) {
            if (response.isSuccessful()) {

                weatherCurent.set(response.body());
                storage.insert_Db_curent(response.body());
            } else if (response.code() == 404) {
                Log.d("myLog", "Calbaker Curent response code:" + response.code()
                        + " Uncorrect cityname! :(");
            } else {
                Log.d("myLog", "Calbaker Curent response code " + response.code());
            }
            progressbarObservable.set(false);
        }

        @Override
        public void onFailure(Call call, Throwable t) {
            storage.query_Db_Curent(city, weatherCurent);
            storage.query_Db_Daily(city, dailyLiveData);
            progressbarObservable.set(false);
        }
    }
//------------------------------Calbaker_Daily-----------------//

    class Callbacker_Daily implements Callback<WeatherALL_Daily> {
        private String city;
        public Callbacker_Daily(String city){
            this.city=city;
        }

        @Override
        public void onResponse(Call<WeatherALL_Daily> call, final Response<WeatherALL_Daily> response) {
            if (response.isSuccessful()) {

                dailyLiveData.setValue(response.body());
                storage.insert_Db_daily(response.body());
            } else if (response.code() == 404) {
                Log.d("myLog", "Calbaker_Daily response code:" + response.code() + " Uncorrect cityname! :(");
                return;
            } else {
                Log.d("myLog", "Calbaker_Daily response code " + response.code());
            }
            progressbarObservable.set(false);
        }
        @Override
        public void onFailure(Call call, Throwable t) {
        }
    }
}
