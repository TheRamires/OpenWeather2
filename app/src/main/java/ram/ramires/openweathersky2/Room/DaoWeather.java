package ram.ramires.openweathersky2.Room;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import io.reactivex.Maybe;
import ram.ramires.openweathersky2.pojo.curent.WeathersALL;
import ram.ramires.openweathersky2.pojo.daily.WeatherALL_Daily;

@Dao
public interface DaoWeather {
    //------------------------------------WeatherAll--------------------------------------
    @Insert
    void insert (WeathersALL...weathersALLS);

    @Update
    void udate (WeathersALL weathersALL);

    @Query("SELECT indificate FROM weathersall WHERE oldCity=:newCity")
    int getIndificate (String newCity);

    @Query("SELECT * FROM weathersall WHERE oldCity=:newCity")
    Maybe<WeathersALL> getByCity (String newCity);

    @Query("SELECT * FROM weathersall ORDER BY time DESC LIMIT 1")
    WeathersALL getByDate ();

    //------------------------------------WeatherAll_Daily---------------------------------

    @Insert
    void insert_Daily (WeatherALL_Daily...weathersALLS_Daily);

    @Query("SELECT indificate FROM WeatherALL_Daily WHERE city=:newCity")
    int getIndificate_Daily (String newCity);

    @Query("SELECT * FROM WeatherALL_Daily WHERE city=:newCity")
    Maybe<WeatherALL_Daily> getByCity_Daily (String newCity);

    @Update
    void udate_Daily (WeatherALL_Daily weatherALL_daily);
}