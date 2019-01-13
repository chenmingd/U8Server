package com.u8.server.sdk.jinkuo.utils;


import com.u8.server.web.pay.U8PayCallbackTestAction;

public class CheckSignUitl {
	/**
	 * 以下两个参数    请替换成爱上游戏分配给游戏的参数
	 */
	private final static  String   appSecret = "7513a2c235647e3213538c6eb329eec9";
	private final static  String   publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDtJvawWjhQhI+J3EnD3gvuh+t1zB4bOMW9PJUdk27YQDyiGVd42QdHLofdTN1yXKXYZR1Bmy4W1pZhucSoDdS7fGfkKHm3zRMsijNOiPWHg0spMEchI4YTlIC43iFVdzSPE/2sIZfrW/9MspXfuWqFySsTsf6c6qJc6A0bNKJhMwIDAQAB";
	
	public static boolean isSignOK(U8PayCallbackTestAction.U8PayCallbackData order){

        /*Log.("channelID:"+order.getChannelID());
        Log.d("currency:"+order.getCurrency());
        Log.d("extension:"+order.getExtension());*/

        StringBuilder sb = new StringBuilder();
        sb.append("channelID=").append(order.getChannelID()).append("&")
                .append("currency=").append(order.getCurrency()).append("&")
                .append("extension=").append(order.getExtension()).append("&")
                .append("gameID=").append(order.getGameID()).append("&")
                .append("money=").append(order.getMoney()).append("&")
                .append("orderID=").append(order.getOrderID()).append("&")
                .append("productID=").append(order.getProductID()).append("&")
                .append("serverID=").append(order.getServerID()).append("&")
                .append("userID=").append(order.getUserID()).append("&")
                .append(appSecret);

        if("md5".equalsIgnoreCase(order.getSignType())){
            return EncryptUtils.md5(sb.toString()).toLowerCase().equals(order.getSign());
        }else{
            return RSAUtils.verify(sb.toString(), order.getSign(), publicKey, "UTF-8", "SHA1withRSA");
        }
    }
}
