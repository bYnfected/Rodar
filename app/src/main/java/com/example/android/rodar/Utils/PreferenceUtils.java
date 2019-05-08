package com.example.android.rodar.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.example.android.rodar.models.Usuario;
import com.example.android.rodar.services.UsuarioService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class PreferenceUtils {

        public PreferenceUtils(){

        }
        public static boolean saveEmail(String email, Context context) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor prefsEditor = prefs.edit();
            prefsEditor.putString("email", email);
            prefsEditor.apply();
            return true;
        }

        public static String getEmail(Context context) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            return prefs.getString("email", null);
        }

        public static boolean savePassword(String password, Context context) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor prefsEditor = prefs.edit();
            prefsEditor.putString("password", password);
            prefsEditor.apply();
            return true;
        }

        public static String getPassword(Context context) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            return prefs.getString("password", null);
        }

        public static boolean saveToken(String token, Context context) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor prefsEditor = prefs.edit();
            prefsEditor.putString("token", token);
            prefsEditor.apply();
            return true;
        }

        public static String getToken(Context context) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            String tokenBearer = ("Bearer " + prefs.getString("token",null));
            return tokenBearer.replace("\"", "");
        }

        public static void getTipoUsuario(final Context context) {
            UsuarioService service = RetrofitClient.getClient().create(UsuarioService.class);
            Call<Usuario> call = service.getUser(getToken(context));

            call.enqueue(new Callback<Usuario>() {
                @Override
                public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                    if (response.isSuccessful()){
                        Usuario usuario = response.body();
                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
                        SharedPreferences.Editor prefsEditor = prefs.edit();
                        prefsEditor.putBoolean("transportador", usuario.isTransportador());
                        prefsEditor.putBoolean("organizador", usuario.isOrganizadorEvento());
                        prefsEditor.apply();
                    }
                }

                @Override
                public void onFailure(Call<Usuario> call, Throwable t) {
                }
            });
        }

        public static boolean getTransportador(Context context){
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            return prefs.getBoolean("transportador", false);
        }

    public static boolean getOrganizador(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getBoolean("organizador", false);
    }
    }
