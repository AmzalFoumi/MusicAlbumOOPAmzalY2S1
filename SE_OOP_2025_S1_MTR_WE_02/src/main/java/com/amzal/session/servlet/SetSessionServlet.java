package com.amzal.session.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/setSession")
public class SetSessionServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String usertype = request.getParameter("usertype");
        
        Integer userId = -1;
		try {
			userId = Integer.parseInt(request.getParameter("userId"));
		} catch (NumberFormatException e) {
			System.out.println("User ID input SetSessionServlet is not an integer value");
			e.printStackTrace();
		}
        
        HttpSession session = request.getSession(true);
	    session.setAttribute("username", username);
	    session.setAttribute("usertype", usertype);
	    session.setAttribute("userId", userId);
	    response.sendRedirect("musicUploadForm.jsp");

//        if (username != null && usertype != null && usertype.equals("artist")) {
//            HttpSession session = request.getSession(true);
//            session.setAttribute("username", username);
//            session.setAttribute("usertype", usertype);
//            response.sendRedirect("musicUploadForm.jsp");
//        } else {
//            response.sendRedirect("landing.jsp");
//        }
    }
}

