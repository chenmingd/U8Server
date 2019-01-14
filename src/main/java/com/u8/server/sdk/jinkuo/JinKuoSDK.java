package com.u8.server.sdk.jinkuo;

import com.alibaba.fastjson.JSONObject;
import com.u8.server.data.UChannel;
import com.u8.server.data.UOrder;
import com.u8.server.data.UUser;
import com.u8.server.log.Log;
import com.u8.server.sdk.ISDKOrderListener;
import com.u8.server.sdk.ISDKScript;
import com.u8.server.sdk.ISDKVerifyListener;
import com.u8.server.sdk.SDKVerifyResult;
import com.u8.server.sdk.UHttpAgent;
import com.u8.server.sdk.UHttpFutureCallback;
import com.u8.server.utils.EncryptUtils;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

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
		String appKey=channel.getCpAppKey();
		if(StringUtils.isBlank(appKey)){
			callback.onFailed("channel配置错误,cpAppKey不能为空");
		}
		String sign = EncryptUtils.md5("userID=" + userID + "token=" + token + appKey);

		UHttpAgent httpClient = UHttpAgent.getInstance();
		String url = channel.getChannelAuthUrl();
		Map<String,String> params = new HashMap<String, String>();
		params.put("userID", userID);
		params.put("token", token);
		params.put("sign", sign);

		UHttpAgent.getInstance().post(url, params, new UHttpFutureCallback() {
			@Override
			public void completed(String result) {
				Log.e("JinKuoSDK,auth,userId>"+userID+",token>"+token+",result>" + result);

				JSONObject resultJson = JSONObject.parseObject(result);

				//state 1：(登录认证成功)；其他值为失败 。
				//      6：玩家不存在，（ userID为金阔平台玩家唯一标示）。
				//      8：token参数为空。
				//      9：token错误。
				//      12：参数签名错误。
				//      15：登录未开放。
				//      16：新增未开放。
				//      17：访问IP被禁用。
				//
				//
				//data	认证成功才有数据，否则为空
				//		userID:金阔Server生成的唯一用户ID
				//		username:金阔Server生成的统一格式的用户名
				//		channelID：渠道ID
				//		sdkUserID:渠道SDK玩家唯一标示
				//callback.onSuccess(new SDKVerifyResult(true, uid, "", ""));
				Integer state = resultJson.getInteger("state");
				if(state==null || state!=1){
					callback.onFailed(channel.getMaster().getSdkName() + " verify failed. the post result is " + result);
				}
				JSONObject data = resultJson.getJSONObject("data");
				callback.onSuccess(new SDKVerifyResult(true, data.getString("userID"), data.getString("username"), ""));
				return;
			}

			@Override
			public void failed(String e) {
				callback.onFailed(channel.getMaster().getSdkName() + " verify failed. " + e);
			}


		});
	}

	@Override
	public void onGetOrderID(UUser user, UOrder order, ISDKOrderListener callback) {

	}
}
