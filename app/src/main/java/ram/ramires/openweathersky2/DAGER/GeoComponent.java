package ram.ramires.openweathersky2.DAGER;

import dagger.Component;
import ram.ramires.openweathersky2.Model.Geopoint;
import ram.ramires.openweathersky2.Model.Model;
import ram.ramires.openweathersky2.ViewModel_Sky;

@Component(modules = {GeoModule.class, RetrofitModule.class})
public interface GeoComponent {

    void injectsViewModel(ViewModel_Sky viewModelSky);
    void injectsModel(Model model);
    void injectsGeoPoint(Geopoint geopoint);

}
