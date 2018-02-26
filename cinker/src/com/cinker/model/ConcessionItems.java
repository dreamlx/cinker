package com.cinker.model;

public class ConcessionItems {
	
	private AlternateItems[] alternateItems;
	private String description;
	private String descriptionAlt;
	private String extendedDescription;
	private String extendedDescriptionAlt;
	private String headOfficeItemCode;
	private String itemId;
	private String priceInCents;
	
	
	public String getPriceInCents() {
		return priceInCents;
	}
	public void setPriceInCents(String priceInCents) {
		this.priceInCents = priceInCents;
	}
	public String getExtendedDescription() {
		return extendedDescription;
	}
	public void setExtendedDescription(String extendedDescription) {
		this.extendedDescription = extendedDescription;
	}
	public String getExtendedDescriptionAlt() {
		return extendedDescriptionAlt;
	}
	public void setExtendedDescriptionAlt(String extendedDescriptionAlt) {
		this.extendedDescriptionAlt = extendedDescriptionAlt;
	}
	public String getHeadOfficeItemCode() {
		return headOfficeItemCode;
	}
	public void setHeadOfficeItemCode(String headOfficeItemCode) {
		this.headOfficeItemCode = headOfficeItemCode;
	}
	public AlternateItems[] getAlternateItems() {
		return alternateItems;
	}
	public void setAlternateItems(AlternateItems[] alternateItems) {
		this.alternateItems = alternateItems;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDescriptionAlt() {
		return descriptionAlt;
	}
	public void setDescriptionAlt(String descriptionAlt) {
		this.descriptionAlt = descriptionAlt;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	
	

}
