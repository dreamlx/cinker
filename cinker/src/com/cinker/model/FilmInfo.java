package com.cinker.model;

import java.io.Serializable;

public class FilmInfo implements Serializable{

	private static final long serialVersionUID = -7809688999146308453L;
	
	private int id;
	private String filmId;
	private String ChineseName;
	private String EnglishName;
	private String SurfaceImage;
	private String imageUrl;
	private String Director;
	private String ScriptWriter;
	private String ShowTime;
	private String RunTime;
	private String StarringActor;
	private String FilmType;
	private String Language;
	private String cinecism;
		
	public String getFilmId() {
		return filmId;
	}
	public void setFilmId(String filmId) {
		this.filmId = filmId;
	}
	public String getChineseName() {
		return ChineseName;
	}
	public void setChineseName(String chineseName) {
		ChineseName = chineseName;
	}
	public String getEnglishName() {
		return EnglishName;
	}
	public void setEnglishName(String englishName) {
		EnglishName = englishName;
	}
	public String getSurfaceImage() {
		return SurfaceImage;
	}
	public void setSurfaceImage(String surfaceImage) {
		SurfaceImage = surfaceImage;
	}
	public String getDirector() {
		return Director;
	}
	public void setDirector(String director) {
		Director = director;
	}
	public String getScriptWriter() {
		return ScriptWriter;
	}
	public void setScriptWriter(String scriptWriter) {
		ScriptWriter = scriptWriter;
	}
	public String getShowTime() {
		return ShowTime;
	}
	public void setShowTime(String showTime) {
		ShowTime = showTime;
	}
		
	public String getRunTime() {
		return RunTime;
	}
	public void setRunTime(String runTime) {
		RunTime = runTime;
	}
	public String getStarringActor() {
		return StarringActor;
	}
	public void setStarringActor(String starringActor) {
		StarringActor = starringActor;
	}
	public String getFilmType() {
		return FilmType;
	}
	public void setFilmType(String filmType) {
		FilmType = filmType;
	}
	public String getLanguage() {
		return Language;
	}
	public void setLanguage(String language) {
		Language = language;
	}
	
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCinecism() {
		return cinecism;
	}
	public void setCinecism(String cinecism) {
		this.cinecism = cinecism;
	}
	
	

	
	

}
