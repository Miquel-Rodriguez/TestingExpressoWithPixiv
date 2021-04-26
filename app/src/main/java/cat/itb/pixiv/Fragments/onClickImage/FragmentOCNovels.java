package cat.itb.pixiv.Fragments.onClickImage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

import cat.itb.pixiv.ClassesModels.MangaClass;
import cat.itb.pixiv.ClassesModels.NovelClass;
import cat.itb.pixiv.Fragments.HomeFragment;
import cat.itb.pixiv.Fragments.HomeFragments.FragmentHomeNovels;
import cat.itb.pixiv.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class FragmentOCNovels extends Fragment {
    CircleImageView userimage;
    TextView username,title,description;
    MaterialButton exitButton,favbutton;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_o_c_novels, container, false);
        userimage=v.findViewById(R.id.novel_oc_ProfileImage);
        username=v.findViewById(R.id.novel_oc_username);
        title=v.findViewById(R.id.novel_oc_title);
        description=v.findViewById(R.id.novel_oc_description);
        exitButton=v.findViewById(R.id.novel_oc_close);
        favbutton=v.findViewById(R.id.novel_oc_favbutton);
        Bundle arguments=getArguments();
        NovelClass novel =arguments.getParcelable("novelRecomended");
        if(novel!=null){
            setNovels(novel);
        }else if(novel==null){
            novel=arguments.getParcelable("novelRanking");
            setNovels(novel);
        }

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentHomeNovels()).commit();

            }
        });

        favbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return v;
    }

    private void setNovels(NovelClass novelc){
        Picasso.with(getActivity()).load(novelc.getUserImgUrl()).into(userimage);
        username.setText(novelc.getUsername());
        title.setText(novelc.getTitle());
        description.setText(novelc.getDescription());
    }
}