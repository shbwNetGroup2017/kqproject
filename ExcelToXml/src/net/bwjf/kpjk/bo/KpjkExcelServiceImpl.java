package net.bwjf.kpjk.bo;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import net.bwjf.kpjk.dao.IDaoExcelToXml;
import net.bwjf.kpjk.domain.entities.KpjkExcel;

/**
 * ��Ʊ����ӿ�ʵ����
 * @author cyl
 *
 */
public class KpjkExcelServiceImpl implements KpjkExcelService {

	private IDaoExcelToXml iDaoExcelToXml;
	 

	public IDaoExcelToXml getiDaoExcelToXml() {
		return iDaoExcelToXml;
	}

	public void setiDaoExcelToXml(IDaoExcelToXml iDaoExcelToXml) {
		this.iDaoExcelToXml = iDaoExcelToXml;
	}
	@Transactional
	@Override
	public String saveExcel(List<KpjkExcel> excelList) {
		String excelfileBs = iDaoExcelToXml.selectSEQ_KPJKEXCEL();
		List<KpjkExcel> kpList= excelList;
		for (KpjkExcel kp : kpList) {
			kp.setEXCELFILEBS(excelfileBs);
			iDaoExcelToXml.saveExcel(kp);
		}
		return excelfileBs;
	}

	@Override
	public List<Map> scxmlProc(String str){
		return iDaoExcelToXml.scxmlProc(str);
	}
	@Transactional
	@Override
	public void delExcel(KpjkExcel kpjk) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Map> getXml(KpjkExcel kpjk) {
		return iDaoExcelToXml.getXml(kpjk);
	}

	@Override
	public List<KpjkExcel> getExcel(KpjkExcel kpjk) {
		return iDaoExcelToXml.getExcel(kpjk);
	}
	
	/**
	 * ��ѯ��Ʊ������Ϣ
	 * @param kpjk
	 * @return
	 */
	public List<KpjkExcel> selectByExcelID_HZ(KpjkExcel kpjk){
		List<KpjkExcel> hzfpList=iDaoExcelToXml.selectByExcelID_HZ(kpjk);
		for (KpjkExcel k : hzfpList) {
			 String[] aa = k.getBZ().split("%");
             k.setGFMC(aa[0]);
             k.setGFSBH(aa[1]);
             System.out.println("length....."+aa.length);
             if(aa.length>2){
                 k.setGFDZDH(aa[2]);
                if(aa.length>3){
                    k.setGFYHZH(aa[3]);
                }
             }
		}
		return hzfpList;
	} 
	/**
	 * ��ѯ��Ʊ������Ϣ
	 * @param kpjk
	 * @return
	 */
	public List<KpjkExcel> selectFPDetails(KpjkExcel kpjk){
		return iDaoExcelToXml.selectFPDetails(kpjk);
	}
}
