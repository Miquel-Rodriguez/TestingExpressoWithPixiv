package cat.itb.pixiv.Fragments.SubmitWorkFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;

import cat.itb.pixiv.ClassesModels.NovelClass;
import cat.itb.pixiv.FireBase.FireBaseHelper;
import cat.itb.pixiv.Fragments.HomeFragment;
import cat.itb.pixiv.R;

public class FragmentSubmitNovels extends Fragment {

    Button submit;
    MaterialToolbar toolbar;
    FireBaseHelper fireBaseHelper;
    TextInputEditText title;
    TextInputEditText description;
    TextInputEditText content;
    RadioGroup radioGroup;
    public int pubpriv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.submit_work_novels, container, false);

        submit = v.findViewById(R.id.submitNovels);
        toolbar = v.findViewById(R.id.toolbar_subitwork_novels);
        title = v.findViewById(R.id.edit_text_title_submit_novel);
        description = v.findViewById(R.id.edit_text_description_submit_novel);
        content = v.findViewById(R.id.edit_text_content_submit_novel);
        radioGroup = v.findViewById(R.id.group_radio_button_submit_novels);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.radio_button_public_novels){

                    pubpriv = 0;
                }else{

                }
                pubpriv = 1;
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fireBaseHelper.subirMyWork(new NovelClass( 1, title.getText().toString(), description.getText().toString(), content.getText().toString(), FireBaseHelper.getThisUser().getUsername(),  FireBaseHelper.getUrlImage(), FireBaseHelper.getDefaultUserImage()));

                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.fragment_container, new HomeFragment()).commit();
            }
        });



        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new cat.itb.pixiv.Fragments.HomeFragment()).commit();
            }});



        return v;
    }

}

