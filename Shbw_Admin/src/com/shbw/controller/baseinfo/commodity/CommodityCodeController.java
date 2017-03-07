package com.shbw.controller.baseinfo.commodity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.shbw.controller.base.BaseController;
import com.shbw.entity.Page;
import com.shbw.service.baseinfo.commodity.CommodityCodeService;
import com.shbw.util.Const;
import com.shbw.util.FileUpload;
import com.shbw.util.FromValidate;
import com.shbw.util.ObjectExcelRead;
import com.shbw.util.PageData;
import com.shbw.util.PathUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "server/commodity")
public class CommodityCodeController extends BaseController {
	
	@Resource(name="commodityCodeService")
	private CommodityCodeService commodityCodeService;

	/**
	 * 查询商品编码的list界面
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "code")
	public ModelAndView toListPage(Page page) throws Exception{
		ModelAndView mv=new ModelAndView();
		PageData pd=this.getPageData();
		page.setPd(pd);
		List<PageData> list=(List<PageData>)commodityCodeService.listCommodityCode(page);
		mv.addObject("list",list);
		mv.addObject("pd",pd);
		mv.setViewName("baseinfo/commodityCode/commodityCode_list");
		return mv;
	}
	
	/**
	 * 跳转到excel导入界面
	 * @return
	 */
	@RequestMapping(value = "goCommodityCodeExcel")
	public ModelAndView goCommodityCodeExcel(){
		ModelAndView mv=new ModelAndView();
		mv.setViewName("baseinfo/commodityCode/upload_excel");
		return mv;
	}
	
	/**
	 * 批量excel导入
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/readExcel")
	public ModelAndView readExcel(@RequestParam(value = "excel", required = false) MultipartFile file) throws Exception {
		ModelAndView mv=new ModelAndView();
		mv.setViewName("baseinfo/commodityCode/upload_excel");
		if (null != file && !file.isEmpty()) {
			String filePath = PathUtil.getClasspath() + Const.FILEPATHFILE; // 文件上传路径
			String fileName = FileUpload.fileUp(file, filePath, "userexcel"); // 执行上传
			@SuppressWarnings("unchecked")
			List<PageData> listPd = (List) ObjectExcelRead.readExcel(filePath, fileName, 1, 0, 0); // 执行读EXCEL操作,读出的数据导入List 2:从第3行开始；0:从第A列开始；0:第0个sheet
			List<PageData> insertList = new ArrayList<PageData>();
			FromValidate fv=new FromValidate();
		    String errorMsg="";
		    String username=this.getSessionUser();
			for(int i=0;i<listPd.size();i++){
				PageData pd=new PageData();
				if(listPd.get(i).size()<5){
					errorMsg+="，列数不够！";
				}else{
					pd.putAll(listPd.get(i));
			    	if(!fv.judgeLength(listPd.get(i).get("value0").toString(), 32)){
			    		errorMsg+="，商品编码信息不能为空或长度不能超过32个字符";
			    	}
			    	if(!fv.judgeLength(listPd.get(i).get("value1").toString(), 64)){
			    		errorMsg+="，原商品编码信息不能为空或长度不能超过32个字符";
			    	}
			    	if(!fv.judgeLength(listPd.get(i).get("value2").toString(), 64)){
			    		errorMsg+="，原商品名称信息不能为空或长度不能超过64个字符";
			    	}
			    	System.out.println("税率："+listPd.get(i).get("value3").toString());
			    	if(!fv.judgeLength(listPd.get(i).get("value3").toString(), 8)){
			    		errorMsg+="，税率信息不能为空或长度不能超过8个字符";
			    	}
			    	if(!fv.judgeLength(listPd.get(i).get("value4").toString(), 32)){
			    		errorMsg+="，数据来源不能为空或长度不能超过32个字符";
			    	}
				}
		    	if(StringUtils.isNotBlank(errorMsg)){
		    		int errorRow=i+1;
			    	mv.addObject("msg", "第"+errorRow+"行"+errorMsg);
			    	return mv;
		    	}
		    	pd.put("creator", username);
		    	insertList.add(pd);
		    }
			commodityCodeService.insertCommodityCodeList(insertList);
			mv.addObject("msg", "上传成功！");
		}
		return mv;
	}
	
	/**
	 * 跳转到商品编码修改界面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/editCommodityCodeInfo")
	public ModelAndView editCommodityCodeInfo() throws Exception{
		ModelAndView mv=new ModelAndView();
		PageData pd=this.getPageData();
		//查询
		if(pd.getString("type").equals("1")){
			PageData codePd=commodityCodeService.findCommodityCodeById(pd);
			mv.addObject("pd",codePd);
			mv.setViewName("baseinfo/commodityCode/commodityCode_edit");
		}else if(pd.getString("type").equals("2")){
			PageData codePd=commodityCodeService.findCommodityCodeById(pd);
			mv.addObject("pd",codePd);
			mv.setViewName("baseinfo/commodityCode/commodityCode_detail");
		}else{
			mv.setViewName("baseinfo/commodityCode/commodityCode_edit");
		}
		return mv;
	}
	
	/**
	 * 保存用户信息
	 * @return
	 */
	@RequestMapping(value="/saveCommodityCodeInfo")
	@ResponseBody
	public Object saveCommodityCodeInfo(){
		PageData pd=this.getPageData();
		JSONObject json=new JSONObject();
		pd.put("username", this.getSessionUser());
		String errorMsg="";
		FromValidate fv=new FromValidate();
    	if(!fv.judgeLength(pd.get("spbm").toString(), 32)){
    		errorMsg+="，商品编码信息不能为空或长度不能超过32个字符";
    	}
    	if(!fv.judgeLength(pd.get("yspbm").toString(), 64)){
    		errorMsg+="，原商品编码信息不能为空或长度不能超过32个字符";
    	}
    	if(!fv.judgeLength(pd.get("yspmc").toString(), 64)){
    		errorMsg+="，原商品名称信息不能为空或长度不能超过64个字符";
    	}
    	if(!fv.judgeLength(pd.get("sl").toString(), 8)){
    		errorMsg+="，税率信息不能为空或长度不能超过8个字符";
    	}
    	if(!fv.judgeLength(pd.get("sjly").toString(), 32)){
    		errorMsg+="，数据来源不能为空或长度不能超过32个字符";
    	}
    	if(!StringUtils.isNotBlank(errorMsg)){
    		try {
    			PageData comPd=commodityCodeService.findCommodityCode(pd);
    			if(comPd!=null){
    	  			if(!pd.getString("id").equals("")){
        				commodityCodeService.updateCommodityCodeById(pd);
        			}else{
        				commodityCodeService.insertCommodityCode(pd);
        			}
    	  			json.put("msg", "success");
    			}else{
    				json.put("msg", "not exit");
    			}
    		} catch (Exception e) {
    			e.printStackTrace();
    			json.put("msg", "fail");
    			json.put("errorMsg", errorMsg);
    		}
    	}else{
			json.put("msg", "validateError");
			json.put("errorMsg", errorMsg);
    	}
		return json;
	}
	
	/**
	 * 删除商品编码信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteCommodityCodeInfo")
	@ResponseBody
	public Object deleteCommodityCodeInfo() throws Exception{
		Map<String, String> map = new HashMap<String, String>();
		PageData pd=this.getPageData();
		String ids = pd.getString("DATA_IDS");
		if (!StringUtils.isEmpty(ids)) {
			commodityCodeService.deleteCommodityCodeInfo(ids);
			map.put("msg", "ok");
		}
		return map;
	}
}
