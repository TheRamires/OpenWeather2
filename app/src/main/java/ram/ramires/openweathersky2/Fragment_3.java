package ram.ramires.openweathersky2;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ram.ramires.openweathersky2.databinding.Fragment3Binding;

public class Fragment_3 extends Fragment {
    private String cityName="";
    private ViewModel_Sky viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Fragment3Binding binding_3=Fragment3Binding.inflate(inflater,container,false);
        viewModel = new ViewModelProvider(requireActivity()).get(ViewModel_Sky.class);

        binding_3.setWeatherReciver(this);
        binding_3.setWeatherAll(viewModel);

        return binding_3.getRoot();
    }
    /*
        s: CharSequence!,
        start: Int,
        count: Int,
        after: Int*/
    //Edit Text ввод города пользователем
    public void getWeatherByCityName(CharSequence s,int start, int count, int after){
        cityName=""+s.toString();
        Log.d("myLog", "Edtit TExt " + s+"; after = "+after);
        if (cityName.length()>3) {
            //viewModel.getWether(cityName);
        }
    }
    public void onClick (View view){
        viewModel.getWether(cityName);
        Log.d("myLog", "onClick");
    }
}