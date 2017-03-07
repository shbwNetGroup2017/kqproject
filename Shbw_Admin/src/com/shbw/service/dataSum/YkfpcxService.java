package com.shbw.service.dataSum;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.shbw.dao.DaoSupport;
import com.shbw.entity.Page;
import com.shbw.util.PageData;

@Service("ykfpcxService")
public class YkfpcxService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	public List<Map<String,Object>> selectYkfpcxlistPage(Page page) throws Exception{
		return (List<Map<String,Object>>) dao.findForList("ykfpcxMapper.selectYkfpcxlistPage", page);
	}
}
