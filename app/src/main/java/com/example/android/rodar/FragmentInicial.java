package com.example.android.rodar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.rodar.activities.ILoginActivity;
import com.example.android.rodar.models.Usuario;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class FragmentInicial extends Fragment {

    private Button cadastra,entra;
    private LoginButton loginButtonFB;
    private TextInputLayout email,senha;
    private CallbackManager callbackManager;
    private ILoginActivity loginActivity;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.pagina_inicial, container, false);
        cadastra = v.findViewById(R.id.inicial_cadastrar) ;
        entra = v.findViewById(R.id.inicial_entrar);
        email = v.findViewById(R.id.inicial_email_cpf);
        senha = v.findViewById(R.id.inicial_senha);

        cadastra.setOnClickListener(cadastraListener);
        entra.setOnClickListener(entraListener);

        loginButtonFB = v.findViewById(R.id.inicial_button_fb);
        loginButtonFB.setFragment(this);
        callbackManager = CallbackManager.Factory.create();
        loginButtonFB.setReadPermissions(Arrays.asList("email","public_profile"));

        loginButtonFB.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        loginActivity = (ILoginActivity) getActivity();
    }

    private View.OnClickListener cadastraListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.inicial_fragment_container, new FragmentCadastro());
            transaction.addToBackStack(null);
            transaction.commit();
        }
    };

    private View.OnClickListener entraListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            PreferenceUtils.saveEmail(email.getEditText().getText().toString(), getContext());
            PreferenceUtils.savePassword(senha.getEditText().getText().toString(),getContext());
            loginActivity.loginUsuario();
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode,resultCode,data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    AccessTokenTracker tokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            if (currentAccessToken == null){
                Toast.makeText(getContext(), "Usuário Deslogado", Toast.LENGTH_LONG).show();
            }
            else {
                loadUserProfile(currentAccessToken);
            }

        }
    };

    private void loadUserProfile(AccessToken newAccessToken){
        GraphRequest request = GraphRequest.newMeRequest(newAccessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    Usuario usuario = new Usuario();
                    usuario.setNome(object.getString("first_name") + " " + object.getString("last_name"));
                    usuario.setEmail(object.getString("email"));
                    usuario.setFacebookId(object.getString("id"));



                    //String image_url = "https://graph.facebook.com/"+id+ "/picture?type=normal";


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });

        Bundle paramenters = new Bundle();
        paramenters.putString("fields","first_name,last_name,email,id");
        request.setParameters(paramenters);
        request.executeAsync();

    }


}
