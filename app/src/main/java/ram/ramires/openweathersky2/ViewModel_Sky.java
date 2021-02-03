package ram.ramires.openweathersky2;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import javax.inject.Inject;

import ram.ramires.openweathersky2.All_Adapters.GeoJCoder;
import ram.ramires.openweathersky2.Model.Model;
import ram.ramires.openweathersky2.pojo.curent.WeathersALL;
import ram.ramires.openweathersky2.pojo.daily.Hourly;
import ram.ramires.openweathersky2.pojo.daily.WeatherALL_Daily;

import static ram.ramires.openweathersky2.FragmentChart.LOG2;

public class ViewModel_Sky extends AndroidViewModel {
    @Inject
    public Model model;
    @Inject
    public GeoJCoder geoJCoder;
    public ObservableField<WeathersALL> weatherCurent=new ObservableField<>();
    public MutableLiveData<WeatherALL_Daily> dailyLiveData=new MutableLiveData<>();
    public MutableLiveData<List<Hourly>> hourlyLiveData=new MutableLiveData<>();
    public ObservableField<Boolean> progressbarObservable=new ObservableField<>();
    public ObservableField<Boolean> visibility=new ObservableField<>(false);
    private double lat;
    private double lon;

    public ViewModel_Sky(@NonNull Application application) {
        super(application);

        Log.d(LOG2, "Visibility -> "+visibility.get());

        App.getGeoComponent().injectsViewModel(this);
        model.setArguments(weatherCurent,dailyLiveData,progressbarObservable,visibility, hourlyLiveData);
        storageDb();
    }
    private void storageDb()  {
        model.storageData();
    }

    public void startApp(){
        model.geoLocation();
    }

    public void getWether(String city){
        lat=geoJCoder.getLat(city);
        lon=geoJCoder.getLon(city);
        model.getWether(lat, lon, city);
    }
}
