package ram.ramires.openweathersky2;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class Permission {
    private ViewModel_Sky viewModel;
    private Activity activity;
    private Context context;
    private Dialog dialog;

    public Permission (ViewModel_Sky viewModel, Activity activity, Context context){
        this.viewModel=viewModel;
        this.activity=activity;
        this.context=context;

        dialog=new Dialog(context);
        dialog.setContentView(R.layout.dialog_layout);
        dialog.setTitle("Важно");
    }

    public void permissionsResult(int requestCode, @NonNull int[] grantResults){
        switch (requestCode){
            case MainActivity.REQUEST_CODE_PERMISSION_FINE_LOCATION:if (grantResults.length > 0){
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    viewModel.startApp();
                }else if (grantResults[0] == PackageManager.PERMISSION_DENIED){
                    // Should we show an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION)) {
                        dialog.show();

                    }else{
                        //Never ask again selected, or device policy prohibits the app from having that permission.
                        //So, disable that feature, or fall back to another situation...
                    }
                }
            }
        }
    }

    public void requestPerm(){
        int permissionStatus = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
            viewModel.startApp();
        } else {
            ActivityCompat.requestPermissions(activity, new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                    MainActivity.REQUEST_CODE_PERMISSION_FINE_LOCATION);
        }
    }

    public void back(){
        requestPerm();
        dialog.dismiss();
    }
}
