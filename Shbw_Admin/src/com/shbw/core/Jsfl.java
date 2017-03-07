package com.shbw.core;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;



/**
 * 价税分离工具类
 * @author Administrator
 *
 */
public class Jsfl {
	
	/**
	 * 计算保留小数位
	 */
	private int SCALE;
	
	private MathContext mc;
	private RoundingMode RM = RoundingMode.HALF_UP;
	
	/**
	 * 输出保留小数位
	 */
	private int SCALEOUT = 2;
	
	private MathContext mcOut;
	
	/**
	 * 含税税率标识
	 * 0是专普票，1是减按计增，2是差额征收
	 */
	private int HSSLBS = 0;
	
	/**
	 * 金额合计（不含税）
	 */
	private BigDecimal jehj;
	
	/**
	 * 税额合计
	 */
	private BigDecimal sehj;
	
	/**
	 * 价税合计
	 */
	private BigDecimal jshj;
	
	private Spxx lastSpxx;
	
	/**
	 * 明细数据内容
	 */
	private List<Spxx> mx = new ArrayList<Spxx>();
	
	/**
	 * 金额合计
	 * @return
	 * @throws Exception 
	 */
	public String getJehj() throws Exception {
		if(jehj == null)
		{
			js();
		}
		return jehj.setScale(SCALEOUT, RM).toString();
	}

	/**
	 * 税额合计
	 * @return
	 * @throws Exception 
	 */
	public String getSehj() throws Exception {
		if(sehj == null)
		{
			js();
		}
		return sehj.setScale(SCALEOUT, RM).toString();
	}

	/**
	 * 价税合计
	 * @return
	 * @throws Exception 
	 */
	public String getJshj() throws Exception {
		if(jshj == null)
		{
			js();
		}
		return jshj.setScale(SCALEOUT, RM).toString();
	}
	
	/**
	 * 获取最后添加的明细 不含税单价
	 * @return
	 * @throws Exception 
	 */
	public String getMxDj() throws Exception
	{
		if(lastSpxx == null)
		{
			throw new Exception("没有明细数据");
		}
		return lastSpxx.getSpdj();
	}
	
	/**
	 * 获取最后添加的明细  金额
	 * @return
	 * @throws Exception 
	 */
	public String getMxJe() throws Exception
	{
		if(lastSpxx == null)
		{
			throw new Exception("没有明细数据");
		}
		return lastSpxx.getJeStr();
	}
	
	/**
	 * 获取最后添加的明细  税额
	 * @return
	 * @throws Exception 
	 */
	public String getMxSe() throws Exception
	{
		if(lastSpxx == null)
		{
			throw new Exception("没有明细数据");
		}
		return lastSpxx.getSeStr();
	}
	
	/**
	 * 获取最后添加的明细  合计金额
	 * @return
	 * @throws Exception 
	 */
	public String getMxHjje() throws Exception
	{
		if(lastSpxx == null)
		{
			throw new Exception("没有明细数据");
		}
		return lastSpxx.getHjjeStr();
	}

	/**
	 * 价税分离计算，空数据对象,设置保留小数位
	 */
	public Jsfl(int scale) {
		this.SCALE = scale;
		mc = new MathContext(20, RM);
		mcOut = new MathContext(20, RM);
	}
	
	/**
	 * 价税分离计算，空数据对象,设置保留小数位,可设置特殊税率计算方式
	 */
	public Jsfl(int scale, int hsslbs) {
		this(scale);
		this.HSSLBS = hsslbs;
	}
	
	/**
	 * 价税分离计算，默认   不含税
	 * @param spdj 单价
	 * @param spsl 商品数量
	 * @param sl 税率
	 * @throws Exception 
	 */
	public Jsfl(String spdj,String spsl, String sl) throws Exception {
		this(spdj,spsl, sl, false);
	}
	
	/**
	 * 价税分离计算,带有含税标志
	 * @param spdj 单价
	 * @param spsl 商品数量
	 * @param sl 税率
	 * @param isHs 是否含税
	 * @throws Exception 
	 */
	public Jsfl(String spdj,String spsl, String sl, boolean isHs) throws Exception {
		this(6);
		addMx(spdj, spsl, sl, isHs);
	}
	
	/**
	 * 价税分离计算,带有含税标志
	 * @param spdj 单价
	 * @param spsl 商品数量
	 * @param sl 税率
	 * @param isHs 是否含税
	 * @throws Exception 
	 */
	public Jsfl addMx(String spdj,String spsl, String sl, boolean isHs) throws Exception {
		return addMx(spdj, spsl, sl, null, isHs);
	}
	
	/**
	 * 价税分离计算,带有含税标志
	 * @param spdj 单价
	 * @param spsl 商品数量
	 * @param sl 税率
	 * @param skcjel 扣除金额 差额征收时使用
	 * @param isHs 是否含税
	 * @throws Exception 
	 */
	public Jsfl addMx(String spdj,String spsl, String sl, String kcje, boolean isHs) throws Exception {
		if(sehj !=null)
		{
			throw new Exception("已合计，不能再添加明细数据");
		}
		lastSpxx = new Spxx(spdj, spsl, sl, kcje, isHs);
		mx.add(lastSpxx);
		return this;
	}
	
	/**
	 * 计算合计数据
	 * @throws Exception 
	 */
	private void js() throws Exception
	{
		jehj = new BigDecimal("0",mcOut).setScale(SCALE, RM);
		sehj = new BigDecimal("0",mcOut).setScale(SCALE, RM);
		jshj = new BigDecimal("0",mcOut).setScale(SCALE, RM);
		for(Spxx sp : mx)
		{
			jehj =  jehj.add(sp.getJe());
			sehj =  sehj.add(sp.getSe());
			jshj = jshj.add(sp.getHjje());
		}
	}

	public int getSCALE() {
		return SCALE;
	}

	public void setSCALE(int scale) {
		SCALE = scale;
	}

	
	///////////////////////////////////////
	public class Spxx
	{
		/**
		 * 商品单价，含税
		 */
		private BigDecimal spdj;
		
		/**
		 * 商品单价，不含税
		 */
		private BigDecimal spdj2;
		
		/**
		 * 商品数量
		 */
		private BigDecimal spsl;
		
		/**
		 * 税率
		 */
		private BigDecimal sl;
		
		/**
		 * 税额
		 */
		private BigDecimal se;
		
		/**
		 * 金额
		 */
		private BigDecimal je;
		
		/**
		 * 扣除金额
		 */
		private BigDecimal kcje;
		
		/**
		 * 合计金额
		 */
		private BigDecimal hjje;
		
		/**
		 * 是否含税，默认  含税
		 */
		private boolean isHs = true;
		
		public String getSpdj() {
			return spdj2.setScale(SCALEOUT, RM).toString();
		}

		/**
		 * 是否含税
		 * @return
		 */
		public boolean isHs() {
			return isHs;
		}

		public Spxx(String spdj,String spsl, String sl, boolean isHs) {
			this(spdj, spsl, sl, null, isHs);
		}
		
		public Spxx(String spdj,String spsl, String sl, String kcje, boolean isHs) 
		{
			this.sl = new BigDecimal(sl, mc).setScale(SCALE, RM);
			this.spsl = new BigDecimal(spsl, mc).setScale(SCALE, RM);
			if(kcje != null)
			{
				this.kcje = new BigDecimal(kcje, mcOut).setScale(SCALE, RM);
			}
			this.isHs = isHs;
			if(isHs)
			{//含税数据
				
				this.spdj = new BigDecimal(spdj, mc).setScale(SCALE, RM);
				//不含税单价计算
				//单价（不含税）  =  单价（含税）/（1+税率）
				this.spdj2 = this.spdj.divide(this.sl.add(new BigDecimal("1")), SCALE, BigDecimal.ROUND_HALF_UP);
			}else
			{//不含税数据
				
				this.spdj2 = new BigDecimal(spdj, mc).setScale(SCALE, RM);
				// 单价（含税） = 单价（不含税） * (1+税率)
				this.spdj = this.spdj2.multiply(this.sl.add(new BigDecimal("1")));
			}
		}
		
		/**
		 * 获取 明细 金额(不含税)
		 * 
		 * = 单价（含税）/（1+税率）*数量
		 * = 单价（不含税）*数量
		 * @return
		 */
		public BigDecimal getJe()
		{
			if(je == null)
			{
				je = this.spdj2.multiply(this.spsl).setScale(SCALEOUT, BigDecimal.ROUND_HALF_UP);
			}
			return je;
		}
		
		/**
		 * 获取 明细 金额(不含税)
		 * 
		 * = 单价（含税）/（1+税率）*数量
		 * = 单价（不含税）*数量
		 * @return
		 */
		public String getJeStr()
		{
			return getJe().setScale(SCALEOUT, RM).toString();
		}
		
		/**
		 * 获取 明细 税额
		 * 
		 * = 单价（不含税） * 税率 * 数量
		 * @return
		 * @throws Exception 
		 */
		public BigDecimal getSe() throws Exception
		{
			if(se == null)
			{
				if(HSSLBS == 0)
				{//税额 = 单价（不含税） * 税率 * 数量
					this.se = this.spdj2.multiply(this.sl).multiply(this.spsl).setScale(SCALEOUT, BigDecimal.ROUND_HALF_UP);
				}else if(HSSLBS == 1)
				{//税额 = 单价（不含税） * 1.5% * 数量------待确定
					this.se = this.spdj2.multiply(new BigDecimal(0.015)).multiply(this.spsl).setScale(SCALEOUT, BigDecimal.ROUND_HALF_UP);
				}else if(HSSLBS == 2)
				{//税额 = (单价（不含税） - 扣除金额)* 税率 * 数量------待确定
					this.se = this.spdj2.subtract(this.kcje).multiply(this.sl).multiply(this.spsl).setScale(SCALEOUT, BigDecimal.ROUND_HALF_UP);
				}else
				{
					throw new Exception("错误的含税税率标识：" + HSSLBS);
				}
			}
			return se;
		}
		
		/**
		 * 获取 明细 税额
		 * 
		 * = 单价（不含税） * 税率 * 数量
		 * @return
		 * @throws Exception 
		 */
		public String getSeStr() throws Exception
		{
			return getSe().setScale(SCALEOUT, RM).toString();
		}
		
		/**
		 * 获取 明细 合计金额(价税合计-明细)</p>
		 * 
		 * = 税额（明细）+ 金额(不含税)<b>（默认）</b></p>
		 * 
		 * = 单价（含税）* 数量
		 * @return
		 * @throws Exception 
		 */
		public BigDecimal getHjje() throws Exception
		{
			if(hjje == null)
			{//合计金额(价税合计） = 单价（含税）* 数量
				this.hjje = getJe().add(getSe());
			}
			return hjje;
		}
		
		/**
		 * 获取 明细 合计金额(价税合计-明细)</p>
		 * 
		 * = 税额（明细）+ 金额(不含税)<b>（默认）</b></p>
		 * 
		 * = 单价（含税）* 数量
		 * @return
		 * @throws Exception 
		 */
		public String getHjjeStr() throws Exception
		{
			return getHjje().setScale(SCALEOUT, RM).toString();
		}
		
		/**
		 * 获取税额合计
		 * =价税合计-金额合计
		 * @return
		 * @throws Exception
		 */
		public String getSehj() throws Exception
		{
			return Cf.mul(getJshj(), getJehj());
		}
		
	}
	public static void main(String[] args) throws Exception {
		Jsfl js=new Jsfl(6);
		js.addMx("20", "1000", "0.06", true);
		System.out.println("================="+js.getMxSe());
		js.addMx("20", "1", "0.06", true);
		System.out.println("================="+js.getMxJe());
		System.out.println("================="+js.getMxSe());
		System.out.println("================="+js.getJehj());
		System.out.println("================="+js.getSehj());
	}
}
