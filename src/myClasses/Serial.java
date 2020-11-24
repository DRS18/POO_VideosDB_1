package myClasses;

import entertainment.Season;
import fileio.SerialInputData;

import java.util.ArrayList;

public class Serial extends Show{
    private int numberOfSeasons;
    private ArrayList<Season> seasons;

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    public Serial(String title, int year, ArrayList<String> cast,
                  ArrayList<String> genres, int numberOfSeasons,
                  ArrayList<Season> seasons) {
        super(title, year, cast, genres);
        this.numberOfSeasons = numberOfSeasons;
        this.seasons = seasons;
    }

    @Override
    public String toString() {
        return "Serial{" +
                "numberOfSeasons=" + numberOfSeasons +
                ", seasons=" + seasons +
                '}';
    }
}
