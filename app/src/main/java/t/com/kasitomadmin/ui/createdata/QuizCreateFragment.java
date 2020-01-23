package t.com.kasitomadmin.ui.createdata;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import t.com.kasitomadmin.R;
import t.com.kasitomadmin.model.dataQuiz;

import static android.text.TextUtils.isEmpty;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuizCreateFragment extends Fragment {
    private View view;
    private Button btn_Submit;
    private EditText edt_Soal, edt_OptionA, edt_OptionB, edt_OptionC, edt_OptionD;
    private TextView tv_Count;
    private RadioGroup rg_option;
    private DatabaseReference database;
    private int checkedId = -1;
    private String keyAnswer;
    private long count;

    public QuizCreateFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_quiz_create, container, false);

        initView();

        database.child("Quiz").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                count = (dataSnapshot.getChildrenCount()) + 1;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        btn_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isEmpty(edt_Soal.getText().toString()) && !isEmpty(edt_OptionA.getText().toString())
                        && !isEmpty(edt_OptionB.getText().toString()) && !isEmpty(edt_OptionC.getText().toString())
                        && !isEmpty(edt_OptionD.getText().toString())) {

                    getKeyAnswer();

                    if (checkedId == -1) {
                        Toast.makeText(getContext(), "Pastikan semua terisi !!!", Toast.LENGTH_LONG).show();
                    } else {
                        submitQuiz(new dataQuiz(edt_Soal.getText().toString(), edt_OptionA.getText().toString(),
                                edt_OptionB.getText().toString(), edt_OptionC.getText().toString(),
                                edt_OptionD.getText().toString(), keyAnswer));
                        count++;
                    }
                }else {
                    Toast.makeText(getContext(), "Pastikan semua terisi !!!", Toast.LENGTH_LONG).show();
                }
            }
        });
        return view;
    }

    private void submitQuiz(dataQuiz dataQuiz) {
        database.child("Quiz")
                .child(String.valueOf(count))
                .setValue(dataQuiz).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Snackbar.make(getView(), "Data berhasil diinput ke database Quiz", Snackbar.LENGTH_LONG)
                        .setAction("OKE", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        }).show();
            }
        });
    }

    private void getKeyAnswer() {
        checkedId = rg_option.getCheckedRadioButtonId();
        switch (checkedId) {
            case R.id.ans_OptionA:
                keyAnswer = edt_OptionA.getText().toString();
                break;
            case R.id.ans_OptionB:
                keyAnswer = edt_OptionB.getText().toString();
                break;
            case R.id.ans_OptionC:
                keyAnswer = edt_OptionC.getText().toString();
                break;
            case R.id.ans_OptionD:
                keyAnswer = edt_OptionD.getText().toString();
                break;
        }
    }

    private boolean isEmpty(String s) {
        return TextUtils.isEmpty(s);
    }

    private void initView() {
        database = FirebaseDatabase.getInstance().getReference();

        btn_Submit = view.findViewById(R.id.btn_submit);

        edt_Soal = view.findViewById(R.id.edt_soal);
        edt_OptionA = view.findViewById(R.id.edt_optionA);
        edt_OptionB = view.findViewById(R.id.edt_optionB);
        edt_OptionC = view.findViewById(R.id.edt_optionC);
        edt_OptionD = view.findViewById(R.id.edt_optionD);

        rg_option = view.findViewById(R.id.option_group);
    }

}
