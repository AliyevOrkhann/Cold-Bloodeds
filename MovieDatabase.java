import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * The MovieDatabase class represents a database for managing a collection of movies.
 * It provides methods for adding/removing movies, retrieving movie details, and performing various operations using streams.
 * The movie data is loaded from and saved to a file for persistence.
 */
public class MovieDatabase {
    private static ArrayList<Movie> movies;

    /**
     * Constructs a MovieDatabase object. Loads movies from the file "movies_database.txt" during initialization.
     */
    public MovieDatabase() {
        MovieDatabase.movies = loadFromFile("movies_database.txt");
    }
 

    /**
     * Gets the list of movies in the database.
     *
     * @return The list of movies.
     */
    public ArrayList<Movie> getMovies() {
        return movies;
    }
   
    /**
     * Adds a movie to the database. If the movie already exists, it prints a message indicating that the movie is already in the database.
     *
     * @param m The movie to be added.
     */
    public static void addMovie(Movie m) {
        if(movies.contains(m)) {
            throw new AlreadyInListException("Movie: "+m.getTitle()+" is already in database");
        } else {
            movies.add(m);
            saveToFile(m);
        }
    }

    /**
     * Saves a movie to the file "movies_database.txt".
     *
     * @param m The movie to be saved.
     */
    public static void saveToFile(Movie m) {
        File path = new File("movies_database.txt");
        try (FileWriter fw = new FileWriter(path, true)) {
            fw.write("Title: " + m.getTitle() + "\n" + "Director: " + m.getDirector() + "\n" + "Release Year: " + 
            m.getReleaseYear() + "\n" + "Running Time: " + m.getRunningTime() + " minutes" + "\n\n");
        } catch (Exception e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }

    /**
     * Saves a movie to the file "file".
     *
     * @param m The movie to be saved.
     */
    // Test Case
    public static void saveToFile(Movie m, File file) {
        try (FileWriter fw = new FileWriter(file, true)) {
            fw.write("Title: " + m.getTitle() + "\n" + "Director: " + m.getDirector() + "\n" + "Release Year: " + 
            m.getReleaseYear() + "\n" + "Running Time: " + m.getRunningTime() + " minutes" + "\n\n");
        } catch (Exception e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }

    /**
     * Retrieves and prints details of a given movie.
     *
     * @param m The movie to retrieve details for.
     */
    public void retrieveMovie(Movie m){
        System.out.println("Title: "+m.getTitle());
        System.out.println("Director: "+m.getDirector());
        System.out.println("Release Year: "+m.getReleaseYear());
        System.out.println("Running Time: "+m.getRunningTime()+" minutes");
    }
  
    /**
     * Removes a movie from the database and updates the file "movies_database.txt" accordingly.
     *
     * @param m The movie to be removed.
     */
    public static void removeMovie(Movie m){
        movies.remove(m);
        updateFile("movie_database.txt",m);
    }

    private static void updateFile(String filePath, Movie m) {
        File path = new File(filePath);
        List<String> lines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("Title: " + m.getTitle())) {
                    // Skip the next 4 lines
                    for (int i = 0; i < 4; i++) {
                        br.readLine();
                    }
                } else {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }

        try (FileWriter fw = new FileWriter(path)) {
            for (String line : lines) {
                fw.write(line + "\n");
            }
        } catch (IOException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }


     /**
     * Provides advanced operations for filtering movies based on release year.
     * Retrieves a list of movies released on or after the specified year.
     *
     * @param year   The minimum release year to filter movies.
     * @param movies The list of movies to filter.
     * @return A filtered list of movies.
     * @throws NoSuchElementException If no movie from the specified year is found in the database.
     */

    public static ArrayList<Movie> getMoviesStartingFromYear(int year, ArrayList<Movie> movies){
        var movieFrom = movies.stream()
                        .filter(n->n.getReleaseYear()>=year)
                        .collect(Collectors.toCollection(ArrayList::new));
        if(movieFrom.isEmpty())throw new NoSuchElementException("No Movie starting from "+year+" is found in the database");
        return movieFrom;
    }

    /**
     * Retrieves a list of movies released from the specified year.
     *
     * @param year   The minimum release year to filter movies.
     * @param movies The list of movies to filter.
     * @return A filtered list of movies.
     * @throws NoSuchElementException If no movie from the specified year is found in the database.
     */
    public static ArrayList<Movie> getMoviesFromYear(int year, ArrayList<Movie> movies) {
        var movieFrom = movies.stream()
                        .filter(n->n.getReleaseYear()==year)
                        .collect(Collectors.toCollection(ArrayList::new));
        if(movieFrom.isEmpty())throw new NoSuchElementException("No Movie from "+year+" is found in the database");
        return movieFrom;
    }    

    /**
     * Retrieves a list of movies with the specified title.
     *
     * @param title  The title to filter movies.
     * @param movies The list of movies to filter.
     * @return A filtered list of movies.
     * @throws NoSuchElementException If no movie with the specified title is found in the database.
     */
    public static ArrayList<Movie> getMovieByTitle(String title, ArrayList<Movie> movies){
        var movieFrom = movies.stream()
                        .filter(n->n.getTitle().equals(title))
                        .collect(Collectors.toCollection(ArrayList::new));
        if(movieFrom.isEmpty())throw new NoSuchElementException("No Movie named "+title+" is found in the database");
        return movieFrom;
    }

    /**
     * Retrieves a list of movies directed by the specified director.
     *
     * @param director The director's name to filter movies.
     * @param movies   The list of movies to filter.
     * @return A filtered list of movies.
     * @throws NoSuchElementException If no movie directed by the specified director is found in the database.
     */
    public static ArrayList<Movie> getMovieByDirector(String director, ArrayList<Movie> movies){
        var movieFrom = movies.stream()
                        .filter(n->n.getDirector().equals(director))
                        .collect(Collectors.toCollection(ArrayList::new));
        if(movieFrom.isEmpty())throw new NoSuchElementException("No Movie directed by "+director+" is found in the database");
        return movieFrom;
    }

    /**
     * Sorts movies in ascending order based on their titles.
     *
     * @param movies The list of movies to be sorted.
     * @return A sorted list of movies.
     */
    public static ArrayList<Movie> sortByTitleAscending(ArrayList<Movie> movies){
        return movies.stream()
                .sorted((m1,m2)-> (m1.getTitle()).compareTo(m2.getTitle()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Sorts movies in descending order based on their titles.
     *
     * @param movies The list of movies to be sorted.
     * @return A sorted list of movies.
     */
    public static ArrayList<Movie> sortByTitleDescending(ArrayList<Movie> movies){
        return movies.stream()
                .sorted((m1,m2)-> (m2.getTitle()).compareTo(m1.getTitle()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Sorts movies in ascending order based on their release years.
     *
     * @param movies The list of movies to be sorted.
     * @return A sorted list of movies.
     */
    public static ArrayList<Movie> sortByYearAscending(ArrayList<Movie> movies){
        return movies.stream()
                .sorted((m1,m2)-> Integer.compare(m1.getReleaseYear(), m2.getReleaseYear()))
                .collect(Collectors.toCollection(ArrayList::new));
    }    

    /**
     * Sorts movies in descending order based on their release years.
     *
     * @param movies The list of movies to be sorted.
     * @return A sorted list of movies.
     */
    public static ArrayList<Movie> sortByYearDescending(ArrayList<Movie> movies){
        return movies.stream()
                .sorted((m1,m2)-> Integer.compare(m2.getReleaseYear(), m1.getReleaseYear()))
                .collect(Collectors.toCollection(ArrayList::new));
    }    

    /**
     * Sorts movies in ascending order based on their directors' names.
     *
     * @param movies The list of movies to be sorted.
     * @return A sorted list of movies.
     */
    public static ArrayList<Movie> sortByDirectorAscending(ArrayList<Movie> movies){
        return movies.stream()
                .sorted((m1,m2)-> (m1.getDirector()).compareTo(m2.getDirector()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Sorts movies in descending order based on their directors' names.
     *
     * @param movies The list of movies to be sorted.
     * @return A sorted list of movies.
     */
    public static ArrayList<Movie> sortByDirectorDescending(ArrayList<Movie> movies){
        return movies.stream()
                .sorted((m1,m2)-> (m2.getDirector()).compareTo(m1.getDirector()))
                .collect(Collectors.toCollection(ArrayList::new));
    }



    /**
     * Calculates the total watch time of the movies in the given list.
     *
     * @param movies The list of movies.
     * @return The total watch time in minutes.
     */
    public static int calculateTotalWatchTime(List<Movie> movies) {
        return movies.stream()
                .mapToInt(Movie::getRunningTime)
                .sum();
    }



    /**
     * Loads movies from a file and returns a list of Movie objects.
     *
     * @param fileName The name of the file to load movies from.
     * @return The list of loaded movies.
     */
    public static ArrayList<Movie> loadFromFile(String fileName) {
        ArrayList<Movie> loadedMovies = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            String title = null, director = null;
            int releaseYear = 0, runningTime = 0;
            while((line = reader.readLine()) != null) {
                    if (line.startsWith("Title: ")) {
                        title = line.substring(7);
                    } else if (line.startsWith("Director: ")) {
                        director = line.substring(10);
                    } else if (line.startsWith("Release Year: ")) {
                        releaseYear = Integer.parseInt(line.substring(14));
                    } else if (line.startsWith("Running Time: ")) {
                        runningTime = Integer.parseInt(line.substring(14).split(" ")[0]);

                        Movie movie = new Movie(title, director, releaseYear, runningTime);
                        loadedMovies.add(movie);

                        title = null;
                        director = null;
                        releaseYear = 0;
                        runningTime = 0;
                    }
    
            }
        } catch (IOException e) {
            System.out.println("Error ocurred while loading from file: " + e.getMessage());
        }
        return loadedMovies;
    }
    
}