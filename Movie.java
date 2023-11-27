public class Movie{
    private String title;
    private String director;
    private int releaseYear;
    private int runningTime;

    public Movie(String title, String director, int releaseYear, int runningTime) {
        this.title = title;
        this.director = director;
        setReleaseYear(releaseYear);
        setRunningTime(runningTime);
    }


    public String getTitle() {
        return title;
    }
    public String getDirector() {
        return director;
    }
    public int getReleaseYear() {
        return releaseYear;
    }
    public int getRunningTime() {
        return runningTime;
    }

    private void setReleaseYear(int releaseYear) {
        if(releaseYear<1888 || releaseYear>2024)throw new IllegalArgumentException("The release year cannot be "+releaseYear);
        this.releaseYear = releaseYear;
    }
    private void setRunningTime(int runningTime) {
        if(runningTime<=0)throw new IllegalArgumentException("The running time cannot be negative number");
        this.runningTime = runningTime;
    }
    

    @Override
    public String toString() {
        return "Movie [title=" + title + ", director=" + director + ", releaseYear=" + releaseYear + ", runningTime="
                + runningTime + "]";
    }

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
    
}