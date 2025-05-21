
package com.amzal.musicmanager.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.amzal.musicmanager.dao.MusicDAO;
import com.amzal.musicmanager.dao.MusicDAOInterface;
import com.amzal.musicmanager.model.Music;
import com.amzal.util.AudioUploadService;


@WebServlet("/musicUploadController")
@MultipartConfig // Enables file upload support
public class MusicUploadControlServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, UnsupportedEncodingException {
	    String title = request.getParameter("title");
	    String genre = request.getParameter("genre");
	    String language = request.getParameter("language");
	    
	    String actionValue = request.getParameter("actionValue");
	    
	    Part filePart = request.getPart("audioFile");

        //Get filename from uploaded file
        String originalFileName = filePart.getSubmittedFileName();
        
        //Getting the Servlet Context
        ServletContext servletContext = getServletContext();
        
        //Calling the method in AudioUploadService class that will handle the audio file, passing the filepart and servletContext objects and the originalFileName String
        String filePath = AudioUploadService.handleAudioUpload(filePart, servletContext, originalFileName);

	
	    Music music = new Music(title, genre, language, filePath); //Constructor of music object
	    
	    MusicDAOInterface DAO = MusicDAO.getMusicDAO();
	    int saveReturn = DAO.save(music);
	
	    if (saveReturn == -1) {
	    	request.setAttribute("errorMsg", "Failed to save music details. Please try again.");
            request.getRequestDispatcher("musicUploadForm.jsp").forward(request, response);
            
        } else {

        	response.sendRedirect("musicPreviewController?musicId=" + saveReturn + "&musicTitle=" + URLEncoder.encode(music.getTitle(), "UTF-8") + "&actionValue=" + actionValue);
        }
	}
}


