package t.com.kasitomadmin.ui.uddata.quizud;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import t.com.kasitomadmin.R;
import t.com.kasitomadmin.model.dataQuiz;

public class AdapterQuizUD extends RecyclerView.Adapter<AdapterQuizUD.ViewHolder> {

    private ArrayList<dataQuiz> daftarQuiz;
    private final Context context;
    private DatabaseReference database;

    public AdapterQuizUD(ArrayList<dataQuiz> inputDatas, Context c) {
        daftarQuiz = inputDatas;
        context = c;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_dataquiz, parent, false);
        ViewHolder vh = new ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final String soal = daftarQuiz.get(position).getSoal();
        final String optionA = daftarQuiz.get(position).getOptionA();
        final String optionB = daftarQuiz.get(position).getOptionB();
        final String optionC = daftarQuiz.get(position).getOptionC();
        final String optionD = daftarQuiz.get(position).getOptionD();
        final String jawaban = daftarQuiz.get(position).getJawaban();
        final String key = daftarQuiz.get(position).getKey();

        holder.tvNomor.setText((position + 1) + ". ");
        holder.tvSoal.setText(soal);

        Typeface boldTypeface = Typeface.defaultFromStyle(Typeface.BOLD);

        if (optionA.equals(jawaban)) {
            setViewHolder(holder, optionA, optionB, optionC, optionD);
            holder.tvOptionA.setTypeface(boldTypeface);
        } else if (optionB.equals(jawaban)) {
            setViewHolder(holder, optionA, optionB, optionC, optionD);
            holder.tvOptionB.setTypeface(boldTypeface);
        } else if (optionC.equals(jawaban)) {
            setViewHolder(holder, optionA, optionB, optionC, optionD);
            holder.tvOptionC.setTypeface(boldTypeface);
        } else {
            setViewHolder(holder, optionA, optionB, optionC, optionD);
            holder.tvOptionD.setTypeface(boldTypeface);
        }

        holder.btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, soal, Toast.LENGTH_SHORT).show();
            }
        });

        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteKamus(key);
            }
        });
    }

    private void setViewHolder(ViewHolder holder, String optionA, String optionB, String optionC, String optionD) {
        holder.tvOptionA.setText(optionA);
        holder.tvOptionB.setText(optionB);
        holder.tvOptionC.setText(optionC);
        holder.tvOptionD.setText(optionD);
    }

    private void deleteKamus(final String key) {
        final Dialog dialog;
        final TextView tv_konfirmasi;
        final Button bt_Ok, bt_Cancel;

        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_view_delete);
        dialog.show();

        final Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        tv_konfirmasi = dialog.findViewById(R.id.tv_konfirmasi);
        bt_Ok = dialog.findViewById(R.id.bt_ok);
        bt_Cancel = dialog.findViewById(R.id.bt_cancel);

        tv_konfirmasi.setText("Items will be permanently deleted");

        bt_Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view2) {
                //Log.i("apaa", String.valueOf(view2));
                database = FirebaseDatabase.getInstance().getReference();
                database.child("Quiz")
                        .child(key)
                        .removeValue()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(context, "Berhasil di hapus", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });
            }
        });

        bt_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }


//    private void updateKamus(dataQuiz dataQuiz) {
//        database = FirebaseDatabase.getInstance().getReference();
//        database.child("antonim")
//                .child(dataQuiz.getKey())
//                .setValue(dataQuiz)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Toast.makeText(context, "Berhasil Di Update", Toast.LENGTH_LONG).show();
//                    }
//                });
//    }

    private boolean isEmpty(String s) {
        return TextUtils.isEmpty(s);
    }

    @Override
    public int getItemCount() {
        return daftarQuiz.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNomor, tvSoal, tvOptionA, tvOptionB, tvOptionC, tvOptionD;
        ImageButton btn_edit, btn_delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSoal = itemView.findViewById(R.id.tv_soal);
            tvOptionA = itemView.findViewById(R.id.edt_optionA);
            tvOptionB = itemView.findViewById(R.id.edt_optionB);
            tvOptionC = itemView.findViewById(R.id.edt_optionC);
            tvOptionD = itemView.findViewById(R.id.edt_optionD);
            tvNomor = itemView.findViewById(R.id.tv_nomor);

            btn_edit = itemView.findViewById(R.id.btn_edit);
            btn_delete = itemView.findViewById(R.id.btn_delete);
        }
    }
}

