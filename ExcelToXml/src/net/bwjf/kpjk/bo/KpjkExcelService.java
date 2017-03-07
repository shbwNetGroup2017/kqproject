package net.bwjf.kpjk.bo;

import java.util.List;
import java.util.Map;

import net.bwjf.kpjk.domain.entities.KpjkExcel;

/**
 * ��Ʊ������Ϣ����
 * @author cyl
 *
 */
public interface KpjkExcelService {
	/**
	 * ��excel�����󱣴浽db
	 * @param fileList
	 * @return
	 */
	public String saveExcel(List<KpjkExcel> excelList);
	
	/**
	 * ɾ��excel����
	 * @param kpjk
	 * @return
	 */
	public void delExcel(KpjkExcel kpjk);
	
	/**
	 * ��DBƴװ�õ�XML�ַ������ظ�BO
	 * @param kpjk
	 * @return
	 */
	public List<Map> getXml(KpjkExcel kpjk);
	/**
	 * ��ѯexcel������
	 * @param kpjk
	 * @return
	 */
	public List<KpjkExcel> getExcel(KpjkExcel kpjk);
    /**
     * ���ô洢���̣�����xml
     * @param str
     */
	public List<Map> scxmlProc(String str);
    
	/**
	 * ��ѯ��Ʊ������Ϣ
	 * @param kpjk
	 * @return
	 */
	public List<KpjkExcel> selectByExcelID_HZ(KpjkExcel kpjk);
	/**
	 * ��ѯ��Ʊ������Ϣ
	 * @param kpjk
	 * @return
	 */
	public List<KpjkExcel> selectFPDetails(KpjkExcel kpjk);
}
