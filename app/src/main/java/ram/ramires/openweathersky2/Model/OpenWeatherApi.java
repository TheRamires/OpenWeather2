package ram.ramires.openweathersky2.Model;

import ram.ramires.openweathersky2.pojo.curent.WeathersALL;
import ram.ramires.openweathersky2.pojo.daily.WeatherALL_Daily;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherApi {
    @GET("weather?&units=metric&appid=37d184338de4e1a807dd7600a4bff3b9")
    Call<WeathersALL> weathersByCity (@Query("q") String city, @Query("lang") String lang);

    @GET("weather?&units=metric&lang=ru&units=metric&appid=37d184338de4e1a807dd7600a4bff3b9")
    Call<WeathersALL> weathersByCoordinates (@Query("lat") double lat, @Query("lon") double lon, @Query("lang") String lang) ;

    @GET("onecall?exclude=current,minutely,hourly,alerts&units=metric&appid=37d184338de4e1a807dd7600a4bff3b9")
    Call<WeatherALL_Daily> dailyByCoordinates (@Query("lat") double lat, @Query("lon") double lon, @Query("lang") String lang);

}
