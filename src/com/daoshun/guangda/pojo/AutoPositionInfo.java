package com.daoshun.guangda.pojo;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 
 * @author wjr 自动获取省市区表
 *
 */
@Entity
@Table(name="t_autoposition")
public class AutoPositionInfo implements java.io.Serializable {


	private static final long serialVersionUID = -4607145287962712193L;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="a_id")
    private int aid;
    
    @Column(name="province_id",length=10,nullable=false)
    private int provinceid;
    
    @Column(name="city_id",length=10,nullable=false)
    private int cityid;
    
    @Column(name="area_id",length=10,nullable=false)
    private int areaid;
    
    @Column(name="simulate_exam_url",length=200)
    private String simulateexamurl;
    
    @Column(name="book_reception_url",length=200)
    private String bookreceptionurl;
    
    @Column(name="marks",length=200)
    private String marks;
    
    //教练课程最低单价
 	@Column(name = "minprice", nullable = true, columnDefinition = "Decimal(20,2) default 0.00")
 	private BigDecimal minprice;
 	
 	//教练课程最高单价
 	@Column(name = "maxprice", nullable = true, columnDefinition = "Decimal(20,2) default 0.00")
 	private BigDecimal maxprice;
 	
 	//教练课程默认单价
 	/*@Column(name = "defaultprice", nullable = true, columnDefinition = "Decimal(20,2) default 0.00")
 	private BigDecimal defaultprice;*/
    

	public int getAid() {
		return aid;
	}

	public void setAid(int aid) {
		this.aid = aid;
	}

	public int getProvinceid() {
		return provinceid;
	}

	public void setProvinceid(int provinceid) {
		this.provinceid = provinceid;
	}

	public int getCityid() {
		return cityid;
	}

	public void setCityid(int cityid) {
		this.cityid = cityid;
	}

	public int getAreaid() {
		return areaid;
	}

	public void setAreaid(int areaid) {
		this.areaid = areaid;
	}

	public String getSimulateexamurl() {
		return simulateexamurl;
	}

	public void setSimulateexamurl(String simulateexamurl) {
		this.simulateexamurl = simulateexamurl;
	}

	public String getBookreceptionurl() {
		return bookreceptionurl;
	}

	public void setBookreceptionurl(String bookreceptionurl) {
		this.bookreceptionurl = bookreceptionurl;
	}

	public String getMarks() {
		return marks;
	}

	public void setMarks(String marks) {
		this.marks = marks;
	}

	public BigDecimal getMinprice() {
		return minprice;
	}

	public void setMinprice(BigDecimal minprice) {
		this.minprice = minprice;
	}

	public BigDecimal getMaxprice() {
		return maxprice;
	}

	public void setMaxprice(BigDecimal maxprice) {
		this.maxprice = maxprice;
	}

	/*public BigDecimal getDefaultprice() {
		return defaultprice;
	}
	public void setDefaultprice(BigDecimal defaultprice) {
		this.defaultprice = defaultprice;
	}*/
    
}
