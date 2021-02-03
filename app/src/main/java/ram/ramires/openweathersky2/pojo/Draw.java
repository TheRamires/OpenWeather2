package ram.ramires.openweathersky2.pojo;

import android.graphics.drawable.Drawable;

public class Draw {
    private String idIcon;
    private Drawable drawable;

    public Draw(String idIcon, Drawable drawable){
        this.idIcon=idIcon;
        this.drawable=drawable;
    }

    public void setIdIcon(String id){
        idIcon=id;
    }
    public void setDrawable(Drawable drawable){
        this.drawable=drawable;
    }
    public String getIdIcon(){
        return idIcon;
    }
    public Drawable getDrawable(){
        return drawable;
    }
}
