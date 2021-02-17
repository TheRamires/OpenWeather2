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
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import ram.ramires.openweathersky2.databinding.FragmentChartBinding;
import ram.ramires.openweathersky2.pojo.Draw;
import ram.ramires.openweathersky2.pojo.daily.Hourly;

public class FragmentChart extends Fragment {
    //public static final String LOG2="myLog2";
    private MutableLiveData<Drawable> liveIcons=new MutableLiveData();
    private MutableLiveData<Drawable[]> drawablesArray=new MutableLiveData<>();
    private MutableLiveData<List<Map.Entry<Long,Drawable>>> drawableLive=new MutableLiveData<>();

    private MutableLiveData<Map<Long,Draw>> liveDrawbles=new MutableLiveData<>();
    ViewModel_Sky viewModel;
    LineChart mChart;
    XAxis xAxis;
    YAxis yAxis;
    String url;
    List<Double> hourlies=new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewModel=new ViewModelProvider(requireActivity()).get(ViewModel_Sky.class);
        FragmentChartBinding binding=FragmentChartBinding.inflate(inflater, container,false);
        binding.setInvisible(viewModel);

        View view=binding.getRoot();

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

                List<Long> dates=new ArrayList<>();
                for (int i=0;i<values.size();i++){
                    dates.add(values.get(i).getDt()*1000);
                    hourlies.add((double) values.get(i).getTemp());
                }
                DrawablesIcons drawablesIcons=new DrawablesIcons(values);
                drawablesIcons.toAssembly();

                xAxis=mChart.getXAxis();
                xAxis.setAxisMaximum(35f);
                xAxis.setLabelCount(48);
                xAxis.setTextSize(12f);
                xAxis.setYOffset(20); //смещение названий под осью Х
                xAxis.setDrawAxisLine(false);

                xAxis.setTextColor(getResources().getColor(R.color.purple_700));
                xAxis.setGranularityEnabled(true);
                xAxis.setGranularity(1);
                xAxis.setValueFormatter(new MyXAxisValueFormater(dates));
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                yAxis =mChart.getAxisLeft();

               //setData(values, null);
            }
        });
        liveDrawbles.observe(getViewLifecycleOwner(), new Observer<Map<Long, Draw>>() {
            @Override
            public void onChanged(Map<Long, Draw> longDrawMap) {
                if (hourlies.size()==0){
                    return;
                }
                List<Long> dateList=new ArrayList<>(longDrawMap.keySet());
                List<Draw> drawList=new ArrayList<>(getListDraw(longDrawMap));
                List<Entry> entryList=new ArrayList<>();
                //Log.d(LOG2,"liveDrawblesobserve onChanged: dateList "+dateList.size()+" DrawList: "+drawList.size());

                for (Long lo:dateList){
                    //Log.d(LOG2, ""+lo);
                }

                for (int i=0;i<dateList.size();i++){
                    float f=Math.round(hourlies.get(i));
                    Entry entry=new Entry(i,f,drawList.get(i).getDrawable(),"");
                    entryList.add(entry);
                }
                hourlies.clear();
                //Log.d(LOG2,"EntryList.Size "+entryList.size());
                createSet(entryList);

            }
        });

        return view;
    }
    private List<Draw> getListDraw(Map<Long, Draw> longDrawMap){
        List<Draw> startList=new ArrayList<>(longDrawMap.values());
        //Log.d(LOG2, "getListDraw, startList.size= "+startList.size());
        List<Draw> finishList=new ArrayList<>();

        finishList.add(new Draw(startList.get(0).getIdIcon(),startList.get(0).getDrawable()));
        for (int i=1;i<startList.size();i++){
            String currentIcon=startList.get(i).getIdIcon();
            String previoslytIcon=startList.get(i-1).getIdIcon();
            Drawable drawable=startList.get(i).getDrawable();
            if (!currentIcon.equals(previoslytIcon)) {
                finishList.add(new Draw(currentIcon, drawable));
            } else {finishList.add(new Draw(currentIcon, null));}
        }
        return finishList;
    }
    private void createSet(List<Entry> yValues){
        // III.
        viewModel.visibilityChart.set(true);
        LineDataSet set1;
        set1=new LineDataSet(yValues, "");

        //set1.removeEntry(35);
        set1.setColor(getResources().getColor(R.color.purple_700));
        set1.setCircleColor(Color.BLACK);
        set1.setLineWidth(2f);
        set1.setValueTextSize(15f);
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
                String res="    "+str.split(".",1)[0]+"°C";

                return res;
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
        private int maxSize;
        private List<String> listKey;
        private List<Long> listDates;
        private SortedMap<Long,Draw> mapIcon;
        public DrawablesIcons(List<Hourly> hourlies){
            maxSize=hourlies.size();
            listKey=new ArrayList<>(getIdIcons(hourlies));
            listDates=new ArrayList<>(getDates(hourlies));
            //Log.d(LOG2, "listKey.Size= "+listKey.size()+"; listDates.Size= "+listDates.size());
            mapIcon=new TreeMap<>();
           // Log.d(LOG2,"DrawablesIcons create");
        }
        public void toAssembly (){
            //Log.d(LOG2,"toAssembly");
                for (int i=0;i<listKey.size();i++){
                    //Log.d(LOG2,"i= "+i);
                    String url=listKey.get(i);
                    toPicas(listDates.get(i),url, i);
                }
            }

        private void toPicas(Long date,String url, int count){
            Picasso.with(requireContext())
                    .load("http://openweathermap.org/img/wn/"+url+"@2x.png")
                    .into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                           // Log.d(LOG2,count+" "+url +" onBitmapLoaded ");
                            Drawable drawable=new BitmapDrawable(getResources(),bitmap);
                            mapIcon.put(date,new Draw(url,drawable));
                            if (mapIcon.size()==maxSize) {
                                //Log.d(LOG2,url +" FINISH PICAS onBitmapLoaded "+mapIcon.size());
                                liveDrawbles.setValue(mapIcon);
                            }
                        }

                        @Override
                        public void onBitmapFailed(Drawable errorDrawable) {
                            //Log.d(LOG2,count+" "+url+" onBitmapFailed ");
                            mapIcon.put(date,new Draw(url,null));
                            if (mapIcon.size()==maxSize) {
                               // Log.d(LOG2,url +" FINISH PICAS onBitmapFailed "+mapIcon.size());
                                liveDrawbles.setValue(mapIcon);
                            }
                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {

                        }
                    });
        }
        private List<Long> getDates(List<Hourly> hourlies){
            List<Long> dates=new ArrayList<>();
            for (Hourly hourly: hourlies){
                dates.add(hourly.getDt()*1000);
            }
            return dates;
        }

        private List<String> getIdIcons(List<Hourly> hourlies){
            List<String> icons=new ArrayList<>();
            for(Hourly hourly:hourlies) {
                String key=hourly.getWeather().get(0).getIcon();
                icons.add(key);
            }
            return icons;
        }
    }
}