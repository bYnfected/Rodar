package com.example.android.rodar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.rodar.activities.IMainActivity;
import com.example.android.rodar.models.Evento;

@SuppressLint("ValidFragment")
public class FragmentEventoDetalhe extends Fragment {

    private IMainActivity mainActivity;
    CollapsingToolbarLayout collapsingToolbarLayout;
    private Evento evento;

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private TextView dataHrIni,dataHrFim, local;

    private FloatingActionButton btnCriaTranspCarona;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_evento_detalhe, container, false);

        evento = (Evento) getArguments().getSerializable("evento");

        dataHrIni = v.findViewById(R.id.evento_detalhe_dataHoraIni);
        dataHrFim = v.findViewById(R.id.evento_detalhe_dataHoraFim);
        local = v.findViewById(R.id.evento_detalhe_local);


        dataHrIni.setText(evento.getDataHoraInicio());
        dataHrFim.setText(evento.getDataHoraTermino());
        local.setText(evento.getEnderecoRua());

        collapsingToolbarLayout = v.findViewById(R.id.evento_detalhe_titulo);
        collapsingToolbarLayout.setTitle(evento.getNomeEvento());
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        mViewPager = v.findViewById(R.id.evento_detalhe_viewPager);
        setupViewPager(mViewPager);
        // Como o viewPager ja esta inicializado com os fragments, basta setar ele como fonte das abas
        mTabLayout = v.findViewById(R.id.evento_detalhe_tabLayout);
        mTabLayout.setupWithViewPager(mViewPager);

        // Botao para criar carona OU transporte conforme o tipo de usuario
        btnCriaTranspCarona = v.findViewById(R.id.evento_detalhe_criaTranspCarona);
        btnCriaTranspCarona.setOnClickListener(criaTranspCarona);
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (IMainActivity) getActivity();
    }

    // Configura quais os fragments para as abas
    private void setupViewPager(ViewPager viewPager) {
        EventoDetalhePageAdapter adapter = new EventoDetalhePageAdapter(getChildFragmentManager());
        Bundle bundle = new Bundle();
        bundle.putInt("idEvento",evento.getIdEvento());

        FragmentEventoTransportes fragmentTransportes = new FragmentEventoTransportes();
        fragmentTransportes.setArguments(bundle);
        FragmentEventoCaronas fragmentCaronas = new FragmentEventoCaronas();
        fragmentCaronas.setArguments(bundle);

        adapter.addFragment(fragmentTransportes, "Transportes");
        adapter.addFragment(fragmentCaronas, "Caronas");
        // Seta esse adapter para o viewPager
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            // Verifica se deve ou nao exibir o botao conforme perfil do usuario
            public void onPageSelected(int i) {
                if (((i == 0) && (true)) || ((i == 1) && (i==2)))
                    btnCriaTranspCarona.show();
                else
                    btnCriaTranspCarona.hide();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }

    View.OnClickListener criaTranspCarona = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            Bundle bundle  = new Bundle();
            // Testar aqui se o usuario cria transporte ou carona
            if (mViewPager.getCurrentItem() == 0)
                mainActivity.inflateFragment("criaTransporte",bundle);
            else
                mainActivity.inflateFragment("criaCarona",bundle);
        }
    };

}
