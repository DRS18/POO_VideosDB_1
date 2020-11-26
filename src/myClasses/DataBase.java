package myClasses;

import common.Constants;
import fileio.Input;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DataBase {
    private ArrayList<Actor>  actors  = new ArrayList<>();
    private ArrayList<Movie>  movies  = new ArrayList<>();
    private ArrayList<Serial> serials = new ArrayList<>();
    private ArrayList<User>   users   = new ArrayList<>();
    public ArrayList<Action> actions = new ArrayList<>();

    private static DataBase instance = null;

    public DataBase(){}

    public static DataBase getInstance() {
        if (instance == null) {
            instance = new DataBase();
        }
        return instance;
    }

    public void putActors(Input input) {
        input.getActors()
                .forEach(actor -> actors.add(
                        new Actor(actor.getName(), actor.getCareerDescription(),
                                actor.getFilmography(), actor.getAwards())
                ));
    }

    public void putMovies(Input input) {
        input.getMovies()
                .forEach(movie -> movies.add(
                        new Movie(movie.getTitle(), movie.getYear(), movie.getCast(),
                                movie.getGenres(), movie.getDuration())
                ));
    }

    public void putSerials(Input input) {
        input.getSerials()
                .forEach(serial -> serials.add(
                        new Serial(serial.getTitle(), serial.getYear(),
                                serial.getCast(),serial.getGenres(),
                                serial.getNumberSeason(), serial.getSeasons())
                ));
    }

    public void putUsers(Input input) {
        input.getUsers()
                .forEach(user -> users.add(
                        new User(user.getUsername(), user.getSubscriptionType(),
                                user.getHistory(), user.getFavoriteMovies())
                ));
    }

//    public void putActions(Input input) {
//        input.getCommands()
//                .forEach(action -> actions.add(
//                        new Action(action.getActionId(), action.getActionType(),
//                                action.getType(), action.getUsername(),
//                                action.getObjectType(), action.getSortType(),
//                                action.getCriteria(), action.getTitle(),
//                                action.getGenre(), action.getNumber(),
//                                action.getGrade(),action.getSeasonNumber(),
//                                action.getFilters())
//                ));
//    }

    public org.json.simple.JSONObject writeObject(final int id, final String field,
                                                final String message) {
        org.json.simple.JSONObject object = new org.json.simple.JSONObject();
        object.put(Constants.ID_STRING, id);
        object.put(Constants.MESSAGE, message);

        return object;
    }

//    public void iterateThroughActions (JSONArray arrayResult) {
//        for (int i = 0; i < actions.size(); i++) {
//            if (actions.get(i).getActionType().equals("command") &&
//                    actions.get(i).getType().equals("favorite")){
////                System.out.println(actions.get(i).toString());
//                commandFavorite(actions.get(i).getActionId(), actions.get(i).getUsername(),
//                        actions.get(i).getTitle(),
//                        arrayResult);
//
//            } else if (actions.get(i).getActionType().equals("command") &&
//                    actions.get(i).getType().equals("view")) {
//                System.out.println(actions.get(i).toString());
//                commandView(actions.get(i).getActionId(), actions.get(i).getUsername(),
//                        actions.get(i).getTitle(),
//                        arrayResult);
//            } else if (actions.get(i).getActionType().equals("command") &&
//                    actions.get(i).getType().equals("rating")) {
////                System.out.println(actions.get(i).toString());
//                commandRating(actions.get(i).getUsername(), actions.get(i).getTitle(),
//                        actions.get(i).getGrade(), actions.get(i).getSeasonNumber(),
//                        arrayResult);
//            }
//        }
//    }



    public void commandFavorite(int id, String username, String title, JSONArray arrayResult) {
        JSONObject object = null;
        int ok = -1;


        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(username)) {

                    if (users.get(i).getHistory().containsKey(title)) {
                        ok = i;
                    } else {
                        ok = -3;
                    }

                if (ok == -3) {
                    String message = "error -> " + title + " is not seen";

                    object = writeObject(id, null, message);
                    break;
                }

                for (int j = 0; j < users.get(i).getFavoriteMovies().size(); j++) {
                    if (users.get(i).getFavoriteMovies().get(j).equals(title)) {
                        ok = -2;
                        String message = "error -> "+title+" is already in favourite list";
                        object = writeObject(id, null, message);
                    }
                }
                if (ok == -2) {
                    break;
                }
            }
        }

        if (ok != -1 && ok != -2 && ok != -3) {
            users.get(ok).getFavoriteMovies().add(
                    users.get(ok).getFavoriteMovies().size() - 1, title);
            String message = "success -> "+title+" was added as favourite";
            object = writeObject(id, null, message);
//            System.out.println("AFTER");
//            System.out.println(users.get(ok).toString());
        }
        arrayResult.add(object);
    }

    public void commandView (int id, String username, String title, JSONArray arrayResult) {
        JSONObject object = null;
        int ok = -1;

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(username)) {
                ok = i;

//                System.out.println("BEFORE");
//                System.out.println(users.get(i).toString());

                for (int j = 0; j < users.get(i).getHistory().size(); j++) {

                    if (users.get(i).getHistory().containsKey(title)) {
                        users.get(i).getHistory().put(title, users.get(i).getHistory().get(title) + 1);
                        String message = "success -> " + title + " was viewed with" +
                                " total views of " + users.get(i).getHistory().get(title);
//                        System.out.println(message);
                        object = writeObject(id, null, message);
                        ok = -2;
                        break;
                    }
                }

                if (ok == -2) {
                    break;
                }
            }
        }
        if (ok != -1 && ok != -2) {
            users.get(ok).getHistory().put(title, 1);
            String message = "success -> " + title + " was viewed with" +
                    " total views of " + users.get(ok).getHistory().get(title);

            object = writeObject(id, null, message);
//            System.out.println("AFTER");
//            System.out.println(users.get(ok).toString());
        }
        arrayResult.add(object);
    }

    public void commandRating(int id, String username, String title, double grade, int season,
                              JSONArray arrayResult) {
        JSONObject object = null;
        int ok = -2;

        // Search if user have seen that movie/show recently
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(username)) {
                if (users.get(i).getHistory().containsKey(title)) {
                    ok = -1;
                }
                break;
            }
        }

        if (ok == -2) return;

        // if season == 0 => title is movie
        if (season == 0) {
            for (int i = 0; i < movies.size(); i++) {
                if (movies.get(i).getTitle().equals(title)) {
                    // Check if user gave already rating
                    if (movies.get(i).getRatings().size() == 0) {
                        movies.get(i).getRatings().add(grade);
                        movies.get(i).getUsersRecord().add(0, username);
                        String message = "success -> " + title + " was rated with " +
                                grade + " by " + username;
                        object = writeObject(id, null, message);
                    } else {
                        for (int j = 0; j < movies.get(i).getUsersRecord().size(); j++) {
                            if (movies.get(i).getUsersRecord().equals(username)) {
                                ok = -2;
                            }
                        }
                        if (ok == -1) {
                            movies.get(i).getRatings().add(grade);
                            movies.get(i).getUsersRecord().add(0, username);
                            String message = "success -> " + title + " was rated with " +
                                    grade + " by " + username;
                            object = writeObject(id, null, message);
                        }
                    }
                }
            }
        } else {
            // it's show(time)
            ok = -1;
            for (int i = 0; i < serials.size(); i++) {
                if (serials.get(i).getTitle().equals(title)) {
                    if (serials.get(i).getSeasons().get(season - 1).getRatings().size() == 0) {
                        serials.get(i).getSeasons().get(season - 1).getRatings().add(grade);
                        serials.get(i).getSeasons().get(season - 1).getUsersRecord().add(username);
                        String message = "success -> " + title + " was rated with " +
                                grade + " by " + username;
//                        System.out.println(message);
                        object = writeObject(id, null, message);
                    } else {
                        for (int j = 0; j < serials.get(i).getSeasons().get(season - 1).getUsersRecord().size(); j++) {
                            if (serials.get(i).getSeasons().get(season - 1).getUsersRecord().equals(username)) {
                                ok = -2;
                            }
                        }
                        if (ok == -1) {
                            serials.get(i).getSeasons().get(season - 1).getRatings().add(grade);
                            serials.get(i).getSeasons().get(season - 1).getUsersRecord().add(username);
                            String message = "success -> " + title + " was rated with " +
                                    grade + " by " + username;
//                            System.out.println(message);
                            object = writeObject(id, null, message);
                        }
                    }
                }
            }
        }
        arrayResult.add(object);
    }

    public void queryFavouriteMovies (int id, int number, List<String> years,
                                      List<String> genres, List<String> words,
                                      List<String> awards, String sortType) {

    }
}
