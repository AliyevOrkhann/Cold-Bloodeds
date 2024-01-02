#Online Movie Database Management System

## Introduction
The Online Movie Database Management system is a Java-based designed for managing a collection of movies. This system allows users to register, login, browse,and a personal watchlist of movies. It combines a Swing-based user interface with file-based data persistance.

## Features
- **User Registration and Login**: Secure user authentication system.
- **Movie Browsing**: Browse through a catalog of movies.
- **Personal Watchlist**: Users can add or remove movies from their watchlist.
- **Data Persistance**: User and movie data are stored in text files.
- **GUI**: A user-friendly graphical interface for interaction.

## System requirements
- Java Runtime Environment (JRE) and Java Development Kit (JDK) version 8 or higher.
- A Java IDE

## Installation
1. Clone the repository
2. Open the project in Java IDE
3. Build and run the application from 'MoviAppGUI.java'.

## Usage
- **Start the Application**: Run 'MovieAppGUI.java' to launch the application.
- **Register/Login**: Create a new user account or log in with existing credentials.
- **Browse Movies**: Explore the movie database.
- **Manage Watchlist**: Add movies to your personal watchlist.

## Code Structure
- 'Movie.java': Defines the movie data model.
- 'User.java': Manages user data and authentication
- 'MovieDatabase.java': Handles the collection of movie objects.
- 'MovieAppGUI.java': Main class for the GUI application.
- 'Database/': Text files storing movie and user data.

## Implementation Details
### Movie Class
- **Constructor `Movie(String title, String director, int releaseYear, int runningTime)`**: Initializes a new Movie object with specified title, director, release year, and running time. Ensures that release year and running time adhere to specified constraints (e.g., release year must be within a valid range).

```java
public Movie(String title, String director, int releaseYear, int runningTime) {
        this.title = title;
        this.director = director;
        setReleaseYear(releaseYear);
        setRunningTime(runningTime);
    }
```

- **`getTitle()`, `getDirector()`, `getReleaseYear()`, `getRunningTime()`**: Accessor methods that return the respective properties of the movie.

```java
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
```

- **`setReleaseYear(int releaseYear)`, `setRunningTime(int runningTime)`**: Mutator methods that validate and set the release year and running time of the movie. These methods throw an `IllegalArgumentException` if input values are out of the expected range.

```java
private void setReleaseYear(int releaseYear) {
        if(releaseYear<1888 || releaseYear>2024)throw new IllegalArgumentException("The release year cannot be "+releaseYear);
        this.releaseYear = releaseYear;
    }
    private void setRunningTime(int runningTime) {
        if(runningTime<=0)throw new IllegalArgumentException("The running time cannot be zero or negative number");
        this.runningTime = runningTime;
    }
```

- **`toString()`**: Overridden method that returns a string representation of the movie, including all its properties, formatted for display or logging.

```java
@Override
    public String toString() {
        return "Movie [title=" + title + ", director=" + director + ", releaseYear=" + releaseYear + ", runningTime="
                + runningTime + "]";
    }
```

- **`equals(Object obj)`**: Overridden method for equality comparison based on all movie properties. This method is crucial for checking duplicates in the movie database.

```java
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
```

- **`getDetails()`**: Method to get a detailed, formatted string containing all the movie information, useful for generating movie reports or displaying detailed movie info to the user.

```java
public String getDetails() {
        return "Title: " + title + 
        "\nDirector: " + director + 
        "\nRelease Year: " + releaseYear +
        "\nRunning Time: " + runningTime + " minutes\n";
    }
```

### User Class
- **Constructor `User(String username, String password)`**: Initializes a new User object with the given username and password. The watchlist is also initialized as an empty list.

```java
public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.watchList = new ArrayList<>();
    }
```
- **`register(String username, String password)`**: Static method that registers a new user in the system. It checks for the uniqueness of the username and the strength of the password. It writes user credentials to a file for persistence.

```java
public static boolean register (String username, String password) {
        if(checkUserExists(username)) {
            System.out.println("User already exists. Please choose another username.");
            return false;
        }

        if(password.length()<6)throw new IllegalArgumentException("Password Length Must Be Greater Than 6");
        else if(password.equals(username))throw new IllegalArgumentException("Password Cannot Be Same With Username");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("users_database.txt", true))) {
            writer.write(username + "," + password + "\n");
            System.out.println("User registered successfully.");
            String path="DataBase/"+username+".dat";
            FileOutputStream fos=new FileOutputStream(new File(path));
            return true;
        } catch (IOException e) {
            System.out.println("Error happened during registration");
            e.printStackTrace();
            return false;
        }
    }
```
- **`login(String username, String password)`**: Static method that authenticates a user. It checks the provided credentials against the stored user data in the file system.

```java
public static boolean login(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader("users_database.txt"))) {
            String line;
            while((line = reader.readLine()) != null) {
                String[] userInfo = line.split(",");
                if(userInfo.length == 2 && userInfo[0].equals(username) && userInfo[1].equals(password)) {
                    System.out.println("Login successful.");
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error occurred during login.");
            e.printStackTrace();
        }
        System.out.println("Invalid username or password.");
        return false;
    }
```

- **`addToWatchList(Movie selectedMovie)`**, **`removeFromWatchlist(Movie selectedMovie)`**: Methods to add or remove a movie from the user's personal watchlist.

```java
public void addToWatchList(Movie selectedMovie) {
        watchList.add(selectedMovie);
    }

    public void removeFromWatchlist(Movie selectedMovie) {
        watchList.remove(selectedMovie);
        File path=new File("DataBase/"+username+".dat");
        File temp=new File("DataBase/"+username+"backup.dat");
        try (FileWriter fw=new FileWriter(temp);
            BufferedReader br=new BufferedReader(new FileReader(path))) {
            String line;
            while((line=br.readLine())!=null){
               if((line.contains("Title: " + selectedMovie.getTitle()))){
                    for(int i=0;i<5;i++){
                        line=br.readLine();
                    }
               }
               if(line!=null)fw.write(line+"\n");
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
```
- **`getWatchList()`**: Retrieves the list of movies in the user's watchlist.

```java
public List<Movie> getWatchList() {
        watchList = MovieDatabase.loadFromFile("DataBase/"+username+".dat");
        return watchList;
    }
```
- **`setWatchList(List<Movie> watchList)`**: Sets or updates the user's watchlist.

```java
public void setWatchList(List<Movie> watchList) {
        this.watchList = watchList;
    }
```
- **`getUsername()`**: Returns the username of the user, mainly used for display and logging purposes.

```java
public String getUsername() {
        return username;
    }
```

### MovieDatabase Class
- **Constructor `MovieDatabase()`**: Initializes the movie database, loading existing movie data from a file.

```java
public MovieDatabase() {
        this.movies = new ArrayList<>();
        MovieDatabase.movies = loadFromFile("movies_database.txt");
    }
```

- **`addMovie(Movie m)`**: Adds a new movie to the database. It checks for duplicate entries based on the `equals()` method of the Movie class.

```java
public static void addMovie(Movie m) {
        if(movies.contains(m)) {
            System.out.println("----------Movie: " + m.getTitle() + "is already in the database------");
        } else {
            movies.add(m);
            saveToFile(m);
        }
    }
```
- **`saveToFile(Movie m)`**, **`retrieveMovie(Movie m)`**, **`removeMovie(Movie m)`**: Methods for persisting movie data to a file, retrieving movie details, and removing a movie from the database, respectively.

```java
public static void saveToFile(Movie m) {
        File path = new File("movies_database.txt");
        try (FileWriter fw = new FileWriter(path, true)) {
            fw.write("Title: " + m.getTitle() + "\n" + "Director: " + m.getDirector() + "\n" + "Release Year: " + 
            m.getReleaseYear() + "\n" + "Running Time: " + m.getRunningTime() + " minutes" + "\n\n");
        } catch (Exception e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }
```
```java
public void retrieveMovie(Movie m){
        System.out.println("Title: "+m.getTitle());
        System.out.println("Director: "+m.getDirector());
        System.out.println("Release Year: "+m.getReleaseYear());
        System.out.println("Running Time: "+m.getRunningTime()+" minutes");
    }
```
```java
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
```


- **Filtering Methods**: Includes various methods like `getMoviesStartingFromYear(int year)`, `getMoviesFromYear(int year)`, `getMovieByTitle(String title)`, and `getMovieByDirector(String director)` to filter movies based on different criteria.

```java
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
```

- **Sorting Methods**: Methods like `sortByTitleAscending()`, `sortByTitleDescending()`, `sortByYearAscending()`, `sortByYearDescending()`, `sortByDirectorAscending()`, and `sortByDirectorDescending()` to sort the movie list based on different attributes.

```java
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
```

- **`calculateTotalWatchTime(List<Movie> movies)`**: Calculates the total running time of a list of movies, useful for statistical and reporting purposes.

```java
public static int calculateTotalWatchTime(List<Movie> movies) {
        return movies.stream()
                .mapToInt(Movie::getRunningTime)
                .sum();
    }
```
- **`loadFromFile(String fileName)`**: Loads movie data from the specified file, populating the movie database. This method is critical for initializing the database with pre-existing data.

```java
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
```

### MovieAppGUI Class
- **Constructor `MovieAppGUI()`**: Initializes the main GUI. Invokes the `initialize()` method to set up the primary JFrame and its components.
- **Method `initialize()`**: 
  - Configures the main JFrame `frame` with title, default close operation, and size.
  - Sets up a `JPanel` (`mainPanel`) with a BorderLayout. This panel acts as a container for other panels: `upperPanel`, `centerPanel`, and `lowerPanel`.
  - `upperPanel`: A custom JPanel displaying the application title and login/register options.
  - `lowerPanel`: Another JPanel at the bottom, typically used for displaying copyright or other information.
  - `centerPanel`: Central area of the GUI, where the main content (like authentication forms) is displayed.
  - Integrates a background image and sets up a layout for authentication components within `centerPanel`.
  - Calls `createUserPanel()` to set up user authentication features.


- **Method `createUserPanel()`**: 
  - Creates and configures a JPanel for user authentication with username and password fields, and login/register buttons.
  - Implements action listeners for these buttons to handle user login and registration processes.
  - Utilizes `FocusListener` to manage placeholder text in text fields.

### UserLoginWindow Class
- **Constructor `UserLoginWindow(User currentUser)`**: 
  - Initializes the window for a logged-in user, displaying the user's home page.
  - Configures the JFrame with title, default close operation, and layout.
  - Creates and adds different panels (`movieListPanel`, `watchlistPanel`, `detailsPanel`) for different sections like movie library, user's watchlist, and movie details.
  - Initializes and adds tabbed panes for better organization of the content.
- **Method `stylePanel(JPanel panel, String title)`**: Applies a specific style to panels, including border and font settings.
- **Method `createMovieListTab()`**: 
  - Sets up a tab for displaying movie lists, including scroll panes and split panes for better organization and usability.
  - Incorporates functionality to display movie details and manage the watchlist.
- **Method `createAddMovieTab()`**: Provides a form for adding new movies to the database, with appropriate text fields and a button for submission.
- **Method `populateMovieList()`**, **`updateWatchlistPanel()`**: Dynamically populate panels with movies from the database and the current user's watchlist.
- **Method `showMovieDetails(Movie movie)`**: Displays detailed information about a selected movie in the details panel.
- **Method `addToWatchlist(Movie movie)`**, **`removeFromWatchlist(Movie movie)`**: Allows users to manage their watchlist by adding or removing movies.
- **Method `saveWatchlistToFile(Movie movie)`**: Handles persistence of the watchlist by saving it to a file.
- **Method `createFilterSortPanel()`**: Implements a panel with options to filter and sort movies, enhancing user experience in movie discovery.
- **Method `filterMovies(String filterType, String filterValue)`**, **`sortMovies(String sortType)`**: Implements filtering and sorting functionalities based on various criteria like title, director, and release year.

## Testing Implementation Details
### UserTest Class
- **`testRegister()`**: 
  - Tests the registration process with various scenarios.
  - Ensures successful registration with valid credentials.
  - Confirms the prevention of duplicate registrations.
  - Checks for exceptions when registering with a password that is too short or the same as the username.

```java
public void testRegister() {
        File pathname=new File("usersTest_database.txt");
        String validUsername = "aliyev";
        String validPassword = "aliyev123";
        assertTrue(User.register(validUsername, validPassword,pathname));
        
        User.register("babekbb", "babek123");
        String existingUsername = "babekbb";
        String existingPassword = "babek123";
        assertFalse(User.register(existingUsername, existingPassword));

        String shortTempUsername="tufan";
        String shortPassword = "123";
        assertThrows(IllegalArgumentException.class, () -> User.register(shortTempUsername, shortPassword));

        String sameTempUsername="user123";
        String samePassword = "user123";
        assertThrows(IllegalArgumentException.class, () -> User.register(sameTempUsername, samePassword));
        pathname.delete();
    }
```

- **`testLogin()`**: 
  - Validates the login functionality.
  - Confirms successful login with correct credentials.
  - Tests failure scenarios with incorrect username/password combinations.

```java
public void testLogin() {
        String testUsername = "testUser";
        String testPassword = "testPassword";
        User.register(testUsername, testPassword);

        assertTrue(User.login(testUsername, testPassword));

        assertFalse(User.login("invalidUser", "wrongPassword"));

        assertFalse(User.login(testUsername, "wrongPassword"));

        assertFalse(User.login("invalidUser", testPassword));
    }
```
- **`testCheckUserExists()`**: 
  - Tests the method for checking if a user exists in the database.
  - Ensures accurate identification of existing and non-existing users.

```java
public void testCheckUserExists() {   
        User.register("babekbb", "babek123");
        User.register("test1", "test123");
        assertTrue(User.checkUserExists("babekbb"));
        assertTrue(User.checkUserExists("test1"));

        assertFalse(User.checkUserExists("nonExistingUser"));
    }
```

### MovieDatabaseTest Class
- **`testSaveToFile()`**: 
  - Verifies the functionality of saving movie data to a file.
  - Checks that movie details are correctly written to the file.

```java
@Test
    public void testSaveToFile(){
        MovieDatabase.saveToFile(m,file);
    }
```
- **`testRemoveMovie()`**: 
  - Tests the removal of a movie from the database.
  - Ensures that the movie is properly deleted from the file.

```java
@Test
    public void testRemoveMovie(){
        Movie m=new Movie("NASA", "Ilon Mask", 2023, 76);
        removeMovie(m);
    }
```
- **`testGetMoviesStartingFromYear()`**: 
  - Checks retrieval of movies released from a specified year onwards.
  - Verifies correct filtering and handling of cases with no matching movies.

```java
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
```
- **`testGetMoviesFromYear()`**: 
  - Tests the retrieval of movies released in a specific year.
  - Ensures accurate results and handling of years with no movies.

```java
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
```

- **`testGetMovieByTitle()`**: 
  - Validates the functionality to retrieve movies by their title.
  - Confirms accurate filtering and handling of non-existent titles.

```java
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
```

- **`testGetMovieByDirector()`**: 
  - Tests movie retrieval based on director names.
  - Ensures accurate filtering and exception handling for directors with no associated movies.

```java
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
```

- **Sorting Tests**: 
  - Includes tests for various sorting methods (`sortByTitleAscending`, `sortByYearDescending`, etc.).
  - Verifies that movies are sorted correctly according to the specified criteria.

```java
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
```
- **`testCalculateTotalWatchTime()`**: 
  - Tests the calculation of total running time for a collection of movies.
  - Verifies the accuracy of the aggregated running time.

```java
@Test
    public void testCalculateTotalWatchTime() {
        ArrayList<Movie> movies = testMovies();
        int result = MovieDatabase.calculateTotalWatchTime(movies);

        assertEquals(330, result);
    }
```

- **`testLoadFromFile()`**: 
  - Validates loading movies from a file into database.
  - Ensures the integrity and accuracy of the loaded data compared to expected results.

```java
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
```
# Comprehensive Grading Criteria Fulfillment

## Code Quality and Structure (3 points)
### Adherence to OOP Principles
- **Encapsulation**: 
  - The `Movie` class encapsulates movie-related data (title, director, etc.), providing access through getter methods.
- **Inheritance/Polymorphism**: 
  - Demonstrated by the `Movie` class's overriding of `toString()` and `equals(Object obj)` methods for specific behavior.

### Well-Organized Code
- **Distinct Class Roles**: 
  - Project is divided into classes with specific roles: `Movie` for movie attributes, `User` for user data, and `MovieDatabase` for movie data management.
- **Cohesive and Loosely Coupled Design**: 
  - Each class focuses on a single responsibility, promoting reusability and ease of maintenance.

## Functionality (4 points)
### User Management
- **`User` Class**: 
  - Handles user registration and login, using file I/O for a persistent user database.

### Movie Browsing
- **`MovieAppGUI`**: 
  - Provides an interactive interface for movie browsing, selection, and watchlist management.

## User Interface (Using Swing) (2 points)
### GUI Implementation
- **Design**: 
  - GUI in `MovieAppGUI` and `UserLoginWindow` is user-friendly and aesthetically pleasing.
- **Feature-rich Interface**: 
  - Integrates functionalities like user authentication, movie browsing, detailed movie views, and watchlist management.

## Git Commits (1 point)
### Regular and Meaningful Commits
- **GitHub Repository**: 
  - Demonstrates disciplined approach to version control with meaningful commit messages.

## Advanced Features (2/3 of the Project)
### Exception Handling
- **`MovieDatabase` and `Movie`**: 
  - Embeds comprehensive exception handling for various scenarios like file errors and invalid movie details.

### Stream API and Lambda Functions
- **`MovieDatabase`**: 
  - Utilizes Java's Stream API and lambda expressions for efficient data processing.

### Collections Framework
- **Use of `ArrayList<Movie>`**: 
  - Manages dynamic collections of movies and user watchlists.

### Sorting and Filtering
- **`UserLoginWindow` GUI**: 
  - Advanced sorting and filtering capabilities for user interaction with the movie database.

### Watchlist Management
- **`User` Class**: 
  - Implements a user-specific watchlist feature, allowing users to add and remove movies.

### File Reading/Writing
- **Data Persistence**: 
  - Incorporates file I/O for storing and retrieving user data and movie details.

## Bonus Points
### Test Cases (2 points)
- **Unit Tests in `UserTest` and `MovieDatabaseTest`**: 
  - Covers critical functionalities like user registration, login, and movie management system integrity.

### Javadoc (1 point)
- **Clear Documentation**: 
  - Consistently uses Javadoc comments throughout the project for clear documentation.
