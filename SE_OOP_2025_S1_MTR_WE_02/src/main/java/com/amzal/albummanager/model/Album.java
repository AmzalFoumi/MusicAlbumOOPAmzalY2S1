package com.amzal.albummanager.model;

public class Album {
	
	private int albumId;
	private String albumName;
	private int artistId;
	private String artistName;
	private int songCount;

	//Various overloaded constructors to be used for different requirements
	public Album(String albumName, String artistName, int songCount) {
		this.albumName = albumName;
		this.artistName = artistName;
		this.songCount = songCount;
	}
	
	public Album(int albumId, String albumName, /*String artistName,*/ int songCount) {
		this.albumId = albumId;
		this.albumName = albumName;
		//this.artistId = artistId;
		//this.artistName = artistName;
		this.songCount = songCount;
	}
	
	public Album(int albumId, String albumName, int artistId, String artistName, int songCount) {
		this.albumId = albumId;
		this.albumName = albumName;
		this.artistId = artistId;
		this.artistName = artistName;
		this.songCount = songCount;
	}

	/**
	 * @return the albumId
	 */
	public int getAlbumId() {
		return albumId;
	}

	/**
	 * @return the albumName
	 */
	public String getAlbumName() {
		return albumName;
	}

	/**
	 * @return the artistId
	 */
	public int getArtistId() {
		return artistId;
	}

	/**
	 * @return the artistName
	 */
	public String getArtistName() {
		return artistName;
	}

	/**
	 * @return the songCount
	 */
	public int getSongCount() {
		return songCount;
	}

	/**
	 * @param albumId the albumId to set
	 */
	public void setAlbumId(int albumId) {
		this.albumId = albumId;
	}

	/**
	 * @param albumName the albumName to set
	 */
	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}

	/**
	 * @param artistId the artistId to set
	 */
	public void setArtistId(int artistId) {
		this.artistId = artistId;
	}

	/**
	 * @param artistName the artistName to set
	 */
	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}

	/**
	 * @param songCount the songCount to set
	 */
	public void setSongCount(int songCount) {
		this.songCount = songCount;
	}
	
	

}
