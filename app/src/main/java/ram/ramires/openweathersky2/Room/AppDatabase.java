package ram.ramires.openweathersky2.Room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import ram.ramires.openweathersky2.pojo.curent.WeathersALL;
import ram.ramires.openweathersky2.pojo.daily.WeatherALL_Daily;

@Database(entities = {WeathersALL.class, WeatherALL_Daily.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DaoWeather daoWeather ();
}
