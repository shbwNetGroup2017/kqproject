package com.shbw.service.dataSum;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.shbw.dao.DaoSupport;
import com.shbw.entity.Page;

@Service("nonOrderService")
public class NonOrderService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 查询非订单列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public  List<Map<String,Object>> listNonOrders(Page page) throws Exception{
		return (List<Map<String, Object>>) dao.findForList("NonOrderMapper.listNonOrderslistPage", page);
	}
	
	/**
	 * 查询非订单列表详情
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public  List<Map<String,Object>> listNonOrderMX(Page page) throws Exception{
		return (List<Map<String, Object>>) dao.findForList("NonOrderMapper.listNonOrderMXlistPage", page);
	}
}
