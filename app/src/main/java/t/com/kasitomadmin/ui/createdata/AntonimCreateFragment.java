package t.com.kasitomadmin.ui.createdata;


import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import t.com.kasitomadmin.R;
import t.com.kasitomadmin.model.dataKamus;

import static android.widget.Toast.LENGTH_SHORT;

public class AntonimCreateFragment extends Fragment {
    private DatabaseReference database;

    private Button btnSubmit;
    private EditText edtJudul;
    private EditText edtArti;
    private View view;

    public AntonimCreateFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_antonim_create, container, false);
        btnSubmit = view.findViewById(R.id.btn_submit);
        edtJudul = view.findViewById(R.id.inp_Judul);
        edtArti = view.findViewById(R.id.inp_Arti);

        database = FirebaseDatabase.getInstance().getReference();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isEmpty(edtJudul.getText().toString()) && !isEmpty(edtArti.getText().toString())){
                    submitAntonim(new dataKamus(edtJudul.getText().toString(), edtArti.getText().toString()));
                }else if (isEmpty(edtJudul.getText().toString())){
                    edtJudul.setError("Tidak Boleh Kosong");
                }else if (isEmpty(edtArti.getText().toString())){
                    edtArti.setError("Tidak Boleh Kosong");
                }
            }
        });
        return view;
    }

    private void submitAntonim(dataKamus antonim) {
        database.child("antonim").push().setValue(antonim).addOnSuccessListener((Activity) view.getContext(), new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                edtJudul.setText("");
                edtArti.setText("");
                Toast.makeText(view.getContext(), "Data Berhasil diInput", LENGTH_SHORT).show();
            }
        });
    }

    private boolean isEmpty(String s){
        return TextUtils.isEmpty(s);
    }
}
