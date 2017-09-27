package com.rayhahah.easysports.utils;

import android.text.TextUtils;

import com.rayhahah.rbase.utils.useful.SPManager;

public class SettingPrefUtils {

    public static String getUid() {
        return SPManager.get().getStringValue("uid", "");
    }

    public static void saveUid(String uid) {
        SPManager.get().putString("uid", uid);
    }

    public static String getToken() {
        return SPManager.get().getStringValue("token", "");
    }

    public static void saveToken(String token) {
        SPManager.get().putString("token", token);
    }

    public static String getCookies() {
        return SPManager.get().getStringValue("cookies", "");
    }

    public static void saveCookies(String cookies) {
        SPManager.get().putString("cookies", cookies);
    }

    public static String getUsername() {
        return SPManager.get().getStringValue("username", "");
    }

    public static void saveUsername(String username) {
        SPManager.get().putString("username", username);
    }

    public static String getPassword() {
        return SPManager.get().getStringValue("password", "");
    }

    public static void savePassword(String password) {
        SPManager.get().putString("password", password);
    }

    public static String getNickname() {
        return SPManager.get().getStringValue("nickname", "");
    }

    public static void saveNickname(String nickname) {
        SPManager.get().putString("nickname", nickname);
    }

    public static boolean isLogin() {
        return !TextUtils.isEmpty(getCookies()) && !TextUtils.isEmpty(getToken());
    }

    public static void logout() {
        saveCookies("");
        saveNickname("");
        saveToken("");
        saveUid("");
        saveUsername("");
    }
}
