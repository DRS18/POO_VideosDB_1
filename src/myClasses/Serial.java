package myClasses;

import entertainment.Season;
import fileio.SerialInputData;

import java.util.ArrayList;

public class Serial extends Show{
    private int numberOfSeasons;
    private ArrayList<Season> seasons;
    private double rating;

    public double getRating() {
        double totalSum = 0;
        double sumPerSeason = 0;

        for (int i = 0; i < seasons.size(); i++) {
            sumPerSeason = 0;

            for (int j = 0; j < seasons.get(i).getRatings().size(); j++) {
                sumPerSeason += seasons.get(i).getRatings().get(j);
            }

            totalSum += sumPerSeason;
        }

        if (totalSum > 0) {
            rating = totalSum / seasons.size();
        } else {
            rating = 0;
        }

        return rating;
    }



//    public Serial(String title, int year, ArrayList<String> cast,
//                  ArrayList<String> genres, int numberOfSeasons,
//                  ArrayList<Season> seasons) {
//        super(title, year, cast, genres);
//        this.numberOfSeasons = numberOfSeasons;
//        this.seasons = seasons;
//    }

    public Serial(String title, int year, ArrayList<String> cast,
                  ArrayList<String> genres, int numberSeason,
                  ArrayList<entertainment.Season> seasons) {
        super(title, year, cast, genres);
        this.numberOfSeasons = numberSeason;
        this.seasons = seasons;
    }

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    

    @Override
    public String toString() {
        return "Serial{" +
                "numberOfSeasons=" + numberOfSeasons +
                ", seasons=" + seasons +
                '}';
    }
}
