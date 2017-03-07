package net.bwjf.kpjk.dao;

import java.util.List;
import java.util.Map;

import net.bwjf.kpjk.domain.entities.KpjkExcel;

/**
 * ��Ҫ���ܣ�1.��excel�����󱣴浽db��2.��DBƴװ�õ�XML�ַ������ظ�BO
 * @author cyl
 *
 */
public interface IDaoExcelToXml {
    
	/**
	 * ��excel�����󱣴浽db
	 * @param fileList
	 * @return
	 */
	public void saveExcel(KpjkExcel kpjk);
	
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
	public List<Map>  getXml(KpjkExcel kpjk);
	/**
	 * ��ѯexcel������
	 * @param kpjk
	 * @return
	 */
	public List<KpjkExcel> getExcel(KpjkExcel kpjk);
	
	public String selectSEQ_KPJKEXCEL();
	
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
