package myClasses;

import java.util.List;

public class Commands extends Action{
    public Commands(int actionId, String actionType, String type, String username,
                    String objectType, String sortType, String criteria,
                    String title, String genre, int number, double grade,
                    int seasonNumber, List<List<String>> filters) {
        super(actionId, actionType, type, username, objectType, sortType, criteria,
                title, genre, number, grade, seasonNumber, filters);
    }


}
