import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class UserLoginWindow {
    private JFrame frame;
    private JPanel movieListPanel;
    private JPanel watchlistPanel;
    private JPanel detailsPanel;
    private MovieDatabase movieDatabase;
    private User currentUser;

    UserLoginWindow(User currentUser) {
        this.currentUser = currentUser;
        this.movieDatabase = new MovieDatabase();

        frame = new JFrame(currentUser.getUsername().toUpperCase() + "'s Home Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        movieListPanel = new JPanel(new GridLayout(0, 1));
        watchlistPanel = new JPanel(new GridLayout(0, 1));
        detailsPanel = new JPanel(new GridLayout(0, 1));

        stylePanel(movieListPanel, "Movie Library");
        stylePanel(watchlistPanel, "Watchlist");
        stylePanel(detailsPanel, "Movie Details");

        populateMovieList();
        updateWatchlistPanel();

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Movies and Watch List", createMovieListTab());
        tabbedPane.addTab("Add Movie", createAddMovieTab());

        frame.add(tabbedPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void stylePanel(JPanel panel, String title) {
        TitledBorder border = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.WHITE), title);
        border.setTitleColor(Color.WHITE);
        border.setTitleFont(new Font("Arial", Font.BOLD, 18));
        panel.setBorder(border);
        panel.setBackground(Color.BLACK);
    }

    private JPanel createMovieListTab() {
        JPanel movieListTab = new JPanel(new BorderLayout());

        JScrollPane movieListScrollPane = new JScrollPane(movieListPanel);
        JScrollPane watchScrollPane = new JScrollPane(watchlistPanel);
        JScrollPane detailsScrollPane = new JScrollPane(detailsPanel);

        JSplitPane movieDetailsSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, movieListScrollPane, detailsScrollPane);
        movieDetailsSplitPane.setResizeWeight(0.5);

        JSplitPane mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, movieDetailsSplitPane, watchScrollPane);
        mainSplitPane.setResizeWeight(0.5);

        movieListTab.add(mainSplitPane, BorderLayout.CENTER);
        movieListTab.add(createFilterSortPanel(), BorderLayout.NORTH);
        return movieListTab;
    }

    private JPanel createAddMovieTab() {
        JTextField titleField = new JTextField(20);
        styleTextField(titleField);

        JTextField directorField = new JTextField(20);
        styleTextField(directorField);

        JTextField releaseYearField = new JTextField(20);
        styleTextField(releaseYearField);

        JTextField runningTimeField = new JTextField(20);
        styleTextField(runningTimeField);

        JButton addMovieButton = createStyledButton("Add Movie");
        addMovieButton.addActionListener(e -> {
            try {
                Movie newMovie = new Movie(titleField.getText(), directorField.getText(),
                        Integer.parseInt(releaseYearField.getText()), Integer.parseInt(runningTimeField.getText()));
                MovieDatabase.addMovie(newMovie);
                titleField.setText("");
                directorField.setText("");
                releaseYearField.setText("");
                runningTimeField.setText("");
                updateMovieListPanel();
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(frame, "Please enter valid numbers for year and running time.");
            }
        });

        JPanel inputPanel = new JPanel(new GridLayout(0, 2, 5, 5));
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

        JPanel addMoviePanel = new JPanel();
        addMoviePanel.setLayout(new BoxLayout(addMoviePanel, BoxLayout.Y_AXIS));
        addMoviePanel.add(inputPanel);
        return addMoviePanel;
    }

    private void styleTextField(JTextField textField) {
        textField.setBackground(Color.BLACK);
        textField.setForeground(new Color(173, 216, 230));
        textField.setFont(new Font("Arial", Font.PLAIN, 12));
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.PLAIN, 12));
        button.setFocusable(false);
        button.setBorderPainted(true);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE)); // White border at the bottom
        return button;
    }

    private void populateMovieList() {
        movieListPanel.removeAll();
        List<Movie> movies = movieDatabase.getMovies();
        for (Movie movie : movies) {
            JButton movieButton = createStyledButton(movie.getTitle());
            movieButton.addActionListener(e -> showMovieDetails(movie));
            movieListPanel.add(movieButton);
        }
        movieListPanel.revalidate();
        movieListPanel.repaint();
    }

    private void updateWatchlistPanel() {
        watchlistPanel.removeAll();
        List<Movie> watchList = currentUser.getWatchList();
        for (Movie movie : watchList) {
            JButton movieButton = createStyledButton(movie.getTitle());
            movieButton.addActionListener(e -> showMovieDetails(movie));
            watchlistPanel.add(movieButton);
        }
        watchlistPanel.revalidate();
        watchlistPanel.repaint();
    }

    private void showMovieDetails(Movie movie) {
        detailsPanel.removeAll();

        JLabel titleLabel = new JLabel("Title: " + movie.getTitle());
        styleLabel(titleLabel);

        JLabel directorLabel = new JLabel("Director: " + movie.getDirector());
        styleLabel(directorLabel);

        JLabel releaseLabel = new JLabel("Release Year: " + movie.getReleaseYear());
        styleLabel(releaseLabel);

        JLabel runningTimeLabel = new JLabel("Running Time: " + movie.getRunningTime() + " minutes");
        styleLabel(runningTimeLabel);

        JButton addToWatchlistButton = createStyledButton("Add to Watchlist");
        addToWatchlistButton.addActionListener(e -> addToWatchlist(movie));

        JButton removeFromWatchlistButton = createStyledButton("Remove from Watchlist");
        removeFromWatchlistButton.addActionListener(e -> removeFromWatchlist(movie));

        detailsPanel.add(titleLabel);
        detailsPanel.add(directorLabel);
        detailsPanel.add(releaseLabel);
        detailsPanel.add(runningTimeLabel);
        detailsPanel.add(addToWatchlistButton);
        detailsPanel.add(removeFromWatchlistButton);

        detailsPanel.revalidate();
        detailsPanel.repaint();
    }

    private void styleLabel(JLabel label) {
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.PLAIN, 12));
    }

    private void addToWatchlist(Movie movie) {
        if (!currentUser.getWatchList().contains(movie)) {
            currentUser.addToWatchList(movie);
            saveWatchlistToFile(movie);
            updateWatchlistPanel();
        } else {
            JOptionPane.showMessageDialog(frame, "Movie is already in the Watch List");
        }
    }

    private void removeFromWatchlist(Movie movie) {
        if (currentUser.getWatchList().contains(movie)) {
            currentUser.removeFromWatchlist(movie);
            updateWatchlistPanel();
        } else {
            JOptionPane.showMessageDialog(frame, "Movie is not in the Watch List");
        }
    }

    private void saveWatchlistToFile(Movie movie) {
        File path = new File("Database/" + currentUser.getUsername() + ".dat");
        try (FileWriter fw = new FileWriter(path, true)) {
            fw.write(movie.getDetails() + "\n");
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(frame, "Something went wrong: " + exception.getMessage());
        }
    }

    private void updateMovieListPanel() {
        SwingUtilities.invokeLater(() -> {
            movieListPanel.removeAll();
            List<Movie> movies = movieDatabase.getMovies();
            for (Movie movie : movies) {
                JButton movieButton = createStyledButton(movie.getTitle());
                movieButton.addActionListener(e -> showMovieDetails(movie));
                movieListPanel.add(movieButton);
            }
            movieListPanel.revalidate();
            movieListPanel.repaint();
        });
    }

    // Create Filter and Sort Buttons
    private JPanel createFilterSortPanel() {
        JButton filterTitleButton = createStyledButton("Filter by Title");
        filterTitleButton.addActionListener(e -> {
            String filterValue = JOptionPane.showInputDialog(frame, "Enter title to filter by:");
            if (filterValue != null && !filterValue.isEmpty()) {
                filterMovies("Title", filterValue);
            }
        });

        JButton filterDirectorButton = createStyledButton("Filter by Director");
        filterDirectorButton.addActionListener(e -> {
            String filterValue = JOptionPane.showInputDialog(frame, "Enter director to filter by:");
            if (filterValue != null && !filterValue.isEmpty()) {
                filterMovies("Director", filterValue);
            }
        });

        JButton filterYearButton = createStyledButton("Filter by Year");
        filterYearButton.addActionListener(e -> {
            String filterValue = JOptionPane.showInputDialog(frame, "Enter year to filter by:");
            if (filterValue != null && !filterValue.isEmpty()) {
                try {
                    int year = Integer.parseInt(filterValue);
                    filterMovies("Release Year", String.valueOf(year));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid year.");
                }
            }
        });

        JButton sortTitleAscendingButton = createStyledButton("Sort by Title Ascending");
        sortTitleAscendingButton.addActionListener(e -> {
            sortMovies("Title Ascending");
        });

        JButton sortTitleDescendingButton = createStyledButton("Sort by Title Descending");
        sortTitleDescendingButton.addActionListener(e -> {
            sortMovies("Title Descending");
        });

        JButton sortYearAscendingButton = createStyledButton("Sort by Year Ascending");
        sortYearAscendingButton.addActionListener(e -> {
            sortMovies("Year Ascending");
        });

        JButton sortYearDescendingButton = createStyledButton("Sort by Year Descending");
        sortYearDescendingButton.addActionListener(e -> {
            sortMovies("Year Descending");
        });

        JPanel filterSortPanel = new JPanel(new GridLayout(2, 4));
        filterSortPanel.add(filterTitleButton);
        filterSortPanel.add(filterDirectorButton);
        filterSortPanel.add(filterYearButton);
        filterSortPanel.add(new JLabel(""));
        filterSortPanel.add(sortTitleAscendingButton);
        filterSortPanel.add(sortTitleDescendingButton);
        filterSortPanel.add(sortYearAscendingButton);
        filterSortPanel.add(sortYearDescendingButton);

        return filterSortPanel;
    }

    private void filterMovies(String filterType, String filterValue) {
    List<Movie> filteredMovies = new ArrayList<>();

    switch (filterType) {
        case "Title":
            for (Movie movie : movieDatabase.getMovies()) {
                if (movie.getTitle().equalsIgnoreCase(filterValue)) {
                    filteredMovies.add(movie);
                }
            }
            break;
        case "Director":
            for (Movie movie : movieDatabase.getMovies()) {
                if (movie.getDirector().equalsIgnoreCase(filterValue)) {
                    filteredMovies.add(movie);
                }
            }
            break;
        case "Release Year":
            try {
                int year = Integer.parseInt(filterValue);
                for (Movie movie : movieDatabase.getMovies()) {
                    if (movie.getReleaseYear() == year) {
                        filteredMovies.add(movie);
                    }
                }
            } catch (NumberFormatException e) {
                // Handle invalid input
            }
            break;
        default:
            // Handle unsupported filter type
    }

    // Update the movie list panel with the filtered movies
    updateMovieListPanel(filteredMovies);
}

private void sortMovies(String sortType) {
    List<Movie> sortedMovies = new ArrayList<>(movieDatabase.getMovies());

    switch (sortType) {
        case "Title Ascending":
            sortedMovies.sort(Comparator.comparing(Movie::getTitle));
            break;
        case "Title Descending":
            sortedMovies.sort(Comparator.comparing(Movie::getTitle).reversed());
            break;
        case "Year Ascending":
            sortedMovies.sort(Comparator.comparing(Movie::getReleaseYear));
            break;
        case "Year Descending":
            sortedMovies.sort(Comparator.comparing(Movie::getReleaseYear).reversed());
            break;
        default:
            // Handle unsupported sort type
    }

    // Update the movie list panel with the sorted movies
    updateMovieListPanel(sortedMovies);
}

// Helper method to update the movie list panel with a list of movies
private void updateMovieListPanel(List<Movie> movies) {
    movieListPanel.removeAll();
    for (Movie movie : movies) {
        JButton movieButton = createStyledButton(movie.getTitle());
        movieButton.addActionListener(e -> showMovieDetails(movie));
        movieListPanel.add(movieButton);
    }
    movieListPanel.revalidate();
    movieListPanel.repaint();
}

}