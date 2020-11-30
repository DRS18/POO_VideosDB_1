package main;

import checker.Checkstyle;
import checker.Checker;
import common.Constants;
import fileio.Input;
import fileio.InputLoader;
import fileio.Writer;
import org.json.simple.JSONArray;

import myClasses.DataBase;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

/**
 * The entry point to this homework. It runs the checker that tests your implentation.
 */
public final class Main {
    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * Call the main checker and the coding style checker
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(Constants.TESTS_PATH);
        Path path = Paths.get(Constants.RESULT_PATH);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        File outputDirectory = new File(Constants.RESULT_PATH);

        Checker checker = new Checker();
        checker.deleteFiles(outputDirectory.listFiles());

        for (File file : Objects.requireNonNull(directory.listFiles())) {

            String filepath = Constants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getAbsolutePath(), filepath);
            }
        }

        checker.iterateFiles(Constants.RESULT_PATH, Constants.REF_PATH, Constants.TESTS_PATH);
        Checkstyle test = new Checkstyle();
        test.testCheckstyle();
    }

    /**
     * @param filePath1 for input file
     * @param filePath2 for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePath1,
                              final String filePath2) throws IOException {
        InputLoader inputLoader = new InputLoader(filePath1);
        Input input = inputLoader.readData();

        Writer fileWriter = new Writer(filePath2);
        JSONArray arrayResult = new JSONArray();

        //TODO add here the entry point to your implementation

        // Completam propria baza de date
        DataBase my_db = new DataBase();

        my_db.putActors(input);
        my_db.putMovies(input);
        my_db.putSerials(input);
        my_db.putUsers(input);
//        my_db.putActions(input);

//        my_db.iterateThroughActions(arrayResult);
        for (int i = 0; i < input.getCommands().size(); i++) {
            if (input.getCommands().get(i).getActionType().equals("command") &&
                    input.getCommands().get(i).getType().equals("favorite")){
//                System.out.println(actions.get(i).toString());
                my_db.commandFavorite(input.getCommands().get(i).getActionId(),
                        input.getCommands().get(i).getUsername(),
                        input.getCommands().get(i).getTitle(),
                        arrayResult);
            } else if (input.getCommands().get(i).getActionType().equals("command") &&
                    input.getCommands().get(i).getType().equals("view")) {
//                System.out.println(input.getCommands().get(i).toString());
                my_db.commandView(input.getCommands().get(i).getActionId(),
                        input.getCommands().get(i).getUsername(),
                        input.getCommands().get(i).getTitle(),
                        arrayResult);
            } else if (input.getCommands().get(i).getActionType().equals("command") &&
                    input.getCommands().get(i).getType().equals("rating")) {
//                System.out.println(input.getCommands().get(i).toString());
                my_db.commandRating(input.getCommands().get(i).getActionId(),
                        input.getCommands().get(i).getUsername(),
                        input.getCommands().get(i).getTitle(),
                        input.getCommands().get(i).getGrade(),
                        input.getCommands().get(i).getSeasonNumber(),
                        arrayResult);
            } else if (input.getCommands().get(i).getActionType().equals("query")) {
                List<String> years = input.getCommands().get(i).getFilters().get(0);
                List<String> genres = input.getCommands().get(i).getFilters().get(1);
                List<String> words = input.getCommands().get(i).getFilters().get(2);
                List<String> awards = input.getCommands().get(i).getFilters().get(3);
                if (input.getCommands().get(i).getCriteria().equals("favorite")) {

                    if (input.getCommands().get(i).getObjectType().equals("movies")) {
                        my_db.queryFavouriteMovies(input.getCommands().get(i).getActionId(),
                                input.getCommands().get(i).getNumber(), years, genres,
                                null, null, input.getCommands().get(i).getSortType(), arrayResult);

                    } else if (input.getCommands().get(i).getObjectType().equals("shows")) {
//                        System.out.println(input.getCommands().get(i).toString());

                        my_db.queryFavouriteShows(input.getCommands().get(i).getActionId(),
                                input.getCommands().get(i).getNumber(), years, genres,
                                null, null, input.getCommands().get(i).getSortType(), arrayResult);
                    }

                } else if (input.getCommands().get(i).getCriteria().equals("longest")) {
                    if (input.getCommands().get(i).getObjectType().equals("movies")) {
//                        System.out.println(input.getCommands().get(i).toString());
                        my_db.queryLongestMovie(input.getCommands().get(i).getActionId(),
                                input.getCommands().get(i).getNumber(), years, genres,
                                null, null, input.getCommands().get(i).getSortType(), arrayResult);
                    } else if (input.getCommands().get(i).getObjectType().equals("shows")) {
                        my_db.queryLongestSerial(input.getCommands().get(i).getActionId(),
                                input.getCommands().get(i).getNumber(), years, genres,
                                null, null, input.getCommands().get(i).getSortType(), arrayResult);
                    }

                } else if (input.getCommands().get(i).getCriteria().equals("most_viewed")) {
                    if (input.getCommands().get(i).getObjectType().equals("movies")) {
//                        System.out.println(input.getCommands().get(i).toString());
                        my_db.queryMostViewedMovie(input.getCommands().get(i).getActionId(),
                                input.getCommands().get(i).getNumber(), years, genres,
                                null, null, input.getCommands().get(i).getSortType(), arrayResult);

                    } else if (input.getCommands().get(i).getObjectType().equals("shows")) {
                        my_db.queryMostViewedSerial(input.getCommands().get(i).getActionId(),
                                input.getCommands().get(i).getNumber(), years, genres,
                                null, null, input.getCommands().get(i).getSortType(), arrayResult);
                    }
                } else if (input.getCommands().get(i).getCriteria().equals("ratings")) {
                    if (input.getCommands().get(i).getObjectType().equals("movies")) {
//                        System.out.println(input.getCommands().get(i).toString());
                        my_db.queryRatingMovie(input.getCommands().get(i).getActionId(),
                                input.getCommands().get(i).getNumber(), years, genres,
                                null, null, input.getCommands().get(i).getSortType(), arrayResult);
                    } else if (input.getCommands().get(i).getObjectType().equals("shows")) {
//                        System.out.println(input.getCommands().get(i).toString());
                        my_db.queryRatingSerial(input.getCommands().get(i).getActionId(),
                                input.getCommands().get(i).getNumber(), years, genres,
                                null, null, input.getCommands().get(i).getSortType(), arrayResult);
                    }
                } else if (input.getCommands().get(i).getCriteria().equals("num_ratings")) {
                    if (input.getCommands().get(i).getObjectType().equals("users")) {
//                        System.out.println(input.getCommands().get(i).toString());
                        my_db.queryRatingUsers(input.getCommands().get(i).getActionId(),
                                input.getCommands().get(i).getNumber(), years, genres,
                                null, null, input.getCommands().get(i).getSortType(), arrayResult);
                    }
                } else if (input.getCommands().get(i).getCriteria().equals("average")) {
                    if (input.getCommands().get(i).getObjectType().equals("actors")) {
//                        System.out.println(input.getCommands().get(i).toString());
                        my_db.queryAverageActors(input.getCommands().get(i).getActionId(),
                                input.getCommands().get(i).getNumber(), years, genres,
                                null, null, input.getCommands().get(i).getSortType(), arrayResult);
                    }
                } else if (input.getCommands().get(i).getCriteria().equals("awards")) {
//                    System.out.println(input.getCommands().get(i).toString());
//                    if(awards!=null)System.out.println(awards.toString());
                    my_db.queryAwardsActors(input.getCommands().get(i).getActionId(),
                            input.getCommands().get(i).getNumber(), years, genres,
                            words, awards, input.getCommands().get(i).getSortType(), arrayResult);
                } else if (input.getCommands().get(i).getCriteria().equals("filter_description")) {
//                    System.out.println(input.getCommands().get(i).toString());
                    my_db.queryDescriptionActors(input.getCommands().get(i).getActionId(),
                            input.getCommands().get(i).getNumber(), years, genres,
                            words, awards, input.getCommands().get(i).getSortType(), arrayResult);

                }
            } else if (input.getCommands().get(i).getActionType().equals("recommendation")) {
                if (input.getCommands().get(i).getType().equals("standard")) {
//                    System.out.println(input.getCommands().get(i).toString());
                    my_db.stardardRecommendation(input.getCommands().get(i).getActionId(),
                            input.getCommands().get(i).getType(), input.getCommands().get(i).getUsername(),
                            arrayResult);
                }

            }
        }


//        System.out.println(arrayResult.toJSONString());



//        System.out.println(input.getCommands());

        fileWriter.closeJSON(arrayResult);
    }
}
