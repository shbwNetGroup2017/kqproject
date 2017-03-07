package net.bwjf.kpjk.bo;


import net.bwjf.kpjk.dao.IDaoRegist;
import net.bwjf.kpjk.domain.entities.User;

public class RegistServiceImpl implements RegistService {

	private IDaoRegist iDaoRegistImpl;
	
	
	public IDaoRegist getiDaoRegistImpl() {
		return iDaoRegistImpl;
	}


	public void setiDaoRegistImpl(IDaoRegist iDaoRegistImpl) {
		this.iDaoRegistImpl = iDaoRegistImpl;
	}


	@Override
	public String zhuce(User user) {
		String str=iDaoRegistImpl.zhuce(user);
		return str;
	}


	@Override
	public User login(User user) {
		User us=iDaoRegistImpl.login(user);
		return us;
	}

    /**
     * 判断用户名是否存在
     */
	@Override
	public boolean judgeUser(String userName) {
		User us=iDaoRegistImpl.judgeUser(userName);
		if(us!=null){
			return true;
		}else{
			return false;
		}
	}

}
