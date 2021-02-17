package ram.ramires.openweathersky2.Model;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ram.ramires.openweathersky2.App;
import ram.ramires.openweathersky2.Room.AppDatabase;
import ram.ramires.openweathersky2.Room.DaoWeather;
import ram.ramires.openweathersky2.pojo.curent.WeathersALL;
import ram.ramires.openweathersky2.pojo.daily.WeatherALL_Daily;

public class Storage {
    private String city;
    int indificate;
    private AppDatabase db= App.getInstance().getDatabase();
    public DaoWeather daoWeather=db.daoWeather();
    private WeathersALL bd_Curent;
    private WeatherALL_Daily bd_daily;
    long time;

    public void insert_Db_curent (WeathersALL response){
        if (response==null){
            return;
        }
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                city = response.getName();

                time = System.currentTimeMillis();
                response.setTime(time);

                indificate = daoWeather.getIndificate(city);
                if (indificate != 0) {
                    response.setindificate(indificate);
                    daoWeather.udate(response);
                } else {
                    daoWeather.insert(response);
                }
                indificate=0;
            }
        });
        thread.start();
    }

    public void insert_Db_daily (WeatherALL_Daily response){
        if (response==null){
            return;
        }
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {

                //daily---------------------------------------//
                response.setCity(city);

                indificate=daoWeather.getIndificate_Daily(city);
                if(indificate!=0){
                    response.setindificate(indificate);
                    daoWeather.udate_Daily(response);
                }else{
                    daoWeather.insert_Daily(response);}

                //обнуление переменных
                city="";
                indificate=0;
            }
        });
        thread.start();
    }

    public void query_Db_Curent(String city, ObservableField<WeathersALL> weatherCurent){
        daoWeather.getByCity(city)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(weathersALL -> {weatherCurent.set(weathersALL);},
                        throwable -> {return;});
    }
    public void query_Db_Daily(String city, MutableLiveData<WeatherALL_Daily> dailyLiveData){
        daoWeather.getByCity_Daily(city)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(weatherALL_daily -> {dailyLiveData.setValue(weatherALL_daily);},
                        throwable -> {return;});
    }
    public WeathersALL query_last_dt() throws InterruptedException {
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                bd_Curent=daoWeather.getByDate();
            }
        });
        thread.start();
        thread.join(200);
        return bd_Curent;
    }
}
