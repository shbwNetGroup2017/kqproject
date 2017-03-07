package net.bwjf.kpjk.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.sql.ARRAY;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import net.bwjf.kpjk.domain.entities.KpjkExcel;

public class DaoExcelToXmlImpl extends SqlMapClientDaoSupport  implements IDaoExcelToXml {

	@Override
	public void saveExcel(KpjkExcel kpjk) {
		getSqlMapClientTemplate().insert("KpjkExcel.saveExcel", kpjk);
	}

	@Override
	public void delExcel(KpjkExcel kpjk) {
		getSqlMapClientTemplate().insert("KpjkExcel.delExcel", kpjk);
	}
    /*
     * 取文件标识
     */
	public String selectSEQ_KPJKEXCEL(){
		return getSqlMapClientTemplate().queryForObject("KpjkExcel.selectSEQ_KPJKEXCEL").toString();
	}  
	
	@Override
	public List<Map> scxmlProc(String str){
		Map<String,String> scMap=new HashMap<String, String>();
		scMap.put("p_excelfilebs",str);
		  getSqlMapClientTemplate().queryForList("KpjkExcel.scxmlProc",scMap);
		  List<Map> scList=new ArrayList<Map>();
		  scList.add(scMap);
		return scList;
	}
	
	@Override
	public List<Map> getXml(KpjkExcel kpjk) {
		List list = getSqlMapClientTemplate().queryForList("KpjkExcel.getXml", kpjk);
        return list;
	}
    
	/**
	 * 查询excel的内容
	 * @param kpjk
	 * @return
	 */
	public List<KpjkExcel> getExcel(KpjkExcel kpjk){
		List list = getSqlMapClientTemplate().queryForList("KpjkExcel.selectByExcelID", kpjk);
        return list;
	}

	/**
	 * 查询发票汇总信息
	 * @param kpjk
	 * @return
	 */
	public List<KpjkExcel> selectByExcelID_HZ(KpjkExcel kpjk){
		List list = getSqlMapClientTemplate().queryForList("KpjkExcel.selectByExcelID_HZ", kpjk);
        return list;
	}
	
	/**
	 * 查询发票汇总信息
	 * @param kpjk
	 * @return
	 */
	public List<KpjkExcel> selectFPDetails(KpjkExcel kpjk){
		List list = getSqlMapClientTemplate().queryForList("KpjkExcel.selectFPDetails", kpjk);
        return list;
	}
}
