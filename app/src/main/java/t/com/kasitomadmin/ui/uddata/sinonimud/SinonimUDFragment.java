package t.com.kasitomadmin.ui.uddata.sinonimud;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class SinonimUDFragment extends Fragment {
    private RecyclerView rvView;
    private RecyclerView.Adapter adapter;
    private ArrayList<dataKamus> daftarSinonim;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        RecyclerView.LayoutManager layoutManager;
        DatabaseReference database;

        view = inflater.inflate(R.layout.fragment_sinonim_read, container, false);
        rvView = view.findViewById(R.id.rv_antonim);
        rvView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        rvView.setLayoutManager(layoutManager);

        database = FirebaseDatabase.getInstance().getReference();

        database.child("sinonim").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                daftarSinonim = new ArrayList<>();
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {

                    dataKamus dataSinonim = noteDataSnapshot.getValue(dataKamus.class);
                    dataSinonim.setKey(noteDataSnapshot.getKey());

                    daftarSinonim.add(dataSinonim);
                }

                adapter = new AdapterSinonimUD(daftarSinonim, getContext());
                rvView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println(databaseError.getDetails() + " " + databaseError.getMessage());
            }
        });

        return view;
    }
}
