package myClasses;

import fileio.MovieInputData;

import java.util.ArrayList;

public class Movie extends Show{
    private int duration;

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
