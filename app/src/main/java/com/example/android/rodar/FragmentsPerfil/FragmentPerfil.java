package com.example.android.rodar.FragmentsPerfil;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.rodar.R;
import com.example.android.rodar.Utils.RetrofitClient;
import com.example.android.rodar.Utils.SPUtil;
import com.example.android.rodar.activities.IMainActivity;
import com.example.android.rodar.activities.LoginActivity;
import com.example.android.rodar.services.UsuarioService;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentPerfil extends Fragment {

    private Button btnLogout,btnDados, btnPromoverTransportador, btnPromoverOrganizador;
    private IMainActivity mainActivity;
    private CircularImageView img;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.perfil,container,false);

        btnLogout = v.findViewById(R.id.perfil_btn_sair);
        btnDados = v.findViewById(R.id.perfil_btn_meus_dados);

        btnLogout.setOnClickListener(logoutListener);
        btnDados.setOnClickListener(meusDadosListener);

        btnPromoverOrganizador = v.findViewById(R.id.perfil_btn_organizador);
        btnPromoverOrganizador.setOnClickListener(promoverOrganizador);

        btnPromoverTransportador = v.findViewById(R.id.perfil_btn_transportador);
        btnPromoverTransportador.setOnClickListener(promoverTransportador);

        img = v.findViewById(R.id.perfil_img);

        ConfiguraTela();

        return v;
    }



    private void ConfiguraTela() {
            if (SPUtil.getTransportador(getContext())) {
                btnPromoverTransportador.setText("Você é um transportador!");
                btnPromoverTransportador.setEnabled(false);
            }

            if (SPUtil.getOrganizador(getContext())){
                btnPromoverOrganizador.setText("Você é um organizador de evento!");
                btnPromoverOrganizador.setEnabled(false);
            }


            String tmp = PreferenceManager.getDefaultSharedPreferences(getContext()).getString("urlImg","");
            if(tmp.isEmpty()){
                img.setVisibility(View.GONE);
            } else {
                Picasso.get().load(getString(R.string.url) + tmp)
                        .into(img);
            }
        }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (IMainActivity) getActivity();
    }

    private View.OnClickListener meusDadosListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mainActivity.inflateFragment("perfil_meusDados",null);
        }
    };

    private View.OnClickListener logoutListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("Tem certeza que deseja sair?");
            builder.setCancelable(true);
            builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SPUtil.saveEmail(null,getActivity());
                    SPUtil.savePassword(null, getActivity());
                    SPUtil.saveToken(null,getActivity());
                    Intent activityIntent = new Intent(getActivity().getApplicationContext(), LoginActivity.class);
                    startActivity(activityIntent);
                    getActivity().finish();
                }
            });

            builder.setNegativeButton("Não", null);
            /*builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });*/

            builder.show();
        }
    };

    private View.OnClickListener promoverTransportador = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("Deseja tornar-se um transportador? Você não poderá mais oferecer " +
                            "caronas, e passará a oferer transportes de maior porte");
            builder.setNegativeButton("Não", null);
            builder.setCancelable(true);
            builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    UsuarioService service = RetrofitClient.getClient().create(UsuarioService.class);
                    Call<ResponseBody> call = service.promoverTransportador(SPUtil.getToken(getContext()));
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(getContext(), "Perfil Atualizado", Toast.LENGTH_LONG).show();
                                SPUtil.saveTransportador(getContext());
                                mainActivity.inflateFragment("perfil",null);
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(getContext(), "ERRO FALHA CONEXAO", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });

            builder.show();
        }
    };

    private View.OnClickListener promoverOrganizador = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("Deseja tornar-se um organizador de eventos? Você poderá criar eventos");
            builder.setCancelable(true);
            builder.setNegativeButton("Não", null);
            builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    UsuarioService service = RetrofitClient.getClient().create(UsuarioService.class);
                    Call<ResponseBody> call = service.promoverOrganizador(SPUtil.getToken(getContext()));
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()){
                                Toast.makeText(getContext(), "Perfil Atualizado", Toast.LENGTH_LONG).show();
                                SPUtil.saveOrganizador(getContext());
                                mainActivity.inflateFragment("perfil",null);
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(getContext(), "ERRO FALHA CONEXAO", Toast.LENGTH_LONG).show();
                        }
                    });

                }
            });
            builder.show();
        }
    };

}
