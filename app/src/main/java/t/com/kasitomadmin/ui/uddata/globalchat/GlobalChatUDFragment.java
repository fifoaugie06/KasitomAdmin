package t.com.kasitomadmin.ui.uddata.globalchat;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
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
    private View view;
    private Button btn_delete;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_global_chat_ud, container, false);
        btn_delete = view.findViewById(R.id.btn_delete);
        rvView = view.findViewById(R.id.rv_antonim);
        rvView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        rvView.setLayoutManager(layoutManager);

        database = FirebaseDatabase.getInstance().getReference();

        database.child("globalchat").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chatMessages = new ArrayList<>();

                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()){
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

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

                tv_konfirmasi.setText("More than " + chatMessages.size() + " items will be permanently deleted");

                bt_Ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });

                bt_Cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });

        return view;
    }


}
