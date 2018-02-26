package com.cinker.model;

import java.io.Serializable;

public class SelectedSeat implements Serializable{
	
	private static final long serialVersionUID = -8987690188872525927L;
	
	private String orderId;
	private String areaCategoryCode;
	private String areaNumber;
	private String rowIndex;
	private String columnIndex;
	private String physicalName;
	private String id;
	
	
	public String getAreaCategoryCode() {
		return areaCategoryCode;
	}
	public void setAreaCategoryCode(String areaCategoryCode) {
		this.areaCategoryCode = areaCategoryCode;
	}
	public String getAreaNumber() {
		return areaNumber;
	}
	public void setAreaNumber(String areaNumber) {
		this.areaNumber = areaNumber;
	}
	public String getRowIndex() {
		return rowIndex;
	}
	public void setRowIndex(String rowIndex) {
		this.rowIndex = rowIndex;
	}
	public String getColumnIndex() {
		return columnIndex;
	}
	public void setColumnIndex(String columnIndex) {
		this.columnIndex = columnIndex;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getPhysicalName() {
		return physicalName;
	}
	public void setPhysicalName(String physicalName) {
		this.physicalName = physicalName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
	

}
