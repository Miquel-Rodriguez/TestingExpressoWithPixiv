package cat.itb.pixiv.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;

import cat.itb.pixiv.Adapater.SlideViewAdapter;
import cat.itb.pixiv.BlankFragment;
import cat.itb.pixiv.FireBase.FireBaseHelper;
import cat.itb.pixiv.Fragments.FollowFragments.FollowersFragment;
import cat.itb.pixiv.Fragments.FollowFragments.FollowingFragment;
import cat.itb.pixiv.Fragments.HomeFragments.FragmentHomeIllustrations;
import cat.itb.pixiv.Fragments.HomeFragments.FragmentHomeManga;
import cat.itb.pixiv.Fragments.HomeFragments.FragmentHomeNovels;
import cat.itb.pixiv.Fragments.LoginFragments.FragmentLogin;
import cat.itb.pixiv.Fragments.NavigationDrawerFragments.YourWorksFragment;
import cat.itb.pixiv.Fragments.SubmitWorkFragments.FragmentSubmitIllustrations;
import cat.itb.pixiv.R;
import cat.itb.pixiv.Fragments.SubmitWorkFragments.BottomSheet;
import de.hdodenhof.circleimageview.CircleImageView;

public class HomeFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener {

    private static final int REQUEST_IMAGE_CAPTURE = 100;
    private static final int RESULT_OK = 200;
    ViewPager viewPager;
    TabLayout tabLayout;
    private MaterialToolbar topAppBar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    File url;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private CircleImageView profilePic;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        View header = inflater.inflate(R.layout.header_navigation_drawer, null);

        viewPager = v.findViewById(R.id.slide_view_pager);
        tabLayout= v.findViewById(R.id.tablayout);
        topAppBar= v.findViewById(R.id.top_appbar);
        navigationView= v.findViewById(R.id.navigator_view);
        drawerLayout= v.findViewById(R.id.drawer_layout);
        profilePic = header.findViewById(R.id.profile_image);

        SlideViewAdapter slideViewAdapter=new SlideViewAdapter(getFragmentManager());
        slideViewAdapter.addFragment(FragmentHomeIllustrations.getInstance(),"Illustrations");
        slideViewAdapter.addFragment(FragmentHomeManga.getInstance(),"Manga");
        slideViewAdapter.addFragment(FragmentHomeNovels.getInstance(),"Novels");
        viewPager.setAdapter(slideViewAdapter);
        tabLayout.setupWithViewPager(viewPager);



        actionBarDrawerToggle = new ActionBarDrawerToggle(
                getActivity(),
                drawerLayout,
                topAppBar,
                R.string.openNavDrawer,
                R.string.closeNavDrawer
        );

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        return v;

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && requestCode == RESULT_OK){
            Uri imageUri = CropImage.getPickImageResultUri(getContext(),data);
            recortarImagen(imageUri);

        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            Uri resultUri = result.getUri();
            url = new File(resultUri.getPath());
            Picasso.with(getContext()).load(url).into(profilePic);

            FireBaseHelper.comprimirImatge(getContext(), url);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            FireBaseHelper.subirImagenPerfil(FireBaseHelper.getUrlImage());


        }
        if(requestCode == REQUEST_IMAGE_CAPTURE){
            Uri imageUri = CropImage.getPickImageResultUri(getContext(),data);
            recortarImagen(imageUri);

        }

    }

    public void recortarImagen(Uri imageUri){
        CropImage.activity(imageUri).setGuidelines(CropImageView.Guidelines.ON)
                .setRequestedSize(480,480)
                .start(getContext(), HomeFragment.this);
    }

    /*
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

     */

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new BlankFragment()).commit();
                break;
            case R.id.newest:
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new BlankFragment()).commit();
                break;
            case R.id.search:
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new BlankFragment()).commit();
                break;
            case R.id.submitWork:
                BottomSheet bottomSheet = new BottomSheet();
                bottomSheet.show(getFragmentManager(),
                        "ModalBottomSheet");
                break;
            case R.id.yourWorks:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new YourWorksFragment()).commit();
                break;
            case R.id.logout:
                Bundle bundle = new Bundle();
                bundle.putInt("id",1);
                FragmentLogin fragmentLogin = new FragmentLogin();
                fragmentLogin.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragmentLogin).commit();
                break;
            case R.id.changeImg:
                CropImage.startPickImageActivity(getContext(), HomeFragment.this);
                break;
            case R.id.collection:
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new BlankFragment()).commit();
                break;
            case R.id.browsingHistory:
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new BlankFragment()).commit();
                break;
            case R.id.markers:
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new BlankFragment()).commit();
                break;
            case R.id.following:
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new FollowingFragment()).commit();
                break;
            case R.id.followers:
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new FollowersFragment()).commit();
                break;
            case R.id.myPixiv:
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new BlankFragment()).commit();
                break;
            case R.id.feedback:
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new BlankFragment()).commit();
                break;
            case R.id.help:
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new BlankFragment()).commit();
                break;
            case R.id.settings:
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new BlankFragment()).commit();
                break;
            case R.id.muteSettings:
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new BlankFragment()).commit();
                break;

        }
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
