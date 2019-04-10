package com.example.android.rodar;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.rodar.Utils.PreferenceUtils;
import com.example.android.rodar.Utils.RetrofitClient;
import com.example.android.rodar.activities.IMainActivity;
import com.example.android.rodar.models.Usuario;
import com.example.android.rodar.services.UsuarioService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentEventos extends Fragment {

    private IMainActivity mainActivity;
    private ArrayList<String> mEventos = new ArrayList<>();
    private ArrayList<String> mCidades = new ArrayList<>();
    private ArrayList<String> mDatas = new ArrayList<>();

    private FloatingActionButton btnCadastro;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_eventos, container, false);

        btnCadastro = v.findViewById(R.id.cria_evento_btn);
        btnCadastro.setOnClickListener(criaEventoListener);

        InicilizaLista();

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (IMainActivity) getActivity();
    }
    private View.OnClickListener criaEventoListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            mainActivity.inflateFragment("eventos_criaEvento","");
        }
    };

    private void InicilizaLista(){
        /*mEventos.add("Festa da Uva");
        mEventos.add("Show Racionais MC");
        mEventos.add("Show Comunidade Nin Jitsu");
        mEventos.add("Mano Lima ft Gelain");

        mCidades.add("Caxias do Sul");
        mCidades.add("Caxias do Sul");
        mCidades.add("MALIBU DISCO PUD");
        mCidades.add("BAR DO BRANCHINI");

        mDatas.add("11");
        mDatas.add("12");
        mDatas.add("11/11/2016");
        mDatas.add("04/04/2019");*/

        UsuarioService service = RetrofitClient.getClient().create(UsuarioService.class);

        Call<Usuario> call = service.getUser(PreferenceUtils.getToken(getContext()));

        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {

                if (!response.isSuccessful()){
                    Toast.makeText(getContext(), "BAD", Toast.LENGTH_LONG).show();
                    return;
                }
                // TUDO CERTO AQUI
                    Usuario user = response.body();


                    mCidades.add(user.getSenha());
                    mDatas.add(user.getGenero());
                    mEventos.add(user.getEmail());

                    Toast.makeText(getContext(), "CARREGOU USERS", Toast.LENGTH_LONG).show();
                    RecyclerView recyclerView = getView().findViewById(R.id.recycler_view_eventos);

                    AdapterListaEventos adapter = new AdapterListaEventos(mEventos,mCidades,mDatas,getView().getContext());

                    recyclerView.setAdapter(adapter);

                    recyclerView.setLayoutManager(new LinearLayoutManager(getView().getContext()));

            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {

                Toast.makeText(getContext(), "ERRO FALHA", Toast.LENGTH_LONG).show();
            }
        });

    }
}

