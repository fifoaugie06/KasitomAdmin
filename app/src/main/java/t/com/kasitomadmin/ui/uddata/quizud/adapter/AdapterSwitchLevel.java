package t.com.kasitomadmin.ui.uddata.quizud.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import t.com.kasitomadmin.R;
import t.com.kasitomadmin.ui.uddata.quizud.QuizUDFragment;

public class AdapterSwitchLevel extends RecyclerView.Adapter<AdapterSwitchLevel.ViewHolder> {
    private ArrayList<String> switchLevel;
    private final Context context;
    private View view;

    public AdapterSwitchLevel(ArrayList<String> switchLevel, Context context) {
        this.switchLevel = switchLevel;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_data_switch_level, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tvLevel.setText(switchLevel.get(position));

        holder.tvLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("key_level", switchLevel.get(position));

                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment myFragment = new QuizUDFragment();
                FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();

                myFragment.setArguments(bundle);
                transaction.replace(R.id.framelayout_switchlevel, myFragment, "key_level");
                transaction.addToBackStack("switch level");
                transaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return switchLevel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvLevel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvLevel = itemView.findViewById(R.id.tv_level);
        }
    }
}
