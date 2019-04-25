package com.example.android.rodar.FragmentsPerfil;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.android.rodar.R;
import com.example.android.rodar.Utils.PreferenceUtils;
import com.example.android.rodar.activities.IMainActivity;
import com.example.android.rodar.activities.LoginActivity;

public class FragmentPerfil extends Fragment {

    private Button btnLogout,btnDados;
    private IMainActivity mainActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.perfil,container,false);

        btnLogout = v.findViewById(R.id.perfil_btn_sair);
        btnDados = v.findViewById(R.id.perfil_btn_meus_dados);

        btnLogout.setOnClickListener(logoutListener);
        btnDados.setOnClickListener(meusDadosListener);
        return v;
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
                    PreferenceUtils.saveEmail(null,getActivity());
                    PreferenceUtils.savePassword(null, getActivity());
                    PreferenceUtils.saveToken(null,getActivity());
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

}
