/**
 * The Movie class represents a movie with basic attributes such as title, director, release year, and running time.
 * It includes methods for accessing movie details, setting release year and running time, and checking equality.
 */

public class Movie{
    private String title;
    private String director;
    private int releaseYear;
    private int runningTime;

    /**
     * Constructs a Movie object with the specified title, director, release year, and running time.
     *
     * @param title       The title of the movie.
     * @param director    The director of the movie.
     * @param releaseYear The release year of the movie.
     * @param runningTime The running time of the movie in minutes.
     */
    public Movie(String title, String director, int releaseYear, int runningTime) {
        this.title = title;
        this.director = director;
        setReleaseYear(releaseYear);
        setRunningTime(runningTime);
    }

    /**
     * Gets the title of the movie.
     *
     * @return The title of the movie.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the director of the movie.
     *
     * @return The director of the movie.
     */
    public String getDirector() {
        return director;
    }

    /**
     * Gets the release year of the movie.
     *
     * @return The release year of the movie.
     */
    public int getReleaseYear() {
        return releaseYear;
    }

    /**
     * Gets the running time of the movie in minutes.
     *
     * @return The running time of the movie.
     */
    public int getRunningTime() {
        return runningTime;
    }

    private void setReleaseYear(int releaseYear) {
        if(releaseYear<1888 || releaseYear>2024)throw new IllegalArgumentException("The release year cannot be "+releaseYear);
        this.releaseYear = releaseYear;
    }
    private void setRunningTime(int runningTime) {
        if(runningTime<=0)throw new IllegalArgumentException("The running time cannot be zero or negative number");
        this.runningTime = runningTime;
    }
    
    /**
     * Returns a string representation of the movie, including title, director, release year, and running time.
     *
     * @return A string representation of the movie.
     */
    @Override
    public String toString() {
        return "Movie [title=" + title + ", director=" + director + ", releaseYear=" + releaseYear + ", runningTime="
                + runningTime + "]";
    }

    /**
     * Checks if this Movie object is equal to another object.
     *
     * @param obj The object to compare to.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if(this==obj)return true;
        Movie other = (Movie) obj;
        if (!title.equals(other.title))
            return false;
        if (!director.equals(other.director))
            return false;
        if (releaseYear != other.releaseYear)
            return false;
        if (runningTime != other.runningTime)
            return false;
        return true;
    }

    /**
     * Gets a formatted string with detailed information about the movie.
     *
     * @return A string with details about the movie.
     */
    public String getDetails() {
        return "Title: " + title + 
        "\nDirector: " + director + 
        "\nRelease Year: " + releaseYear +
        "\nRunning Time: " + runningTime + " minutes\n";

    }
    
}