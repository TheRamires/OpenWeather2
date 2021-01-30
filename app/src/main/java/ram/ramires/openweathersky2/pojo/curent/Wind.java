package ram.ramires.openweathersky2.pojo.curent;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import ram.ramires.openweathersky2.App;
import ram.ramires.openweathersky2.R;

public class Wind {
    @SerializedName("speed")
    @Expose
    public double speed;
    @SerializedName("deg")
    @Expose
    private double deg;

    public String getSpeed() {
        int i= (int) Math.round(speed);
        String speed= App.getStrings(R.string.wind_speed);
        String metres=App.getStrings(R.string.metr);
        return speed+" "+i+" "+metres;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getDeg() {
        return deg;
    }

    public void setDeg(double deg) {
        this.deg = deg;
    }

}