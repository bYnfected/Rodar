package com.example.android.rodar.FragmentsCarona;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.rodar.R;
import com.example.android.rodar.Utils.RetrofitClient;
import com.example.android.rodar.Utils.SPUtil;
import com.example.android.rodar.activities.IMainActivity;
import com.example.android.rodar.adapters.AdapterListaCaronas;
import com.example.android.rodar.models.Carona;
import com.example.android.rodar.services.CaronaService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentEventoCaronas extends Fragment {

    private IMainActivity mainActivity;
    private  List<Carona> caronas;
    private int idEvento;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_evento_caronas,container,false);
        idEvento = getArguments().getInt("idEvento");

        CarregaCaronas();

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (IMainActivity) getActivity();
    }

    private void CarregaCaronas() {
        CaronaService service = RetrofitClient.getClient().create(CaronaService.class);
        Call<List<Carona>> call = service.getCaronasEvento(SPUtil.getToken(getContext()),idEvento);
        call.enqueue(new Callback<List<Carona>>() {
            @Override
            public void onResponse(Call<List<Carona>> call, Response<List<Carona>> response) {
                if (response.isSuccessful()){
                    caronas = response.body();
                    RecyclerView recyclerView = getView().findViewById(R.id.recycler_view_evento_detalhe_caronas);
                    AdapterListaCaronas adapter = new AdapterListaCaronas(caronas, listenerCaronas);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getView().getContext()));
                }
            }

            @Override
            public void onFailure(Call<List<Carona>> call, Throwable t) {
                Toast.makeText(getContext(), "ERRO FALHA", Toast.LENGTH_LONG).show();
            }
        });
    }

    AdapterListaCaronas.OnCaronaClickListener listenerCaronas = new AdapterListaCaronas.OnCaronaClickListener() {

        @Override
        public void onCaronaClick(int position) {
            // Listener do card, retorna o objeto da carona
            Bundle bundle = new Bundle();
            bundle.putSerializable("carona", caronas.get(position));
            bundle.putBoolean("ativo",true);
            mainActivity.inflateFragment("participaCarona",bundle);
        }
    };
}
