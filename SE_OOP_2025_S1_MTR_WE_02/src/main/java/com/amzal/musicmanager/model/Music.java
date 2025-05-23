package com.amzal.musicmanager.model;

public class Music {
    private int id;
	private String title;
	private int artistId;
	private String artistName;
    private String genre;
    private String language;
    private String filePath;
	
    public Music(String title, int artistId, String artistName, String genre, String language,  String filePath) {
		
		this.title = title;
		this.artistId = artistId;
		this.artistName = artistName;
		this.genre = genre;
		this.language = language;
        this.filePath = filePath;
	}
    

	public Music(int id, String title, int artistId, String artistName,String genre, String language,  String filePath) {
		
		this.id = id;
		this.title = title;
		this.artistId = artistId;
		this.artistName = artistName;
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
	 * 
	 * @return the artist ID
	 */
	public int getArtistId() {
		return artistId;
	}

	/**
	 * 
	 * @return the artist name
	 */
	public String getArtistName() {
		return artistName;
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

	/**
	 * @return the file path
	 */
	public String getFilePath() {
		return filePath;
	}
 
}