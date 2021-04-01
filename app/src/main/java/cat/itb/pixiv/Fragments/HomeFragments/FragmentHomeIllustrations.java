package cat.itb.pixiv.Fragments.HomeFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;

import cat.itb.pixiv.Adapater.AdaptersFirebase.AdapterIlustrationsRecomended;
import cat.itb.pixiv.Adapater.AdaptersFirebase.AdapterPopularLives;
import cat.itb.pixiv.Adapater.AdaptersFirebase.AdapterRankingIllustrations;
import cat.itb.pixiv.ClassesModels.IllustrationClass;
import cat.itb.pixiv.ClassesModels.IllustrationPLClass;
import cat.itb.pixiv.FireBase.FireBaseHelper;
import cat.itb.pixiv.R;

public class FragmentHomeIllustrations extends Fragment {


public static FragmentHomeIllustrations getInstance(){
    return new FragmentHomeIllustrations();
}

    RecyclerView recyclerView;
    AdapterRankingIllustrations adapterRankingIlus;
    AdapterIlustrationsRecomended adapterRecomended;
    AdapterPopularLives adapterPopularLives;

    DatabaseReference myRef;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home_illustrations, container, false);
        FireBaseHelper.setAllReferences();

//        List<ImatgesP> imageslist = new ArrayList<>();
//        imageslist.add(new ImatgesP("title","description","user",R.raw.img1,2,2,2,R.raw.img6,R.raw.img11));
//        imageslist.add(new ImatgesP("title","description","user",R.raw.img2,2,2,2,R.raw.img7,R.raw.img12));
//        imageslist.add(new ImatgesP("title","description","user",R.raw.img3,2,2,2,R.raw.img8,R.raw.img13));
//        imageslist.add(new ImatgesP("title","description","user",R.raw.img4,2,2,2,R.raw.img9,R.raw.img14));
//        imageslist.add(new ImatgesP("title","description","user",R.raw.img5,2,2,2,R.raw.img10,R.raw.img15));






        recyclerView = rootView.findViewById(R.id.recycler_view_illustrations_ranking);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        FirebaseRecyclerOptions<IllustrationClass> options = new FirebaseRecyclerOptions.Builder<IllustrationClass>()
                .setQuery(FireBaseHelper.getReferenceIllustrationsRanking(), IllustrationClass.class).build();
        adapterRankingIlus = new AdapterRankingIllustrations(options);
        adapterRankingIlus.setContext(getContext());
        recyclerView.setAdapter(adapterRankingIlus);



        recyclerView = rootView.findViewById(R.id.recycler_view_illustrations_popular_lives);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        FirebaseRecyclerOptions<IllustrationPLClass> options2 = new FirebaseRecyclerOptions.Builder<IllustrationPLClass>()
                .setQuery(FireBaseHelper.getReferenceIllustrationsPopularLives(), IllustrationPLClass.class).build();
        adapterPopularLives = new AdapterPopularLives(options2);
        adapterPopularLives.setContext(getContext());
        recyclerView.setAdapter(adapterPopularLives);




        recyclerView = rootView.findViewById(R.id.recycler_view_illustrations_recommended);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        FirebaseRecyclerOptions<IllustrationClass> options3 = new FirebaseRecyclerOptions.Builder<IllustrationClass>()
                .setQuery(FireBaseHelper.getReferenceIllustrationsRecommended(), IllustrationClass.class).build();
        adapterRecomended = new AdapterIlustrationsRecomended(options3);
        adapterRecomended.setContext(getContext());
        recyclerView.setAdapter(adapterRecomended);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapterRecomended.startListening();
        adapterPopularLives.startListening();
        adapterRankingIlus.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapterRecomended.stopListening();
        adapterPopularLives.stopListening();
        adapterRankingIlus.stopListening();
    }
  }

