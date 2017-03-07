package com.shbw.util.weixin;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 将传递的参数进行字典排序
 * @author Administrator
 *
 */
public class CommonUtil {
	
	public static String FormatBizQueryParaMap(Map<String, Object> paraMap,
            boolean urlencode) throws Exception {
        String buff = "";
        try {
            List<Map.Entry<String, Object>> infoIds = new ArrayList<Map.Entry<String, Object>>(
                    paraMap.entrySet());
            Collections.sort(infoIds,
                    new Comparator<Map.Entry<String, Object>>() {
                        public int compare(Map.Entry<String, Object> o1,
                                Map.Entry<String, Object> o2) {
                            return (o1.getKey()).toString().compareTo(
                                    o2.getKey());
                        }
                    });
            for (int i = 0; i < infoIds.size(); i++) {
                Map.Entry<String, Object> item = infoIds.get(i);
                if (item.getKey() != "") {
                    String key = item.getKey();
                    String val = item.getValue().toString();
                    if (urlencode) {
                        val = URLEncoder.encode(val, "utf-8");
                    }
                    buff += key + "=" + val + "&";
                }
            }
         //  if (buff.isEmpty() == false) {
                buff = buff.substring(0, buff.length() - 1);
    //        }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return buff;
    }
	
	/**
	 * 将map数据转换成XML格式
	 * @param arr
	 * @return
	 */
	public static String ArrayToXml(Map<String, Object> arr) {
        String xml = "<xml>";
        Iterator<Entry<String, Object>> iter = arr.entrySet().iterator();
        while (iter.hasNext()) {
            Entry<String, Object> entry = iter.next();
            String key = entry.getKey();
            String val = entry.getValue().toString();
            //if (IsNumeric(val)) {
                xml += "<" + key + ">" + val + "</" + key + ">";
           // } else
            //    xml += "<" + key + "><![CDATA[" + val + "]]></" + key + ">";
        }
        xml += "</xml>";
        return xml;
    }
	
	public static boolean IsNumeric(String str) {
        if (str.matches("\\d *")) {
            return true;
        } else {
            return false;
        }
    }
}
