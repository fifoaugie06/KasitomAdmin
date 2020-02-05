package t.com.kasitomadmin.ui.readdata.antonim;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import java.util.ArrayList;

import t.com.kasitomadmin.R;
import t.com.kasitomadmin.model.dataKamus;

public class AdapterAntonimRead extends RecyclerView.Adapter<AdapterAntonimRead.ViewHolder> {

    private ArrayList<dataKamus> daftarAntonim;
    private Context context;
    private DatabaseReference database;

    public AdapterAntonimRead(ArrayList<dataKamus> inputDatas, Context c){
        daftarAntonim = inputDatas;
        context = c;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_data, parent,false);
        ViewHolder vh = new ViewHolder(view);

        return vh;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final String judul = daftarAntonim.get(position).getJudul();
        final String arti = daftarAntonim.get(position).getArti();

        holder.tvJudul.setText(judul);
        holder.tvArti.setText(arti);

        boolean isExpanded = daftarAntonim.get(position).isExpanded();
        if (isExpanded){
            holder.expandableLayout.setVisibility(View.VISIBLE);
        }else {
            holder.expandableLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return daftarAntonim.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout expandableLayout;
        TextView tvJudul, tvArti;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvJudul = itemView.findViewById(R.id.tv_judul);
            tvArti = itemView.findViewById(R.id.tv_arti);
            expandableLayout = itemView.findViewById(R.id.expandableLayout);

            tvJudul.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dataKamus dataKamus = daftarAntonim.get(getAdapterPosition());
                    dataKamus.setExpanded(!dataKamus.isExpanded());
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }
    }
}

