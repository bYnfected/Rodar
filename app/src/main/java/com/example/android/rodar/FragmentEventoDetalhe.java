package com.example.android.rodar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.rodar.R;

@SuppressLint("ValidFragment")
public class FragmentEventoDetalhe extends Fragment {

    CollapsingToolbarLayout teste;
    private String testeTitulo;

    private EventoDetalhePageAdapter eventoDetalhePageAdapter;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    @SuppressLint("ValidFragment")
    public FragmentEventoDetalhe(String testeTitulo) {
        this.testeTitulo = testeTitulo;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_evento_detalhe, container, false);

        teste = v.findViewById(R.id.evento_detalhe_titulo);
        teste.setTitle(testeTitulo);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        mViewPager = v.findViewById(R.id.evento_detalhe_viewPager);
        setupViewPager(mViewPager);
        // Como o viewPager ja esta inicializado com os fragments, basta setar ele como fonte das abas
        mTabLayout = v.findViewById(R.id.evento_detalhe_tabLayout);
        mTabLayout.setupWithViewPager(mViewPager);

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();

    }

    // Configura quais os fragments para as abas
    private void setupViewPager(ViewPager viewPager) {
        EventoDetalhePageAdapter adapter = new EventoDetalhePageAdapter(getFragmentManager());

        adapter.addFragment(new FragmentEventoTransportes(), "Transportes");
        adapter.addFragment(new FragmentEventoCaronas(), "Caronas");
        // Seta esse adapter para o viewPager
        viewPager.setAdapter(adapter);

    }
}
