package t.com.kasitomadmin.ui.uddata.quizud;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import t.com.kasitomadmin.R;
import t.com.kasitomadmin.model.dataQuiz;
import t.com.kasitomadmin.model.dataScoreBoard;

public class QuizUDFragment extends Fragment {
    private RecyclerView rvView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<dataQuiz> daftarQuiz;
    private ArrayList<dataScoreBoard> daftarScoreBoard;
    private DatabaseReference database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;

        view = inflater.inflate(R.layout.fragment_quiz_ud, container, false);
        setHasOptionsMenu(true);
        rvView = view.findViewById(R.id.rv_quiz);
        rvView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        rvView.setLayoutManager(layoutManager);

        database = FirebaseDatabase.getInstance().getReference();

        // Recyclerview UTAMA
        database.child("Quiz").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                daftarQuiz = new ArrayList<>();
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {

                    dataQuiz dataQuiz = noteDataSnapshot.getValue(dataQuiz.class);
                    dataQuiz.setKey(noteDataSnapshot.getKey());

                    daftarQuiz.add(dataQuiz);
                }
                adapter = new AdapterQuizUD(daftarQuiz, getActivity());
                rvView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println(databaseError.getDetails() + " " + databaseError.getMessage());
            }
        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.top_menu_scoreboard, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.navigation_scoreboard){
            showScoreBoard();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showScoreBoard() {
        //Recyclerview Dialog

        final Dialog dialog;
        final Button btnDelete;

        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_scoreboard);

        rvView = dialog.findViewById(R.id.rv_scoreboard);
        rvView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        rvView.setLayoutManager(layoutManager);

        database = FirebaseDatabase.getInstance().getReference();
        database.child("scoreboard").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                daftarScoreBoard = new ArrayList<>();

                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {

                    dataScoreBoard dataScoreBoards = noteDataSnapshot.getValue(dataScoreBoard.class);
                    dataScoreBoards.setKey(noteDataSnapshot.getKey());

                    daftarScoreBoard.add(dataScoreBoards);
                }

                // descending data
                Collections.sort(daftarScoreBoard, new Comparator<dataScoreBoard>() {
                    @Override
                    public int compare(dataScoreBoard lhs, dataScoreBoard rhs) {
                        if (Float.parseFloat(lhs.getNilai()) > Float.parseFloat(rhs.getNilai())){
                            return  -1;
                        }else {
                            return 1;
                        }
                    }
                });
                adapter = new AdapterDialogScoreboard(daftarScoreBoard);
                rvView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        btnDelete = dialog.findViewById(R.id.btn_deleteScore);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = FirebaseDatabase.getInstance().getReference();
                database.child("scoreboard")
                        .removeValue()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Snackbar.make(getView(), "Scoreboard berhasil direset", Snackbar.LENGTH_LONG)
                                        .setAction("OK", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                            }
                                        }).show();
                                dialog.dismiss();
                            }
                        });
            }
        });
    }
}