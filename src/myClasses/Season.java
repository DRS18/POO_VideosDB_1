package myClasses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Season {
    private int currentSeason;
    private int duration;
    private List<Double> ratings;
    private ArrayList<String> usersRecord = new ArrayList<>();

    public Season(final int currentSeason, final int duration) {
        this.currentSeason = currentSeason;
        this.duration = duration;
        this.ratings = new ArrayList<>();
        this.usersRecord = new ArrayList<>();
    }

    public Season getSeason() {
        return this;
    }

    public ArrayList<String> getUsersRecord() {
        return usersRecord;
    }

    public int getCurrentSeason() {
        return currentSeason;
    }

    public int getDuration() {
        return duration;
    }

    public List<Double> getRatings() {
        return ratings;
    }

    @Override
    public String toString() {
        return "Episode{"
                + "currentSeason="
                + currentSeason
                + ", duration="
                + duration
                + '}';
    }
}
