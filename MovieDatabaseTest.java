import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;

import org.junit.Test;

public class MovieDatabaseTest {
    Movie m=new Movie("NASA", "Ilon Mask", 2023, 76);
    File file=new File("moviesTestCase_database.txt");
    
    @Test
    public void testSaveToFile(){
        MovieDatabase.saveToFile(m,file);
    }

    @Test
    public void testRemoveMovie(){
        Movie m=new Movie("NASA", "Ilon Mask", 2023, 76);
        removeMovie(m);
    }
    public void removeMovie(Movie m){
        File path=new File("moviesTestCase_database.txt");
        File temp=new File("movies_backup.txt");
        try (FileWriter fw=new FileWriter(temp);BufferedReader br=new BufferedReader(new FileReader(path))) {
            String line;
            while((line=br.readLine())!=null){
               if((line.contains("Title: " + m.getTitle()))){
                    for(int i=0;i<5;i++){
                        line=br.readLine();
                    }
               }
               fw.write(line+"\n");
            }
        } catch (Exception e) {
            System.out.println("Something went wrong: "+e.getMessage());
        } 
        try (FileWriter fw=new FileWriter(path);BufferedReader br=new BufferedReader(new FileReader(temp))) {
            String line;
            while((line=br.readLine())!=null){
               fw.write(line+"\n");

            }
        } catch (Exception e) {
            System.out.println("Something went wrong: "+e.getMessage());
        } 
        finally{
            temp.delete();
        }
    }
    // Test movies for sorting tests
    private ArrayList<Movie> testMovies() {
        Movie movie1 = new Movie("Movie1", "Director1", 2000, 120);
        Movie movie2 = new Movie("Movie2", "Director2", 2005, 110);
        Movie movie3 = new Movie("Movie3", "Director1", 2010, 100);

        return new ArrayList<>(Arrays.asList(movie1, movie2, movie3));
    }
    @Test
    public void testGetMoviesStartingFromYear() {
        ArrayList<Movie> movies = testMovies();
        int year = 2000;
        ArrayList<Movie> result = MovieDatabase.getMoviesStartingFromYear(year, movies);

        assertEquals(3, result.size());
        assertEquals("Movie1", result.get(0).getTitle());
        assertEquals("Movie2", result.get(1).getTitle());

        // Test with a year where no movies are available
        assertThrows(NoSuchElementException.class, () -> MovieDatabase.getMoviesStartingFromYear(2100, movies));
    }

    @Test
    public void testGetMoviesFromYear() {
        ArrayList<Movie> movies = testMovies();
        int year = 2000;
        ArrayList<Movie> result = MovieDatabase.getMoviesFromYear(year, movies);

        assertEquals(1, result.size());
        assertEquals("Movie1", result.get(0).getTitle());

        // Test with a year where no movies are available
        assertThrows(NoSuchElementException.class, () -> MovieDatabase.getMoviesFromYear(2100, movies));
    }

    @Test
    public void testGetMovieByTitle() {
        ArrayList<Movie> movies = testMovies();
        String title = "Movie2";
        ArrayList<Movie> result = MovieDatabase.getMovieByTitle(title, movies);

        assertEquals(1, result.size());
        assertEquals("Movie2", result.get(0).getTitle());

        // Test with a title where no movies are available
        assertThrows(NoSuchElementException.class, () -> MovieDatabase.getMovieByTitle("UnknownMovie", movies));
    }

    @Test
    public void testGetMovieByDirector() {
        ArrayList<Movie> movies = testMovies();
        String director = "Director1";
        ArrayList<Movie> result = MovieDatabase.getMovieByDirector(director, movies);

        assertEquals(2, result.size());
        assertEquals("Movie1", result.get(0).getTitle());
        assertEquals("Movie3", result.get(1).getTitle());

        // Test with a director where no movies are available
        assertThrows(NoSuchElementException.class, () -> MovieDatabase.getMovieByDirector("UnknownDirector", movies));
    }

    @Test
    public void testSortByTitleAscending() {
        ArrayList<Movie> movies = testMovies();
        ArrayList<Movie> result = MovieDatabase.sortByTitleAscending(movies);

        assertEquals("Movie1", result.get(0).getTitle());
        assertEquals("Movie2", result.get(1).getTitle());
        assertEquals("Movie3", result.get(2).getTitle());
    }

    @Test
    public void testSortByTitleDescending() {
        ArrayList<Movie> movies = testMovies();
        ArrayList<Movie> result = MovieDatabase.sortByTitleDescending(movies);

        assertEquals("Movie3", result.get(0).getTitle());
        assertEquals("Movie2", result.get(1).getTitle());
        assertEquals("Movie1", result.get(2).getTitle());
    }

    @Test
    public void testSortByYearAscending() {
        ArrayList<Movie> movies = testMovies();
        ArrayList<Movie> result = MovieDatabase.sortByYearAscending(movies);

        assertEquals(2000, result.get(0).getReleaseYear());
        assertEquals(2005, result.get(1).getReleaseYear());
        assertEquals(2010, result.get(2).getReleaseYear());
    }

    @Test
    public void testSortByYearDescending() {
        ArrayList<Movie> movies = testMovies();
        ArrayList<Movie> result = MovieDatabase.sortByYearDescending(movies);

        assertEquals(2010, result.get(0).getReleaseYear());
        assertEquals(2005, result.get(1).getReleaseYear());
        assertEquals(2000, result.get(2).getReleaseYear());
    }

    @Test
    public void testSortByDirectorAscending() {
        ArrayList<Movie> movies = testMovies();
        ArrayList<Movie> result = MovieDatabase.sortByDirectorAscending(movies);

        assertEquals("Director1", result.get(0).getDirector());
        assertEquals("Director1", result.get(1).getDirector());
        assertEquals("Director2", result.get(2).getDirector());
    }

    @Test
    public void testSortByDirectorDescending() {
        ArrayList<Movie> movies = testMovies();
        ArrayList<Movie> result = MovieDatabase.sortByDirectorDescending(movies);

        assertEquals("Director2", result.get(0).getDirector());
        assertEquals("Director1", result.get(1).getDirector());
        assertEquals("Director1", result.get(2).getDirector());
    }

    @Test
    public void testCalculateTotalWatchTime() {
        ArrayList<Movie> movies = testMovies();
        int result = MovieDatabase.calculateTotalWatchTime(movies);

        assertEquals(330, result);
    }


    @Test
    public void testLoadFromFile() {
        Movie movie1=new Movie("Movie1", "Director1", 2000, 120);
        Movie movie2=new Movie("Movie2", "Director2", 2005, 110);
        Movie movie3=new Movie("Movie3", "Director3", 2010, 100);
        ArrayList<Movie> expectedMovies = new ArrayList<>(Arrays.asList(movie1,movie2,movie3));

        File file=new File("moviesTestCase2_database.txt");
        MovieDatabase.saveToFile(movie1,file);
        MovieDatabase.saveToFile(movie2,file);
        MovieDatabase.saveToFile(movie3,file);

        ArrayList<Movie> loadedMovies = MovieDatabase.loadFromFile("moviesTestCase2_database.txt");

        assertEquals(expectedMovies.size(), loadedMovies.size());
        for (int i = 0; i < expectedMovies.size(); i++) {
            assertEquals(expectedMovies.get(i).getTitle(), loadedMovies.get(i).getTitle());
            assertEquals(expectedMovies.get(i).getDirector(), loadedMovies.get(i).getDirector());
            assertEquals(expectedMovies.get(i).getReleaseYear(), loadedMovies.get(i).getReleaseYear());
            assertEquals(expectedMovies.get(i).getRunningTime(), loadedMovies.get(i).getRunningTime());
        }
        file.delete();
    }
}
