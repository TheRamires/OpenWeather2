package ram.ramires.openweathersky2.All_Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ram.ramires.openweathersky2.databinding.DailyItemBinding;
import ram.ramires.openweathersky2.pojo.daily.Daily;

import static ram.ramires.openweathersky2.MainActivity.LOG;

public class Recycler_View_Adapter extends RecyclerView.Adapter<Recycler_View_Adapter.DailyViewHolder> {

    List<Daily> daily;
    public Recycler_View_Adapter(List<Daily> daily){
        this.daily=daily;
        Log.d(LOG, "Adapter constructer "+this.daily.size());
    }

    @NonNull
    @Override
    public DailyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        DailyItemBinding binding=DailyItemBinding.inflate(inflater,parent,false);
        Log.d(LOG,"Create View Holder");
        return new DailyViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull DailyViewHolder holder, int position) {
        //if (position%2==0){holder.itemView.setBackgroundColor(Color.parseColor("#FFF0F9FA"));
        // }
        holder.binding.setDailyBind(daily.get(position));
    }

    @Override
    public int getItemCount() {
        return daily.size();
    }

    class DailyViewHolder extends RecyclerView.ViewHolder{
        DailyItemBinding binding;

        public DailyViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.d(LOG, "Create DailyViewHolder");
            binding= DataBindingUtil.bind(itemView);
        }
    }
}