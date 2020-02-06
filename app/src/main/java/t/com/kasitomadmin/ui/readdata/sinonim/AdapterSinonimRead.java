package t.com.kasitomadmin.ui.readdata.sinonim;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import t.com.kasitomadmin.R;
import t.com.kasitomadmin.model.dataKamus;

public class AdapterSinonimRead extends RecyclerView.Adapter<AdapterSinonimRead.ViewHolder> {
    private ArrayList<dataKamus> daftarSinonim;

    public AdapterSinonimRead(ArrayList<dataKamus> inputDatas){
        daftarSinonim = inputDatas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_data, parent,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final String judul = daftarSinonim.get(position).getJudul();
        final String arti = daftarSinonim.get(position).getArti();

        holder.tvJudul.setText(judul);
        holder.tvArti.setText(arti);

        boolean isExpanded = daftarSinonim.get(position).isExpanded();
        if (isExpanded){
            holder.expandableLayout.setVisibility(View.VISIBLE);
        }else {
            holder.expandableLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return daftarSinonim.size();
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
                    dataKamus dataKamus = daftarSinonim.get(getAdapterPosition());
                    dataKamus.setExpanded(!dataKamus.isExpanded());
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }
    }
}

