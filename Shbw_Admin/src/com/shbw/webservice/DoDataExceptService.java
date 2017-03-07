package com.shbw.webservice;

import javax.jws.WebService;

/**
 * WebService数据同步接口
 * @author cyl
 *
 */
@WebService
public interface DoDataExceptService {
	//客户信息数据
	public String saveCustomDataInfo(String dataInfo);
	//非订单数据
	public String saveUnOrderDataInfo(String dataInfo);
	//订单数据
	public String saveOrderDataInfo(String dataInfo);

}
