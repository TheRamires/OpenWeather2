package ram.ramires.openweathersky2.ViewPager_Instruments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import ram.ramires.openweathersky2.Fragment_Current;
import ram.ramires.openweathersky2.Fragment_Daily;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new Fragment_Current();
            case 1:
                return new Fragment_Daily();

        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}