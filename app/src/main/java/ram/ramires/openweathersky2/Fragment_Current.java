package ram.ramires.openweathersky2;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ram.ramires.openweathersky2.databinding.FragmentCurrentBinding;

public class Fragment_Current extends Fragment {
    private ViewModel_Sky viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        viewModel = new ViewModelProvider(requireActivity()).get(ViewModel_Sky.class);

        FragmentCurrentBinding binding=FragmentCurrentBinding.inflate(inflater, container, false);
        View view=binding.getRoot();
        binding.setWeatherAll(viewModel);

        return view;
    }
}