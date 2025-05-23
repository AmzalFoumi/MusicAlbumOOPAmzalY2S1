package com.amzal.musicmanager.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amzal.musicmanager.dao.MusicDAO;
import com.amzal.musicmanager.dao.MusicDAOInterface;
import com.amzal.musicmanager.model.Music;
import com.amzal.musicmanager.model.PublishedMusic;

/**
 * Servlet that handles preview-related actions for uploaded and published music.
 * 
 * This controller manages redirection to different preview and form pages such as:
 * - Upload preview (`musicUploadPreview.jsp`)
 * - Publish form (`musicPublishForm.jsp`)
 * - Delete confirmation (`musicDeleteConfirm.jsp`)
 * - Published music preview (`publishedMusicPreview.jsp`)
 * 
 * Expected parameters:
 * - musicId: The ID of the music to preview or process.
 * - title: The title of the music (for display or validation).
 * - actionValue: Determines the target action/view (upload, publish, delete, publishedMusic).
 * - isPublished: If set to "true", attempts to retrieve a PublishedMusic object instead.
 * 
 * URL pattern: /musicPreviewController
 */
@WebServlet("/musicPreviewController")
public class MusicPreviewControlServlet extends HttpServlet {

	/**
     * Handles HTTP GET requests to preview music-related actions based on provided parameters.
     * 
     * Expected query parameters:
     * 
     *     musicId - The ID of the music to retrieve.
     *     title - The title of the music (not used directly but may be useful for display).
     *     isPublished - If "true", treats the music as published and loads as PublishedMusic.
     *     actionValue - Determines the action: "upload", "publish", "delete", or "publishedMusic".
     * 
     *
     * Depending on the `actionValue`, this method forwards the request to:
     * 
     *     `musicUploadPreview.jsp` - if actionValue is "upload"
     *     `musicPublishForm.jsp` - if actionValue is "publish"
     *     `musicDeleteConfirm.jsp` - if actionValue is "delete"
     *     `publishedMusicPreview.jsp` - if actionValue is "publishedMusic"
     * 
     * 
     * If the music cannot be found or parameters are invalid, logs an error.
     *
     * @param request  The HTTP request containing music details.
     * @param response The HTTP response used to forward to the appropriate view.
     * @throws ServletException If a servlet-related error occurs.
     * @throws IOException      If an I/O error occurs while handling the request.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Read the details from the query string
        String idParam = request.getParameter("musicId");
        String titleParam = request.getParameter("title");
        
        String isPublished = request.getParameter("isPublished");
        String actionValue = request.getParameter("actionValue");

        if (idParam != null) {
            try {
                int id = Integer.parseInt(idParam); // Convert to int

                // Load music from DB
                MusicDAOInterface DAO = MusicDAO.getMusicDAO();
                Music music = DAO.getMusicById(id);
             
                if (music != null) {
                	
                	request.setAttribute("music", music);
                	
                	if ("upload".equals(actionValue)) {                   	
                        request.getRequestDispatcher("musicUploadPreview.jsp").forward(request, response);
                        return;
                    } 
                	else if ("publish".equals(actionValue)) {
                        request.getRequestDispatcher("musicPublishForm.jsp").forward(request, response);
                        return;
                	}   	
                	else if ("delete".equals(actionValue)) {
                		request.getRequestDispatcher("musicDeleteConfirm.jsp").forward(request, response);
                        return;
                	}
                } else {
            		System.out.println("Error in retrieving music for preview. Null returned");
            	}
                
                
                //When previewing an already published music
                if (isPublished != null && !isPublished.trim().isEmpty() && "true".equals(isPublished)) {
                	
                	PublishedMusic pMusic = DAO.getPublishedMusicByID(id);
                	request.setAttribute("music", pMusic);
                	if (pMusic != null) {
                		if ("publishedMusic".equals(actionValue)) {                   	
                            request.getRequestDispatcher("publishedMusicPreview.jsp").forward(request, response);
                            return;
                        } 
                	} else {
                		System.out.println("Error in retrieving published music for preview. Null returned");
                	}
                	
                }

            } catch (NumberFormatException e) {
                e.printStackTrace(); // Log bad ID format
            }
        }

        // If anything fails, redirect back to dashboard
//        response.sendRedirect("artistDashboard.jsp?error=invalid_music");
    }
}
