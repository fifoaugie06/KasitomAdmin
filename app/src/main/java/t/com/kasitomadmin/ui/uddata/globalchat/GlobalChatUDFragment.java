package t.com.kasitomadmin.ui.uddata.globalchat;

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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import t.com.kasitomadmin.R;

public class GlobalChatUDFragment extends Fragment {
    private DatabaseReference database;
    private RecyclerView rvView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<ChatMessage> chatMessages;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_global_chat_ud, container, false);
        setHasOptionsMenu(true);

        rvView = view.findViewById(R.id.rv_antonim);
        rvView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        rvView.setLayoutManager(layoutManager);

        database = FirebaseDatabase.getInstance().getReference();

        database.child("globalchat").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chatMessages = new ArrayList<>();

                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    ChatMessage dataChat = noteDataSnapshot.getValue(ChatMessage.class);

                    dataChat.setKey(noteDataSnapshot.getKey());
                    chatMessages.add(dataChat);
                }
                adapter = new MessageAdapter(chatMessages, getContext());
                rvView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.top_menu_delete, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.navigation_delete){
            deleteChat();
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteChat() {
        if (chatMessages.size() != 0) {

            final Dialog dialog;
            final TextView tv_konfirmasi;
            final Button bt_Ok, bt_Cancel;

            dialog = new Dialog(getContext());
            dialog.setContentView(R.layout.dialog_view_delete);
            dialog.show();

            Window window = dialog.getWindow();
            window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            tv_konfirmasi = dialog.findViewById(R.id.tv_konfirmasi);
            bt_Ok = dialog.findViewById(R.id.bt_ok);
            bt_Cancel = dialog.findViewById(R.id.bt_cancel);

            final int jumlahChat = chatMessages.size();
            tv_konfirmasi.setText("More than " + jumlahChat + " items will be permanently deleted");

            bt_Ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    database.child("globalchat")
                            .removeValue()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Snackbar.make(getView(), jumlahChat + " Pesan berhasil di hapus", Snackbar.LENGTH_LONG)
                                            .setAction("OKE", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {

                                                }
                                            }).show();
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
        } else {
            Snackbar.make(getView(), "Tidak ada Data !!!", Snackbar.LENGTH_LONG)
                    .setAction("OKE", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    }).show();
        }
    }
}
