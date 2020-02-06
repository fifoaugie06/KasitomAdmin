package t.com.kasitomadmin.ui.uddata.quizud;

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
import t.com.kasitomadmin.model.dataScoreBoard;

public class AdapterDialogScoreboard extends RecyclerView.Adapter<AdapterDialogScoreboard.ViewHolder> {
    private ArrayList<dataScoreBoard> dataScoreBoards;

    public AdapterDialogScoreboard(ArrayList<dataScoreBoard> dataScoreBoards) {
        this.dataScoreBoards = dataScoreBoards;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_datascoreboard, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final String nama = dataScoreBoards.get(position).getNama();
        final String photoUri = dataScoreBoards.get(position).getPhotoURI();
        final String correct = dataScoreBoards.get(position).getCorrect();
        final String size = dataScoreBoards.get(position).getSize();
        final String score = dataScoreBoards.get(position).getNilai();

        SimpleDateFormat formatter = new SimpleDateFormat("MMM d, yyyy 'at' h:mm a");
        final String date = formatter.format(dataScoreBoards.get(position).getScoreUserTime());

        final int wrong = (Integer.parseInt(size) - Integer.parseInt(correct));

        holder.tv_nama.setText(nama);
        holder.tv_tanggal.setText(date);
        holder.tv_correctVal.setText(correct);
        holder.tv_wrongVal.setText(String.valueOf(wrong));
        holder.tv_score.setText(score);
        Picasso.get()
                .load(photoUri)
                .centerInside()
                .resize(350, 350)
                .into(holder.img_photo);
    }

    @Override
    public int getItemCount() {
        return dataScoreBoards.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_nama, tv_correctVal, tv_wrongVal, tv_score, tv_tanggal;
        ImageView img_photo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_photo = itemView.findViewById(R.id.img_photo);
            tv_nama = itemView.findViewById(R.id.tv_nama);
            tv_correctVal = itemView.findViewById(R.id.tv_correct_value);
            tv_wrongVal = itemView.findViewById(R.id.tv_wrong_value);
            tv_score = itemView.findViewById(R.id.tv_score_value);
            tv_tanggal = itemView.findViewById(R.id.tv_tanggal);
        }
    }
}
