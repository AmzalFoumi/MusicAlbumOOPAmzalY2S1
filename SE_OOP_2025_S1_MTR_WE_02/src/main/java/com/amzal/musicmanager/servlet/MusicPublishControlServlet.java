package com.amzal.musicmanager.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amzal.albummanager.dao.AlbumDAO;
import com.amzal.albummanager.dao.AlbumDAOInterface;
import com.amzal.albummanager.model.Album;
import com.amzal.musicmanager.dao.MusicDAO;
import com.amzal.musicmanager.dao.MusicDAOInterface;
import com.amzal.musicmanager.model.Music;
import com.amzal.musicmanager.model.PublishedMusic;


/**
 * Handles publishing of a music track by saving publication details such as region, price,
 * release date, and optional album association.
 *
 * URL pattern: /musicPublishController
 *
 * Expects POST parameters: musicId, price, region, releaseDate, albumName.
 * If albumName is provided, it checks if the album exists or creates it,
 * then publishes the music with the associated details.
 */
@WebServlet("/musicPublishController")
public class MusicPublishControlServlet extends HttpServlet {

	
	/**
     * Processes POST requests to publish music.
     *
     * Retrieves music and album details, creates or updates album if needed,
     * and attempts to publish the music. Forwards to success or retry page based on result.
     *
     * @param request  the HTTP request containing publication details
     * @param response the HTTP response used to forward or redirect
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
	protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		int musicId = Integer.parseInt(request.getParameter("musicId"));
//		String title = request.getParameter("title");
//	    String genre = request.getParameter("genre");
//	    String language = request.getParameter("language");
		
		Double price = Double.parseDouble(request.getParameter("price"));
	    String region = request.getParameter("region");
	    String releaseDate = request.getParameter("releaseDate");
	    String albumName = request.getParameter("albumName");
	    
	    int albumId = 0;  //Album ID shows as zero if not associated with any album
	    
	    //Getting the DAO
	 	MusicDAOInterface musDAO = MusicDAO.getMusicDAO();
	 	
	 	//Getting the music details from DB
	 	Music music = musDAO.getMusicById(musicId);
	    
	    //Processing of album getting or creation if user fills album field
	    if (albumName != null && !albumName.trim().isEmpty()) {
	        AlbumDAOInterface albDAO = new AlbumDAO();
	        Album albumReturned = albDAO.getAlbumByName(albumName);

	        if (albumReturned == null) {
	            Album albumCreated = albDAO.createAlbumByName(albumName);
	            if (albumCreated != null) {
	                albumId = albumCreated.getAlbumId();
	            } else {
	                request.setAttribute("errorMsg", "Failed to create album. Please try again.");
	                //Music music = new Music(musicId, title, genre, language);
	                request.setAttribute("music", music);
	                request.getRequestDispatcher("musicPublishForm.jsp").forward(request, response);
	                return;
	            }
	        } else {
	        	//Using existing album
	            albumId = albumReturned.getAlbumId();
	            
	            //Increment song count for existing album
	            boolean isUpdated = albDAO.incrementSongCount(albumId);
	            if (!isUpdated) {
	                request.setAttribute("errorMsg", "Failed to update album song count.");
	                //Music music = new Music(musicId, title, genre, language);
	                request.setAttribute("music", music);
	                request.getRequestDispatcher("musicPublishForm.jsp").forward(request, response);
	                return;
	            }
	        }
	    } else {
	        // Album field left empty, so we do not associate this music with any album
	        albumName = null; 
	    }
	    
	    
	    
	    PublishedMusic pMusic = new PublishedMusic(music, region, price, releaseDate, albumId, albumName);
	    

	    Boolean isPublished = musDAO.publish(pMusic);
	    
	    
		if (isPublished) {
			
            request.getRequestDispatcher("test.jsp").forward(request, response);

            
        } else {
        	request.setAttribute("errorMsg", "Failed to publish music details. Please try again.");
        	request.setAttribute("music", pMusic);
            request.getRequestDispatcher("musicPublishForm.jsp").forward(request, response);
        }
	    
	}

}