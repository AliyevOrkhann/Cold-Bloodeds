import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.LinkedHashSet;

public class MovieDatabase {
    static LinkedHashSet<Movie> movies=new LinkedHashSet<>();
    
    public static void addMovie(Movie m){
        movies.add(m);
        File path=new File("movies_database.txt");
        try (FileWriter fw=new FileWriter(path, true)) {
            fw.write("Title: " +m.getTitle()+"\n" + "Director: " + m.getDirector()+"\n" + "ReleaseYear: " + m.getReleaseYear() +"\n"+"Running Time: "
            + m.getRunningTime() +" minutes"+"\n"+"\n");
            
        } catch (Exception e) {
            System.out.println("Something went wrong: "+e.getMessage());
        } 
    }

    public static void addMovie(String title, String director, int releaseYear, int runningTime){
        Movie m=new Movie(title, director, releaseYear, runningTime);
        movies.add(m);

        File path=new File("movies_database.txt");
        try (FileWriter fw=new FileWriter(path, true)) {
            fw.write("Title: " + title+"\n" + "Director: " + director+"\n" + "ReleaseYear: " + releaseYear +"\n"+"Running Time: "
            + runningTime +" minutes"+"\n"+"\n");

        } catch (Exception e) {
            System.out.println("Something went wrong: "+e.getMessage());
        } 
    }

    public static void retrieveMovie(Movie m){
        System.out.println("Title: "+m.getTitle());
        System.out.println("Director: "+m.getDirector());
        System.out.println("Release Year: "+m.getReleaseYear());
        System.out.println("Running Time: "+m.getRunningTime()+" minutes");
    }

}
