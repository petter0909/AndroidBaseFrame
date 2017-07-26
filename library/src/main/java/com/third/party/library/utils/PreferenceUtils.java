package com.third.party.library.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;


public class PreferenceUtils {	

	public static <T> T setPrefObject(Context context, final String key,
									  final T t) {
		if (context==null)return null;
		final SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(context);
		String toJsonStr = JsonUtil.toJson(t);
		settings.edit().putString(key, toJsonStr).commit();
		return t;
	}

	public static <T> T getPrefObject(Context context, String key,
									  final Class<T> classOfT) {
		if (context==null)return null;
		final SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(context);
		String jsonStr = settings.getString(key, "");
		T t = null;
		if (jsonStr.length()>0){
			t = JsonUtil.fromJson(jsonStr, classOfT);
		}
		return t;
	}

	public static String getPrefString(Context context, String key,
									   final String defaultValue) {
		if (context==null)return null;
		final SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(context);
		return settings.getString(key, defaultValue);
	}

	public static void setPrefString(Context context, final String key,
									 final String value) {
		if (context==null)return;
		final SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(context);
		settings.edit().putString(key, value).commit();
	}

	public static boolean getPrefBoolean(Context context, final String key,
										 final boolean defaultValue) {
		if (context==null)return false;
		final SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(context);
		return settings.getBoolean(key, defaultValue);
	}

	public static boolean hasKey(Context context, final String key) {
		if (context==null)return false;
		return PreferenceManager.getDefaultSharedPreferences(context).contains(key);
	}

	public static void setPrefBoolean(Context context, final String key,
									  final boolean value) {
		if (context==null)return;
		final SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(context);
		settings.edit().putBoolean(key, value).commit();
	}

	public static void setPrefInt(Context context, final String key,
								  final int value) {
		if (context==null)return;
		final SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(context);
		settings.edit().putInt(key, value).commit();
	}

	public static int getPrefInt(Context context, final String key,
								 final int defaultValue) {
		if (context==null)return 0;
		final SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(context);
		return settings.getInt(key, defaultValue);  //如果key取不到值，就取回后面的值!
	}

	public static void setPrefFloat(Context context, final String key,
									final float value) {
		if (context==null)return;
		final SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(context);
		settings.edit().putFloat(key, value).commit();
	}

	public static float getPrefFloat(Context context, final String key,
									 final float defaultValue) {
		if (context==null)return 0 ;
		final SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(context);
		return settings.getFloat(key, defaultValue);
	}

	public static void setSettingLong(Context context, final String key,
									  final long value) {
		if (context==null)return;
		final SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(context);
		settings.edit().putLong(key, value).commit();
	}

	public static long getPrefLong(Context context, final String key,
								   final long defaultValue) {
		if (context==null)return 0;
		final SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(context);
		return settings.getLong(key, defaultValue);
	}

	public static void clearPreference(Context context,
			final SharedPreferences p) {
		if (context==null)return;
		final Editor editor = p.edit();
		editor.clear();
		editor.commit();
	}

	private SharedPreferences mSharedPref;
	private Editor mEdit;
	private Context mShareContext;

	public PreferenceUtils(Context mShareContext) {
		this.mShareContext = mShareContext;
	}

	public SharedPreferences getSharePreference(){
		mSharedPref = mShareContext.getSharedPreferences("zhibo_player", Context.MODE_PRIVATE);
		return mSharedPref;
	}

	public Editor getEdit(){
		mEdit = mSharedPref.edit();
		return mEdit;
	}
}
