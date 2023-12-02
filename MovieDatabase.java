import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class MovieDatabase {
    static ArrayList<Movie> movies=new ArrayList<>();
    
    public static void addMovie(Movie m){
        if(ifExist(m))System.out.println("----------Movie: "+m.getTitle()+" is already in the database-----------");
        else{
            movies.add(m);
            File path=new File("movies_database.txt");
            try (FileWriter fw=new FileWriter(path, true)) {
                fw.write("Title: " +m.getTitle()+"\n" + "Director: " + m.getDirector()+"\n" + "ReleaseYear: " + m.getReleaseYear() +"\n"+"Running Time: "
                + m.getRunningTime() +" minutes"+"\n"+"\n");
                
            } catch (Exception e) {
                System.out.println("Something went wrong: "+e.getMessage());
            } 
        }
    }

    public static void addMovie(String title, String director, int releaseYear, int runningTime){
        Movie m=new Movie(title, director, releaseYear, runningTime);
        if(ifExist(m))System.out.println("----------Movie: "+m.getTitle()+" is already in the database-----------");
        else{
            movies.add(m);
            File path=new File("movies_database.txt");
            try (FileWriter fw=new FileWriter(path, true)) {
                fw.write("Title: " + title+"\n" + "Director: " + director+"\n" + "ReleaseYear: " + releaseYear +"\n"+"Running Time: "
                + runningTime +" minutes"+"\n"+"\n");

            } catch (Exception e) {
                System.out.println("Something went wrong: "+e.getMessage());
            } 
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
        File path=new File("movies_database.txt");
        try (BufferedReader br=new BufferedReader(new FileReader(path))) {
            String line;
            while((line=br.readLine())!=null){
               if((line.equals("Title: " + m.getTitle()))){
                    return true;
               }
            }
            return false;
        } catch (Exception e) {
            System.out.println("Something went wrong: "+e.getMessage());
            return false;
        } 
    }

    public static MovieDatabase loadFromFile(String string) {
        return null;
    }

    public List<Movie> getMovies(Object object) {
        return null;
    }
}


