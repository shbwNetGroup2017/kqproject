package com.shbw.util.weixin;

import java.util.Map;

/**
 * 微信支付签名工具类 字典排序
 * @author Administrator
 *
 */
public class SignUtil {
	
    public static String getPayCustomSign(Map<String,Object> bizObj,String key) throws Exception {
        String bizString = CommonUtil.FormatBizQueryParaMap(bizObj,false);//字典排序
		return MD5SignUtil.sign(bizString, key);//MD5签名
    }
}
