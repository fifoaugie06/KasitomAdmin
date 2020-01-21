package t.com.kasitomadmin.ui.uddata.globalchat;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import t.com.kasitomadmin.R;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    private ArrayList<ChatMessage> chatMessages;
    private Context c;
    private ImageView tvPhoto;

    public MessageAdapter(ArrayList<ChatMessage> chatMessages, Context context) {
        this.chatMessages = chatMessages;
        c = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_datachat, parent,false);
        ViewHolder vh = new ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final String nama = chatMessages.get(position).getMessageUser();
        final String chat = chatMessages.get(position).getMessageText();
        final String photoUri = chatMessages.get(position).getMessageUserPhotoUri();
        final String key = chatMessages.get(position).getKey();

        holder.tvNama.setText(nama);
        holder.tvChat.setText(chat);
        Picasso.get()
                .load(photoUri)
                .centerInside()
                .resize(150, 150)
                .into(tvPhoto);

    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNama, tvChat;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.tv_judul);
            tvChat = itemView.findViewById(R.id.tv_arti);
            tvPhoto = itemView.findViewById(R.id.tv_photchat);
        }
    }
}
