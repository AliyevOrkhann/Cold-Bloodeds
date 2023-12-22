import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

public class UserLoginWindow {
    private JPanel movieListPanel;
    private JPanel watchlistPanel;
    private JPanel detailsPanel;
    private MovieDatabase movieDatabase;
    private User currentUser;

    public User getCurrentUser() {
        return currentUser;
    }

    UserLoginWindow(User currentUser) {
        JFrame frame = new JFrame(currentUser.getUsername() + " welcome<3");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        this.currentUser = currentUser;

        this.movieDatabase = new MovieDatabase();

        JTabbedPane tabbedPane = new JTabbedPane();

        // Movie List Tab
        JPanel movieListTabPanel = new JPanel();
        movieListTabPanel.setLayout(new BorderLayout());

        movieListPanel = new JPanel(new GridLayout(0, 1));
        watchlistPanel = new JPanel(new GridLayout(0, 1));
        detailsPanel = new JPanel(new GridLayout(0, 1));

        updateWatchlistPanel(currentUser);
        List<Movie> movies = movieDatabase.getMovies();
        for (Movie movie : movies) {
            JButton movieButton = new JButton(movie.getTitle());
            movieButton.addActionListener(e -> showMovieDetails(movie, currentUser));
            movieListPanel.add(movieButton);
            movieButton.setFocusable(true);
        }

        JScrollPane movieListScrollPane = new JScrollPane(movieListPanel);
        JScrollPane watchScrollPane = new JScrollPane(watchlistPanel);
        JScrollPane detailsScrollPane = new JScrollPane(detailsPanel);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, movieListScrollPane, detailsScrollPane);
        splitPane.setResizeWeight(0.5);

        JSplitPane rightSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, splitPane, watchScrollPane);
        rightSplitPane.setResizeWeight(0.8);

        movieListTabPanel.add(rightSplitPane, BorderLayout.CENTER);

        // Add Movie Tab
        JPanel addMoviePanel = new JPanel(new BorderLayout());

        JTextField titleField = new JTextField();
        JTextField directorField = new JTextField();
        JTextField releaseYearField = new JTextField();
        JTextField runningTimeField = new JTextField();

        JButton addMovieButton = new JButton("Add Movie");
        addMovieButton.addActionListener(e -> {
            addMovieToDatabase(titleField.getText(), directorField.getText(),releaseYearField.getText(), runningTimeField.getText());
            titleField.setText("");
            directorField.setText("");
            releaseYearField.setText("");
            runningTimeField.setText("");
        });

        JPanel inputPanel = new JPanel(new GridLayout(5, 2));
        inputPanel.add(new JLabel("Title:"));
        inputPanel.add(titleField);
        inputPanel.add(new JLabel("Director:"));
        inputPanel.add(directorField);
        inputPanel.add(new JLabel("Release Year:"));
        inputPanel.add(releaseYearField);
        inputPanel.add(new JLabel("Running Time:"));
        inputPanel.add(runningTimeField);
        inputPanel.add(new JLabel(""));
        inputPanel.add(addMovieButton);

        addMoviePanel.add(inputPanel, BorderLayout.CENTER);

        // Add tabs to the tabbedPane
        tabbedPane.addTab("Movies and Watch List", movieListTabPanel);
        tabbedPane.addTab("Add Movie", addMoviePanel);

        frame.add(tabbedPane);
        frame.setVisible(true);
    }

    private void addMovieToDatabase(String title, String director, String releaseYear, String runningTime) {
        // Add the new movie to the database (you may need to validate input)
        Movie newMovie = new Movie(title, director, Integer.parseInt(releaseYear), Integer.parseInt(runningTime));
        movieDatabase.addMovie(newMovie);

        // Update the movie list panel
        updateMovieListPanel();

        
        // Clear the input fields
        // You can add more validation and error handling here
    }

    private void updateMovieListPanel() {
        movieListPanel.removeAll();
        List<Movie> movies = movieDatabase.getMovies();
        for (Movie movie : movies) {
            JButton movieButton = new JButton(movie.getTitle());
            movieButton.addActionListener(e -> showMovieDetails(movie, null)); // Passing null for the user
            movieListPanel.add(movieButton);
            movieButton.setFocusable(true);
        }
        movieListPanel.revalidate();
        movieListPanel.repaint();
    }

    private void updateWatchlistPanel(User currentUser) {
        watchlistPanel.removeAll();
        List<Movie> watchList = getCurrentUser().getWatchList();
        for(Movie movie : watchList) {
            JButton movieButton = new JButton(movie.getTitle());
            movieButton.setFocusable(true);
            movieButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    getCurrentUser().removeFromWatchlist(movie);
                    updateWatchlistPanel(currentUser);
                }
            });
            watchlistPanel.add(movieButton);
        }
        watchlistPanel.revalidate();
        watchlistPanel.repaint();
    }

    private void showMovieDetails(Movie movie, User currentUser) {
        detailsPanel.removeAll();

        JLabel titleLabel = new JLabel("Title: " + movie.getTitle());
        JLabel directorLabel = new JLabel("Director: " + movie.getDirector());
        JLabel releaseLabel = new JLabel("Release Year: " + movie.getReleaseYear());
        JLabel runningTimeLabel = new JLabel("Running Time: " + movie.getRunningTime() + " minutes");

        JButton addToWatchlistButton = new JButton("Add to Watchlist");
        addToWatchlistButton.setFocusable(true);
        addToWatchlistButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getCurrentUser().addToWatchList(movie);
                File path = new File("Database/"+getCurrentUser().getUsername()+".dat");
                try (FileWriter fw = new FileWriter(path, true)) {
                fw.write(movie.getDetails()+"\n");
                } catch (Exception exception) {
                System.out.println("Something went wrong: " + exception.getMessage());
                }
                updateWatchlistPanel(currentUser);
            }
        });

        JButton removeFromWatchlistButton = new JButton("Remove from Watchlist");
        removeFromWatchlistButton.setFocusable(true);
        removeFromWatchlistButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getCurrentUser().removeFromWatchlist(movie);
                updateWatchlistPanel(currentUser);
            }
        });

        detailsPanel.add(titleLabel);
        detailsPanel.add(directorLabel);
        detailsPanel.add(releaseLabel);
        detailsPanel.add(runningTimeLabel);
        detailsPanel.add(addToWatchlistButton);
        detailsPanel.add(removeFromWatchlistButton);

        detailsPanel.revalidate();
        detailsPanel.repaint();
    }
}
