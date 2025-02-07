package myClasses;

import actor.ActorsAwards;
import fileio.ActorInputData;
import myClasses.DataBase;

import java.util.ArrayList;
import java.util.Map;

public class Actor {
    private String name;
    private String careerDescription;
    private ArrayList<String> filmography;
    private Map<ActorsAwards, Integer> awards;
    private double filmographyRatingAverage;
    private int numberOfAwards;

    public int getNumberOfAwards() {
        numberOfAwards = 0;

        for (ActorsAwards key: awards.keySet()) {
            numberOfAwards += awards.get(key);
        }

        return numberOfAwards;
    }



    public double getFilmographyRatingAverage() {
        return filmographyRatingAverage;
    }



    public void setFilmographyRatingAverage(double filmographyRatingAverage) {
        this.filmographyRatingAverage = filmographyRatingAverage;
    }



    public Actor(String name, String careerDescription,
                 ArrayList<String> filmography,
                 Map<ActorsAwards, Integer> awards) {
        this.name = name;
        this.careerDescription = careerDescription;
        this.filmography = filmography;
        this.awards = awards;
    }

    public String getName() {
        return name;
    }

    public String getCareerDescription() {
        return careerDescription;
    }

    public ArrayList<String> getFilmography() {
        return filmography;
    }

    public Map<ActorsAwards, Integer> getAwards() {
        return awards;
    }

    @Override
    public String toString() {
        return "Actor{" +
                "name='" + name + '\'' +
                ", careerDescription='" + careerDescription + '\'' +
                ", filmography=" + filmography +
                ", awards=" + awards +
                '}';
    }
}
