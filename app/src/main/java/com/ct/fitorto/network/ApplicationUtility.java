package com.ct.fitorto.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ApplicationUtility {

	public static boolean checkConnection(Context context) {
		boolean flag = false;
		try {
			ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = connectivityManager.getActiveNetworkInfo();

			if (info.getType() == ConnectivityManager.TYPE_WIFI) {
				System.out.println(info.getTypeName());
				flag = true;
			}
			if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
				System.out.println(info.getTypeName());
				flag = true;
			}
		} catch (Exception exception) {
			System.out.println("Exception at network connection....."+ exception);
		}
		return flag;
	}
}
