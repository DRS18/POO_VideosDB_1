package myClasses;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public final class MultipleComparators {
    public final static class CompareByTitle implements Comparator<Show> {
        public int compare(final Show a, final Show b) {
            return a.getTitle().compareTo(b.getTitle());
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

    public final static class ValueComparator<K, V extends Comparable<V>> implements Comparator<K>{

        HashMap<K, V> map = new HashMap<K, V>();

        public ValueComparator(HashMap<K, V> map){
            this.map.putAll(map);
        }

        @Override
        public int compare(K s1, K s2) {
            return -map.get(s1).compareTo(map.get(s2));//descending order
        }
    }

    public final static class CompareActorByNumberOfAwards implements Comparator {
        Map map;

        public void ValueComparator(Map map) {
            this.map = map;
        }

        public int compare(Object keyA, Object keyB) {
            Comparable valueA = (Comparable) map.get(keyA);
            Comparable valueB = (Comparable) map.get(keyB);
            return valueB.compareTo(valueA);
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

    public final static class CompareUserByRatings implements Comparator<User> {
        public int compare (final User a, final User b) {
            return a.getNumberOfRatings() - b.getNumberOfRatings();
        }
    }


}
