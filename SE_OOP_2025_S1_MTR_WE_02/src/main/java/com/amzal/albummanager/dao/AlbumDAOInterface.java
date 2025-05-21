package com.amzal.albummanager.dao;

import com.amzal.albummanager.model.Album;

public interface AlbumDAOInterface {
	public Album getAlbumByName(String albumName);
	
	public Album createAlbumByName(String albumName);

	boolean incrementSongCount(int albumId);
}
