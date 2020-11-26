package myClasses;

import fileio.Input;
import main.Main;

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

    public void iterateThrowActions () {
        for (int i = 0; i < actions.size(); i++) {
            if (actions.get(i).getActionType().equals("command") &&
                    actions.get(i).getType().equals("favorite")){
//                System.out.println(actions.get(i).toString());
                commandFavorite(actions.get(i).getUsername(), actions.get(i).getTitle());

            } else if (actions.get(i).getActionType().equals("command") &&
                    actions.get(i).getType().equals("view")) {
                System.out.println(actions.get(i).toString());
                commandView(actions.get(i).getUsername(), actions.get(i).getTitle());
            } else if (actions.get(i).getActionType().equals("command") &&
                    actions.get(i).getType().equals("rating")) {
                System.out.println(actions.get(i).toString());
                commandRating(actions.get(i).getUsername(), actions.get(i).getTitle(),
                        actions.get(i).getGrade(), actions.get(i).getSeasonNumber());
            }
        }
    }



    public void commandFavorite(String username, String title) {
        int ok = -1;

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(username)) {
                ok = i;
//                System.out.println("BEFORE");
//                System.out.println(users.get(i).toString());

                for (int j = 0; j < users.get(i).getFavoriteMovies().size(); j++) {
                    if (users.get(i).getFavoriteMovies().get(j).equals(title)) {
                        ok = -2;
                    }
                }
                if (ok == -2) {
                    break;
                }
            }
        }

        if (ok != -1 && ok != -2) {
            users.get(ok).getFavoriteMovies().add(
                    users.get(ok).getFavoriteMovies().size() - 1, title);
//            System.out.println("AFTER");
//            System.out.println(users.get(ok).toString());
        }
    }

    public void commandView (String username, String title) {
        int ok = -1;

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(username)) {
                ok = i;

//                System.out.println("BEFORE");
//                System.out.println(users.get(i).toString());

                for (int j = 0; j < users.get(i).getHistory().size(); j++) {

                    if (users.get(i).getHistory().entrySet().equals(title)) {
                        ok = -2;
                    }
                }

                if (ok == -2) {
                    break;
                }
            }
        }
        if (ok != -1 && ok != -2) {
            users.get(ok).getHistory().put(title, users.get(ok).getHistory().size() - 1);
//            System.out.println("AFTER");
//            System.out.println(users.get(ok).toString());
        }
    }

    public void commandRating(String username, String title, int grade, int season) {
        int ok = -1;

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(username)) {
                ok = i;

                System.out.println("BEFORE");
                System.out.println(users.get(i).toString());

                for (int j = 0; j < users.get(i).getRatings().size(); j++) {
                    if (users.get(i).getRatings().get(j).equals(title)) {
                        ok = -2;
                    }
                }

                if (ok == -2) {
                    break;
                }
            }
        }

        if (ok != -1 && ok != -2) {
            int check = -1;
            users.get(ok).getRatings().add(users.get(ok).getRatings().size() - 1, title);
            for (int i = 0; i < movies.size(); i++) {
                if (movies.get(i).getTitle().equals(title)) {
                    movies.get(i).getRatings().add(movies.get(i).getRatings().size() - 1,
                            (double) grade);
                    movies.get(i).getUsersRecord().add(movies.get(i).getUsersRecord().size() - 1,
                            username);
                    check = 0;
                    break;
                }
            }

            if (check == -1) {
                for (int i = 0; i < serials.size(); i++) {
                    if (serials.get(i).getTitle().equals(title)) {
                        serials.get(i).getSeasons().get
                    }
                }
            }

        }
    }
}
