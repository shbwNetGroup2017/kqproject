package com.shbw.util.mail;

import java.beans.PropertyDescriptor;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtils;

@SuppressWarnings("unchecked")
public class BaseMapModel implements Map, java.io.Serializable
{
	private static final long	serialVersionUID	= 1358430100778604350L;
	protected Map				model				= null;

	public BaseMapModel()
	{
		this.model = new HashMap();
	}

	public BaseMapModel(Map model)
	{
		this.model = model;
	}

	public void clear()
	{
		this.model.clear();
	}

	public boolean containsKey(Object key)
	{
		return this.model.containsKey(key);
	}

	public boolean containsValue(Object value)
	{
		return this.model.containsValue(value);
	}

	public Set entrySet()
	{
		return this.model.entrySet();
	}

	public Object get(Object key)
	{
		return this.model.get(key);
	}

	public boolean isEmpty()
	{
		return this.model.isEmpty();
	}

	public Set keySet()
	{
		return this.model.keySet();
	}

	public Object put(Object key, Object value)
	{
		return this.model.put(key, value);
	}

	/**
	 * 直接将值存储为指定类型
	 * 
	 * @param key
	 * @param value
	 * @param clazz
	 * @return
	 */
	public Object put(Object key, String value, Class clazz)
	{
		return this.put(key, ConvertUtils.convert(value, clazz));
	}

	public void putAll(Map t)
	{
		this.model.putAll(t);
	}

	public Object remove(Object key)
	{
		return this.model.remove(key);
	}

	public int size()
	{
		return this.model.size();
	}

	public Collection values()
	{
		return this.model.values();
	}

	public String toString()
	{
		StringBuffer strBuf = new StringBuffer(50);
		try
		{
			if (this instanceof Map)
			{
				Iterator it = ((Map) this).entrySet().iterator();
				while (it.hasNext())
				{
					Map.Entry en = (Map.Entry) it.next();
					String name = (String) en.getKey();
					Object value = en.getValue();
					if (value != null)
					{
						strBuf.append(",\"" + name + "\":\"" + value.toString() + "\"");
					}else{
						strBuf.append(",\"" + name + "\":null");
					}
				}
			} else
			{
				PropertyDescriptor[] pds = PropertyUtils.getPropertyDescriptors(this);
				for (int i = 0; i < pds.length; i++)
				{
					String key = pds[i].getName();
					if (key.equalsIgnoreCase("class") || key.equalsIgnoreCase("page") || key.equalsIgnoreCase("pageSize")
							|| key.equalsIgnoreCase("totalRecords") || key.equalsIgnoreCase("orderByClause") || key.equalsIgnoreCase("oredCriteria")
							|| key.equalsIgnoreCase("orderByClause") || key.equalsIgnoreCase("orderByClause"))
					{
						continue;
					}
					if (pds[i].getReadMethod() != null)
					{
						Object value = PropertyUtils.getProperty(this, key);
						if (value != null)
						{
							strBuf.append(",\"" + key + "\":\"" + value.toString() + "\"");
						}else{
							strBuf.append(",\"" + key + "\":null");
						}
					}
				}
			}
			String val = strBuf.toString();
			if (val.startsWith(","))
			{
				return "[" + val.substring(1) + "]";
			}
			return "[" + strBuf.toString() + "]";
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return "";
	}

	public Map getModel()
	{
		return model;
	}

	public Long getLong(Object key)
	{
		return new Long(this.get(key).toString());
	}

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * 获取指定的键对应的值 默认值为空字符串 </br>
	 * 同 getString(Object key)
	 * @param key
	 * @return
	 */
	public String getS(Object key)
	{
		return getString(key);
	}
	
	/**
	 * 获取指定的键对应的值 为null时返回默认值
	 * @param key
	 * @param defvalue
	 * @return
	 */
	public String getS(Object key, String defvalue)
	{
		return getString(key, null, defvalue);
	}
	
	/**
	 * 获取指定的键对应的值 默认值为空字符串
	 * @param key
	 * @return
	 */
	public String getString(Object key)
	{
		return getString(key,"");
	}
	
	/**
	 * 获取指定的键对应的值,并将之格式化
	 * @param key
	 * @param format
	 * @return
	 */
	public String getString(Object key, String format)
	{
		return getString(key, format, "");
	}
	
	public String getString(Object key, Map dic)
	{
		return getString(key, dic, "");
	}
	
	/**
	 * 
	 * @param key
	 * @param format
	 * @param defaulevalue
	 * @return
	 */
	public String getString(Object key, Object format, String defaulevalue)
	{
		Object obj = this.get(key);
		if(obj == null)
		{
			return defaulevalue;
		}
		if (format instanceof String && obj instanceof Date)
		{
			Date date = (Date) obj;
			if(format != null && !"".equals(format))
			{
				return new SimpleDateFormat((String)format).format(date);
			}
			return sdf.format(date);
		}else if(format instanceof Map)
		{
			Map dic = (Map)format;
			String[] tmp = String.valueOf(obj).split(",");
			if(tmp.length>1)
			{
				obj = "";
				for(String str :tmp)
				{
					if("".equals(obj))
					{
						obj = dic.get(str);
					}else
					{
						obj =obj + "," + dic.get(str);
					}
				}
				return String.valueOf(obj);
			}
			obj = dic.get(obj);
			if(obj == null)
			{
				return defaulevalue;
			}
		}
		return String.valueOf(obj);
	}
	
	/**
	 *  获取字符串并在结束位置追加字符串<br>
	 *  条件是获取的值不为空,default is end
	 * @param key
	 * @param str
	 * @return
	 */
	public String getStringAppend(Object key, String str)
	{
		return getStringAppend(key, str,0);
	}
	
	/**
	 * 获取字符串并根据模式进行起始位置或者结束位置修改<br>
	 * 条件是获取的值不为空
	 * @param key 
	 * @param str 追加的字符串
	 * @param mod 字符串追加的模式，0 为结束位置追加(end)，1为起始位置追加(before)
	 * @return
	 */
	public String getStringAppend(Object key, String str,int mod)
	{
		Object obj = this.get(key);
		if(obj == null)
		{
			return "";
		}
		if(str != null)
		{
			if(mod == 0)
			{
				obj = obj + str;
			}else
			{
				obj = str + obj;
			}
		}
		return String.valueOf(obj);
	}

	/**
	 * 根据键 key 获取相应的值，如果 对照字典存在的话，将结果和对照字典进行匹配，返回匹配后的键
	 * @param key 键
	 * @param dic 对照字典
	 * @return 匹配后的键，字典为空或者找不到对应关系的返回空字符串
	 */
	public String getStringKey(Object key, Map dic)
	{
		return getStringKV(key, dic, 0);
	}

	/**
	 * 根据键 key 获取相应的值，如果 对照字典存在的话，将结果和对照字典进行匹配，返回匹配后的值
	 * @param key 键
	 * @param dic 对照字典
	 * @return 配后的值，字典为空或者找不到对应关系的返回空字符串
	 */
	public String getStringValue(Object key, Map dic)
	{
		return getStringKV(key, dic, 1);
	}
	
	/**
	 * 根据键 key 获取相应的值，如果 对照字典存在的话，将结果和对照字典进行匹配，返回匹配后的键或者值
	 * @param key 键
	 * @param dic 对照字典
	 * @param m 返回模式 为0 时返回字典的键，为1时返回 字典的值
	 * @return 字典为空或者找不到对应关系的返回空字符串
	 */
	public String getStringKV(Object key, String dic,int m)
	{
		return getStringKV(key, JSONObject.fromObject(dic), m);
	}
	
	/**
	 * 根据键 key 获取相应的值，如果 对照字典存在的话，将结果和对照字典进行匹配，返回匹配后的键或者值
	 * @param key 键
	 * @param dic 对照字典
	 * @param m 返回模式 为0 时返回字典的键，为1时返回 字典的值
	 * @return 字典为空或者找不到对应关系的返回空字符串
	 */
	public String getStringKV(Object key, Map dic,int m)
	{
		m = (m == 0? m : 1);
		/**
		 * 返回的结果
		 */
		Object obj = this.get(key);
		if(obj == null)
		{
			return "";
		}
		if(dic != null)
		{
			String[] tmp1 = String.valueOf(obj).split(",");
			if(tmp1.length>1)
			{
				obj = "";
				for(String str :tmp1)
				{
					if("".equals(obj))
					{
						if(dic.get(str) != null)
						{
							obj = String.valueOf(dic.get(str)).split(":", -1)[m];
						}
					}else
					{
						obj =obj + "," + String.valueOf(dic.get(str)).split(":", -1)[m];
					}
				}
				return String.valueOf(obj);
			}
			obj = dic.get(obj);
			if(obj != null)
			{
				String[] tmp = String.valueOf(obj).split(":", -1);
				if(tmp.length > 1)
				{
					return tmp[m];
				}else
				{
					return tmp[0];
				}
			}
		}
		return "";
	}
	
	/**
	 * 默认值为false
	 * @param key
	 * @return
	 */
	public boolean getBoolean(Object key)
	{
		return getBoolean(key,false);
	}
	
	/**
	 * 获取键值为key的value的布尔表示形式 <br/>
	 * 如果value为null，则返回默认值  <br/>
	 * 如果value为数字 当其内容大于0 时，返回true  <br/>
	 * 如果value为String 仅当其内容为"true"时返回true  <br/>
	 * @param key
	 * @param def 默认值
	 * @return
	 */
	public boolean getBoolean(Object key,boolean def)
	{
		Object obj = this.get(key);
		if(obj == null)
		{
			return def;
		}
		if (obj instanceof Boolean)
		{
			return (Boolean) obj;
		}
		if (obj instanceof Number)
		{
			return ((Number)obj).longValue() > 0;
		}
		if (obj instanceof String)
		{
			return Boolean.getBoolean((String)obj);
		}else
		{
			return Boolean.getBoolean(String.valueOf(obj));
		}
	}

	/**
	 * 返回该键所对应的 integer 的值，如果不存在或者不是整形，则返回 0
	 * @param key
	 * @return
	 */
	public Integer getI(Object key)
	{
		return getI(key,0);
	}
	
	/**
	 * 返回该键所对应的 integer 的值，如果不存在或者不是整形，则返回指定的 默认值
	 * @param key
	 * @param def
	 * @return
	 */
	public Integer getI(Object key, Integer def)
	{
		Object obj = this.get(key);
		if(obj == null)
		{
			return def;
		}
		if (obj instanceof Number)
		{
			return ((Number)obj).intValue();
		}
		String s_int;
		if (obj instanceof String)
		{
			s_int = (String)obj;
			
		}else
		{
			s_int = obj.toString();
		}
		try
		{
			if("".equals(s_int))
			{
				return def;
			}
			return new Integer(s_int);
		}catch (Exception e) {
			return def;
		}
	}
	
	public Integer getInteger(Object key)
	{
		return new Integer(this.get(key).toString());
	}

	public Double getDouble(Object key)
	{
		return new Double(this.get(key).toString());
	}

	public Date getDate(Object key)
	{
		return (Date) this.get(key);
	}
	
	/**
	 * 转换失败时返回null
	 * @param key
	 * @return
	 */
	public XMLGregorianCalendar getXMLDate(Object key)
	{
		GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(getDate(key));
        XMLGregorianCalendar gc = null;
        try {
            gc = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return gc;
	}

	public Object getObject(Object key)
	{
		return this.get(key);
	}
}
