package myClasses;

import fileio.UserInputData;

import java.util.ArrayList;
import java.util.Map;

public class User {
    private String username;
    private String subscriptionType;
    private Map<String, Integer> history;
    private ArrayList<String> favoriteMovies;
    private int numberOfRatings = 0;

    public int getNumberOfRatings() {
        return numberOfRatings;
    }

    public void incrementNumberOfRatings() {
        numberOfRatings++;
    }

    public void setNumberOfRatings(int numberOfRatings) {
        this.numberOfRatings = numberOfRatings;
    }

//    private ArrayList<String> ratings = new ArrayList<>();

//    public ArrayList<String> getRatings() {
//        return ratings;
//    }

    public String getUsername() {
        return username;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public Map<String, Integer> getHistory() {
        return history;
    }

    public ArrayList<String> getFavoriteMovies() {
        return favoriteMovies;
    }

    public User(String username, String subscriptionType,
                Map<String, Integer> history,
                ArrayList<String> favoriteMovies) {
        this.username = username;
        this.subscriptionType = subscriptionType;
        this.history = history;
        this.favoriteMovies = favoriteMovies;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", subscriptionType='" + subscriptionType + '\'' +
                ", history=" + history +
                ", favoriteMovies=" + favoriteMovies +
//                ", ratings=" + ratings +
                '}';
    }
}
