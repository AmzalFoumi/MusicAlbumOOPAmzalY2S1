package com.amzal.session.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(urlPatterns = {
    "/musicUploadForm.jsp",
    "/musicPublishForm.jsp",
    "/musicDeleteConfirm.jsp",
    "/musicUploadPreview.jsp",
    "/test.jsp",
    "/musicPreviewController",
    "/musicPublishController",
    "/musicDeleteController",
    "/musicUploadController"
})
public class SessionCheckFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

    	// Try to get the current session, don't create a new one if it doesn't exist
    	HttpSession session = request.getSession(false);
    	String username = null;
    	String usertype = null;

    	//Retrieve the user name and password if session exists
    	if (session != null) {
    	    username = (String) session.getAttribute("username");
    	    usertype = (String) session.getAttribute("usertype");
    	}

    	if (username == null || usertype == null || username.trim().isEmpty() || usertype.trim().isEmpty()) {
            request.setAttribute("errorMsg", "User is logged out. Please log in again.");
            request.getRequestDispatcher("landing.jsp").forward(request, response);
        } else if (!"artist".equals(usertype)) {
            request.setAttribute("errorMsg", "Access denied. Only artists can proceed.");
            request.getRequestDispatcher("landing.jsp").forward(request, response);
        } else {
            chain.doFilter(request, response);
        }
    }
}
