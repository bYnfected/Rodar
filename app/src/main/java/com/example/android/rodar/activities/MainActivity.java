package com.example.android.rodar.activities;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Menu;

import com.example.android.rodar.FragmentCriaCarona;
import com.example.android.rodar.FragmentCriaEvento;
import com.example.android.rodar.FragmentEventos;
import com.example.android.rodar.FragmentMensagens;
import com.example.android.rodar.FragmentsPerfil.FragmentMeusDados;
import com.example.android.rodar.FragmentsPerfil.FragmentPerfil;
import com.example.android.rodar.FragmentTransportes;
import com.example.android.rodar.R;

public class MainActivity extends AppCompatActivity implements IMainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.botton_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

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
                            selectedFragment = new FragmentTransportes();
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

    private void doFragmentTransaction(Fragment fragment, String acao){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void setToolbarTitle(String fragmentTag) {
        // mudar toolbar
    }

    @Override
    public void inflateFragment(String fragment, String acao) {
        if (fragment == "eventos"){
            FragmentEventos novoFragment = new FragmentEventos();
            doFragmentTransaction(novoFragment, acao);
        }
        else if (fragment == "mensagens"){
            FragmentMensagens novoFragment = new FragmentMensagens();
            doFragmentTransaction(novoFragment, acao);
        }
        else if (fragment == "perfil"){
            FragmentPerfil novoFragment = new FragmentPerfil();
            doFragmentTransaction(novoFragment,acao);
        }
        else if (fragment == "perfil_meusDados"){
            FragmentMeusDados novoFragment = new FragmentMeusDados();
            doFragmentTransaction(novoFragment,acao);
        }
        else if (fragment == "eventos_criaEvento"){
            FragmentCriaEvento novoFragment = new FragmentCriaEvento();
            doFragmentTransaction(novoFragment,acao);
        }
    }
}
