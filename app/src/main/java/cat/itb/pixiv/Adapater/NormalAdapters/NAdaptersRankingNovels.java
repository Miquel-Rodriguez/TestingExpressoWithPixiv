package cat.itb.pixiv.Adapater.NormalAdapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import cat.itb.pixiv.ClassesModels.ImatgesP;
import cat.itb.pixiv.R;

public class NAdaptersRankingNovels extends RecyclerView.Adapter<NAdaptersRankingNovels.NAViewHolder>{

    private List<ImatgesP> imagesList;

    public NAdaptersRankingNovels(List<ImatgesP> imagesList) {
        this.imagesList = imagesList;
    }

    @NonNull
    @Override
    public NAdaptersRankingNovels.NAViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_novels_ranking, parent,false);
        return new NAdaptersRankingNovels.NAViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NAdaptersRankingNovels.NAViewHolder holder, int position) {
        holder.binData(imagesList.get(position));
    }

    @Override
    public int getItemCount() {
        return imagesList.size();
    }


    class NAViewHolder extends RecyclerView.ViewHolder{
        ImageView imageViewimage, imageViewlike;
        TextView textViewTitle, textViewDescription, textViewUser;
        public NAViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewimage = itemView.findViewById(R.id.image_view_novels_ranking);
            imageViewlike = itemView.findViewById(R.id.heart_novel_ranking);
            textViewTitle = itemView.findViewById(R.id.text_view_novels_rankings_title);
            textViewDescription = itemView.findViewById(R.id.text_view_novels_rankings_description);
            textViewUser = itemView.findViewById(R.id.text_view_novels_rankings_user);
        }

        public  void binData(ImatgesP imatgesP){
            imageViewimage.setImageResource(R.drawable.ic_launcher_background);
            final boolean[] heart = {false};
            imageViewlike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(heart[0]){
                        imageViewlike.setImageResource(R.drawable.likeheartwhite);
                    }else imageViewlike.setImageResource(R.drawable.likeheartred);
                    heart[0] = !heart[0];
                }
            });

            textViewTitle.setText(imatgesP.getTitle());
            textViewDescription.setText(imatgesP.getDescription());
            textViewUser.setText(imatgesP.getUser());
        }
    }

}

