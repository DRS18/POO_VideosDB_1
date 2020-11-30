package myClasses;

import java.util.Comparator;

public final class MultipleComparators {
    public final static class CompareByTitle implements Comparator<Show> {
        public int compare(final Show a, final Show b) {
            return a.getTitle().compareTo(b.getTitle());
        }
    }

    public final static class CompareMovieByNumberOfViews implements Comparator<Show> {
        public int compare (final Show show1, final Show show2) {
            return show1.getNumberOfViews() - show2.getNumberOfViews();
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


}
