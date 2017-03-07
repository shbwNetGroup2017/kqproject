package com.shbw.controller.baseinfo.groupmanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.shbw.controller.base.BaseController;
import com.shbw.entity.Page;
import com.shbw.service.baseinfo.groupmanager.ZzjgService;
import com.shbw.util.Const;
import com.shbw.util.PageData;

import net.sf.json.JSONObject;

/**
 * @author Aiwen
 *
 */
@Controller
@RequestMapping(value = "server/zzgl")
public class ZzjgController extends BaseController {
	@Resource(name = "zzjgService")
	private ZzjgService zzjgService;

	/**
	 * 显示分页组织机构列表
	 */
	@RequestMapping(value = "/listZzgl")
	public ModelAndView listZzjgs(Page page) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		page.setPd(pd);
		List<PageData> zzglList = zzjgService.selectListZzjgs(page);
		mv.setViewName("baseinfo/zzjg/zzjg_list");
		mv.addObject("jgbm", pd.getString("sjjgbm"));
		mv.addObject("pd", pd);
		mv.addObject("zzglList", zzglList);
		return mv;
	}

	/**
	 * 打开新增或者修改一个组织机构页面
	 * 
	 * @return mv
	 */
	@RequestMapping(value = "/addZzjg")
	public ModelAndView addZzjg(HttpServletRequest request) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String id = pd.getString("id");
		PageData zzjg = new PageData();
		if (!StringUtils.isEmpty(id)) {
			zzjg = zzjgService.selectZzjgById(id);
		} else {
			String text = null;
			String sjjgbm = null;
			text = new String(pd.getString("text").getBytes("ISO-8859-1"), "UTF-8");
			sjjgbm = pd.getString("sjjgbm");
			if(sjjgbm!=null&&!"".equals(sjjgbm)){
				
			}
			zzjg.put("sjjgbm", sjjgbm);
			zzjg.put("sjjg", text);
		}
		String add = pd.getString("sjjgbm");//新增标志
		if(add!=null&&!"".equals(add)){
			mv.addObject("add",add);
		}
		mv.setViewName("baseinfo/zzjg/zzjg_edit");
		mv.addObject("zzjg", zzjg);
		return mv;
	}

	@RequestMapping(value = "/saveZzjg")
	@ResponseBody
	public Object saveZzjg() throws Exception {
		JSONObject json = new JSONObject();
		PageData pd = new PageData();
		pd = this.getPageData();
		String id = pd.getString("id");
		// 获取用户名
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		String userName = (String) session.getAttribute(Const.SESSION_USERNAME);
		//

		if (StringUtils.isEmpty(id)) {
			// 判断机构编码是否存在
			String jgbm = pd.getString("jgbm");
			long jgbmCount = zzjgService.getJgbmCount(jgbm);
			if (jgbmCount > 0) {
				json.put("msg", "fail");
				json.put("result", "机构编码已经存在,请重新填写");
			} else {
				pd.put("xf_yxtid", this.get32UUID());
				pd.put("creator", userName);
				logBefore(logger, "新增一个组织机构");
				zzjgService.saveZzjg(pd);
				json.put("msg", "success");
			}

		} else {
			pd.put("updator", userName);
			logBefore(logger, "修改一个组织机构");
			zzjgService.updateZzjg(pd);
			json.put("msg", "success");
		}

		return json;
	}

	/**
	 * 根据Id 查看组织机构的详细信息
	 * 
	 * @return mv
	 * @throws Exception
	 */
	@RequestMapping(value = "/lookZzjg")
	public ModelAndView lookZzjg() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String id = pd.getString("id");
		PageData zzjg = new PageData();
		if (!StringUtils.isEmpty(id)) {
			zzjg = zzjgService.selectZzjgById(id);
			mv.addObject("zzjg", zzjg);
		}
		mv.setViewName("baseinfo/zzjg/zzjg_detail");
		return mv;
	}

	@RequestMapping(value = "/deleteZzjg")
	@ResponseBody
	public Object deleteZzjg() throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String ids = pd.getString("DATA_IDS");
		if (!StringUtils.isEmpty(ids)) {
			// List<String> list = Arrays.asList(ids);
			logBefore(logger, "开始删除组织机构!");
			zzjgService.deleteZzjg(ids, pd.getString("jgbm"));
			map.put("msg", "ok");

		}
		return map;
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/zzjgByTreeId")
	@ResponseBody
	public ModelAndView zzjgByTreeId() throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		List<PageData> list = new ArrayList<PageData>();
		list = zzjgService.selectZzjgBySjjgbm(pd.getString("id"));
		mv.addObject("zzglList", list);
		mv.setViewName("baseinfo/zzjg/zzjgTable");
		return mv;
	}

	@RequestMapping(value = "/zzjgTree")
	@ResponseBody
	public Object getKqZzjg() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<PageData> list = new ArrayList<PageData>();
		list = zzjgService.selectZzjgTree();
		map.put("tree", list);
		return map;
	}
	/*
	 * treeview 显示注释
	 * 
	 * @RequestMapping(value = "/zzjgTree")
	 * 
	 * @ResponseBody public Object getKqZzjg() throws Exception { Map<String,
	 * Object> map = new HashMap<String, Object>(); ZzjgTreeNode zzjg =
	 * recursiveTree("01"); JSONArray json = JSONArray.fromObject(zzjg); String
	 * str = json.toString(); str = str.replaceAll("\"nodes\":\\[\\],",
	 * "\"child\":true,"); map.put("tree", JSONArray.fromObject(str)); return
	 * map; }
	 * 
	 * /** 递归算法解析成树形结构
	 *
	 * @param cid
	 * 
	 * @return
	 * 
	 * @author aiwen
	 */
	/*
	 * public ZzjgTreeNode recursiveTree(String jgbm) throws Exception { //
	 * 根据cid获取节点对象(SELECT * FROM tb_tree t WHERE t.cid=?) ZzjgTreeNode node =
	 * null; List<ZzjgTreeNode> childTreeNodes = new ArrayList<ZzjgTreeNode>();
	 * node = zzjgService.selectZzjgParent(jgbm); // 查询cid下的所有子节点(SELECT * FROM
	 * tb_tree t WHERE t.pid=?) childTreeNodes =
	 * zzjgService.selectZzjgChilds(jgbm); // 遍历子节点 for (ZzjgTreeNode child :
	 * childTreeNodes) { ZzjgTreeNode n = recursiveTree(child.getJgbm()); // 递归
	 * node.getNodes().add(n); } return node; }
	 **/
}
