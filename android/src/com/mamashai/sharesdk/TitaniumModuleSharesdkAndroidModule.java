/**
 * This file was auto-generated by the Titanium Module SDK helper for Android
 * Appcelerator Titanium Mobile
 * Copyright (c) 2009-2010 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 *
 */
package com.mamashai.sharesdk;

import java.util.HashMap;

import org.appcelerator.kroll.KrollModule;
import org.appcelerator.kroll.annotations.Kroll;
import android.os.Message;
import org.appcelerator.titanium.TiApplication;
import org.appcelerator.kroll.common.Log;
import org.appcelerator.kroll.common.TiConfig;

import org.appcelerator.titanium.util.TiRHelper;
import org.appcelerator.titanium.util.TiRHelper.ResourceNotFoundException;

import android.app.Activity;

import java.util.Iterator;  
import java.util.List;  
import java.util.Map.Entry;  


import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;

@Kroll.module(name="TitaniumModuleSharesdkAndroid", id="com.mamashai.sharesdk")
public class TitaniumModuleSharesdkAndroidModule extends KrollModule implements PlatformActionListener
{
	private static final int MSG_SMSSDK_CALLBACK = 1;
	private static final int MSG_AUTH_CANCEL = 2;
	private static final int MSG_AUTH_ERROR= 3;
	private static final int MSG_AUTH_COMPLETE = 4;
	
	private OnLoginListener signupListener;
	
	// Standard Debugging variables
	private static final String TAG = "Sharesdk_Android_Module";
	private static final boolean DBG = TiConfig.LOGD;

	// You can define constants with @Kroll.constant, for example:
	// @Kroll.constant public static final String EXTERNAL_NAME = value;

	public TitaniumModuleSharesdkAndroidModule()
	{
		super();
		
	}

	@Kroll.onAppCreate
	public static void onAppCreate(TiApplication app)
	{
		Log.d(TAG, "inside onAppCreate");
		ShareSDK.initSDK(app);
		
		// put module init code that needs to run when the application is created
	}
	
	@Kroll.method
	public void fire(final HashMap args){
		HashMap<String, Object> event = new HashMap<String, Object>();
		fireEvent("hello_event", event);
	}

	// Methods
	@Kroll.method
	public void share(final HashMap args){
      Log.d(TAG, "share");
      Log.d(TAG, getActivity().toString());
      
      Activity activity = TiApplication.getInstance().getCurrentActivity();
      Log.d(TAG, activity.toString());
      ShareSDK.initSDK(activity);

      String title = "" , content = "", url ="", type= "", imageUrl = "";
      if (args.containsKey("title")) {
        Object otitle = args.get("title");
        if (otitle instanceof String) {
          title = (String)otitle;
        }
      }
      if (args.containsKey("content")) {
        Object ocontent = args.get("content");
        if (ocontent instanceof String) {
          content = (String)ocontent;
        }
      }
      if (args.containsKey("url")) {
        Object ourl = args.get("url");
        if (ourl instanceof String) {
          url = (String)ourl;
        }
      }
      if (args.containsKey("type")) {
        Object otype = args.get("type");
        if (otype instanceof String) {
          type = (String)otype;
        }
      }
      if (args.containsKey("imageUrl")) {
      	imageUrl = (String)args.get("imageUrl");
      }

      Log.d(TAG, title);
      Log.d(TAG, content);
      Log.d(TAG, url);
      Log.d(TAG, type);
      Log.d(TAG, imageUrl);

      final OnekeyShare oks = new OnekeyShare();

/*
      int appicon = 0x7f020000;

      try {
        appicon = TiRHelper.getApplicationResource("drawable.appicon");
        Log.d(TAG, "getApplicationResource drawable.appicon");
      } catch (ResourceNotFoundException e) {
      }

      oks.setNotification(appicon, "测试ShareSDK");
      */

      oks.setAddress("000000");
      oks.setTitle(title);
      oks.setText(content);
      Log.d(TAG, resolveUrl(null,""));
//      oks.setImagePath(resolveUrl(null,""));
//      oks.setImageUrl("http://img.appgo.cn/imgs/sharesdk/content/2013/07/25/1374723172663.jpg");
      if (url != ""){
        oks.setUrl(url);
      }
      if (imageUrl != ""){
      	oks.setImageUrl(imageUrl);
      }
//      oks.setFilePath(resolveUrl(null,""));
//      oks.setComment(menu.getContext().getString(R.string.share));

//      oks.setSite(menu.getContext().getString(R.string.app_name));
//      oks.setSiteUrl("http://sharesdk.cn");

//      oks.setVenueName("ShareSDK");
//      oks.setVenueDescription("This is a beautiful place!");
//      oks.setLatitude(23.056081f);
//      oks.setLongitude(113.385708f);
      oks.setSilent(false);

//      oks.setShareContentCustomizeCallback(new ShareContentCustomizeDemo());
      Log.d(TAG, activity.toString());
      oks.show(activity.getBaseContext());
      Log.d(TAG, "show finish");
    }
    
    @Kroll.method
	public void login(final HashMap args){
		String tp = (String)args.get("tp");
		
		if(tp.equals("Wechat")) {
				//微信登录
				//测试时，需要打包签名；sample测试时，用项目里面的demokey.keystore
				//打包签名apk,然后才能产生微信的登录
				Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
				authorize(wechat);
				
				Log.d(TAG, "start weixin authorize");
		}
		else if (tp.equals("SinaWeibo")) {
				//新浪微博
				Platform sina = ShareSDK.getPlatform(SinaWeibo.NAME);
				authorize(sina);
				
				Log.d(TAG, "start weibo authorize");
		}
		else if (tp.equals("QZone")) {
				//QQ空间
				Platform qzone = ShareSDK.getPlatform(QZone.NAME);
				authorize(qzone);
				
				Log.d(TAG, "start qq authorize");
		}
		else{
				//其他登录
				authorize(null);
		}
	}
	
	//执行授权,获取用户信息
	//文档：http://wiki.mob.com/Android_%E8%8E%B7%E5%8F%96%E7%94%A8%E6%88%B7%E8%B5%84%E6%96%99
	private void authorize(Platform plat) {
		if (plat == null) {
			HashMap<String, Object> event = new HashMap<String, Object>();
			event.put("code", -1);
			fireEvent("third_login", event);
			return;
		}
		
		plat.setPlatformActionListener(this);
		//关闭SSO授权
		plat.SSOSetting(true);
		plat.showUser(null);
		
		Log.d(TAG, "show User null");
	}
	
	public void onComplete(Platform platform, int action, HashMap<String, Object> res) {
		Log.d(TAG, "on Complete");
		
		if (action == Platform.ACTION_USER_INFOR) {
			HashMap<String, Object> event = new HashMap<String, Object>();
			//event.put("json", res.toString());
			res.put("platform", platform.getName());
			res.put("code", 0);
			res.put("text", "授权成功");
			res.remove("status");
			fireEvent("third_login", res);	
		}
	}
	
	public void onError(Platform platform, int action, Throwable t) {
		if (action == Platform.ACTION_USER_INFOR) {
			HashMap<String, Object> event = new HashMap<String, Object>();
			event.put("code", -1);
			event.put("platform", platform.getName());
			event.put("text", "授权失败");
			fireEvent("third_login", event);
		}
		t.printStackTrace();
	}
	
	public void onCancel(Platform platform, int action) {
		if (action == Platform.ACTION_USER_INFOR) {
			HashMap<String, Object> event = new HashMap<String, Object>();
			event.put("code", -2);
			event.put("platform", platform.getName());
			event.put("text", "取消授权");
			fireEvent("third_login", event);
		}
	}
}

