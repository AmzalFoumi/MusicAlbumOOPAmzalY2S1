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
import com.amzal.exceptions.InvalidPriceException;
import com.amzal.exceptions.InvalidRegionException;
import com.amzal.exceptions.InvalidTitleException;
import com.amzal.musicmanager.model.Music;
import com.amzal.musicmanager.validator.MusicValidator;

/*
 * This filter runs everytime MusicPublishControlServlet is called
 */
@WebFilter("/musicPublishController")
public class MusicPublishValidateFilter extends HttpFilter{

	public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		 
		
		// Validation flags
        boolean valid = true;
        StringBuilder errorMsg = new StringBuilder();
		
        // Get form inputs
		Integer musicId = 0;
		try {
			musicId = Integer.parseInt(request.getParameter("musicId"));
		} catch (NumberFormatException e) {
			valid = false;
            errorMsg.append("Invalid music ID format.<br />");
		}
        String title = request.getParameter("title");
        String genre = request.getParameter("genre");
        String language = request.getParameter("language");
        String priceStr = request.getParameter("price");
        String region = request.getParameter("region");
        String albumName = request.getParameter("albumName");
        String filePath = request.getParameter("filePath");
        
        

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
        
        // Price validation
        try {
			MusicValidator.validatePrice(priceStr);
		} catch (InvalidPriceException e) {
			valid = false;
			errorMsg.append(e.getMessage() + "<br />");
		}
        
        // Region validation
        try {
			MusicValidator.validateRegion(region);
		} catch (InvalidRegionException e) {
			valid = false;
			errorMsg.append(e.getMessage() + "<br />");
		}
        
        
        if (!valid) {
        	
        	Music music = new Music(musicId, title, genre, language, filePath);
        	request.setAttribute("music", music); //Have to create this music object and pass it back to the publish form, otherwise error in loading the preview in the form
        	
            request.setAttribute("errorMsg", errorMsg.toString());
            request.getRequestDispatcher("/musicPublishForm.jsp").forward(request, response);
        } else {
            chain.doFilter(request, response);
        }
    }

//    private boolean isValidLanguage(String lang) {
//        String lowerLang = lang.toLowerCase();
//        return lowerLang.equals("english") || lowerLang.equals("sinhala") ||
//               lowerLang.equals("tamil") || lowerLang.equals("chinese");
//    }
//
//    private boolean isValidRegion(String region) {
//        String lowerRegion = region.toLowerCase();
//        return lowerRegion.equals("europe") || lowerRegion.equals("asia") ||
//               lowerRegion.equals("america") || lowerRegion.equals("africa") ||
//               lowerRegion.equals("australia");
//    }
//
//    private boolean isValidPositiveDouble(String value) {
//        try {
//            double d = Double.parseDouble(value);
//            return d > 0;  // Will return true if d is a double value AND positive
//        } catch (NumberFormatException e) {
//            return false;
//        }
//    }
	
	
}
