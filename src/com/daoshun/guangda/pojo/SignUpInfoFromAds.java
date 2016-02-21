package com.daoshun.guangda.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
/**
 * 版本信息表
 * @author liukn
 *
 */
@Entity
@Table(name = "t_sign_up_from_ads")
public class SignUpInfoFromAds implements Serializable {

	// 版本id
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "signupId", length = 10, nullable = false)
	private int signupId;

	@Column(name = "name", nullable = false, columnDefinition = "varchar(100)")
	private String name;
	
	@Column(name = "phone", length = 50, nullable = false)
	private String phone;
	
	// 添加时间
	@Column(name = "addtime")
	private Date addtime;
	
	
	/**
	 * 广告名称
	 */
	@Column(name="adsTypeName",columnDefinition = "varchar(100)")
	private String adsTypeName;
	
	/**
	 *   0:待处理   1:已处理 
	 */
	@Column(name = "state", length = 1, nullable = false, columnDefinition = "INT default 0")
	private Integer state = 0;

	@Column(name="processInfo")
	private String processInfo;
	
	// 处理时间
	@Column(name = "processTime")
	private Date processTime;

	/**
	 * 处理的人员ID
	 */
	@Column(name = "processorId", length = 20, nullable = false)
	private int processorId;

	/**
	 * 是否删除
	 */
	@Column(name = "isAbandoned")
	private int isAbandoned=0;
	
	
	/**
	 * 客服信息
	 */
	@Transient
	private AdminInfo processor;
	
	/**
	 * 报名渠道来源
	 */
	@Column(name = "source", length = 50)
	private String source;
	
	public int getSignupId()
	{
		return signupId;
	}

	public void setSignupId(int signupId)
	{
		this.signupId = signupId;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public Date getAddtime()
	{
		return addtime;
	}

	public void setAddtime(Date addtime)
	{
		this.addtime = addtime;
	}

	public String getAdsTypeName()
	{
		return adsTypeName;
	}

	public void setAdsTypeName(String adsTypeName)
	{
		this.adsTypeName = adsTypeName;
	}

	public Integer getState()
	{
		return state;
	}

	public void setState(Integer state)
	{
		this.state = state;
	}

	public String getProcessInfo()
	{
		return processInfo;
	}

	public void setProcessInfo(String processInfo)
	{
		this.processInfo = processInfo;
	}

	public Date getProcessTime()
	{
		return processTime;
	}

	public void setProcessTime(Date processTime)
	{
		this.processTime = processTime;
	}

	public int getProcessorId()
	{
		return processorId;
	}

	public void setProcessorId(int processorId)
	{
		this.processorId = processorId;
	}

	public int getIsAbandoned()
	{
		return isAbandoned;
	}

	public void setIsAbandoned(int isAbandoned)
	{
		this.isAbandoned = isAbandoned;
	}

	public AdminInfo getProcessor()
	{
		return processor;
	}

	public void setProcessor(AdminInfo processor)
	{
		this.processor = processor;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	
	
	
	

}
