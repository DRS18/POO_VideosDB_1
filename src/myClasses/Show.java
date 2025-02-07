package myClasses;

import fileio.ShowInput;

import java.util.ArrayList;

public abstract class Show {
    private String title;
    private int year;
    private ArrayList<String> cast;
    private ArrayList<String> genres;
    private int NumberOfViews = 0;
    private double ShowGeneralRating;
    private int numberOfFavourites;

    public int getNumberOfFavourites() {
        return numberOfFavourites;
    }

    public void setNumberOfFavourites(int numberOfFavourites) {
        this.numberOfFavourites = numberOfFavourites;
    }



    public double getShowGeneralRating() {
        return ShowGeneralRating;
    }

    public void setShowGeneralRating(double showGeneralRating) {
        ShowGeneralRating = showGeneralRating;
    }



    public void setNumberOfViews(int numberOfViews) {
        NumberOfViews = numberOfViews;
    }

    public int getNumberOfViews() {
        return NumberOfViews;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public ArrayList<String> getCast() {
        return cast;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public Show(String title, int year, ArrayList<String> cast,
                ArrayList<String> genres) {
        this.title = title;
        this.year = year;
        this.cast= cast;
        this.genres = genres;
    }
}
