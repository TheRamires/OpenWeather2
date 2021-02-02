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
        if (!urlll.equals("")) {
            Picasso
                    .with(imageView.getContext())
                    .load(urlll)
                    .resize(150, 150)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(imageView);

        }else {
        }
    }
}
