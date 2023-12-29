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
- 'getTitle()', 'getDirector()': Accessor methods for movie properties.
- 'setTitle(String title)': Mutator methods for updating movie properties.

### User Class
- 'register(String username, String password)': Registers a new user.
- login(String username, String password)': Authenticates a user.

### MovieDatabase Class
- 'addMovie(Movie m)': Adds a new movie to the database.
- 'getMovies()': Retrieves the list of movies.

