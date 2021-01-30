package ram.ramires.openweathersky2.DAGER;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import ram.ramires.openweathersky2.All_Adapters.GeoJCoder;
import ram.ramires.openweathersky2.Model.Geopoint;

@Module(includes = ContextModule.class)
public class GeoModule {
    @Provides
    public Geopoint geopoint(Context context, GeoJCoder geoJCoder ){
        return new Geopoint(context, geoJCoder);
    }
    @Provides
    public GeoJCoder geoJCoder(Context context){
        return new GeoJCoder(context);
    }
}
