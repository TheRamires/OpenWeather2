package ram.ramires.openweathersky2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import ram.ramires.openweathersky2.ViewPager_Instruments.ViewPagerAdapter;
import ram.ramires.openweathersky2.ViewPager_Instruments.ZoomOutPageTransformer;

public class MainActivity extends FragmentActivity {
    private String [] titleList;

    public final static String LOG="myLog";
    public static final int REQUEST_CODE_PERMISSION_FINE_LOCATION =1 ;
    private View view;

    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private FragmentStateAdapter pagerAdapter;
    private Permission permission;

    private ViewModel_Sky viewModel;

    /*Permission methods */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permission.permissionsResult(requestCode,grantResults);
    }
    public void buttonBack(View view){
        permission.back();
    }
    /*---------------------------------------------------------------------------------*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view=(View)findViewById(R.id.frameLayout);
        titleList=getResources().getStringArray(R.array.title);

        //Клавиатура поверх
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        //------------------------------------View Model----------------------------------
        viewModel = new ViewModelProvider(this).get(ViewModel_Sky.class);

        //====================================Permisions==================================
        permission= new Permission(viewModel, this, this);
        /* ...and GeoPoint  */
        permission.requestPerm();

        //-------------------------------------View Pager---------------------------------

        // Instantiate a ViewPager2 and a PagerAdapter.
        tabLayout = findViewById(R.id.tabs);

        viewPager = findViewById(R.id.pager);
        viewPager.setPageTransformer(new ZoomOutPageTransformer());

        //viewPager.setPageTransformer(new ZoomOutPageTransformer());
        pagerAdapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);
        new TabLayoutMediator(tabLayout, viewPager,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        tab.setText(titleList[position]);
                    }
                }).attach();
    }
    //-------------------------------------View Pager methods---------------------------------
    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }
}