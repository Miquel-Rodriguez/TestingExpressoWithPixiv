package cat.itb.pixiv.Adapater.AdaptersFirebase;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

import cat.itb.pixiv.ClassesModels.ImatgesP;
import cat.itb.pixiv.ClassesModels.NovelClass;
import cat.itb.pixiv.Fragments.onClickImage.FragmentOCIllustrations;
import cat.itb.pixiv.Fragments.onClickImage.FragmentOCNovels;
import cat.itb.pixiv.R;

public class AdapterNovelsRecommended extends FirebaseRecyclerAdapter<NovelClass, AdapterNovelsRecommended.ViewHolderNovelsRecommended> {

    private NovelClass model;
    private Context context;

    public Context getContext() {
        return context;
    }
    public void setContext(Context context) {
        this.context = context;
    }

    public AdapterNovelsRecommended(@NonNull FirebaseRecyclerOptions<NovelClass> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull AdapterNovelsRecommended.ViewHolderNovelsRecommended holder, int position, @NonNull NovelClass model) {
        this.model = model;
        holder.bind(model);
    }

    @NonNull
    @Override
    public AdapterNovelsRecommended.ViewHolderNovelsRecommended onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdapterNovelsRecommended.ViewHolderNovelsRecommended(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_novels_recommended,parent,false));
    }

    class ViewHolderNovelsRecommended extends RecyclerView.ViewHolder {

        ImageView imageViewimage, imageViewlike;
        TextView textViewTitle, textViewDescription, textViewNumlikes;

        public ViewHolderNovelsRecommended(@NonNull View itemView) {
            super(itemView);

            imageViewimage = itemView.findViewById(R.id.image_view_novels_recommended);
            imageViewlike = itemView.findViewById(R.id.image_view_novels_recommended_like);
            textViewTitle = itemView.findViewById(R.id.text_view_novels_recommended_title);
            textViewDescription = itemView.findViewById(R.id.text_view_novels_recommended_description);
            textViewNumlikes = itemView.findViewById(R.id.text_view_novels_recommended_numlikes);

        }

        public void bind(final NovelClass novel){
            Picasso.with(getContext()).load(model.getNovelImgUrl()).into(imageViewimage);
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

            textViewTitle.setText(model.getTitle());
            textViewDescription.setText(model.getDescription());
            textViewNumlikes.setText(String.valueOf(model.getLikesNumber()));

            imageViewimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(novel!=null){
                        Bundle argument=new Bundle();
                        argument.putParcelable("novelRecomended",novel);
                        AppCompatActivity context=(AppCompatActivity)v.getContext();
                        FragmentOCNovels fragmentnovels=new FragmentOCNovels();
                        fragmentnovels.setArguments(argument);
                        context.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragmentnovels).commit();
                    }
                }
            });
        }
    }
}
