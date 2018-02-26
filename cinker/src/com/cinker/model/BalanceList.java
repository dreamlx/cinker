package com.cinker.model;

public class BalanceList {
	
	private String BalanceTypeID;
	private boolean IsDefault;
	private Double LifetimePointsBalanceDisplay;
	private String Message;
	private String Name;
	private String NameAlt;
	private String[] NameTranslations;
	private Double PointsRemaining;
	private String RedemptionRate;
	
	public String getBalanceTypeID() {
		return BalanceTypeID;
	}
	public void setBalanceTypeID(String balanceTypeID) {
		BalanceTypeID = balanceTypeID;
	}
	public boolean isIsDefault() {
		return IsDefault;
	}
	public void setIsDefault(boolean isDefault) {
		IsDefault = isDefault;
	}
	public Double getLifetimePointsBalanceDisplay() {
		return LifetimePointsBalanceDisplay;
	}
	public void setLifetimePointsBalanceDisplay(Double lifetimePointsBalanceDisplay) {
		LifetimePointsBalanceDisplay = lifetimePointsBalanceDisplay;
	}
	public String getMessage() {
		return Message;
	}
	public void setMessage(String message) {
		Message = message;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getNameAlt() {
		return NameAlt;
	}
	public void setNameAlt(String nameAlt) {
		NameAlt = nameAlt;
	}
	public String[] getNameTranslations() {
		return NameTranslations;
	}
	public void setNameTranslations(String[] nameTranslations) {
		NameTranslations = nameTranslations;
	}
	public Double getPointsRemaining() {
		return PointsRemaining;
	}
	public void setPointsRemaining(Double pointsRemaining) {
		PointsRemaining = pointsRemaining;
	}
	public String getRedemptionRate() {
		return RedemptionRate;
	}
	public void setRedemptionRate(String redemptionRate) {
		RedemptionRate = redemptionRate;
	}
	
	

}
