package com.u8.server.web.pub;

import com.u8.server.common.UActionSupport;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.stereotype.Controller;

/***
 * 请求获取订单号
 */
@Controller
@Namespace("/pub/download")
public class DownloadAction extends UActionSupport{
	@Action(value = "app")
	public void app(){
		if(this.isWeixin()){
			this.renderHtml("用QQ或者浏览器扫描");
			return;
		}
		if(this.isIos()){
			this.redirect("itms-services://?action=download-manifest&url=https://down.gfyouxi.com/ipas/qywd143_330_190117.plist");
		}
		this.redirect("http://down.gfyouxi.com/qdapks/qyj304190117_143_1547819774028.apk");
	}
}
