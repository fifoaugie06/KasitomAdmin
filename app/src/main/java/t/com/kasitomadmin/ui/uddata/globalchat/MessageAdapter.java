package t.com.kasitomadmin.ui.uddata.globalchat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import t.com.kasitomadmin.R;
import t.com.kasitomadmin.model.dataChatMessage;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    private ArrayList<dataChatMessage> dataChatMessages;

    public MessageAdapter(ArrayList<dataChatMessage> dataChatMessages) {
        this.dataChatMessages = dataChatMessages;
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
        final String nama = dataChatMessages.get(position).getMessageUser();
        final String chat = dataChatMessages.get(position).getMessageText();
        final String photoUri = dataChatMessages.get(position).getMessageUserPhotoUri();

        SimpleDateFormat formatter = new SimpleDateFormat("h:mm a");
        String date = formatter.format(dataChatMessages.get(position).getMessageUserTime());

        holder.tvTime.setText(date);
        holder.tvNama.setText(nama);
        holder.tvChat.setText(chat);
        Picasso.get()
                .load(photoUri)
                .centerInside()
                .resize(150, 150)
                .into(holder.tvPhoto);
    }

    @Override
    public int getItemCount() {
        return dataChatMessages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNama, tvChat, tvTime;
        ImageView tvPhoto;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.tv_judul);
            tvChat = itemView.findViewById(R.id.tv_arti);
            tvPhoto = itemView.findViewById(R.id.tv_photchat);
            tvTime = itemView.findViewById(R.id.tv_time);
        }
    }
}
