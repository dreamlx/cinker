package com.cinker.model;



public class MemberItems {
	
	private String userSessionId;
	private String memberLevelId;
	private int qtyAvailable;
	private String recognitionID;
	private String cinemaType;

	public String getUserSessionId() {
		return userSessionId;
	}
	public void setUserSessionId(String userSessionId) {
		this.userSessionId = userSessionId;
	}
	public String getMemberLevelId() {
		return memberLevelId;
	}
	public void setMemberLevelId(String memberLevelId) {
		this.memberLevelId = memberLevelId;
	}
	public int getQtyAvailable() {
		return qtyAvailable;
	}
	public void setQtyAvailable(int qtyAvailable) {
		this.qtyAvailable = qtyAvailable;
	}
	public String getRecognitionID() {
		return recognitionID;
	}
	public void setRecognitionID(String recognitionID) {
		this.recognitionID = recognitionID;
	}
	public String getCinemaType() {
		return cinemaType;
	}
	public void setCinemaType(String cinemaType) {
		this.cinemaType = cinemaType;
	}


	

}
