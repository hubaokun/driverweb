package com.daoshun.guangda.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.type.descriptor.sql.VarcharTypeDescriptor;

/**
 * @author liukn
 *
 */
@Entity
@Table(name = "t_teach_address")
public class CaddAddressInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1202207835234140893L;
	
	//地址Id
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "addressid", length=10, nullable = false)
	private int addressid;
	
	
	//教练Id
	@Column(name = "coachid",length=10,nullable = false)
	private int coachid;
	
	//经度
	@Column(name = "longitude",length=50,nullable = false)
	private String longitude;
	
	//纬度
	@Column(name = "latitude",length=50,nullable = false)
	private String latitude;
		
	//当前使用地址
	@Column(name = "iscurrent",length=1,nullable = false, columnDefinition = "INT default 0")
	private int iscurrent; 
	
	//地址所属区
	@Column(name = "area",length=100,nullable = false)
	private String area;
	
	//地址详细
	@Column(name = "detail", length=400,nullable = false)
	private String detail;

	
	public int getAddressid() {
		return addressid;
	}

	
	public void setAddressid(int addressid) {
		this.addressid = addressid;
	}

	
	public int getCoachid() {
		return coachid;
	}

	
	public void setCoachid(int coachid) {
		this.coachid = coachid;
	}

	
	public String getLongitude() {
		return longitude;
	}

	
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	
	public String getLatitude() {
		return latitude;
	}

	
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	
	public int getIscurrent() {
		return iscurrent;
	}

	
	public void setIscurrent(int iscurrent) {
		this.iscurrent = iscurrent;
	}

	
	public String getArea() {
		return area;
	}

	
	public void setArea(String area) {
		this.area = area;
	}

	
	public String getDetail() {
		return detail;
	}

	
	public void setDetail(String detail) {
		this.detail = detail;
	}
	
	
	
}
