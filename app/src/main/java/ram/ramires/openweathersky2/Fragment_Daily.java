package ram.ramires.openweathersky2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ram.ramires.openweathersky2.All_Adapters.Recycler_View_Adapter;
import ram.ramires.openweathersky2.databinding.FragmentDailyBinding;
import ram.ramires.openweathersky2.pojo.daily.WeatherALL_Daily;

public class Fragment_Daily extends Fragment {
    private ViewModel_Sky viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        viewModel=new ViewModelProvider(requireActivity()).get(ViewModel_Sky.class);
        FragmentDailyBinding binding_2=FragmentDailyBinding.inflate(inflater,container,false);
        View view = binding_2.getRoot();
        binding_2.setWeatherAll(viewModel);

        //-------------------------------------Recycler View------------------------------
        RecyclerView recyclerView=view.findViewById(R.id.recycler_view);

        viewModel.dailyLiveData.observe(getViewLifecycleOwner(), new Observer<WeatherALL_Daily>() {
            @Override
            public void onChanged(WeatherALL_Daily weatherALL_daily) {
                if (weatherALL_daily==null){
                    return;
                }
                //Log.d(MainActivity.LOG, weatherALL_daily.daily.size()+"");
                RecyclerView.Adapter adapter=new Recycler_View_Adapter(weatherALL_daily.daily);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });
        return view;
    }
}