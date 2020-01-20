package t.com.kasitomadmin.ui.readdata.antonim;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import t.com.kasitomadmin.MainActivity;
import t.com.kasitomadmin.R;
import t.com.kasitomadmin.model.dataKamus;
import t.com.kasitomadmin.ui.createdata.AntonimCreateFragment;
import t.com.kasitomadmin.ui.createdata.CreateFragment;
import t.com.kasitomadmin.ui.readdata.sinonim.SinonimReadFragment;

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

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final String judul = daftarAntonim.get(position).getJudul();
        final String arti = daftarAntonim.get(position).getArti();

        holder.tvJudul.setText(judul);
        holder.tvArti.setText(arti);
    }

    @Override
    public int getItemCount() {
        return daftarAntonim.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvJudul, tvArti;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvJudul = itemView.findViewById(R.id.tv_judul);
            tvArti = itemView.findViewById(R.id.tv_arti);
        }
    }
}

