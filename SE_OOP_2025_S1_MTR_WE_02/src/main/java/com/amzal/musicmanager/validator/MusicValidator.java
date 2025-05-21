package com.amzal.musicmanager.validator;

import com.amzal.exceptions.InvalidGenreException;
import com.amzal.exceptions.InvalidLanguageException;
import com.amzal.exceptions.InvalidPriceException;
import com.amzal.exceptions.InvalidRegionException;
import com.amzal.exceptions.InvalidTitleException;

public class MusicValidator {

    public static void validateTitle(String title) throws InvalidTitleException {
        if (title == null || title.trim().isEmpty() || title.length() > 255) {
            throw new InvalidTitleException("Title must be a non-empty string under 255 characters.");
        }
    }

    public static void validateGenre(String genre) throws InvalidGenreException {
        if (genre == null || genre.trim().isEmpty() || genre.length() > 255) {
            throw new InvalidGenreException("Genre must be a non-empty string under 255 characters.");
        }
    }

    public static void validateLanguage(String language) throws InvalidLanguageException {
        if (language == null || language.trim().isEmpty()) {
            throw new InvalidLanguageException("Language is required.");
        }

        String lang = language.toLowerCase();
        if (!lang.equals("english") && !lang.equals("sinhala") && !lang.equals("tamil") && !lang.equals("chinese")) {
            throw new InvalidLanguageException("Allowed languages: English, Sinhala, Tamil, Chinese.");
        }
    }

    public static void validateRegion(String region) throws InvalidRegionException {
        if (region == null || region.trim().isEmpty()) {
            throw new InvalidRegionException("Region is required.");
        }

        String reg = region.toLowerCase();
        if (!reg.equals("europe") && !reg.equals("asia") &&
            !reg.equals("america") && !reg.equals("africa") && !reg.equals("australia")) {
            throw new InvalidRegionException("Allowed regions: Europe, Asia, America, Africa, Australia.");
        }
    }

    public static void validatePrice(String priceStr) throws InvalidPriceException {
        if (priceStr == null || priceStr.trim().isEmpty()) {
            throw new InvalidPriceException("Price cannot be empty.");
        }

        try {
            double price = Double.parseDouble(priceStr);
            if (price <= 0) {
                throw new InvalidPriceException("Price must be a positive number.");
            }
        } catch (NumberFormatException e) {
            throw new InvalidPriceException("Price must be a valid number.");
        }
    }
}
