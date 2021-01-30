package ram.ramires.openweathersky2.All_Adapters;


import android.view.View;

import androidx.databinding.BindingAdapter;

public class VisibilityAdapter {
    @BindingAdapter("android:visibility")
    public static void BarVisibility (View view, Boolean value) {
        if (value==null){
            view.setVisibility(View.INVISIBLE);
        }else if (value){
            view.setVisibility(View.VISIBLE);
        }else view.setVisibility(View.INVISIBLE);
    }
}
