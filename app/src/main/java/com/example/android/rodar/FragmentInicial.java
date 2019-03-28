package com.example.android.rodar;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.rodar.activities.LoginActivity;
import com.example.android.rodar.activities.MainActivity;
import com.example.android.rodar.models.Usuario;
import com.example.android.rodar.models.UsuarioLogin;
import com.example.android.rodar.services.UsuarioService;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentInicial extends Fragment {

    private Button cadastra,entra;
    private LoginButton loginButtonFB;
    private TextInputLayout email,senha;
    private CallbackManager callbackManager;


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
            UsuarioLogin usuario = new UsuarioLogin();
            usuario.setUsername(email.getEditText().getText().toString());
            usuario.setPassword(senha.getEditText().getText().toString());
            //usuario.setGrant_type("password");

            UsuarioService usrService = RetrofitClient.getClient().create(UsuarioService.class);

            Call<JsonObject> call = usrService.loginUser(email.getEditText().getText().toString(),senha.getEditText().getText().toString(),"password");
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    JsonObject teste = response.body();
                    if (teste.has("access_token")) {
                        PreferenceUtils.saveEmail(email.getEditText().getText().toString(), getContext());
                        PreferenceUtils.savePassword(senha.getEditText().getText().toString(),getContext());
                        PreferenceUtils.saveToken(teste.get("access_token").toString(), getContext());

                        Intent activityIntent = new Intent(getContext(), MainActivity.class);
                        startActivity(activityIntent);
                        //finish();
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {

                }
            });



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
                    usuario.setNomeCompleto(object.getString("first_name") + " " + object.getString("last_name"));
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