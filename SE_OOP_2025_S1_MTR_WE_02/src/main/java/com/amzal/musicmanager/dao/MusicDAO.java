package com.amzal.musicmanager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import com.amzal.dbconnect.DBconnect;
import com.amzal.musicmanager.model.Music;
import com.amzal.musicmanager.model.PublishedMusic;

/**
 * Data Access Object (DAO) for managing Music objects in the database.
 * Implements the MusicDAOInterface to provide CRUD operations and other
 * data-related functionalities for music.
 */
public class MusicDAO implements MusicDAOInterface {

	
	private static MusicDAO instance;

	/**
     * Private constructor to enforce the Singleton pattern.
     */    
	private MusicDAO() {}

    /**
     * Static Method returns the singleton instance of the MusicDAO.
     *
     * @return The singleton instance of MusicDAO.
     */
    public static MusicDAO getMusicDAO() {
        if (instance == null) {
            synchronized (MusicDAO.class) { // Thread-safe initialization
                if (instance == null) {
                    instance = new MusicDAO();
                }
            }
        }
        return instance;
    }

    /**
	 * Saves a new Music object to the database.
	 *
	 * @param music The Music object to be saved.
	 * @return The generated ID of the newly inserted music record, or -1 if the
	 * operation failed.
	 */
	@Override
	public int save(Music music) {
	    String sql = "INSERT INTO songs (title, artistId, artist, genre, language, file_path) VALUES (?, ?, ?, ?, ?, ?)";
	    Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    int generatedId = -1;

	    try {
	        conn = DBconnect.getConnection();
	        if (conn == null) {
	            System.err.println("Database connection failed.");
	            return -1;
	        }
	        stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);  
	        stmt.setString(1, music.getTitle());
	        stmt.setInt(2, music.getArtistId());
	        stmt.setString(3, music.getArtistName());
	        stmt.setString(4, music.getGenre());
	        stmt.setString(5, music.getLanguage());
	        stmt.setString(6, music.getFilePath());

	        int rowsAffected = stmt.executeUpdate();
	        if (rowsAffected > 0) {
	            rs = stmt.getGeneratedKeys();
	            if (rs.next()) {
	                generatedId = rs.getInt(1); // return generated ID
	            }
	        } else {
	        	return -1;
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return -1;
	       
	    } finally {
	        // Close resources
	    	try {
	    		if (rs != null) rs.close();;
	    		if (stmt != null) stmt.close();
	    		if (conn != null) conn.close(); 
          } catch (SQLException e) {
              	e.printStackTrace();
              	return -1;
          }
	    }
	    return generatedId;
	}
	
	
	
	/**
	 * Retrieves a Music object from the database based on its ID.
	 *
	 * @param id The ID of the music record to retrieve.
	 * @return A Music object if found, otherwise null.
	 */
	@Override
    public Music getMusicById(int id) {
        String sql = "SELECT * FROM songs WHERE id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Open DB connection
            conn = DBconnect.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id); // Set the ID parameter

            rs = stmt.executeQuery(); // Execute SELECT

            // If a matching record is found
            if (rs.next()) {
                String title = rs.getString("title");
                int artistId = rs.getInt("artistId");
                String artistName = rs.getString("artist");
                String genre = rs.getString("genre");
                String language = rs.getString("language");
                String filePath = rs.getString("file_path");

                // Return a Music object constructed with DB values
                return new Music(id, title, artistId, artistName, genre, language, filePath);
            }

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            // Close resources
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return null; // Return null if not found
    }
	
	
	
	/**
	 * Retrieves a PublishedMusic object from the database based on its ID.
	 *
	 * @param musicId The ID of the published music record to retrieve.
	 * @return A PublishedMusic object if found, otherwise null.
	 */
	@Override
	public PublishedMusic getPublishedMusicByID(int musicId) {
	    String sql = "SELECT * FROM songs WHERE id = ?";
	    Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;

	    try {
	        // Open DB connection
	        conn = DBconnect.getConnection();
	        stmt = conn.prepareStatement(sql);
	        stmt.setInt(1, musicId); // Set the ID parameter

	        rs = stmt.executeQuery(); // Execute SELECT

	        if (rs.next()) {
	            int id = rs.getInt("id");
	            String title = rs.getString("title");
	            int artistId = rs.getInt("artistId");
	            String artistName = rs.getString("artist");
	            String genre = rs.getString("genre");
	            String language = rs.getString("language");
	            String filePath = rs.getString("file_path");

	            Boolean publishedStatus = rs.getBoolean("published");
	            String region = rs.getString("region");
	            double price = rs.getDouble("price");
	            String releaseDate = rs.getString("releaseDate");
	            int albumId = rs.getInt("albumId");
	            String albumName = rs.getString("albumName");

	            // Return a PublishedMusic object with the extracted data
	            return new PublishedMusic(id, title, artistId, artistName, genre, language, filePath,
	                    region, price, releaseDate, albumId, albumName, publishedStatus);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        // Close all resources
	        try {
	            if (rs != null) rs.close();
	            if (stmt != null) stmt.close();
	            if (conn != null) conn.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return null; // Return null if not found
	}
	
	
	/**
	 * Retrieves a list of  music entries for a given artist from the database.
	 *
	 * @param artistId The ID of the artist whose published music is to be retrieved.
	 * @return An ArrayList of PublishedMusic objects associated with the given artist ID.
	 *         If no published music is found, an empty list is returned.
	 */
	@Override
	public ArrayList<PublishedMusic> getAllMusicListByArtist(int artistId) {
	    String sql =  "SELECT * FROM songs WHERE artistId = ?";    //"SELECT * FROM songs WHERE artistId = ? AND published = 1";
	    Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    ArrayList<PublishedMusic> musicList = new ArrayList<>();

	    try {
	        conn = DBconnect.getConnection();
	        stmt = conn.prepareStatement(sql);
	        stmt.setInt(1, artistId);

	        rs = stmt.executeQuery();

	        while (rs.next()) {
	            int id = rs.getInt("id");
	            String title = rs.getString("title");
	            String artistName = rs.getString("artist");
	            String genre = rs.getString("genre");
	            String language = rs.getString("language");
	            String filePath = rs.getString("file_path");

	            Boolean publishedStatus = rs.getBoolean("published");
	            String region = rs.getString("region");
	            double price = rs.getDouble("price");
	            String releaseDate = rs.getString("releaseDate");
	            int albumId = rs.getInt("albumId");
	            String albumName = rs.getString("albumName");
	            

	            PublishedMusic musicReturned = new PublishedMusic(id, title, artistId, artistName, genre, language, filePath,
	                    region, price, releaseDate, albumId, albumName, publishedStatus);

	            musicList.add(musicReturned);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (stmt != null) stmt.close();
	            if (conn != null) conn.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return musicList;
	}
    
	/**
	 * Updates the publishing information for a Music object in the database.
	 *
	 * @param pMusic The PublishedMusic object containing the publishing details.
	 * @return True if the update was successful, false otherwise.
	 */
	@Override
    public Boolean publish(PublishedMusic pMusic) { 
    	String sql = "UPDATE songs SET price = ?, region = ?, releaseDate = ?, published = ?, albumId = ?, albumName = ? WHERE id = ?";  
        Connection conn = null;
        PreparedStatement stmt = null;
        //ResultSet rs = null;
        int rowsAffected = 0;
        Timestamp currentTimestamp = Timestamp.valueOf(LocalDateTime.now());
        
        try {
            // Open DB connection
            conn = DBconnect.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setDouble(1, pMusic.getPrice());       
            stmt.setString(2, pMusic.getRegion());        
            stmt.setTimestamp(3, currentTimestamp);
            stmt.setBoolean(4, true);
            stmt.setInt(5, pMusic.getAlbumId());
            stmt.setString(6, pMusic.getAlbumName());
            stmt.setInt(7, pMusic.getId());
            
            rowsAffected = stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            // Close resources
            try {
                //if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } 	
        return rowsAffected > 0; //Returns true if only the sql statement runs properly
    }


	/**
	 * Deletes a Music record from the database based on its ID.
	 *
	 * @param musicId The ID of the music record to delete.
	 * @return True if the deletion was successful, false otherwise.
	 */
	@Override
	public Boolean delete(int musicId) {
		
		String sql = "DELETE FROM songs WHERE id = ?";  
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int rowsAffected = 0;
		
		
		try {
			conn = DBconnect.getConnection();
			
			stmt = conn.prepareStatement(sql);
			
			stmt.setInt(1, musicId);
			
			rowsAffected = stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
		}
		
		return rowsAffected > 0;
	}

	
}	