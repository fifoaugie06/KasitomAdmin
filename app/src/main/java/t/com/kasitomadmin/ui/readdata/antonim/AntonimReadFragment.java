package t.com.kasitomadmin.ui.readdata.antonim;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import t.com.kasitomadmin.R;
import t.com.kasitomadmin.model.dataKamus;

public class AntonimReadFragment extends Fragment {
    private RecyclerView rvView;
    private RecyclerView.Adapter adapter;
    private ArrayList<dataKamus> daftarAntonim;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        DatabaseReference database;
        RecyclerView.LayoutManager layoutManager;
        View view;

        view = inflater.inflate(R.layout.fragment_antonim_read, container, false);
        rvView = view.findViewById(R.id.rv_antonim);
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

                adapter = new AdapterAntonimRead(daftarAntonim);
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
