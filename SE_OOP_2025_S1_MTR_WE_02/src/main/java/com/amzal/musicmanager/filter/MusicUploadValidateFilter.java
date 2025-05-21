package com.amzal.musicmanager.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amzal.exceptions.InvalidGenreException;
import com.amzal.exceptions.InvalidLanguageException;
import com.amzal.exceptions.InvalidTitleException;
import com.amzal.musicmanager.validator.MusicValidator;

/*
 * This filter is to validate data input in the music upload form
 */

@WebFilter("/musicUploadController")
public class MusicUploadValidateFilter extends HttpFilter {

    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // Get form inputs
        String title = request.getParameter("title");
        String genre = request.getParameter("genre");
        String language = request.getParameter("language");

        // Validation flags
        boolean valid = true;
        StringBuilder errorMsg = new StringBuilder();

        // Title validation
        try {			
			MusicValidator.validateTitle(title);	
		} catch (InvalidTitleException e) {
			valid = false;
			errorMsg.append(e.getMessage() + "<br />");
		}

        // Genre validation
        try {
			MusicValidator.validateGenre(genre);
		} catch (InvalidGenreException e) {
			valid = false;
			errorMsg.append(e.getMessage() + "<br />");
		}

        // Language validation
        try {
			MusicValidator.validateLanguage(language);
		} catch (InvalidLanguageException e) {
			valid = false;
			errorMsg.append(e.getMessage() + "<br />");
		}

        if (!valid) {
        	// Forward user back to the JSP with error message
            request.setAttribute("errorMsg", errorMsg.toString());           
            request.getRequestDispatcher("/musicUploadForm.jsp").forward(request, response);
        } else {
            // Continue if all inputs are valid
            chain.doFilter(request, response);
        }
    }

//    private boolean isValidLanguage(String lang) {
//        String lowerLang = lang.toLowerCase();
//        return lowerLang.equals("english") || lowerLang.equals("sinhala") ||
//               lowerLang.equals("tamil") || lowerLang.equals("chinese");
//    }
}

