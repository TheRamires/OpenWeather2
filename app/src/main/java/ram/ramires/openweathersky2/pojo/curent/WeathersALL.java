package ram.ramires.openweathersky2.pojo.curent;

import androidx.databinding.BaseObservable;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import ram.ramires.openweathersky2.App;

@Entity
public class WeathersALL extends BaseObservable {
    @PrimaryKey(autoGenerate = true)
    public int indificate;

    public long time;

    @SerializedName("coord")
    @Expose
    @Embedded
    public Coord coord;

    @SerializedName("weather")
    @Expose
    @TypeConverters(Convertor.class)
    public List<Weather> weather;

    @SerializedName("base")
    @Expose
    public String base;
    @SerializedName("main")
    @Expose
    @Embedded
    public Main main;
    @SerializedName("visibility")
    @Expose
    public int visibility;
    @SerializedName("wind")
    @Expose
    @Embedded
    public Wind wind;
    @SerializedName("clouds")
    @Expose
    @Embedded
    public Clouds clouds;
    @SerializedName("dt")
    @Expose
    public long dt;
    @SerializedName("sys")
    @Expose
    @Embedded
    public Sys sys;
    @SerializedName("timezone")
    @Expose
    public int timezone;

    @SerializedName("id")
    @Expose
    public int id_WeatherAll;

    @SerializedName("name")
    @Expose
    public String oldCity;
    @SerializedName("cod")
    @Expose
    public int cod;

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public String getDt() {

        Locale locale= App.getLocale();
        String date=new SimpleDateFormat("E dd.MM",locale).format(dt*1000);
        return date;
    }

    public void setDt(long dt) {
        this.dt = dt;
    }

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public int getTimezone() {
        return timezone;
    }

    public void setTimezone(int timezone) {
        this.timezone = timezone;
    }

    public int getId() {
        return id_WeatherAll;
    }

    public void setId(int id) {
        this.id_WeatherAll = id;
    }


    public String getName() {
        return oldCity;
    }

    public void setName(String name) {
        this.oldCity = name;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public void setindificate(int indificate) {
        this.indificate=indificate;
    }

    public int getindificate() {
        return indificate;
    }
    public void setTime(long time){
        this.time=time;
    }
    public long getTime(){
        return time;
    }
}

