package com.example.android.rodar;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;



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
    }
