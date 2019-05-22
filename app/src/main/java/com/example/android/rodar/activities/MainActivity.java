package com.example.android.rodar.activities;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.android.rodar.FragmentAtivos;
import com.example.android.rodar.FragmentsCarona.FragmentCriaCarona;
import com.example.android.rodar.FragmentCriaEvento;
import com.example.android.rodar.FragmentsCarona.FragmentParticipaCarona;
import com.example.android.rodar.FragmentsTransporte.FragmentCriaTransporte;
import com.example.android.rodar.FragmentEventoDetalhe;
import com.example.android.rodar.FragmentEventos;
import com.example.android.rodar.FragmentMensagens;
import com.example.android.rodar.FragmentPesquisaEventos;
import com.example.android.rodar.FragmentsPerfil.FragmentMeusDados;
import com.example.android.rodar.FragmentsPerfil.FragmentPerfil;
import com.example.android.rodar.FragmentTransportesParticipei;
import com.example.android.rodar.FragmentsTransporte.FragmentParticipaTransporte;
import com.example.android.rodar.R;
import com.example.android.rodar.Utils.SPUtil;

public class MainActivity extends AppCompatActivity implements IMainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.botton_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        SPUtil.getTipoUsuario(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new FragmentEventos()).commit();
    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;

                    switch (menuItem.getItemId()){
                        case R.id.nav_eventos :
                            selectedFragment = new FragmentEventos();
                            break;
                        case R.id.nav_transportes :
                        //    selectedFragment = new FragmentTransportesParticipei();
                            selectedFragment = new FragmentAtivos();
                            break;
                        case R.id.nav_mensagens :
                            selectedFragment = new FragmentCriaCarona();
                            break;
                        case R.id.nav_perfil :
                            selectedFragment = new FragmentPerfil();
                            break;
                    }


                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;

                }
            };

    private void doFragmentTransaction(Fragment fragment,Bundle bundle){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        fragment.setArguments(bundle);
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void setToolbarTitle(String fragmentTag) {

    }

    @Override
    public void inflateFragment(String fragment, Bundle bundle) {
        if (fragment == "eventos"){
            FragmentEventos novoFragment = new FragmentEventos();
            doFragmentTransaction(novoFragment, bundle);
        }
        else if (fragment == "mensagens"){
            FragmentMensagens novoFragment = new FragmentMensagens();
            doFragmentTransaction(novoFragment, bundle);
        }
        else if (fragment == "perfil"){
            FragmentPerfil novoFragment = new FragmentPerfil();
            doFragmentTransaction(novoFragment,bundle);
        }
        else if (fragment == "perfil_meusDados"){
            FragmentMeusDados novoFragment = new FragmentMeusDados();
            doFragmentTransaction(novoFragment,bundle);
        }
        else if (fragment == "eventos_criaEvento"){
            FragmentCriaEvento novoFragment = new FragmentCriaEvento();
            doFragmentTransaction(novoFragment,bundle);
        }
        else if (fragment == "eventos_criaEvento"){
            FragmentCriaEvento novoFragment = new FragmentCriaEvento();
            doFragmentTransaction(novoFragment,bundle);
        }
        else if (fragment == "eventos_criaEvento"){
            FragmentCriaEvento novoFragment = new FragmentCriaEvento();
            doFragmentTransaction(novoFragment,bundle);
        }
        else if (fragment == "eventos_pesquisaEvento"){
            FragmentPesquisaEventos novoFragment = new FragmentPesquisaEventos();
            doFragmentTransaction(novoFragment,bundle);
        }
        else if (fragment == "criaCarona") {
            FragmentCriaCarona novoFragment = new FragmentCriaCarona();
            doFragmentTransaction(novoFragment,bundle);
        }
        else if (fragment == "criaTransporte") {
            FragmentCriaTransporte novoFragment = new FragmentCriaTransporte();
            doFragmentTransaction(novoFragment,bundle);
        }
        else if (fragment == "evento_detalhe") {
            FragmentEventoDetalhe novoFragment = new FragmentEventoDetalhe();
            doFragmentTransaction(novoFragment, bundle);
        }
        else if (fragment == "participaCarona") {
            FragmentParticipaCarona novoFramgment = new FragmentParticipaCarona();
            doFragmentTransaction(novoFramgment,bundle);
        }
        else if (fragment == "participaTransporte") {
            FragmentParticipaTransporte novoFramgment = new FragmentParticipaTransporte();
            doFragmentTransaction(novoFramgment,bundle);
        }
    }
}
