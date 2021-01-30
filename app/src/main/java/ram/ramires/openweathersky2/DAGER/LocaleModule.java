package ram.ramires.openweathersky2.DAGER;

import java.util.Locale;

import dagger.Module;
import dagger.Provides;

@Module
public class LocaleModule {
    private Locale locale;

    public LocaleModule (Locale locale){
        this.locale=locale;
    }
    @Provides
    public String locale(){
        return locale.getLanguage();
    }
}
