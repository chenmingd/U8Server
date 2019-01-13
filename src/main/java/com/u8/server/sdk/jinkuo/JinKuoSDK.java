package com.u8.server.sdk.jinkuo;

import com.alibaba.fastjson.JSONObject;
import com.u8.server.data.UChannel;
import com.u8.server.data.UOrder;
import com.u8.server.data.UUser;
import com.u8.server.sdk.ISDKOrderListener;
import com.u8.server.sdk.ISDKScript;
import com.u8.server.sdk.ISDKVerifyListener;
import org.apache.commons.lang.StringUtils;

/**
 * Created by mingdongc on 2019/1/13.
 */
public class JinKuoSDK implements ISDKScript{
	@Override
	public void verify(UChannel channel, String extension, ISDKVerifyListener callback) {
		if(StringUtils.isBlank(extension)){
			callback.onFailed("extension需要传入金阔SDK登录返回的token和userID,传入格式为{'token':'...',userId:'...'}");
		}
		JSONObject extensionJSON = JSONObject.parseObject(extension);
		String token = extensionJSON.getString("token");
		String userID = extensionJSON.getString("userId");
		if(StringUtils.isBlank(token) || StringUtils.isBlank(userID)){
			callback.onFailed("extension需要传入金阔SDK登录返回的token和userID,传入格式为{'token':'...',userId:'...'}");
		}

	}

	@Override
	public void onGetOrderID(UUser user, UOrder order, ISDKOrderListener callback) {

	}
}
