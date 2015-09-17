package com.example.nethelp;

import java.util.Map;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.util.HttpUtil;
import com.example.util.HttpUtil.HttpCallbackListener;


public class NetHelp {
	public static void uploadLableInfo(Map<String,String> lableInfo){
		String bathUrl = "http://192.168.207.26:8080/RegisterService/LableManageServlet";
		HttpUtil.sendHttpRequest(bathUrl,lableInfo,new HttpCallbackListener() {
			
			@Override
			public void onFinish(String response) {
			}
			@Override
			public void onError(Exception e) {
				Log.d("Exception", e.toString());
			}
		});
	}

	/**
	 * 用于用户在新手机登录后恢复用户的lable记录的方法
	 * 
	 * @param lableInfo  封装登录用户user_num的map
	 * @param handler
	 */
	public static void fetchLableInfo(Map<String,String> lableInfo, final Handler handler) {
		final Message msg = handler.obtainMessage();
		String bathUrl = "http://192.168.207.26:8080/RegisterService/FetchLableInfoServlet";
		HttpUtil.sendHttpRequest(bathUrl,lableInfo,new HttpCallbackListener() {
			
			@Override
			public void onFinish(final String response) {
				msg.what = 10;
				msg.obj = response;
				handler.sendMessage(msg);
			}
			@Override
			public void onError(Exception e) {
				e.printStackTrace();
			}
		});
		
	}
}
