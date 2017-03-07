package com.shbw.taskjob;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.shbw.service.webservice.DataSynService;
import com.shbw.util.PageData;

/**
 * 数据汇总
 * @author cyl
 *
 */
public class DataStatistics {
	
	@Resource(name = "dataSynService")
	private DataSynService dataSynService;
	/**
	 * 非订单类信息的金额的统计，根据不同的数据来源统计下面的信息
	 * @throws Exception 
	 */
	public void unOrderStatics() throws Exception{

		List<PageData> pd0=dataSynService.findData("0");
		List<PageData> pd1=dataSynService.findData("1");
		List<PageData> pd2=dataSynService.findData("2");
		List<PageData> pd3=dataSynService.findData("3");
		
		List<PageData> pdList=new ArrayList<PageData>();
		pdList.addAll(pd0);
		pdList.addAll(pd1);
		pdList.addAll(pd2);
		pdList.addAll(pd3);
		dataSynService.insertData(pdList);
	}

}
