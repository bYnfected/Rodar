package com.example.android.rodar;

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

import com.example.android.rodar.Utils.PreferenceUtils;
import com.example.android.rodar.Utils.RetrofitClient;
import com.example.android.rodar.adapters.AdapterListaCaronas;
import com.example.android.rodar.models.Carona;
import com.example.android.rodar.services.CaronaService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FragmentEventoCaronas extends Fragment {

    private  List<Carona> caronas;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_evento_caronas,container,false);

        CarregaCaronas();

        return v;
    }

    private void CarregaCaronas() {
        CaronaService service = RetrofitClient.getClient().create(CaronaService.class);
        Call<List<Carona>> call = service.getCaronas(PreferenceUtils.getToken(getContext()));
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
            Toast.makeText(getContext(), "CLICOU CARONA", Toast.LENGTH_LONG).show();
        }
    };
}
