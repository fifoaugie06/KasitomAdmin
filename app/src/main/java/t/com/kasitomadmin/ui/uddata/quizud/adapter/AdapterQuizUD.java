package t.com.kasitomadmin.ui.uddata.quizud.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import t.com.kasitomadmin.R;
import t.com.kasitomadmin.model.dataQuiz;

public class AdapterQuizUD extends RecyclerView.Adapter<AdapterQuizUD.ViewHolder> {
    private ArrayList<dataQuiz> daftarQuiz;
    private final Context context;
    private DatabaseReference database;
    private Dialog dialog;
    private String key_level;

    public AdapterQuizUD(ArrayList<dataQuiz> inputDatas, Context c, String key_level) {
        daftarQuiz = inputDatas;
        context = c;
        this.key_level = key_level;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_dataquiz, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final String soal = daftarQuiz.get(position).getSoal();
        final String optionA = daftarQuiz.get(position).getOptionA();
        final String optionB = daftarQuiz.get(position).getOptionB();
        final String optionC = daftarQuiz.get(position).getOptionC();
        final String optionD = daftarQuiz.get(position).getOptionD();
        final String jawaban = daftarQuiz.get(position).getJawaban();
        final String key = daftarQuiz.get(position).getKey();

        holder.tvNomor.setText((position + 1) + ". ");
        holder.tvSoal.setText(soal);

        final Typeface boldTypeface = Typeface.defaultFromStyle(Typeface.BOLD);

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
                //show dialog view and fill edit text
                showDialogUpdateKamus(key, soal, optionA, optionB, optionC, optionD, jawaban);
            }
        });

        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteKamus(key);
            }
        });
    }

    private void showDialogUpdateKamus(final String key, final String soal, final String optionA, final String optionB,
                                       final String optionC, final String optionD, final String jawaban) {
        final EditText edtSoal, edtOptA, edtOptB, edtOptC, edtOptD;
        final Button btnUpdate;
        final Spinner sp_option, sp_level;

        dialog = new Dialog(context);
        dialog.setContentView(R.layout.fragment_quiz_create);
        dialog.show();

        final Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        btnUpdate = dialog.findViewById(R.id.btn_submit);

        edtSoal = dialog.findViewById(R.id.edt_soal);
        edtOptA = dialog.findViewById(R.id.edt_optionA);
        edtOptB = dialog.findViewById(R.id.edt_optionB);
        edtOptC = dialog.findViewById(R.id.edt_optionC);
        edtOptD = dialog.findViewById(R.id.edt_optionD);

        sp_option = dialog.findViewById(R.id.sp_option);
        sp_level = dialog.findViewById(R.id.sp_level);

        sp_level.setVisibility(View.GONE);

        edtSoal.setText(soal);
        edtOptA.setText(optionA);
        edtOptB.setText(optionB);
        edtOptC.setText(optionC);
        edtOptD.setText(optionD);

        if (optionA.equals(jawaban)) {
            sp_option.setSelection(0);
        } else if (optionB.equals(jawaban)) {
            sp_option.setSelection(1);
        } else if (optionC.equals(jawaban)) {
            sp_option.setSelection(2);
        } else {
            sp_option.setSelection(3);
        }

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataQuiz dataQuiz = new dataQuiz();
                dataQuiz.setSoal(edtSoal.getText().toString());
                dataQuiz.setOptionA(edtOptA.getText().toString());
                dataQuiz.setOptionB(edtOptB.getText().toString());
                dataQuiz.setOptionC(edtOptC.getText().toString());
                dataQuiz.setOptionD(edtOptD.getText().toString());
                dataQuiz.setJawaban(getKeyAnswer(sp_option, edtOptA, edtOptB, edtOptC, edtOptD)); // getkeyfrom radiobutton
                getKeyAnswer(sp_option, edtOptA, edtOptB, edtOptC, edtOptD);
                updateKamus(dataQuiz, key); //updatekamus
            }
        });
    }

    private String getKeyAnswer(Spinner sp_option, EditText edtOptA, EditText edtOptB, EditText edtOptC, EditText edtOptD) {
        int checkedId = (int) sp_option.getSelectedItemId();
        String keyAnswer = null;
        switch (checkedId) {
            case 0:
                keyAnswer = edtOptA.getText().toString();
                break;
            case 1:
                keyAnswer = edtOptB.getText().toString();
                break;
            case 2:
                keyAnswer = edtOptC.getText().toString();
                break;
            case 3:
                keyAnswer = edtOptD.getText().toString();
                break;
        }
        return keyAnswer;
    }

    private void updateKamus(dataQuiz dataQuiz, String key) {
        database = FirebaseDatabase.getInstance().getReference();
        database.child("Quiz")
                .child(key_level)
                .child(key)
                .setValue(dataQuiz)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context, "Berhasil Di Update", Toast.LENGTH_LONG).show();
                        dialog.dismiss();
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
                database.child("Quiz").child(key_level)
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

    private boolean isEmpty(String s) {
        return TextUtils.isEmpty(s);
    }

    @Override
    public int getItemCount() {
        return daftarQuiz.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNomor, tvSoal, tvOptionA, tvOptionB, tvOptionC, tvOptionD;
        ImageButton btn_edit, btn_delete;

        private ViewHolder(@NonNull View itemView) {
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

