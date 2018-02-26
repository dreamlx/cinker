package com.cinker.model;

import java.io.Serializable;

public class FilmContentImage implements Serializable{

	private static final long serialVersionUID = 3552949538687398888L;
	
	private String id;
	private String filmId;
	private String imageUrl;
	
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFilmId() {
		return filmId;
	}
	public void setFilmId(String filmId) {
		this.filmId = filmId;
	}
	
	

}
