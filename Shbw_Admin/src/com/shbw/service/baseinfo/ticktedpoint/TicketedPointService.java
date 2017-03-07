package com.shbw.service.baseinfo.ticktedpoint;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.shbw.dao.DaoSupport;
import com.shbw.entity.Page;
import com.shbw.util.PageData;

@Service("ticketedPointService")
public class TicketedPointService {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 开票点信息的列表查询
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listTicketedPoint(Page page) throws Exception{
		return (List<PageData>)dao.findForList("TicketedPointMapper.findTicketedPointlistPage", page);
	}
	
	/**
	 * 通过ID查询开票信息
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findTicketedPointById(PageData pd) throws Exception{
		return (PageData)dao.findForObject("TicketedPointMapper.findTicketedPointById", pd);
	}
	
	/**
	 * 通过NAME查询开票信息
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findTicketedPointByName(PageData pd) throws Exception{
		return (PageData)dao.findForObject("TicketedPointMapper.findTicketedPointByName", pd);
	}
	
	/**
	 * 查询所有的组织结构信息
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> findGroupList(PageData pd) throws Exception{
		return (List<PageData>)dao.findForList("TicketedPointMapper.findGroupList",pd);
	}
	
	/**
	 * 修改开票信息
	 * @param pd
	 * @throws Exception
	 */
	public void updateTicketedPointById(PageData pd) throws Exception{
		dao.update("TicketedPointMapper.updateTicketedPointById", pd);
	}
	
	/**
	 * 新增开票点信息
	 * @param pd
	 * @throws Exception
	 */
	public void insertTicketedPoint(PageData pd) throws Exception{
		dao.update("TicketedPointMapper.insertTicketedPoint", pd);
	}
	
	/**
	 * 删除开票点信息
	 * @param ids
	 * @throws Exception
	 */
	public void deleteTicketedPointInfo(String ids) throws Exception{
		dao.update("TicketedPointMapper.deleteTicketedPointInfo", ids);
	}

}
