package ram.ramires.openweathersky2.All_Adapters;

import android.util.Log;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.squareup.picasso.Picasso;

import ram.ramires.openweathersky2.R;

public class ImageBindingAdapter {

    @BindingAdapter({"bind:imageUrl"})
    public static void setImageUrl(ImageView imageView, String url) {
        String urlll;
        if(url==null){
            urlll="";
        }else {
            urlll="http://openweathermap.org/img/wn/"+url+"@2x.png";
        }
        Log.d("myLog","icon code "+url);
        if (!urlll.equals("")) {
            Picasso
                    .with(imageView.getContext())
                    .load(urlll)
                    .resize(200, 200)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(imageView);

            Log.d("myLog","urlll "+urlll);
        }else {
            Log.d("myLog","none urlll");
        }
    }
}
