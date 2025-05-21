package com.amzal.musicmanager.model;

public class Music {
    private int id;
	private String title;
    private String genre;
    private String language;
    private String filePath;
	
    public Music(String title, String genre, String language,  String filePath) {
		
		this.title = title;
		this.genre = genre;
		this.language = language;
        this.filePath = filePath;
	}
    

	public Music(int id, String title, String genre, String language,  String filePath) {
		
		this.id = id;
		this.title = title;
		this.genre = genre;
		this.language = language;
        this.filePath = filePath;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return the genre
	 */
	public String getGenre() {
		return genre;
	}

	/**
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}

	public String getFilePath() {
		return filePath;
	}
 
}