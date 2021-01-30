package ram.ramires.openweathersky2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ram.ramires.openweathersky2.databinding.FragmentBarBinding;

public class Fragment_Bar extends Fragment {
    private ViewModel_Sky viewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity()).get(ViewModel_Sky.class);
        FragmentBarBinding fragmentBarBinding=FragmentBarBinding.inflate(inflater,container,false);
        fragmentBarBinding.setBar(viewModel);

        return fragmentBarBinding.getRoot();
    }
}