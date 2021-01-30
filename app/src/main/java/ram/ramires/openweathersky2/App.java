package ram.ramires.openweathersky2;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

import java.util.Locale;

import ram.ramires.openweathersky2.DAGER.ContextModule;
import ram.ramires.openweathersky2.DAGER.DaggerGeoComponent;
import ram.ramires.openweathersky2.DAGER.GeoComponent;
import ram.ramires.openweathersky2.DAGER.LocaleModule;
import ram.ramires.openweathersky2.Room.AppDatabase;

public class App extends Application {
    public static App instance;
    private AppDatabase database;
    private static GeoComponent geoComponent;
    private static Context context;
    private static Locale locale;

    @Override
    public void onCreate() {
        super.onCreate();
        context=this;
        locale=getResources().getConfiguration().locale;

        instance = this;
        database = Room.databaseBuilder(this, AppDatabase.class, "database")
                .fallbackToDestructiveMigration()
                .build();
        geoComponent= DaggerGeoComponent.builder()
                .contextModule(new ContextModule(this))
                .localeModule(new LocaleModule(locale))
                .build();
    }

    public static App getInstance() {
        return instance;
    }

    public AppDatabase getDatabase() {
        return database;
    }

    public static GeoComponent getGeoComponent(){
        return geoComponent;
    }

    public static String getStrings (int resId){
        return context.getString(resId);
    }
    public static Locale getLocale(){
        return locale;
    }
}