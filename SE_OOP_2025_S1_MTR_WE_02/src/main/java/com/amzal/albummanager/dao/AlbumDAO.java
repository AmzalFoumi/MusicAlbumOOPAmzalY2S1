package com.amzal.albummanager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.amzal.albummanager.model.Album;
import com.amzal.dbconnect.DBconnect;

public class AlbumDAO implements AlbumDAOInterface {

	public AlbumDAO() {	
	}

	@Override
	public Album getAlbumByName(String albumName) {
		
		String sql = "SELECT * FROM album WHERE albumName = ?";
		Connection conn = DBconnect.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Album album = null;
        
        
        try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, albumName);
			
			rs = stmt.executeQuery();
			
			if(rs.next()) {
				int albumId = rs.getInt("id");
				int artistId = rs.getInt("artistId");
				String artistName = rs.getString("artistName");
				int songCount = rs.getInt("songCount");
				
				return album = new Album(albumId, albumName, artistId, artistName, songCount);
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
       
		return null;
	}

	@Override
	public Album createAlbumByName(String albumName) {
		
		
		String sql = "INSERT INTO album (albumName, songCount) VALUES (?,?)";
		Connection conn = DBconnect.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int generatedId = -1;
		int songCount = 1;  //Album is created by name when a song is added to an album that doesnt exist, so it starts with a song count of 1
		
		try {
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1,albumName);
			stmt.setInt(2, songCount);
			
			int rowsAffected = stmt.executeUpdate();
	        if (rowsAffected > 0) {
	            rs = stmt.getGeneratedKeys();
	            if (rs.next()) {
	                generatedId = rs.getInt(1); // return generated ID
	            }
	        } else {
	        	return null;
	        }
	        
	        Album albumCreated = new Album(generatedId, albumName, songCount);
	        
	        return albumCreated;
			
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
		
		return null;
	}
	
	@Override
	public boolean incrementSongCount(int albumId) {
	    String sql = "UPDATE album SET songCount = songCount + 1 WHERE id = ?";
	    Connection conn = null;
	    PreparedStatement stmt = null;

	    try {
	        conn = DBconnect.getConnection();
	        stmt = conn.prepareStatement(sql);
	        stmt.setInt(1, albumId);

	        int rowsAffected = stmt.executeUpdate();
	        return rowsAffected > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    } finally {
	        try {
	            if (stmt != null) stmt.close();
	            if (conn != null) conn.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}
	
}
