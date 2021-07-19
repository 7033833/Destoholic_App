package com.example.destoholicstudent;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PrefHelper {
    // TODO Define All Key
    public static final String KEY_LOGIN_RESPONSE = "login_response";
    public static final String KEY_IS_LOGIN = "is_login";

    // Faster pref saving for high performance
    @Nullable
    private static final Method sApplyMethod = findApplyMethod();

    public static void setString(final String key, final String value, Context context) {
        final Editor editor = PreferenceManager.getDefaultSharedPreferences(
                context).edit();
        editor.putString(key, value);
        apply(editor);
    }

    public static String getString(final String key, final String defaultValue, Context context) {
        final SharedPreferences _preference = PreferenceManager
                .getDefaultSharedPreferences(context);
        return _preference.getString(key, defaultValue);
    }

    public static void setInt(final String key, final int value, Context context) {
        final Editor editor = PreferenceManager.getDefaultSharedPreferences(
                context).edit();
        editor.putInt(key, value);
        apply(editor);
    }

    public static int getInt(final String key, final int defaultValue, Context context) {
        final SharedPreferences _preference = PreferenceManager
                .getDefaultSharedPreferences(context);
        return _preference.getInt(key, defaultValue);
    }

    public static boolean getBoolean(final String key,
                                     final boolean defaultValue, Context context) {
        final SharedPreferences _preference = PreferenceManager
                .getDefaultSharedPreferences(context);
        return _preference.getBoolean(key, defaultValue);
    }

    public static void setBoolean(final String key, final boolean value, Context context) {
        final Editor editor = PreferenceManager.getDefaultSharedPreferences(
                context).edit();
        editor.putBoolean(key, value);
        apply(editor);
    }

    public static Boolean getBooleanBig(final String key,
                                        final Boolean defaultValue, Context context) {
        final SharedPreferences _preference = PreferenceManager
                .getDefaultSharedPreferences(context);
        return _preference.getBoolean(key, defaultValue);
    }

    public static void setBooleanBig(final String key, final Boolean value, Context context) {
        final Editor editor = PreferenceManager.getDefaultSharedPreferences(
                context).edit();
        editor.putBoolean(key, value);
        apply(editor);
    }

    public static void setLong(final String key, final long value, Context context) {
        final Editor editor = PreferenceManager.getDefaultSharedPreferences(
                context).edit();
        editor.putLong(key, value);
        apply(editor);
    }

    public static void deletePreference(final String key, Context context) {
        final Editor editor = PreferenceManager.getDefaultSharedPreferences(
                context).edit();
        editor.remove(key);
        apply(editor);
    }

    public static void deleteAllPreferences(Context context) {
        final Editor editor = PreferenceManager.getDefaultSharedPreferences(
                context).edit();
        editor.clear();
        apply(editor);
    }

    @Nullable
    private static Method findApplyMethod() {
        try {
            final Class<Editor> cls = Editor.class;
            return cls.getMethod("apply");
        } catch (@NonNull final NoSuchMethodException unused) {
            // fall through
            
        }
        return null;
    }


    public static void apply(@NonNull final Editor editor) {
        if (sApplyMethod != null) {
            try {
                sApplyMethod.invoke(editor);
                return;
            } catch (@NonNull final InvocationTargetException unused) {
                // fall through
                
            } catch (@NonNull final IllegalAccessException unused) {
                // fall through
                
            }
        }
        editor.commit();
    }

}