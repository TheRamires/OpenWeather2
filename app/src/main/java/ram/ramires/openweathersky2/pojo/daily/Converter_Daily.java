package ram.ramires.openweathersky2.pojo.daily;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class Converter_Daily {
    static Gson gson = new Gson();

    @TypeConverter
    public static List<Daily> stringToSomeObjectList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<Daily>>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String someObjectListToString(List<Daily> someObjects) {
        return gson.toJson(someObjects);
    }
}
