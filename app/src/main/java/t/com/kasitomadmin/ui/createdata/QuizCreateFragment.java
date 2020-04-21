package t.com.kasitomadmin.ui.createdata;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import t.com.kasitomadmin.R;
import t.com.kasitomadmin.model.dataQuiz;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuizCreateFragment extends Fragment {
    private View view;
    private Button btn_Submit;
    private EditText edt_Soal, edt_OptionA, edt_OptionB, edt_OptionC, edt_OptionD;
    private DatabaseReference database;
    private Spinner spinnerOption, spinnerLevel;
    private int selectedId;
    private String keyAnswer;

    public QuizCreateFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_quiz_create, container, false);
        database = FirebaseDatabase.getInstance().getReference();

        btn_Submit = view.findViewById(R.id.btn_submit);

        edt_Soal = view.findViewById(R.id.edt_soal);
        edt_OptionA = view.findViewById(R.id.edt_optionA);
        edt_OptionB = view.findViewById(R.id.edt_optionB);
        edt_OptionC = view.findViewById(R.id.edt_optionC);
        edt_OptionD = view.findViewById(R.id.edt_optionD);

        spinnerOption = view.findViewById(R.id.sp_option);
        spinnerLevel = view.findViewById(R.id.sp_level);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isEmpty(edt_Soal.getText().toString()) && !isEmpty(edt_OptionA.getText().toString())
                        && !isEmpty(edt_OptionB.getText().toString()) && !isEmpty(edt_OptionC.getText().toString())
                        && !isEmpty(edt_OptionD.getText().toString())) {

                    submitQuiz(new dataQuiz(edt_Soal.getText().toString(), edt_OptionA.getText().toString(),
                            edt_OptionB.getText().toString(), edt_OptionC.getText().toString(),
                            edt_OptionD.getText().toString(), getKeyAnswer()), "Level " + spinnerLevel.getSelectedItem());

                } else {
                    Toast.makeText(getContext(), "Pastikan semua terisi !!!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void submitQuiz(dataQuiz dataQuiz, String level) {
        database.child("Quiz")
                .child(level)
                .push()
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

    private String getKeyAnswer() {
        selectedId = (int) spinnerOption.getSelectedItemId();
        switch (selectedId) {
            case 0:
                keyAnswer = edt_OptionA.getText().toString();
                break;
            case 1:
                keyAnswer = edt_OptionB.getText().toString();
                break;
            case 2:
                keyAnswer = edt_OptionC.getText().toString();
                break;
            case 3:
                keyAnswer = edt_OptionD.getText().toString();
                break;
        }
        return keyAnswer;
    }

    private boolean isEmpty(String s) {
        return TextUtils.isEmpty(s);
    }
}
