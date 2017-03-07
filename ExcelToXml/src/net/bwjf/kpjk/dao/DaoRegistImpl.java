package net.bwjf.kpjk.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import net.bwjf.kpjk.domain.entities.User;

public class DaoRegistImpl extends SqlMapClientDaoSupport implements IDaoRegist {

	@Override
	public String zhuce(User user) {
		Map<String,String> scMap=new HashMap<String, String>();
		scMap.put("p_user_dm",user.getUSERNAME());
		scMap.put("p_user_pwd",user.getPASSWORD());
		scMap.put("p_gsmc",user.getGSMC());
		scMap.put("p_nsrsbh",user.getSH());
		  getSqlMapClientTemplate().queryForList("user.yhzcProc",scMap);
		  List<Map> scList=new ArrayList<Map>();
		  scList.add(scMap);
		return "success";
	}

	@Override
	public User login(User user) {
		User us=(User) getSqlMapClientTemplate().queryForObject("user.selectUser", user);
		return us;
	}
    
	@Override
	public User judgeUser(String userName) {
		User us=(User) getSqlMapClientTemplate().queryForObject("user.judgeUser", userName);
		return us;
	}

}
