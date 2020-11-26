package myClasses;

import common.Constants;
import fileio.Input;
import main.Main;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class dataBase {
    public ArrayList<Actor>  actors  = new ArrayList<>();
    public ArrayList<Movie>  movies  = new ArrayList<>();
    public ArrayList<Serial> serials = new ArrayList<>();
    public ArrayList<User>   users   = new ArrayList<>();
    public ArrayList<Action> actions = new ArrayList<>();

    public dataBase(){}

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

    public void putActions(Input input) {
        input.getCommands()
                .forEach(action -> actions.add(
                        new Action(action.getActionId(), action.getActionType(),
                                action.getType(), action.getUsername(),
                                action.getObjectType(), action.getSortType(),
                                action.getCriteria(), action.getTitle(),
                                action.getGenre(), action.getNumber(),
                                action.getGrade(),action.getSeasonNumber(),
                                action.getFilters())
                ));
    }

    public org.json.simple.JSONObject writeObject(final int id, final String field,
                                                final String message) {
        org.json.simple.JSONObject object = new org.json.simple.JSONObject();
        object.put(Constants.ID_STRING, id);
        object.put(Constants.MESSAGE, message);

        return object;
    }

    public void iterateThrowActions (JSONArray arrayResult) {
        for (int i = 0; i < actions.size(); i++) {
            if (actions.get(i).getActionType().equals("command") &&
                    actions.get(i).getType().equals("favorite")){
                System.out.println(actions.get(i).toString());
                commandFavorite(actions.get(i).getActionId(), actions.get(i).getUsername(),
                        actions.get(i).getTitle(),
                        arrayResult);

            } else if (actions.get(i).getActionType().equals("command") &&
                    actions.get(i).getType().equals("view")) {
//                System.out.println(actions.get(i).toString());
                commandView(actions.get(i).getUsername(), actions.get(i).getTitle(),
                        arrayResult);
            } else if (actions.get(i).getActionType().equals("command") &&
                    actions.get(i).getType().equals("rating")) {
//                System.out.println(actions.get(i).toString());
                commandRating(actions.get(i).getUsername(), actions.get(i).getTitle(),
                        actions.get(i).getGrade(), actions.get(i).getSeasonNumber(),
                        arrayResult);
            }
        }
    }



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
                    System.out.println(message);
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

    public void commandView (String username, String title, JSONArray arrayResult) {
//        int ok = -1;
//
//        for (int i = 0; i < users.size(); i++) {
//            if (users.get(i).getUsername().equals(username)) {
//                ok = i;
//
//                System.out.println("BEFORE");
//                System.out.println(users.get(i).toString());
//
//                for (int j = 0; j < users.get(i).getHistory().size(); j++) {
//
//                    if (users.get(i).getHistory().containsKey(title)) {
//                        ok = -2;
//                    }
//                }
//
//                if (ok == -2) {
//                    break;
//                }
//            }
//        }
//        if (ok != -1 && ok != -2) {
//            users.get(ok).getHistory().put(title, users.get(ok).getHistory().size() - 1);
//            System.out.println("AFTER");
//            System.out.println(users.get(ok).toString());
//        }
    }

    public void commandRating(String username, String title, double grade, int season,
                              JSONArray arrayResult) {
//        int ok = -1;
//
//        for (int i = 0; i < users.size(); i++) {
//            if (users.get(i).getUsername().equals(username)) {
//                ok = i;
//
//                System.out.println("BEFORE");
//                System.out.println(users.get(i).toString());
//
//                for (int j = 0; j < users.get(i).getRatings().size(); j++) {
//                    if (users.get(i).getRatings().get(j).equals(title)) {
//                        ok = -2;
//                    }
//                }
//
//                if (ok == -2) {
//                    break;
//                }
//            }
//        }
//
//        if (ok != -1 && ok != -2) {
//            int check = -1;
//            users.get(ok).getRatings().add(users.get(ok).getRatings().size() - 1, title);
//            for (int i = 0; i < movies.size(); i++) {
//                if (movies.get(i).getTitle().equals(title)) {
//                    movies.get(i).getRatings().add(movies.get(i).getRatings().size() - 1,
//                            (double) grade);
//                    movies.get(i).getUsersRecord().add(movies.get(i).getUsersRecord().size() - 1,
//                            username);
//                    check = 0;
//                    break;
//                }
//            }
//
//            if (check == -1) {
//                for (int i = 0; i < serials.size(); i++) {
//                    if (serials.get(i).getTitle().equals(title)) {
//                        check = i;
//                        for (int j = 0; j < serials.get(i).getSeasons().get(season).getUsersRecord().size();
//                            j++) {
//                            if (serials.get(i).getSeasons().get(season).getUsersRecord().get(j).equals(username)) {
//                                System.out.println("User " + username +" already have " + title+ " in history");
//                                check = 0;
//                                break;
//                            }
//                        }
//                        serials.get(i).getSeasons().get(season);
//                    }
//                    if (check == 0) {
//                        break;
//                    }
//                }
//                if (check != -1 && check != -2) {
//                    serials.get(check).getSeasons().get(season).getRatings().add(grade);
//                    serials.get(check).getSeasons().get(season).getUsersRecord().add(username);
//                }
//            }
//
//        }
    }
}
