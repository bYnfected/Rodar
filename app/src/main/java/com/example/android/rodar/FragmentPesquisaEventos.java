package com.example.android.rodar;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.example.android.rodar.Utils.RetrofitClient;
import com.example.android.rodar.Utils.SPUtil;
import com.example.android.rodar.activities.IMainActivity;
import com.example.android.rodar.services.EventoService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentPesquisaEventos extends Fragment {

    private IMainActivity mainActivity;
    AutoCompleteTextView autoCompleteTextView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pesquisa_evento, container, false);
        autoCompleteTextView = view.findViewById(R.id.pesquisa_evento_cidade);

        carregaCidades();
        return view;
    }

    private void carregaCidades() {
        EventoService service = RetrofitClient.getClient().create(EventoService.class);
        Call<List<String>> call = service.getCidadesEventos(SPUtil.getToken(getContext()));
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful()){
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                            android.R.layout.simple_dropdown_item_1line, response.body());

                    autoCompleteTextView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {

            }
        });
    }

    /*private static final String[] CIDADES  = new String[] {
            "Caxias do Sul", "Bento Gonçalves", "Farroupilha", "Flores da Cunha", "Gramado"
    };*/



}
