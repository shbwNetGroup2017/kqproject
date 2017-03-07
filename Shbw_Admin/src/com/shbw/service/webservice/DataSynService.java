package com.shbw.service.webservice;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.shbw.dao.DaoSupport;
import com.shbw.util.PageData;

/**
 * 接口数据入库操作
 * @author cyl
 *
 */
@Service("dataSynService")
public class DataSynService {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 订单类信息
	 * @param zMap
	 * @param list
	 * @throws Exception
	 */
	public void insertDdjyls(String flag,Map<Object,Object> zMap,List<Map<Object,Object>> list) throws Exception{
		if(flag.equals("U")){
			dao.update("DataMapper.deleteData", zMap);
		}
		dao.update("DataMapper.insertWsdlDataByOrderZ", zMap);
		dao.update("DataMapper.insertWsdlDataByOrderM", list);
	}
	
	/**
	 * 删除订单类主表信息
	 * @param list
	 * @throws Exception
	 */
	public void deleteDdjylsZ(Map<Object,Object> map) throws Exception{
		dao.update("DataMapper.deleteDdjylsZ", map);
	}
	
	/**
	 * 删除订单类明细表信息
	 * @param list
	 * @throws Exception
	 */
	public void deleteDdjylsM(Map<Object,Object> map) throws Exception{
		dao.update("DataMapper.deleteDdjylsM", map);
	}
	
	/**
	 * 非订单类信息
	 * @param map
	 * @throws Exception
	 */
	public void insertUnDdjy(Map<Object,Object> map,String flag) throws Exception{
		if(flag.equals("U")){
			dao.update("DataMapper.deleteUnDdjyData", map);
		}
		dao.update("DataMapper.insertWsdlUnDataByUnOrder", map);
	}
	
	/**
	 * 删除非订单信息
	 * @param id
	 * @throws Exception
	 */
    public void deleteUnDdjy(Map<Object,Object> map) throws Exception{
    	dao.update("DataMapper.deleteUnDdjyData", map);
    }
	
	/**
	 * 客户信息
	 * @param map
	 * @throws Exception
	 */
	public void insertCustom(Map<Object,Object> map,String flag) throws Exception{
		if(flag.equals("U")){
			dao.update("DataMapper.deleteCustomData", map);
		}
		dao.update("DataMapper.insertWsdlDataByCustom", map);
	}

	/**
	 * 删除客户信息
	 * @param id
	 * @throws Exception
	 */
    public void deleteCustom(Map<Object,Object> map) throws Exception{
    	dao.update("DataMapper.deleteCustomData", map);
    }
    
    /**
     * 查询数据汇总信息
     * @param ids
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
	public List<PageData> findData(String ids) throws Exception{
    	return (List<PageData>)dao.findForList("DataMapper.dataTimer", ids);
    }
    
    /**
     * 汇总数据
     * @param pdList
     * @throws Exception
     */
    public void insertData(List<PageData> pdList) throws Exception{
    	dao.update("DataMapper.insertTimerData", pdList);
    }
}
