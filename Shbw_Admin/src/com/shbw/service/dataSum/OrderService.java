package com.shbw.service.dataSum;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.shbw.dao.DaoSupport;
import com.shbw.entity.Page;
import com.shbw.util.PageData;
@Service("orderService")
public class OrderService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 查询订单列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public  List<Map<String,Object>> listOrder(Page page) throws Exception{
		return (List<Map<String, Object>>) dao.findForList("OrderMapper.getOrderlistPageInfo", page);
	}
	/**
	 * 查询订单明细
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public  List<Map<String,Object>> listOrderMX(Page page) throws Exception{
		return (List<Map<String, Object>>) dao.findForList("OrderMapper.getOrderMXlistPageInfo", page);
	}
	
    /**
     * 删除订单信息
     * @param pd
     * @throws Exception
     */
	public void deleteOrder(PageData pd) throws Exception {
		dao.update("OrderMapper.deleteOrder", pd);
	}
	
	/**
	 * 删除订单明细信息
	 * @param pd
	 * @throws Exception
	 */
	public void deleteOrderMX(PageData pd) throws Exception {
		dao.update("OrderMapper.deleteOrderMX", pd);
	}
}
