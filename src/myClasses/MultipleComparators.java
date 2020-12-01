package myClasses;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public final class MultipleComparators {
    public final static class CompareShowByTitle implements Comparator<Show> {
        public int compare(final Show a, final Show b) {
            return a.getTitle().compareTo(b.getTitle());
        }
    }

    public final static class CompareShowByFavourites implements Comparator<Show> {
        public int compare(final Show a, final Show b) {
            return a.getNumberOfFavourites() - b.getNumberOfFavourites();
        }
    }

    public final static class CompareActorByName implements Comparator<Actor> {
        public int compare(final Actor a, final Actor b) {
            return a.getName().compareTo(b.getName());
        }
    }

    public final static class CompareActorByAverageRating implements Comparator <Actor> {
        public int compare(final Actor a, final Actor b) {
            return Double.compare(a.getFilmographyRatingAverage(), b.getFilmographyRatingAverage());
        }
    }

    public final static class CompareActorByNumberOfAwards implements Comparator<Actor> {
        public int compare(final Actor a, final Actor b) {
            return a.getNumberOfAwards() - b.getNumberOfAwards();
        }
    }

    public final static class CompareMovieByNumberOfViews implements Comparator<Show> {
        public int compare (final Show show1, final Show show2) {
            return show1.getNumberOfViews() - show2.getNumberOfViews();
        }
    }

    public final static class CompareShoeByGeneralRating implements Comparator<Show> {
        public int compare (final Show a, final Show b) {
            return Double.compare(a.getShowGeneralRating(), b.getShowGeneralRating());
        }
    }

    public final static class CompareMovieByRating implements Comparator<Movie> {
        public int compare (final Movie a, final Movie b) {
            return Double.compare(a.getRating(), b.getRating());
        }
    }

    public final static class CompareSerialByRating implements Comparator<Serial> {
        public int compare (final Serial a, final Serial b) {
            return Double.compare(a.getRating(), b.getRating());
        }
    }

    public final static class CompareUserByRatings implements Comparator<User> {
        public int compare (final User a, final User b) {
            return a.getNumberOfRatings() - b.getNumberOfRatings();
        }
    }


}
