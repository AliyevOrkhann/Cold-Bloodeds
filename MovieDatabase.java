import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class MovieDatabase {
    private static ArrayList<Movie> movies;

    public MovieDatabase() {
        //this.movies = new ArrayList<>();
        MovieDatabase.movies = loadFromFile("movies_database.txt");
    }
    

    public ArrayList<Movie> getMovies() {
        return movies;
    }
   

    public static void addMovie(Movie m) {
        if(movies.contains(m)) {
            System.out.println("----------Movie: " + m.getTitle() + "is already in the database------");
        } else {
            movies.add(m);
            saveToFile(m);
        }
    }

    public static void saveToFile(Movie m) {
        File path = new File("movies_database.txt");
        try (FileWriter fw = new FileWriter(path, true)) {
            fw.write("Title: " + m.getTitle() + "\n" + "Director: " + m.getDirector() + "\n" + "Release Year: " + 
            m.getReleaseYear() + "\n" + "Running Time: " + m.getRunningTime() + " minutes" + "\n\n");
        } catch (Exception e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }

    public void retrieveMovie(Movie m){
        System.out.println("Title: "+m.getTitle());
        System.out.println("Director: "+m.getDirector());
        System.out.println("Release Year: "+m.getReleaseYear());
        System.out.println("Running Time: "+m.getRunningTime()+" minutes");
    }
  
    public void removeMovie(Movie m){
        movies.remove(m);
        File path=new File("movies_database.txt");
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

    public static ArrayList<Movie> getMoviesStartingFromYear(int year, ArrayList<Movie> movies){
        var movieFrom = movies.stream()
                        .filter(n->n.getReleaseYear()>=year)
                        .collect(Collectors.toCollection(ArrayList::new));
        if(movieFrom.isEmpty())throw new NoSuchElementException("No Movie starting from "+year+" is found in the database");
        return movieFrom;
    }

    public static ArrayList<Movie> getMoviesFromYear(int year, ArrayList<Movie> movies) {
        var movieFrom = movies.stream()
                        .filter(n->n.getReleaseYear()==year)
                        .collect(Collectors.toCollection(ArrayList::new));
        if(movieFrom.isEmpty())throw new NoSuchElementException("No Movie from "+year+" is found in the database");
        return movieFrom;
    }    

    public static ArrayList<Movie> getMovieByTitle(String title, ArrayList<Movie> movies){
        var movieFrom = movies.stream()
                        .filter(n->n.getTitle().equals(title))
                        .collect(Collectors.toCollection(ArrayList::new));
        if(movieFrom.isEmpty())throw new NoSuchElementException("No Movie named "+title+" is found in the database");
        return movieFrom;
    }

    public static ArrayList<Movie> getMovieByDirector(String director, ArrayList<Movie> movies){
        var movieFrom = movies.stream()
                        .filter(n->n.getDirector().equals(director))
                        .collect(Collectors.toCollection(ArrayList::new));
        if(movieFrom.isEmpty())throw new NoSuchElementException("No Movie directed by "+director+" is found in the database");
        return movieFrom;
    }

    public static ArrayList<Movie> sortByTitleAscending(ArrayList<Movie> movies){
        return movies.stream()
                .sorted((m1,m2)-> (m1.getTitle()).compareTo(m2.getTitle()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static ArrayList<Movie> sortByTitleDescending(ArrayList<Movie> movies){
        return movies.stream()
                .sorted((m1,m2)-> (m2.getTitle()).compareTo(m1.getTitle()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static ArrayList<Movie> sortByYearAscending(ArrayList<Movie> movies){
        return movies.stream()
                .sorted((m1,m2)-> Integer.compare(m1.getReleaseYear(), m2.getReleaseYear()))
                .collect(Collectors.toCollection(ArrayList::new));
    }    

    public static ArrayList<Movie> sortByYearDescending(ArrayList<Movie> movies){
        return movies.stream()
                .sorted((m1,m2)-> Integer.compare(m2.getReleaseYear(), m1.getReleaseYear()))
                .collect(Collectors.toCollection(ArrayList::new));
    }    

    public static ArrayList<Movie> sortByDirectorAscending(ArrayList<Movie> movies){
        return movies.stream()
                .sorted((m1,m2)-> (m1.getDirector()).compareTo(m2.getDirector()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static ArrayList<Movie> sortByDirectorDescending(ArrayList<Movie> movies){
        return movies.stream()
                .sorted((m1,m2)-> (m2.getDirector()).compareTo(m1.getDirector()))
                .collect(Collectors.toCollection(ArrayList::new));
    }



    public static int calculateTotalWatchTime(List<Movie> movies) {
        return movies.stream()
                .mapToInt(Movie::getRunningTime)
                .sum();
    }




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