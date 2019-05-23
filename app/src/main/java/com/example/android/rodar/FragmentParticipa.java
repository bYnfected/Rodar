package com.example.android.rodar;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.rodar.adapters.AdapterTranspCarona;

public class FragmentParticipa extends Fragment {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_participa, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        mViewPager = v.findViewById(R.id.fragment_participa_viewPager);
        setupViewPager();

        mTabLayout = v.findViewById(R.id.fragment_participa_tabLayout);
        mTabLayout.setupWithViewPager(mViewPager);

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
    }

    private void setupViewPager() {
        PageAdapterPadrao adapter = new PageAdapterPadrao(getChildFragmentManager());

        FragmentParticipaAtivos fragmentAtivos = new FragmentParticipaAtivos();
        FragmentParticipaHistorico fragmentHistorico = new FragmentParticipaHistorico();

        adapter.addFragment(fragmentAtivos,"Ativos");
        adapter.addFragment(fragmentHistorico,"Histórico");

        mViewPager.setAdapter(adapter);

    }

}
