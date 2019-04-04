package com.example.android.rodar;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.rodar.activities.IMainActivity;
import com.example.android.rodar.models.Usuario;
import com.example.android.rodar.services.UsuarioService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentMeusDados extends Fragment {

    Button btnSalvar;
    private TextInputLayout nome, sobrenome, genero, cpf, celular, email, senha, senhaConfirma;
    private IMainActivity mainActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.atualiza_cadastro,container,false);

        btnSalvar = v.findViewById(R.id.atualiza_salvar);
        btnSalvar.setOnClickListener(salvarListener);

        nome = v.findViewById(R.id.atualiza_nome);
        sobrenome = v.findViewById(R.id.atualiza_sobrenome);
        cpf = v.findViewById(R.id.atualiza_cpf);
        celular = v.findViewById(R.id.atualiza_celular);
        email = v.findViewById(R.id.atualiza_email);
        senha = v.findViewById(R.id.atualiza_senha);
        senhaConfirma = v.findViewById(R.id.atualiza_senha2);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        carregaDados();
    }

    private void carregaDados() {
        UsuarioService service = RetrofitClient.getClient().create(UsuarioService.class);
        String auth = "Bearer ".concat(PreferenceUtils.getToken(getContext()));
        auth =  auth.replace("\"","");
        Call<Usuario> call = service.getUser(auth,14);


        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful()){
                    Usuario user = response.body();

                    nome.getEditText().setText(user.getNomeCompleto());
                    cpf.getEditText().setText(user.getCPF());
                    celular.getEditText().setText(user.getNumeroTelefone());
                    email.getEditText().setText(user.getEmail());


                }
                else {
                    Toast.makeText(getContext(), "ERRO NOT SUCCESSFUL", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(getContext(), "ERRO FALHA CONEXAO", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (IMainActivity) getActivity();
    }

    private View.OnClickListener salvarListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            // Depois de salvar retorna a tela de perfil
            mainActivity.inflateFragment("perfil","");
        }
    };
}
