package com.amzal.musicmanager.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amzal.musicmanager.dao.MusicDAO;
import com.amzal.musicmanager.dao.MusicDAOInterface;


/**
 * Servlet that handles deletion of a music record.
 *
 * Expects a POST request with a "musicId" parameter.
 * Deletes the music entry from the database if found.
 * Redirects to dashboard with a success message upon successful deletion.
 * 
 * URL pattern: /musicDeleteController
 */
@WebServlet("/musicDeleteController")
public class MusicDeleteControlServlet extends HttpServlet {

	/**
     * Processes POST requests to delete a music record.
     *
     * Retrieves the musicId from the request, deletes the record using DAO,
     * and redirects with a status message if successful.
     *
     * @param request  the HTTP request containing the musicId
     * @param response the HTTP response used for redirection
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
	protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException ,IOException {
		
		int musicId=-1;
		
		try {
			 musicId = Integer.parseInt(request.getParameter("musicId"));			
			
		} catch (NumberFormatException e) {
			
			e.printStackTrace();
		}
		
		MusicDAOInterface DAO = MusicDAO.getMusicDAO();
		Boolean isDeleted = DAO.delete(musicId);
		
		if (isDeleted) {
			String message = "music+deleted";
			response.sendRedirect("test.jsp?message=" + message);
		}
	}
	

}
