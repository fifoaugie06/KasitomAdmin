package t.com.kasitomadmin.ui.uddata.antonimud;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import t.com.kasitomadmin.R;
import t.com.kasitomadmin.model.dataKamus;

public class AntonimUDFragment extends Fragment {
    private RecyclerView rvView;
    private RecyclerView.Adapter adapter;
    private ArrayList<dataKamus> daftarAntonim;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        DatabaseReference database;
        RecyclerView.LayoutManager layoutManager;
        View view;
        EditText edtSearch;

        view = inflater.inflate(R.layout.fragment_antonim_read, container, false);
        rvView = view.findViewById(R.id.rv_antonim);
        edtSearch = view.findViewById(R.id.edt_searchas);

        rvView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        rvView.setLayoutManager(layoutManager);

        database = FirebaseDatabase.getInstance().getReference();

        database.child("antonim").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                daftarAntonim = new ArrayList<>();
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    dataKamus dataAntonim = noteDataSnapshot.getValue(dataKamus.class);
                    dataAntonim.setKey(noteDataSnapshot.getKey());

                    daftarAntonim.add(dataAntonim);
                }

                adapter = new AdapterAntonimUD(daftarAntonim, getContext());
                rvView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println(databaseError.getDetails() + " " + databaseError.getMessage());
            }
        });

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;
    }

    private void filter(String newText) {
        ArrayList<dataKamus> dataKamusList = new ArrayList<>();
        try {
            for (dataKamus s : daftarAntonim) {
                if (s.getJudul().toLowerCase().contains(newText.toLowerCase())) {
                    dataKamusList.add(s);
                }
            }
            adapter = new AdapterAntonimUD(dataKamusList, getContext());
            rvView.setAdapter(adapter);
        } catch (NullPointerException e) {
            Log.i("e", String.valueOf(e));
        }
    }
}
