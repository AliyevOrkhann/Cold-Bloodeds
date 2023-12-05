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
    static ArrayList<Movie> movies=new ArrayList<>();
    
   

    public static void addMovie(Movie m) {
        if(ifExist(m)) {
            System.out.println("----------Movie: " + m.getTitle() + "is already in the database------");
        } else {
            movies.add(m);
            saveToFile(m);
        }
    }

    private static void saveToFile(Movie m) {
        File path = new File("movies_database.txt");
        try (FileWriter fw = new FileWriter(path, true)) {
            fw.write("Title: " + m.getTitle() + "\n" + "Director: " + m.getDirector() + "\n" + "ReleaserYear: " + 
            m.getReleaseYear() + "\n" + "Running Time: " + m.getRunningTime() + " minutes" + "\n" + "\n");
        } catch (Exception e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }
  
    public static void retrieveMovie(Movie m){
        System.out.println("Title: "+m.getTitle());
        System.out.println("Director: "+m.getDirector());
        System.out.println("Release Year: "+m.getReleaseYear());
        System.out.println("Running Time: "+m.getRunningTime()+" minutes");
    }
  
    public static void removeMovie(Movie m){
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

    private static boolean ifExist(Movie m) {
        for (Movie movie : movies) {
            if (movie.getTitle().equals(m.getTitle())) {
                return true;
            }
        }
        return false;
    }

    private static List<Movie> getMoviesFromYear(int year){
        var movieFrom = movies.stream()
                        .filter(n->n.getReleaseYear()==year)
                        .collect(Collectors.toList());
        if(movieFrom.isEmpty())throw new NoSuchElementException("No Movie from "+year+" is found in the database");
        return movieFrom;
    }

    public static MovieDatabase loadFromFile(String fileName) {
        MovieDatabase movieDatabase = new MovieDatabase();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while((line = reader.readLine()) != null) {
                    String title = null, director = null;
                    int releaseYear = 0, runningTime = 0;

                if(line.startsWith("Title: ")) {
                    if (line.startsWith("Title: ")) {
                        title = line.substring(7);
                    } else if (line.startsWith("Director: ")) {
                        director = line.substring(11);
                    } else if (line.startsWith("ReleaseYear: ")) {
                        releaseYear = Integer.parseInt(line.substring(13));
                    } else if (line.startsWith("Running Time: ")) {
                        runningTime = Integer.parseInt(line.substring(14).split(" ")[0]);

                        Movie movie = new Movie(title, director, releaseYear, runningTime);
                        MovieDatabase.addMovie(movie);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error ocurred while loading from file: " + e.getMessage());
        }
        return movieDatabase;
    }

    public List<Movie> getMovies() {
        return movies;
    }
}