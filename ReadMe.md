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
- **`getTitle()`, `getDirector()`, `getReleaseYear()`, `getRunningTime()`**: Accessor methods that return the respective properties of the movie.
- **`setReleaseYear(int releaseYear)`, `setRunningTime(int runningTime)`**: Mutator methods that validate and set the release year and running time of the movie. These methods throw an `IllegalArgumentException` if input values are out of the expected range.
- **`toString()`**: Overridden method that returns a string representation of the movie, including all its properties, formatted for display or logging.
- **`equals(Object obj)`**: Overridden method for equality comparison based on all movie properties. This method is crucial for checking duplicates in the movie database.
- **`getDetails()`**: Method to get a detailed, formatted string containing all the movie information, useful for generating movie reports or displaying detailed movie info to the user.

### User Class
- **Constructor `User(String username, String password)`**: Initializes a new User object with the given username and password. The watchlist is also initialized as an empty list.
- **`register(String username, String password)`**: Static method that registers a new user in the system. It checks for the uniqueness of the username and the strength of the password. It writes user credentials to a file for persistence.
- **`login(String username, String password)`**: Static method that authenticates a user. It checks the provided credentials against the stored user data in the file system.
- **`addToWatchList(Movie selectedMovie)`**, **`removeFromWatchlist(Movie selectedMovie)`**: Methods to add or remove a movie from the user's personal watchlist.
- **`getWatchList()`**: Retrieves the list of movies in the user's watchlist.
- **`setWatchList(List<Movie> watchList)`**: Sets or updates the user's watchlist.
- **`getUsername()`**: Returns the username of the user, mainly used for display and logging purposes.

### MovieDatabase Class
- **Constructor `MovieDatabase()`**: Initializes the movie database, loading existing movie data from a file.
- **`addMovie(Movie m)`**: Adds a new movie to the database. It checks for duplicate entries based on the `equals()` method of the Movie class.
- **`saveToFile(Movie m)`**, **`retrieveMovie(Movie m)`**, **`removeMovie(Movie m)`**: Methods for persisting movie data to a file, retrieving movie details, and removing a movie from the database, respectively.
- **Filtering Methods**: Includes various methods like `getMoviesStartingFromYear(int year)`, `getMoviesFromYear(int year)`, `getMovieByTitle(String title)`, and `getMovieByDirector(String director)` to filter movies based on different criteria.
- **Sorting Methods**: Methods like `sortByTitleAscending()`, `sortByTitleDescending()`, `sortByYearAscending()`, `sortByYearDescending()`, `sortByDirectorAscending()`, and `sortByDirectorDescending()` to sort the movie list based on different attributes.
- **`calculateTotalWatchTime(List<Movie> movies)`**: Calculates the total running time of a list of movies, useful for statistical and reporting purposes.
- **`loadFromFile(String fileName)`**: Loads movie data from the specified file, populating the movie database. This method is critical for initializing the database with pre-existing data.

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
- **`testLogin()`**: 
  - Validates the login functionality.
  - Confirms successful login with correct credentials.
  - Tests failure scenarios with incorrect username/password combinations.
- **`testCheckUserExists()`**: 
  - Tests the method for checking if a user exists in the database.
  - Ensures accurate identification of existing and non-existing users.

### MovieDatabaseTest Class
- **`testSaveToFile()`**: 
  - Verifies the functionality of saving movie data to a file.
  - Checks that movie details are correctly written to the file.
- **`testRemoveMovie()`**: 
  - Tests the removal of a movie from the database.
  - Ensures that the movie is properly deleted from the file.
- **`testGetMoviesStartingFromYear()`**: 
  - Checks retrieval of movies released from a specified year onwards.
  - Verifies correct filtering and handling of cases with no matching movies.
- **`testGetMoviesFromYear()`**: 
  - Tests the retrieval of movies released in a specific year.
  - Ensures accurate results and handling of years with no movies.
- **`testGetMovieByTitle()`**: 
  - Validates the functionality to retrieve movies by their title.
  - Confirms accurate filtering and handling of non-existent titles.
- **`testGetMovieByDirector()`**: 
  - Tests movie retrieval based on director names.
  - Ensures accurate filtering and exception handling for directors with no associated movies.
- **Sorting Tests**: 
  - Includes tests for various sorting methods (`sortByTitleAscending`, `sortByYearDescending`, etc.).
  - Verifies that movies are sorted correctly according to the specified criteria.
- **`testCalculateTotalWatchTime()`**: 
  - Tests the calculation of total running time for a collection of movies.
  - Verifies the accuracy of the aggregated running time.
- **`testLoadFromFile()`**: 
  - Validates loading movies from a file into the database.
  - Ensures the integrity and accuracy of the loaded data compared to expected results.
