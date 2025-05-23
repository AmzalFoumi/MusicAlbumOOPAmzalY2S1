package com.amzal.musicmanager.dao;

import java.util.ArrayList;

import com.amzal.musicmanager.model.Music;
import com.amzal.musicmanager.model.PublishedMusic;

public interface MusicDAOInterface {

	/**
	 * Saves a new Music object to the database.
	 *
	 * @param music The Music object to be saved.
	 * @return The generated ID of the newly inserted music record, or -1 if the
	 *         operation failed.
	 */
	int save(Music music);

	/**
	 * Retrieves a Music object from the database based on its ID.
	 *
	 * @param id The ID of the music record to retrieve.
	 * @return A Music object if found, otherwise null.
	 */
	Music getMusicById(int id);

	

	/**
	 * Updates the publishing information for a Music object in the database.
	 *
	 * @param pMusic The PublishedMusic object containing the publishing details.
	 * @return True if the update was successful, false otherwise.
	 */
	Boolean publish(PublishedMusic pMusic);

	/**
	 * Deletes a Music record from the database based on its ID.
	 *
	 * @param musicId The ID of the music record to delete.
	 * @return True if the deletion was successful, false otherwise.
	 */
	Boolean delete(int musicId);

	/**
	 * Retrieves a list of music entries for a given artist from the database.
	 *
	 * @param artistId The ID of the artist whose published music is to be
	 *                 retrieved.
	 * @return An ArrayList of PublishedMusic objects associated with the given
	 *         artist ID. If no published music is found, an empty list is returned.
	 */
	ArrayList<PublishedMusic> getAllMusicListByArtist(int artistId);

	PublishedMusic getPublishedMusicByID(int musicId);

	

}
/* *//**
			 * Retrieves a list of PublishedMusic objects from the database based on their
			 * artist.
			 *
			 * @param artistId The ID of the artist whose music records to retrieve.
			 * @return an Arraylist of all published music objects returned from the DB
			 *//*
				 * ArrayList<PublishedMusic> getPublishedMusicByArtist(int artistId);
				 */