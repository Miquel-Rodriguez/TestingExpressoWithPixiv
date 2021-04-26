package cat.itb.pixiv.Adapater.AdaptersFirebase;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

import cat.itb.pixiv.ClassesModels.User;
import cat.itb.pixiv.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterFollowing extends FirebaseRecyclerAdapter<User, AdapterFollowing.ViewHolderFollowing> {

    private User model;
    private Context context;

    public Context getContext() {
        return context;
    }
    public void setContext(Context context) {
        this.context = context;
    }

    public AdapterFollowing(@NonNull FirebaseRecyclerOptions<User> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull AdapterFollowing.ViewHolderFollowing holder, int position, @NonNull User model) {
        this.model = model;
        holder.bind();
    }

    @NonNull
    @Override
    public AdapterFollowing.ViewHolderFollowing onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    class ViewHolderFollowing extends RecyclerView.ViewHolder {

        CircleImageView imageViewFollowers;
        TextView textViewUsername;
        MaterialButton followButton;
        ImageView image1;
        ImageView image2;
        ImageView image3;

        public ViewHolderFollowing(@NonNull View itemView) {
            super(itemView);


        }

        public void bind(){
            System.out.println("111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111");
            Picasso.with(getContext()).load(model.getImatgePerfil()).into(imageViewFollowers);
            textViewUsername.setText(model.getUsername());
            followButton = itemView.findViewById(R.id.followButtonFollowing);
            image1 = itemView.findViewById(R.id.img_following_1);
            image2 = itemView.findViewById(R.id.img_following_2);
            image3 = itemView.findViewById(R.id.img_following_3);

        }
    }
}