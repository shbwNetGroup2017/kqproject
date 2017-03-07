package com.shbw.service.baseinfo.groupmanager;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.shbw.dao.DaoSupport;
import com.shbw.entity.Page;
import com.shbw.util.PageData;

/**
 * @author Aiwen
 *
 */
@Service("zzjgService")
public class ZzjgService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;

	@SuppressWarnings("unchecked")
	public List<PageData> selectListZzjgs(Page page) throws Exception {
		return (List<PageData>) dao.findForList("GroupManagerMapper.zzjgslistPage", page);
	}

	/*
	 * public ZzjgTreeNode selectZzjgParent(String jgbm) throws Exception {
	 * return (ZzjgTreeNode)
	 * dao.findForObject("GroupManagerMapper.selectZzjgParent", jgbm); }
	 * 
	 * @SuppressWarnings("unchecked") public List<ZzjgTreeNode>
	 * selectZzjgChilds(String jgbm) throws Exception { return
	 * (List<ZzjgTreeNode>)
	 * dao.findForList("GroupManagerMapper.selectZzjgChlids", jgbm); }
	 */

	public PageData selectZzjgById(String id) throws Exception {
		return (PageData) dao.findForObject("GroupManagerMapper.selectZzjgById", id);
	}

	public int saveZzjg(PageData pd) throws Exception {
		return (int) dao.save("GroupManagerMapper.insertZzjg", pd);
	}

	public int updateZzjg(PageData pd) throws Exception {
		return (int) dao.update("GroupManagerMapper.updateByPrimaryKeySelective", pd);
	}

	public long getJgbmCount(String jgbm) throws Exception {
		PageData pd = (PageData) dao.findForObject("GroupManagerMapper.getJgbmCount", jgbm);
		Long counts =(Long)pd.get("counts");		
		return counts;
	}

	public int deleteZzjg(String ids, String jgbms) throws Exception {
		// 先删除子组织机构
		String[] jgbmArr = jgbms.split(",");
		for (String bm : jgbmArr) {
			dao.update("GroupManagerMapper.deleteChilldZzjg", bm);
		}
		dao.update("GroupManagerMapper.delete", ids);
		return (int) dao.delete("GroupManagerMapper.delete", ids);
	}

	@SuppressWarnings("unchecked")
	public List<PageData> selectZzjgTree() throws Exception {
		return (List<PageData>) dao.findForList("GroupManagerMapper.selectZzjgTree", null);
	}

	@SuppressWarnings("unchecked")
	public List<PageData> selectZzjgBySjjgbm(String jgbm) throws Exception {
		return (List<PageData>) dao.findForList("GroupManagerMapper.selectZzjgBySjjgbm", jgbm);
	}

	@SuppressWarnings("unchecked")
	public List<PageData> selectXfmc() throws Exception {
		return (List<PageData>) dao.findForList("GroupManagerMapper.selectXfmc", null);
	}
}
