package cat.itb.pixiv.Fragments.LoginFragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


import cat.itb.pixiv.FireBase.FireBaseHelper;
import cat.itb.pixiv.Fragments.HomeFragment;
import cat.itb.pixiv.R;

public class FragmentLogin extends Fragment {

    TextView title;
    TextInputLayout loginInput;
    TextInputEditText loginEditText;
    TextInputLayout passwordInput;
    TextInputEditText passwordEditText;
    Button login;
    Button register;
    FireBaseHelper fireBaseHelper;
    SharedPreferences.Editor editor;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login,container,false);
        title = v.findViewById(R.id.LoginTitle);
        loginInput = v.findViewById(R.id.input_layout_login_username);
        loginEditText = v.findViewById(R.id.input_text_login_username);
        passwordInput = v.findViewById(R.id.input_layout_login_password);
        passwordEditText = v.findViewById(R.id.input_text_login_password);
        login = v.findViewById(R.id.loginButtonn);


        if(getArguments()!=null && getArguments().getInt("id")==1){
            hacerlogout();
        }
//        cargarPreferences();



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loginEditText.getText().toString().isEmpty()){
                    loginInput.setError("Empty username");
                } else{
                    loginInput.setError("");
                    if (passwordEditText.getText().toString().isEmpty()){
                        passwordInput.setError("Insert a valid password");
                    } else if(passwordEditText.getText().toString().length()<8) {
                        passwordInput.setError("Insert a valid password");

//                    }else if(){
//
                    } else {
                        fireBaseHelper.compararUserPassword(passwordEditText.getText().toString(),loginEditText.getText().toString());
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                if(comprobarPasUser()){

                                    guardarPreferencias();

                                    passwordInput.setError("");
                                    FragmentManager manager = getFragmentManager();
                                    FragmentTransaction transaction = manager.beginTransaction();
                                    transaction.replace(R.id.fragment_container, new HomeFragment()).commit();
                                }
                            }
                        }, 200);   //5 seconds

                    }
                }
            }
        });

        register = v.findViewById(R.id.registerButton);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
    //                FragmentManager manager = getFragmentManager();
    //                FragmentTransaction transaction = manager.beginTransaction();
    //                transaction.replace(R.id.fragment_container, new FragmentRegister()).commit();

                hacerlogout();
            }
        });
        return v;
    }

    public boolean comprobarPasUser(){

        boolean [] f = fireBaseHelper.userExists;
        System.out.println("fuck");
        if(f[0]){
            if(f[1]){
                return true;
            }else passwordInput.setError("Wrong Password");

        }else loginInput.setError("Wrong user name");

        return false;
    }

    private void guardarPreferencias(){
        Context context = getContext();
        SharedPreferences preferences  =  context.getSharedPreferences("credencials", Context.MODE_PRIVATE);
        String user = loginEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        editor = preferences.edit();
        editor.putString("user",user);
        editor.putString("password", password);

        loginEditText.setText(user);
        passwordEditText.setText(password);

        editor.apply();
        editor.commit();
    }

//    private void cargarPreferences(){
//        Context context = getContext();
//        SharedPreferences preferences  =  context.getSharedPreferences("credencials", Context.MODE_PRIVATE);
//
//        String user = preferences.getString("user","");
//        String password = preferences.getString("password","");
//
//        loginEditText.setText(user);
//        passwordEditText.setText(password);
//    }

    public void hacerlogout(){
        Context context = getContext();
        SharedPreferences preferences  =  context.getSharedPreferences("credencials", Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.clear();
        editor.apply();
    }
}
