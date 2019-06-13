package com.example.android.rodar.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.android.rodar.models.Usuario;
import com.example.android.rodar.services.UsuarioService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SPUtil {

        public SPUtil(){

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
            String tokenBearer = (prefs.getString("token",null));
            if (tokenBearer == null){
                return null;
            } else
            {
                tokenBearer = ("Bearer " + tokenBearer);
                return tokenBearer.replace("\"", "");
            }
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
                        prefsEditor.putInt("id", usuario.getIdUsuario());
                        prefsEditor.putString("urlImg",usuario.getUrlImagemSelfie());
                        prefsEditor.apply();
                    }
                }

                @Override
                public void onFailure(Call<Usuario> call, Throwable t) {
                }
            });
        }

        public static void saveTransportador(final Context context){
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor prefsEditor = prefs.edit();
            prefsEditor.putBoolean("transportador", true);
            prefsEditor.apply();

        }

        public static void saveOrganizador(final Context context) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor prefsEditor = prefs.edit();
            prefsEditor.putBoolean("organizador", true);
            prefsEditor.apply();
        }

        public static boolean getTransportador(Context context){
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            return prefs.getBoolean("transportador", false);
        }

    public static boolean getOrganizador(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getBoolean("organizador", false);
    }

    public static int getID(final Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getInt("id",0);
    }

    public static void saveTokenFirebase(final Context context, String s) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString("tokenFirebase", s);
        prefsEditor.apply();
    }

    public static String getTokenFirebase(final Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString("tokenFirebase", null);
    }
}
