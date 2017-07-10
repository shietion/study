package edu.devlopment.cms.excel.test;

import edu.devlopment.cms.excel.util.ExcelAttribute;

public class AreaModel {


	@ExcelAttribute(name="所属城市",isMer=true)
	private String city;
	
	@ExcelAttribute(name="所属片区",isMer=true)
	private String area ;
	
	@ExcelAttribute(name="区域类别",isMer=true)
	private String areaType = "" ;
	
	@ExcelAttribute(name="区域名称")
	private String areaName = "" ;
	
	@ExcelAttribute(name="区域包含的设备集合(多个设备编号用逗号分隔)",width=51)
	private String devs = "" ;
	
	@ExcelAttribute(name="区域图形描述(经纬度坐标点集合)",width=58)
	private String points = "" ;
	
	@ExcelAttribute(name="区域参数描述",width=58)
	private String params = "" ;
	
	@ExcelAttribute(name="备注")
	private String remark = "" ;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getAreaType() {
		return areaType;
	}

	public void setAreaType(String areaType) {
		this.areaType = areaType;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getDevs() {
		return devs;
	}

	public void setDevs(String devs) {
		this.devs = devs;
	}

	public String getPoints() {
		return points;
	}

	public void setPoints(String points) {
		this.points = points;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

    @Override
    public String toString() {
        return "AreaModel [city=" + city + ", area=" + area + ", areaType=" + areaType + ", areaName=" + areaName + ", devs=" + devs + ", points=" + points + ", params=" + params + ", remark=" + remark + "]";
    }
	
	
	
//	public String toString() {
//		StringBuffer sb = new StringBuffer() ;
//		sb = EsStrUtil.printFields(sb, this) ;
//		return sb.toString() ;
//	}
}
