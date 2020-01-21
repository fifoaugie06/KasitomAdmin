package t.com.kasitomadmin.ui.uddata.sinonimud;

import android.app.Dialog;
import android.content.Context;
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
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import t.com.kasitomadmin.R;
import t.com.kasitomadmin.model.dataKamus;

public class AdapterSinonimUD extends RecyclerView.Adapter<AdapterSinonimUD.ViewHolder> {

    private ArrayList<dataKamus> daftarSinonim;
    private Context context;
    private DatabaseReference database;

    public AdapterSinonimUD(ArrayList<dataKamus> inputDatas, Context c) {
        daftarSinonim = inputDatas;
        context = c;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_data, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final String judul = daftarSinonim.get(position).getJudul();
        final String arti = daftarSinonim.get(position).getArti();

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                final Dialog dialog;
                dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_view);
                dialog.show();
                final Button edtBtn = dialog.findViewById(R.id.bt_cancel);
                final Button dltBtn = dialog.findViewById(R.id.bt_ok);
                final EditText tvJudul, tvArti;

                Window window = dialog.getWindow();
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                tvJudul = dialog.findViewById(R.id.tv_judul);
                tvArti = dialog.findViewById(R.id.tv_arti);

                tvJudul.setText(judul);

                edtBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        dataKamus dataKamus = new dataKamus();
                        dataKamus.setJudul(tvJudul.getText().toString());
                        dataKamus.setArti(tvArti.getText().toString());
                        dataKamus.setKey(daftarSinonim.get(position).getKey());
                        updateKamus(dataKamus);
                    }
                });

                dltBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        deleteKamus(daftarSinonim.get(position).getKey());
                    }
                });
                return true;
            }
        });
        holder.tvJudul.setText(judul);
        holder.tvArti.setText(arti);
    }

    private void deleteKamus(String key) {
        database = FirebaseDatabase.getInstance().getReference();
        database.child("sinonim")
                .child(key)
                .removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context, "Berhasil Di Hapus", Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void updateKamus(dataKamus dataKamus) {
        database = FirebaseDatabase.getInstance().getReference();
        database.child("sinonim")
                .child(dataKamus.getKey())
                .setValue(dataKamus)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context, "Berhasil Di Update", Toast.LENGTH_LONG).show();
                    }
                });
    }

    @Override
    public int getItemCount() {
        return daftarSinonim.size();
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

