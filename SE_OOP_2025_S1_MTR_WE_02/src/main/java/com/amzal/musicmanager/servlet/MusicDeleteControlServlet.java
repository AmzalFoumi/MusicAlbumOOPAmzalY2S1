package com.amzal.musicmanager.servlet;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amzal.musicmanager.dao.MusicDAO;
import com.amzal.musicmanager.dao.MusicDAOInterface;

@WebServlet("/musicDeleteController")
public class MusicDeleteControlServlet extends HttpServlet {

	protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException ,IOException {
		
		try {
			int musicId = Integer.parseInt(request.getParameter("musicId"));
			
			MusicDAOInterface DAO = MusicDAO.getMusicDAO();
			Boolean isDeleted = DAO.delete(musicId);
			
			if (isDeleted) {
				String message = "music+deleted";
				response.sendRedirect("test.jsp?message=" + message);
			}
			
			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
