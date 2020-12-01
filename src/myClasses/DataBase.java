package myClasses;

import actor.ActorsAwards;
import com.sun.source.tree.Tree;
import common.Constants;
import entertainment.Genre;
import fileio.Input;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;

public class DataBase {
    private ArrayList<Actor>  actors  = new ArrayList<>();
    private ArrayList<Movie>  movies  = new ArrayList<>();
    private ArrayList<Serial> serials = new ArrayList<>();
    private ArrayList<User>   users   = new ArrayList<>();
    public ArrayList<Action> actions = new ArrayList<>();

    private Map<Integer, List<String>> gogu;

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

        if (ok == -2) {
            String message = "error -> " + title + " is not seen";
            object = writeObject(id, null, message);
            arrayResult.add(object);
            return;
        }

        // if season == 0 => title is movie
        if (season == 0) {
            for (int i = 0; i < movies.size(); i++) {
                if (movies.get(i).getTitle().equals(title)) {
                    // Check if user gave already rating
                    if (movies.get(i).getRatings().size() == 0) {
                        movies.get(i).getRatings().add(grade);
                        movies.get(i).getUsersRecord().add(username);
                        String message = "success -> " + title + " was rated with " +
                                grade + " by " + username;
                        object = writeObject(id, null, message);
                    } else {
                        for (int j = 0; j < movies.get(i).getUsersRecord().size(); j++) {
                            if (movies.get(i).getUsersRecord().get(j).equals(username)) {
                                String message = "error -> " + title + " has been already rated";
                                object = writeObject(id, null, message);
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
                            if (serials.get(i).getSeasons().get(season - 1).getUsersRecord().get(j).equals(username)) {
                                String message = "error -> " + title + " has been already rated";
                                object = writeObject(id, null, message);
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
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(username)) {
                users.get(i).incrementNumberOfRatings();
                break;
            }
        }


        arrayResult.add(object);
    }

    public Integer howManyFavourites (Movie movie) {
        Integer n = 0;

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getHistory().containsKey(movie.getTitle()) &&
                users.get(i).getFavoriteMovies().contains(movie.getTitle())) {
                n++;
            }
        }


        return n;
    }

    public ArrayList<Movie> FoundMoviesByFilters (List<String> years, List<String> genres,
                                                List<String> words, List<String> awards,
                                                ArrayList<Movie> shows) {
        ArrayList<Movie> result = new ArrayList<>();

        int ok = 0;
        int yearsCheck = 0;
        for (int i = 0; i < shows.size(); i++) {
            // Filter movies by year
            ok = -1;
            yearsCheck = -1;
            if (years != null && years.size() > 0 && years.get(0) != null) {
                for (int j = 0; j < years.size(); j++) {
//                    System.out.println("Cautam " + years.get(j) + " in " + shows.get(i).getTitle());
                    if (years.get(j) != null && shows.get(i).getYear() == parseInt(years.get(j))) {
//                        System.out.println("L-am gasit");
                        yearsCheck = 0;
                        break;
                    }
                }
            } else {
                yearsCheck = 0;
            }

            if (yearsCheck == -1) continue;

            // Filter movies by genres
            if (genres.size() > 0 && genres.get(0) != null) {
//                System.out.println(genres.toString());
                for (int j = 0; j < genres.size(); j++) {
                    if (genres.get(j) != null && shows.get(i).getGenres().contains(genres.get(j))) {
                        ok = 0;
                    } else {
                        ok = -1;
                    }
                }
            } else {
                ok = 0;
            }
//            System.out.println("ok = " + ok + " years_check = " + yearsCheck);
            if (ok == -1 && yearsCheck == -1) continue;

            if (ok == 0 && yearsCheck == 0) {
//                System.out.println("adaugam " + shows.get(i));
                result.add(shows.get(i));
            }
        }

        return result;
    }

    public void queryFavouriteMovies (int id, int number, List<String> years, List<String> genres,
                                      List<String> words, List<String> awards, String sortType,
                                      JSONArray arrayResult) {

        ArrayList<Movie> FoundMovies = null;
//        Map<Integer, ArrayList<String>> indexing = new HashMap<>();
        JSONObject object = null;

        FoundMovies = FoundMoviesByFilters(years, genres, words, awards, movies);
//        System.out.println(FoundMovies.size());

//        System.out.println("--------------------------------------------------");
        for (int i = 0; i < FoundMovies.size(); i++) {
            FoundMovies.get(i).setNumberOfFavourites(howManyFavourites(FoundMovies.get(i)));
//            System.out.println(FoundMovies.get(i).getTitle() + " " + FoundMovies.get(i).getNumberOfFavourites());
        }
//        System.out.println("*******************");
        FoundMovies.sort(new MultipleComparators.CompareShowByTitle());
        FoundMovies.sort(new MultipleComparators.CompareShowByFavourites());
        if (sortType.equals("desc")) {
            Collections.reverse(FoundMovies);
        }
//        for (int i = 0; i < FoundMovies.size(); i++) {
//            System.out.println(FoundMovies.get(i).getTitle() + " " + FoundMovies.get(i).getNumberOfFavourites());
//        }
//        System.out.println("--------------------------------------------------");
//        for (int i = 0; i < FoundMovies.size(); i++) {
//            System.out.println(FoundMovies.get(i).getTitle());
//        }

        // Now we have movies by filters
//        for (int i = 0; i < FoundMovies.size(); i++) {
//            if (indexing.containsKey(howManyFavourites(FoundMovies.get(i))) &&
//                    howManyFavourites(FoundMovies.get(i)) > 0) {
//                ArrayList<String> temp = indexing.get(howManyFavourites(FoundMovies.get(i)));
//                temp.add(temp.size(), FoundMovies.get(i).getTitle());
//            } else if (howManyFavourites(FoundMovies.get(i)) > 0){
//                ArrayList<String> list = new ArrayList<>();
//                list.add(list.size(), FoundMovies.get(i).getTitle());
//                indexing.put(howManyFavourites(FoundMovies.get(i)), list);
//            }
//        }
//
//
//
//        if (indexing.size() > 0){
//            String message = "Query result: ";
//            Map<Integer, ArrayList<String>> sortedMap = new TreeMap<Integer, ArrayList<String>>(indexing);
//            int ok = 0;
//            for (Integer temp : sortedMap.keySet()) {
//                ok++;
//                if (ok == number) break;
//                message = message + sortedMap.get(temp);
//            }
////            System.out.println(message);
//            object = writeObject(id, null, message);
//        } else {
//            String message = "Query result: []";
//            object = writeObject(id, null, message);
//        }

        if (FoundMovies.size() > 0) {
            String message = "Query result: [";
            for (int i = 0; i < FoundMovies.size(); i++) {
                if (i == number) {
                    break;
                }
                message = message + FoundMovies.get(i).getTitle();
                if (i < FoundMovies.size() - 1 && i < number - 1) {
                    message = message + ", ";
                }
            }
            message = message + "]";
            object = writeObject(id, null, message);
//            System.out.println(message);
        } else {
            String message = "Query result: []";
            object = writeObject(id, null, message);
        }

//        Map<Integer, ArrayList<String>> sortedMap = new TreeMap<Integer, ArrayList<String>>(indexing);
//        System.out.println("AFTER SORTING");
//        System.out.println(sortedMap.toString());
        arrayResult.add(object);
    }

    public ArrayList<Serial> FoundSerialsByFilters (List<String> years, List<String> genres,
                                                  List<String> words, List<String> awards,
                                                  ArrayList<Serial> shows) {
        ArrayList<Serial> result = new ArrayList<>();

        int ok = 0;
        for (int i = 0; i < shows.size(); i++) {
            // Filter movies by year
            ok = -1;
            if (years != null && years.size() > 0 && years.get(0) != null) {
                for (int j = 0; j < years.size(); j++) {
                    if (years.get(j) != null && shows.get(i).getYear() == parseInt(years.get(j))) {
                        ok = 0;
                        break;
                    }
                }
            } else {
                ok = 0;
            }

            if (ok == -1) continue;

            ok = -1;

            // Filter movies by genres
            if (genres.size() > 0 && genres.get(0) != null) {
                for (int j = 0; j < genres.size(); j++) {
                    if (genres.get(j) != null && shows.get(i).getGenres().contains(genres.get(j))) {
                        ok = 0;
                    } else {
                        ok = -1;
                    }
                }
            } else {
                ok = 0;
            }

            if (ok == -1) continue;

            if (ok == 0) {
                result.add(shows.get(i));
            }
        }

        return result;
    }

    public Integer howManyFavouritesSerials (Serial serial) {
        Integer n = 0;

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getHistory().containsKey(serial.getTitle()) &&
                    users.get(i).getFavoriteMovies().contains(serial.getTitle())) {
                n++;
            }
        }

        return n;
    }

    public void queryFavouriteShows(int id, int number, List<String> years, List<String> genres,
                                    List<String> words, List<String> awards, String sortType,
                                    JSONArray arrayResult) {
        ArrayList<Serial> FoundSerials = FoundSerialsByFilters(years, genres, words, awards, serials);

        Map<Integer, ArrayList<String>> indexing = new HashMap<>();
        JSONObject object = null;

        for (int i = 0; i < FoundSerials.size(); i++) {
            if (indexing.containsKey(howManyFavouritesSerials(FoundSerials.get(i))) &&
                    howManyFavouritesSerials(FoundSerials.get(i)) > 0) {
                ArrayList<String> temp = indexing.get(howManyFavouritesSerials(FoundSerials.get(i)));
                temp.add(temp.size(), FoundSerials.get(i).getTitle());
            } else if (howManyFavouritesSerials(FoundSerials.get(i)) > 0){
                ArrayList<String> list = new ArrayList<>();
                list.add(list.size(), FoundSerials.get(i).getTitle());
                indexing.put(howManyFavouritesSerials(FoundSerials.get(i)), list);
            }
//            System.out.println(howManyFavouritesSerials(FoundSerials.get(i)));
//            System.out.println(indexing.get(howManyFavouritesSerials(FoundSerials.get(i))));
        }



        if (indexing.size() > 0){
            String message = "Query result: ";
            Map<Integer, ArrayList<String>> sortedMap = new TreeMap<Integer, ArrayList<String>>(indexing);
            int ok = 0;
            for (Integer temp : sortedMap.keySet()) {
                ok++;
                if (ok == number) break;
                message = message + sortedMap.get(temp);
            }
//            System.out.println(message);
            object = writeObject(id, null, message);
        } else {
            String message = "Query result: []";
            object = writeObject(id, null, message);
        }

        arrayResult.add(object);
    }

    public void queryLongestMovie(int id, int number, List<String> years, List<String> genres,
                                  List<String> words, List<String> awards, String sortType,
                                  JSONArray arrayResult) {
        ArrayList<Movie> FoundMovies = FoundMoviesByFilters(years, genres, null, null, movies);
        Map<Integer, ArrayList<String>> indexing = new HashMap<>();
        JSONObject object = null;

        for (int i = 0; i < FoundMovies.size(); i++) {
            if (indexing.containsKey(FoundMovies.get(i).getDuration())) {
                ArrayList<String> temp = indexing.get(FoundMovies.get(i).getDuration());
                temp.add(temp.size(), FoundMovies.get(i).getTitle());
            } else {
                ArrayList<String> list = new ArrayList<>();
                list.add(list.size(), FoundMovies.get(i).getTitle());
                indexing.put(FoundMovies.get(i).getDuration(), list);
            }
        }

        if (indexing.size() > 0){
            String message = "Query result: [";
            Map<Integer, ArrayList<String>> sortedMap = new TreeMap<Integer, ArrayList<String>>(indexing);
            int ok = 0;
            for (Integer temp : sortedMap.keySet()) {
                ok++;

                if (ok == number) break;
                if (ok > 1) {
                    message = message + ", ";
                }

                String eliminateBrackets = sortedMap.get(temp).toString();
                eliminateBrackets = eliminateBrackets.replaceAll("\\[", "").replaceAll("\\]","");
//                System.out.println(sortedMap.get(temp).toString());
                message = message + eliminateBrackets;
            }
            message = message + "]";
//            System.out.println(message);
            object = writeObject(id, null, message);
        } else {
            String message = "Query result: []";
//            System.out.println(message);
            object = writeObject(id, null, message);
        }

        arrayResult.add(object);
    }

    public Integer getSerialDuration (Serial serial) {
        int duration = 0;

        for (int i = 0; i < serial.getSeasons().size(); i++) {
            duration += serial.getSeasons().get(i).getDuration();
        }

        return duration;
    }

    public void queryLongestSerial(int id, int number, List<String> years, List<String> genres,
                                  List<String> words, List<String> awards, String sortType,
                                  JSONArray arrayResult) {
        ArrayList<Serial> FoundSerials = FoundSerialsByFilters(years, genres, words, awards, serials);

        Map<Integer, ArrayList<String>> indexing = new HashMap<>();
        JSONObject object = null;

        for (int i = 0; i < FoundSerials.size(); i++) {
            if (indexing.containsKey(getSerialDuration(FoundSerials.get(i)))) {
                ArrayList<String> temp = indexing.get(getSerialDuration(FoundSerials.get(i)));
                temp.add(temp.size(), FoundSerials.get(i).getTitle());
            } else {
                ArrayList<String> list = new ArrayList<>();
                list.add(list.size(), FoundSerials.get(i).getTitle());
                indexing.put(getSerialDuration(FoundSerials.get(i)), list);
            }
        }

        if (indexing.size() > 0){
            String message = "Query result: [";
            Map<Integer, ArrayList<String>> sortedMap = new TreeMap<Integer, ArrayList<String>>(indexing);
            int ok = 0;
            for (Integer temp : sortedMap.keySet()) {
                ok++;

                if (ok == number) break;
                if (ok > 1) {
                    message = message + ", ";
                }

                String eliminateBrackets = sortedMap.get(temp).toString();
                eliminateBrackets = eliminateBrackets.replaceAll("\\[", "").replaceAll("\\]","");
//                System.out.println(sortedMap.get(temp).toString());
                message = message + eliminateBrackets;
            }
            message = message + "]";
//            System.out.println(message);
            object = writeObject(id, null, message);
        } else {
            String message = "Query result: []";
//            System.out.println(message);
            object = writeObject(id, null, message);
        }

        arrayResult.add(object);
    }

    public int getNumberOfViews(Show show) {
        int views = 0;

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getHistory().containsKey(show.getTitle())) {
                views += users.get(i).getHistory().get(show.getTitle());
            }
        }

        return views;
    }

    public void queryMostViewedMovie(int id, int number, List<String> years, List<String> genres,
                                     List<String> words, List<String> awards, String sortType,
                                     JSONArray arrayResult) {
        ArrayList<Movie> FoundMovies = FoundMoviesByFilters(years, genres, null, null, movies);
//        System.out.println("FodMovies size = " + FoundMovies.size());
        JSONObject object = null;


        for (int i = 0 ;i < FoundMovies.size(); i++) {
            if (getNumberOfViews(FoundMovies.get(i)) > 0) {
                FoundMovies.get(i).setNumberOfViews(getNumberOfViews(FoundMovies.get(i)));
            } else {
                FoundMovies.remove(i);
            }
        }


        FoundMovies.sort(new MultipleComparators.CompareMovieByNumberOfViews());

        if (sortType.equals("desc")) {
            Collections.reverse(FoundMovies);
        }

        if (FoundMovies.size() > 0) {
            String message = "Query result: [";
            for (int i = 0; i < FoundMovies.size(); i++) {
                if (i == number) {
                    break;
                }
                message = message + FoundMovies.get(i).getTitle();
                if (i < FoundMovies.size() - 1) {
                    message = message + ", ";
                }
            }
            message = message + "]";
            object = writeObject(id, null, message);
        } else {
            String message = "Query result: []";
            object = writeObject(id, null, message);
        }

        arrayResult.add(object);
    }

    public void queryMostViewedSerial(int id, int number, List<String> years, List<String> genres,
                                     List<String> words, List<String> awards, String sortType,
                                     JSONArray arrayResult) {
        ArrayList<Serial> FoundSerials = FoundSerialsByFilters(years, genres, null, null, serials);
        JSONObject object = null;

        for (int i = 0 ;i < FoundSerials.size(); i++) {
            if (getNumberOfViews(FoundSerials.get(i)) > 0) {
                FoundSerials.get(i).setNumberOfViews(getNumberOfViews(FoundSerials.get(i)));
            } else {
                FoundSerials.remove(i);
            }
        }

        FoundSerials.sort(new MultipleComparators.CompareMovieByNumberOfViews());

        if (sortType.equals("desc")) {
            Collections.reverse(FoundSerials);
        }

        if (FoundSerials.size() > 0) {
            String message = "Query result: [";
            for (int i = 0; i < FoundSerials.size(); i++) {
                if (i == number) {
                    break;
                }
                message = message + FoundSerials.get(i).getTitle();
                if (i < FoundSerials.size() - 1) {
                    message = message + ", ";
                }
            }
            message = message + "]";
            object = writeObject(id, null, message);
        } else {
            String message = "Query result: []";
            object = writeObject(id, null, message);
        }

        arrayResult.add(object);
    }

    public void queryRatingMovie (int id, int number, List<String> years, List<String> genres,
                                  List<String> words, List<String> awards, String sortType,
                                  JSONArray arrayResult) {
        ArrayList<Movie> FoundMovies = FoundMoviesByFilters(years, genres, null, null, movies);
        JSONObject object = null;

//        for (int i = 0; i < FoundMovies.size(); i++) {
//            System.out.print(FoundMovies.get(i).getTitle() + " - ");
//        }

        for (int i = 0; i < FoundMovies.size(); i++) {
            if (FoundMovies.get(i).getRating() == 0) {
                FoundMovies.remove(i);
            }
        }

        FoundMovies.sort(new MultipleComparators.CompareMovieByRating());
        if (sortType.equals("desc")) {
            Collections.reverse(FoundMovies);
        }

        if (FoundMovies.size() > 0) {
            String message = "Query result: [";
            for (int i = 0; i < FoundMovies.size(); i++) {
                if (i == number) {
                    break;
                }
                message = message + FoundMovies.get(i).getTitle();
                if (i < FoundMovies.size() - 1) {
                    message = message + ", ";
                }
            }
            message = message + "]";
            object = writeObject(id, null, message);
        } else {
            String message = "Query result: []";
            object = writeObject(id, null, message);
        }

        arrayResult.add(object);
    }

    public void queryRatingSerial(int id, int number, List<String> years, List<String> genres,
                                  List<String> words, List<String> awards, String sortType,
                                  JSONArray arrayResult) {
        ArrayList<Serial> FoundSerials = FoundSerialsByFilters(years, genres, null, null, serials);
        JSONObject object = null;

        for (int i = 0; i < FoundSerials.size(); i++) {
            if (FoundSerials.get(i).getRating() == 0) {
                FoundSerials.remove(i);
            }
        }

        FoundSerials.sort(new MultipleComparators.CompareSerialByRating());
        if (sortType.equals("desc")) {
            Collections.reverse(FoundSerials);
        }

        if (FoundSerials.size() > 0) {
            String message = "Query result: [";
            for (int i = 0; i < FoundSerials.size(); i++) {
                if (i == number) {
                    break;
                }
                message = message + FoundSerials.get(i).getTitle();
                if (i < FoundSerials.size() - 1) {
                    message = message + ", ";
                }
            }
            message = message + "]";
            object = writeObject(id, null, message);
        } else {
            String message = "Query result: []";
            object = writeObject(id, null, message);
        }

        arrayResult.add(object);
    }

    public void queryRatingUsers(int id, int number, List<String> years, List<String> genres,
                                 List<String> words, List<String> awards, String sortType,
                                 JSONArray arrayResult) {
        ArrayList<User> FoundUsers = new ArrayList<>();
        JSONObject object = null;

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getNumberOfRatings() >= 1) {
                FoundUsers.add(users.get(i));
            }
        }

//        for (int i = 0; i < FoundUsers.size(); i++) {
//            System.out.println(FoundUsers.get(i).getUsername() + " --- " + FoundUsers.get(i).getNumberOfRatings());
//        }
//        System.out.println("SORTED");
        FoundUsers.sort(new MultipleComparators.CompareUserByUsername());
        FoundUsers.sort(new MultipleComparators.CompareUserByRatings());
//        for (int i = 0; i < FoundUsers.size(); i++) {
//            System.out.println(FoundUsers.get(i).getUsername() + " --- " + FoundUsers.get(i).getNumberOfRatings());
//        }

        if (sortType.equals("desc")) {
            Collections.reverse(FoundUsers);
        }

        if (FoundUsers.size() > 0) {
            String message = "Query result: [";
            for (int i = 0; i < FoundUsers.size(); i++) {
                if (i == number) {
                    break;
                }
                message = message + FoundUsers.get(i).getUsername();
                if (i < FoundUsers.size() - 1) {
                    message = message + ", ";
                }
            }
            message = message + "]";
            object = writeObject(id, null, message);
        } else {
            String message = "Query result: []";
            object = writeObject(id, null, message);
        }

        arrayResult.add(object);
    }

    public double actualizeActorRatingAverage(Actor actor) {
        double sum = 0;
        int ok = 0;
        int cnt = 0;

        for (int i = 0; i < actor.getFilmography().size(); i++) {
            ok = 0;
//            System.out.println("Size of filmography is " + movies.size());
            for (int j = 0; j < movies.size(); j++) {
//                System.out.println("YES");
                if (actor.getFilmography().get(i).equals(movies.get(j).getTitle())) {
                    ok = -2; // => it's movie
                    if (movies.get(j).getRating() != 0){
                        sum += movies.get(j).getRating();
                        cnt++;
                    }
                    break;
                }
            }
            if (ok != -2) {
                for (int j = 0; j < serials.size(); j++) {
                    if (actor.getFilmography().get(i).equals(serials.get(j).getTitle())) {
                        ok = -1; // => it's serial
                        if (serials.get(j).getRating() != 0) {
                            sum += serials.get(j).getRating();
                            cnt++;
                        }
                        break;
                    }
                }
            }
        }

//        System.out.println("ACTOR AVERAGE = " + sum);

        if (sum == 0){
            return sum;
        } else {
//            System.out.println(actor.getName() + " " + sum + "->" + actor.getFilmography().size());
            return (sum/cnt);
        }

    }

    public void queryAverageActors(int id, int number, List<String> years, List<String> genres,
                                   List<String> words, List<String> awards, String sortType,
                                   JSONArray arrayResult) {
        ArrayList<Actor> FoundActors = new ArrayList<>();
        JSONObject object = null;

        for (int i = 0; i < actors.size(); i++) {
//            System.out.println(actualizeActorRatingAverage("ACTOR AVERAGE "+actualizeActorRatingAverage(actors.get(i))));
            actors.get(i).setFilmographyRatingAverage(actualizeActorRatingAverage(actors.get(i)));
//            System.out.println(actors.get(i).getName() + " " +actors.get(i).getFilmographyRatingAverage());
            if (actors.get(i).getFilmographyRatingAverage() > 0) {
                FoundActors.add(actors.get(i));
            }
        }


        FoundActors.sort(new MultipleComparators.CompareActorByName());
        FoundActors.sort(new MultipleComparators.CompareActorByAverageRating());

        if (FoundActors.size() > 0) {
            String message = "Query result: [";
            for (int i = 0; i < FoundActors.size(); i++) {
                if (i == number) {
                    break;
                }
                message = message + FoundActors.get(i).getName();
                if (i < FoundActors.size() - 1 && i < number - 1) {
                    message = message + ", ";
                }
            }
            message = message + "]";
            object = writeObject(id, null, message);
//            System.out.println(message);
        } else {
            String message = "Query result: []";
            object = writeObject(id, null, message);
        }

        arrayResult.add(object);
    }

    public ArrayList<Actor> FoundActorsByAwards (List<String> awards, ArrayList<Actor> actors) {
        ArrayList<Actor> result = new ArrayList<>();

        int ok = 0;
        for (int i = 0; i < actors.size(); i++) {
            ok = 0;
            for (int j = 0; j < awards.size(); j++) {
//                System.out.println("Looking for " + awards.get(j));
                if (actors.get(i).getAwards().containsKey(ActorsAwards.valueOf(awards.get(j)))) {
//                    System.out.println("CONTINEEE");
                    continue;
                } else {
                    ok = -1;
                    break;
                }
            }
            if (ok != -1) {
                result.add(actors.get(i));
            }

        }

        return result;
    }

    public void queryAwardsActors(int id, int number, List<String> years, List<String> genres,
                                  List<String> words, List<String> awards, String sortType,
                                  JSONArray arrayResult) {
        JSONObject object = null;
        ArrayList<Actor> FoundActors = new ArrayList<>();

        if (awards != null) {
//            System.out.println("Avem de cautat " + awards.toString());
            FoundActors = FoundActorsByAwards(awards, actors);
        }


//        FoundActors.sort(new MultipleComparators.CompareActorByName());

//        FoundActors.sort(new MultipleComparators.ValueComparator<>());
//        System.out.println("FoundActors size = " + FoundActors.size());
        if (FoundActors.size() == 0) {
            String message = "Query result: []";
            object = writeObject(id, null, message);
        } else {
//            System.out.println("-------------------------------------------");
//            for (int i = 0; i < FoundActors.size(); i++) {
//                System.out.println(FoundActors.get(i).getName() + " " + FoundActors.get(i).getNumberOfAwards());
//            }
            FoundActors.sort(new MultipleComparators.CompareActorByName());
            FoundActors.sort(new MultipleComparators.CompareActorByNumberOfAwards());
            if (sortType.equals("desc")) {
                Collections.reverse(FoundActors);
            }
//            System.out.println("**************");
//            for (int i = 0; i < FoundActors.size(); i++) {
//                System.out.println(FoundActors.get(i).getName() + " " + FoundActors.get(i).getNumberOfAwards());
//            }
//            System.out.println("-------------------------------------------");

            String message = "Query result: [";
            for (int i = 0; i < FoundActors.size(); i++) {
                if (i == number) {
                    break;
                }
                message = message + FoundActors.get(i).getName();
                if (i < FoundActors.size() - 1 && i < number - 1) {
                    message = message + ", ";
                }
            }
            message = message + "]";
            object = writeObject(id, null, message);
//            System.out.println(message);
        }

        arrayResult.add(object);
    }

    public ArrayList<Actor> FoundActorsByWords (List<String> words, ArrayList<Actor> actors) {
        ArrayList<Actor> result = new ArrayList<>();
        int ok = 0;

        for (int i = 0; i < actors.size(); i++) {
            ok = 0;
//            System.out.println("SIZEOF actors " + actors.size());
            for (int j = 0; j < words.size(); j++) {
//                System.out.println("Look for " + words.get(j) + " in " + actors.get(i).getName());

                Pattern pattern = Pattern.compile(words.get(j), Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(actors.get(i).getCareerDescription());
                boolean matchFound = matcher.find();
                if (!matchFound) {
                    ok = -1;
                    break;
                }

//                if (actors.get(i).getCareerDescription().indexOf(words.get(j)) == -1) {
//                    ok = -1;
//                    break;
//                }
            }
            if (ok != -1) {
                result.add(actors.get(i));
            }
        }
//        System.out.println("Size of foundactors " + result.size());
        return result;
    }

    public void queryDescriptionActors(int id, int number, List<String> years, List<String> genres,
                                       List<String> words, List<String> awards, String sortType,
                                       JSONArray arrayResult) {

        ArrayList<Actor> FoundActors = new ArrayList<>();
        JSONObject object = null;

        FoundActors = FoundActorsByWords(words, actors);

        if (FoundActors.size() == 0) {
            String message = "Query result: []";
            object = writeObject(id, null, message);
        } else {
            FoundActors.sort(new MultipleComparators.CompareActorByName());
            String message = "Query result: [";
            for (int i = 0; i < FoundActors.size(); i++) {
                if (i == number) {
                    break;
                }
                message = message + FoundActors.get(i).getName();
                if (i < FoundActors.size() - 1 && i < number - 1) {
                    message = message + ", ";
                }
            }
            message = message + "]";
            object = writeObject(id, null, message);
//            System.out.println(message);

        }
        arrayResult.add(object);
    }

    public void stardardRecommendation(int id, String type, String username,
                                       JSONArray arrayResult) {
        JSONObject object = null;
        User unknownUser = null;
        int ok = -1;

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(username)) {
                unknownUser = users.get(i);
//                System.out.println("Found unknown " + unknownUser.getUsername());
            }
        }

        for (int i = 0; i < movies.size(); i++) {
            if (unknownUser.getHistory().containsKey(movies.get(i).getTitle()) == false) {
                String message = "StandardRecommendation result: " +
                        movies.get(i).getTitle();
                ok = 0;
                object = writeObject(id, null, message);
                break;
            }
        }

        if (ok == -1) {
            String message = "StandardRecommendation cannot be applied!";
            object = writeObject(id, null, message);
        }

        arrayResult.add(object);
    }

    public ArrayList<Show> getAllUnseenShows(User user) {
        ArrayList<Show> unseenShows = new ArrayList<>();

        for (int i = 0; i < movies.size(); i++) {
            if (user.getHistory().containsKey(movies.get(i).getTitle()) == false) {
                unseenShows.add(movies.get(i));
            }
        }
        for (int i = 0; i < serials.size(); i++) {
            if (user.getHistory().containsKey(serials.get(i).getTitle()) == false) {
                unseenShows.add(serials.get(i));
            }
        }

        return unseenShows;
    }

    public void bestUnseenRecommendation(int id, String type, String username,
                                         JSONArray arrayResult) {
        JSONObject object = null;
        Show uknownShow = null;
        User unknownUser = null;
        ArrayList<Show> FoundShows = null;
        int ok = -1;

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(username)) {
                unknownUser = users.get(i);
            }
        }

        FoundShows = getAllUnseenShows(unknownUser);

        FoundShows.sort(new MultipleComparators.CompareShoeByGeneralRating());

        if (FoundShows.size() > 0) {
            String message = "BestRatedUnseenRecommendation result: " + FoundShows.get(0).getTitle();
            object = writeObject(id, null, message);
        } else {
            String message = "BestRatedUnseenRecommendation cannot be applied!";
            object = writeObject(id, null, message);
        }

        arrayResult.add(object);
    }


    public void popularRecommendation(int id, String type, String username,
                                      JSONArray arrayResult) {
        JSONObject object = null;
        User unknownUser = null;
        Map<String, Integer> popularGenres = new HashMap<>();

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(username)) {
                unknownUser = users.get(i);
            }
        }

        if (unknownUser.getSubscriptionType().equals("BASIC")) {
            String message = "PopularRecommendation cannot be applied!";
            object = writeObject(id, null, message);
        } else {
            for (int i = 0; i < movies.size(); i++) {
                for (int j = 0; j < movies.get(i).getGenres().size(); j++) {
                    if (popularGenres.containsKey(movies.get(i).getGenres().get(j)) == false) {
                        popularGenres.put(movies.get(i).getGenres().get(j),
                                getNumberOfViews(movies.get(i)));
                    } else {
                        popularGenres.put(movies.get(i).getGenres().get(j),
                                popularGenres.get(movies.get(i).getGenres().get(j)) +
                                        getNumberOfViews(movies.get(i)));
                    }
                }
            }
            for (int i = 0; i < serials.size(); i++) {
                for (int j = 0; j < movies.get(i).getGenres().size(); j++) {
                    if (popularGenres.containsKey(movies.get(i).getGenres().get(j)) == false) {
                        popularGenres.put(movies.get(i).getGenres().get(j),
                                getNumberOfViews(movies.get(i)));
                    } else {
                        popularGenres.put(movies.get(i).getGenres().get(j),
                                popularGenres.get(movies.get(i).getGenres().get(j)) +
                                        getNumberOfViews(movies.get(i)));
                    }
                }
            }

//            System.out.println(popularGenres.toString());

            int maximumSize = popularGenres.size();
            int ok = 0;
            Integer max = 0;
            String toSearch = null;
            ArrayList<Movie> tempMovies = new ArrayList<>();
            ArrayList<Serial> tempSerials = new ArrayList<>();
            List<String> tempGenres= new ArrayList<>();

            while (ok < 10) {
                max = 0;
//                ok = 0;
                tempMovies.clear();
                tempSerials.clear();
                tempGenres.clear();
                toSearch = null;

                Iterator it = popularGenres.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry)it.next();
                    if ((Integer)pair.getValue() > max) {
                        max = (Integer) pair.getValue();
                        toSearch = (String) pair.getKey();
                    }
//                    it.remove();
                }

//                ok++;
//                System.out.println("We Want to search " + toSearch + " " + max);
                popularGenres.remove(toSearch);

                tempGenres.add(toSearch);

                tempMovies = FoundMoviesByFilters(null, tempGenres, null, null, movies);
                tempSerials= FoundSerialsByFilters(null, tempGenres, null, null ,serials);

//                System.out.println(tempMovies.size() + "---" + tempSerials.size());
                for (int i = 0; i < tempMovies.size(); i++) {

                    if (unknownUser.getHistory().containsKey(tempMovies.get(i).getTitle()) == false) {
//                        System.out.println("Cautam daca " + unknownUser.getUsername() + " a vazut " +
//                                tempMovies.get(i).getTitle());
                        String message = "PopularRecommendation result: " +
                                tempMovies.get(i).getTitle();

                        object = writeObject(id, null, message);
                        ok = -1;
                        break;
                    }
                }

                if (ok == -1) {
                    arrayResult.add(object);
                    return;

                }

                for (int i = 0; i < tempSerials.size(); i++) {

                    if (unknownUser.getHistory().containsKey(tempSerials.get(i).getTitle()) == false) {
//                        System.out.println("Cautam daca " + unknownUser.getUsername() + " a vazut!!!! " +
//                                tempSerials.get(i).getTitle());
                        String message = "PopularRecommendation result: " +
                                tempSerials.get(i).getTitle();
//                        System.out.println(message);
                        ok = -1;
                        object = writeObject(id, null, message);

                        break;
                    }
                }
                if (ok == -1) {
//                    System.out.println("L am schimbat pe ok");
                    arrayResult.add(object);
                    return;

                }
                ok ++;
            }
        }

        arrayResult.add(object);
    }

    public void SearchRecommendation(int id, String type, String username, String genre,
                                      JSONArray arrayResult) {
        JSONObject object = null;
        User unknownUser = null;
        ArrayList<Movie> FoundMovies = null;
        ArrayList<Serial> FoundSerials = null;
        ArrayList<Show> FoundShows = new ArrayList<>();
        ArrayList<Show> tempShows = new ArrayList<>();
        List<String> tempList = new ArrayList<>();
        tempList.add(genre);

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(username)) {
                unknownUser = users.get(i);
            }
        }

        if (unknownUser.getSubscriptionType().equals("BASIC")) {
            String message = "PopularRecommendation cannot be applied!";
            object = writeObject(id, null, message);
        } else {
            FoundMovies = FoundMoviesByFilters(null, tempList, null, null, movies);
            FoundSerials = FoundSerialsByFilters(null, tempList, null, null, serials);

            for (int i = 0; i < FoundMovies.size(); i++) {
//            System.out.println(FoundMovies.get(i).getTitle() + "---" + FoundMovies.get(i).getRating());
                FoundShows.add(FoundMovies.get(i));
            }
            for (int i = 0; i < FoundSerials.size(); i++) {
//            System.out.println(FoundSerials.get(i).getTitle() + "---" + FoundSerials.get(i).getRating());
                FoundShows.add(FoundSerials.get(i));
            }

//            System.out.println("size = " + FoundShows.size());
            for (int i = 0; i < FoundShows.size(); i++) {

                if (unknownUser.getHistory().containsKey(FoundShows.get(i).getTitle())) {
                    tempShows.add(FoundShows.get(i));
                }
            }

            for (int i = 0; i < tempShows.size(); i++) {
//                System.out.println("Cautam " + tempShows.get(i).getTitle());
                FoundShows.remove(tempShows.get(i));
            }

//            System.out.println("Size of foundshows " + FoundShows.size());
            FoundShows.sort(new MultipleComparators.CompareShoeByGeneralRating());
            Collections.reverse(FoundShows);
            FoundShows.sort(new MultipleComparators.CompareShowByTitle());

            if (FoundShows.size() == 0) {
                String message = "SearchRecommendation cannot be applied!";
                object = writeObject(id, null, message);
            } else {
                String message = "SearchRecommendation result: [";
                for (int i = 0; i < FoundShows.size(); i++) {
                    message = message + FoundShows.get(i).getTitle();
                    if (i < FoundShows.size() - 1) {
                        message = message + ", ";
                    }
                }
                message = message + "]";
                object = writeObject(id, null, message);
            }
        }
        arrayResult.add(object);
    }

    public Integer howManyFavouritesSerial (Serial serial) {
        Integer n = 0;

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getHistory().containsKey(serial.getTitle()) &&
                    users.get(i).getFavoriteMovies().contains(serial.getTitle())) {
                n++;
            }
        }

        return n;
    }

    public void FavoriteRecommendation(int id, String type, String username,
                                     JSONArray arrayResult) {
        JSONObject object = null;
        User unknownUser = null;
        ArrayList<Show> FoundShows = new ArrayList<>();

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(username)) {
                unknownUser = users.get(i);
            }
        }

        if (unknownUser.getSubscriptionType().equals("BASIC")) {
            String message = "FavoriteRecommendation cannot be applied!";
            object = writeObject(id, null, message);
        } else {
            for (int i = 0; i < movies.size(); i++) {
                movies.get(i).setNumberOfFavourites((int) howManyFavourites(movies.get(i)));
                if (movies.get(i).getNumberOfFavourites() > 0 &&
                    unknownUser.getHistory().containsKey(movies.get(i).getTitle()) == false) {
                    FoundShows.add(movies.get(i));
                }
            }
            for (int i = 0; i < serials.size(); i++) {
                serials.get(i).setNumberOfFavourites((int) howManyFavouritesSerial(serials.get(i)));
                if (serials.get(i).getNumberOfFavourites() > 0 &&
                        unknownUser.getHistory().containsKey(movies.get(i).getTitle()) == false) {
                    FoundShows.add(serials.get(i));
                }
            }
            FoundShows.sort(new MultipleComparators.CompareShowByTitle());
            FoundShows.sort(new MultipleComparators.CompareShowByFavourites());
            if (FoundShows.size() == 0) {
                String message ="FavoriteRecommendation cannot be applied!";
                object = writeObject(id, null, message);
            } else {
//                System.out.println(FoundShows.get(0).getTitle());
                String message = "FavoriteRecommendation result: ";
                message = message + FoundShows.get(0).getTitle();
                object = writeObject(id, null, message);
            }

        }
        arrayResult.add(object);
    }

}
