package ram.ramires.openweathersky2.DAGER;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
public class ContextModule {
    Context context;

    public ContextModule(Context context){
        this.context=context;
    }
    @Provides
    public Context context(){
        return context.getApplicationContext();
    }
}
