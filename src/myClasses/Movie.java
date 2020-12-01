package myClasses;

import fileio.MovieInputData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Movie extends Show{
    private int duration;
    private double rating = 0;

    private ArrayList<Double> ratings = new ArrayList<>();
    private ArrayList<String> usersRecord= new ArrayList<>();



    public List<Double> getRatings() {
        return ratings;
    }

    public ArrayList<String> getUsersRecord() {
        return usersRecord;
    }

    public double getRating() {
        double sum = 0;

        for (int i = 0; i < ratings.size(); i++) {
            sum += ratings.get(i);
        }
        if (sum > 0) {
            rating = sum / ratings.size();
        } else {
            rating = 0;
        }

        super.setShowGeneralRating(rating);
        return rating;
    }

    public int getDuration() {
        return duration;
    }

    public Movie(String title, int year, ArrayList<String> cast,
                 ArrayList<String> genres, int duration) {
        super(title, year, cast, genres);
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "duration=" + duration +
                '}';
    }
}
