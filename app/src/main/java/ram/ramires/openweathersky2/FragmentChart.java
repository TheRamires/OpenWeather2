package ram.ramires.openweathersky2;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.MPPointF;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import ram.ramires.openweathersky2.pojo.daily.Hourly;

public class FragmentChart extends Fragment {
    private MutableLiveData<Drawable> liveIcons=new MutableLiveData();
    private MutableLiveData<Drawable[]> drawablesArray=new MutableLiveData<>();
    private MutableLiveData<List<Map.Entry<Long,Drawable>>> drawableLive=new MutableLiveData<>();
    ViewModel_Sky viewModel;
    LineChart mChart;
    XAxis xAxis;
    YAxis yAxis;
    String url;
    List<Hourly> hourlies=new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewModel=new ViewModelProvider(requireActivity()).get(ViewModel_Sky.class);

        View view=inflater.inflate(R.layout.fragment_chart, container, false);

        mChart=(LineChart) view.findViewById(R.id.lineChart);

        mChart.getAxisLeft().setDrawGridLines(false);

        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(false); //увеличение масшатаба графика

        mChart.getDescription().setEnabled(false); //убрать Description label

        mChart.getDescription().setEnabled(false);
        mChart.getAxisRight().setEnabled(false);
        mChart.getAxisLeft().setEnabled(false);
        mChart.getXAxis().setDrawGridLines(true); //убирает фоновую разметку

        mChart.animateX(1000);

        viewModel.hourlyLiveData.observe(getViewLifecycleOwner(), new Observer<List<Hourly>>() {
            @Override
            public void onChanged(List<Hourly> values) {
                List<String> iconsList=new ArrayList<>();
                for (Hourly hourly:values){
                    iconsList.add(
                        hourly.getWeather().get(0).getIcon());
                }
                //url=values.get(0).getWeather().get(0).getIcon();
                //setDrawable(url);

                DrawablesIcons drawablesIcons=new DrawablesIcons(values);
                drawablesIcons.toAssembly();

                //long currentDate=System.currentTimeMillis();

                hourlies.clear();
                hourlies.addAll(values);

                List <Long> dates=new ArrayList<>();
                for (int i=0;i<values.size();i++){
                    long temp=values.get(i).getDt()*1000;
                        dates.add(temp);
                }

                xAxis=mChart.getXAxis();
                xAxis.setAxisMaximum(35f);
                xAxis.setLabelCount(48);
                xAxis.setTextSize(12f);
                xAxis.setYOffset(20); //смещение названий под осью Х
                xAxis.setDrawAxisLine(false);

                xAxis.setTextColor(getResources().getColor(R.color.purple_700));
                xAxis.setGranularityEnabled(true);
                xAxis.setGranularity(1);
                //xAxis.setValueFormatter(new MyXAxisValueFormater(dates));
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                yAxis =mChart.getAxisLeft();

               //setData(values, null);
            }
        });

        /*drawablesArray.observe(getViewLifecycleOwner(), new Observer<Drawable[]>() {
            @Override
            public void onChanged(Drawable[] drawablesArr) {
                setData(hourlies, drawablesArr);
            }
        });*/
        drawableLive.observe(getViewLifecycleOwner(), new Observer<List<Map.Entry<Long,Drawable>>>() {
            @Override
            public void onChanged(List<Map.Entry<Long,Drawable>> entries) {
                Log.d("myLog2","drawableLive is observe");
                setData(hourlies,entries);
            }
        });
        return view;
    }
    private void setData (List<Hourly> values, List<Map.Entry<Long,Drawable>> entries){
        // I. Распаковка
        Log.d("myLog2","setData");
        List<Long> dates=new ArrayList<>();
        List<Drawable> drawables=new ArrayList<>();
        for (int i=0;i<entries.size();i++){
            dates.add(entries.get(i).getKey()*1000);
            drawables.add((Drawable) entries.get(i).getValue());
        }

        // II.
        ArrayList<Entry> yValues= new ArrayList<>();
        Log.d("myLog2","drawables SIZE: "+entries.size()+" values SIZE: "+values.size());

        for (int i=0;i<values.size();i++){
            float f=values.get(i).getTemp();
            Drawable drawable;
            if (i==0){
                yValues.add(new Entry(i,Math.round(f),drawables.get(i)));
                continue;
            }
            String current=values.get(i).getWeather().get(0).getIcon();
            String priviosly=values.get(i-1).getWeather().get(0).getIcon();
            Log.d("myLog2", ""+i+" "+current);

            if (!current.equals(priviosly)){
                drawable=drawables.get(i);
            } else {drawable=null; }

            yValues.add(new Entry(i,Math.round(f),drawable));
        }
        xAxis.setValueFormatter(new MyXAxisValueFormater(dates));

        // III.
        LineDataSet set1;
        set1=new LineDataSet(yValues, "");

        set1.setColor(getResources().getColor(R.color.purple_700));
        set1.setCircleColor(Color.BLACK);
        set1.setLineWidth(2f);
        set1.setValueTextSize(17f);
        set1.setValueTextColor(Color.BLACK);

        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
        set1.setHighlightEnabled(true);

        set1.setFormSize(10);
        set1.setIconsOffset(new MPPointF(42,20)); // смещение иконки drawable
        set1.setDrawHighlightIndicators(false); //откл сенсорное выдедление линий

        set1.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                int i=Math.round(value);
                String str=String.valueOf(i);

                return str.split(".",1)[0]+"°C";
            }
        });

        LineData data =new LineData(set1);
        mChart.setData(data);
        mChart.invalidate();
        mChart.notifyDataSetChanged();

    }

    class MyXAxisValueFormater extends ValueFormatter {
        Locale locale= App.getLocale();
        Date date=new Date();
        SimpleDateFormat formatTime=new SimpleDateFormat("HH:mm");
        SimpleDateFormat formatDay=new SimpleDateFormat("E dd.MM", locale);
        private String currentDate= formatTime.format(date);
        private Long [] mValues;
        public MyXAxisValueFormater(List<Long> values){

            mValues=values.toArray(new Long[values.size()]);
        }
        @Override
        public String getFormattedValue(float value) {
            if (mValues.length==0){
                return null;
            } else if (formatTime.format(mValues[(int) value]).equals("00:00")) {
                return formatDay.format(mValues[(int) value]);
            }else {
                return formatTime.format(mValues[(int) value]);
            }
        }
    }
    class DrawablesIcons{
        private List<Hourly> hourlies;
        private int maxSize;
        Map<Long,Drawable> drawableMap;

        public DrawablesIcons(List<Hourly> hourlies){
            Log.d("myLog2","DrawablesIcons is create");
            this.hourlies=hourlies;
            maxSize=hourlies.size();
            drawableMap=new HashMap<>();
        }
        public void toAssembly(){
            Log.d("myLog2","DrawablesIcons toAssembly");
            for (int i=0;i<maxSize;i++){
                toPicas(hourlies, hourlies.get(i).getWeather().get(0).getIcon(), i);
            }
        }
        private void toPicas(List<Hourly> hourlies, String url, int count){
            Picasso.with(requireContext())
                .load("http://openweathermap.org/img/wn/"+url+".png")
                .resize(100,100)
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        Log.d("myLog2","Count "+count+"; DrawablesIcons onBitmapLoaded");
                        Drawable drawable=new BitmapDrawable(getResources(),bitmap);
                        drawableMap.put(hourlies.get(count).getDt(),
                                drawable);
                        if (drawableMap.size()==maxSize){
                            Log.d("myLog2","Count "+count+"; DrawablesIcons iscomplited");
                            drawableLive.setValue(toSort(drawableMap));
                        }
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {
                        Log.d("myLog2","Count "+count+"; DrawablesIcons onBitmapFailed");
                        drawableMap.put(hourlies.get(count).getDt(),
                                null);
                        if (drawableMap.size()==maxSize){
                            Log.d("myLog2","Count "+count+"; DrawablesIcons iscomplited on Failure");
                            drawableLive.setValue(toSort(drawableMap));

                        }
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });
        }
        private List<Map.Entry<Long,Drawable>> toSort(Map<Long,Drawable> map){
            List<Map.Entry<Long,Drawable>>list=new ArrayList(map.entrySet());
            Collections.sort(list, new Comparator<Map.Entry<Long,Drawable>>() {
                @Override
                public int compare(Map.Entry<Long, Drawable> o1, Map.Entry<Long, Drawable> o2) {
                    return o1.getKey().compareTo(o2.getKey());
                }
            });
            return  list;
        }
    }
}