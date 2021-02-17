package ram.ramires.openweathersky2.Model;

import android.util.Log;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONObject;

import java.util.List;

import javax.inject.Inject;

import ram.ramires.openweathersky2.App;
import ram.ramires.openweathersky2.pojo.curent.WeathersALL;
import ram.ramires.openweathersky2.pojo.daily.Hourly;
import ram.ramires.openweathersky2.pojo.daily.WeatherALL_Daily;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

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
    public MutableLiveData<List<Hourly>> hourlyLiveData;
    public ObservableField<Boolean> visibilityRecycler;
    public ObservableField<Boolean> visibilityChart;
    private WeathersALL bd_Curent;
    private WeatherALL_Daily bd_daily;
    private int cheakTemp;
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
                              ObservableField<Boolean> progressbarObservable,
                              ObservableField<Boolean> visibilityRecycler,
                              ObservableField<Boolean> visibilityChart,
                              MutableLiveData<List<Hourly>> hourlyLiveData){
        this.weatherCurent=weatherCurent;
        this.dailyLiveData=dailyLiveData;
        this.progressbarObservable=progressbarObservable;
        this.visibilityRecycler=visibilityRecycler;
        this.visibilityChart=visibilityChart;
        this.hourlyLiveData=hourlyLiveData;
    }

    public void getWether(double lat, double lon, String city){
        progressbarObservable.set(true);
        Call<WeathersALL> weatherByCity=openWeatherApi.weathersByCity(city, lang);
        weatherByCity.enqueue(new Callbacker(city));

        if (lat==0.0 && lon==0.0){
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
                Log.d("myLog",response.body().getWeather().toString());
                visibilityRecycler.set(false);
                visibilityChart.set(false);
                cheakTemp= (int) response.body().main.temp;

                weatherCurent.set(response.body());
                storage.insert_Db_curent(response.body());
            } else if (response.code() == 404) {
            } else {
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
            int cheakTemp2=response.body().getHourly().get(0).getTemp();
            if (getCheak(cheakTemp, cheakTemp2)>2){
                return;
            }
            visibilityRecycler.set(true);

            if (response.isSuccessful()) {
                Log.d("myLog", response.body().getHourly().toString());
                dailyLiveData.setValue(response.body());
                storage.insert_Db_daily(response.body());

                hourlyLiveData.setValue(response.body().getHourly());


            } else if (response.code() == 404) {
                return;
            } else {
            }
            progressbarObservable.set(false);
        }
        @Override
        public void onFailure(Call call, Throwable t) {
        }
    }
    private int getCheak(int a, int b){
        return Math.abs(a-b);
    }
}
