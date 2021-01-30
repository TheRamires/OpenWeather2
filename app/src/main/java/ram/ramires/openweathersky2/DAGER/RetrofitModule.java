package ram.ramires.openweathersky2.DAGER;

import dagger.Module;
import dagger.Provides;
import ram.ramires.openweathersky2.Model.Geopoint;
import ram.ramires.openweathersky2.Model.Model;
import ram.ramires.openweathersky2.Model.OpenWeatherApi;
import ram.ramires.openweathersky2.Model.Storage;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = {GeoModule.class, LocaleModule.class})
public class RetrofitModule {

    @Provides
    public Model model(
            Retrofit retrofit,
            Storage storage,
            Geopoint geopoint,
            String lang){
        return new Model(retrofit, storage, geopoint, lang);
    }

    @Provides
    public Storage storage(){
        return new Storage();
    }

    @Provides
    public Retrofit retrofit(){
        return new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    public OpenWeatherApi openWeatherApi(Retrofit retrofit){
        return retrofit.create(OpenWeatherApi.class);
    }
}
