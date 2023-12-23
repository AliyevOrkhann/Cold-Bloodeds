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

        JPanel movieListTabPanel = new JPanel();
        movieListTabPanel.setLayout(new BorderLayout());

        movieListPanel = new JPanel(new GridLayout(0, 1));
        watchlistPanel = new JPanel(new GridLayout(0, 1));
        detailsPanel = new JPanel(new GridLayout(0, 1));

        updateWatchlistPanel(currentUser);
        List<Movie> movies = movieDatabase.getMovies();
        for (Movie movie : movies) {
            JButton movieButton = new JButton(movie.getTitle());
            movieButton.addActionListener(e -> showMovieDetails(movie, currentUser,false));
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

        JPanel addMoviePanel = new JPanel(new BorderLayout());

        JTextField titleField = new JTextField();
        JTextField directorField = new JTextField();
        JTextField releaseYearField = new JTextField();
        JTextField runningTimeField = new JTextField();

        JButton addMovieButton = new JButton("Add Movie");
        addMovieButton.addActionListener(e -> {
            Movie newMovie = new Movie(titleField.getText(), directorField.getText(), Integer.parseInt(releaseYearField.getText()), Integer.parseInt(runningTimeField.getText()));
            MovieDatabase.addMovie(newMovie);
            titleField.setText("");
            directorField.setText("");
            releaseYearField.setText("");
            runningTimeField.setText("");
            updateMovieListPanel();
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

        tabbedPane.addTab("Movies and Watch List", movieListTabPanel);
        tabbedPane.addTab("Add Movie", addMoviePanel);

        frame.add(tabbedPane);
        frame.setVisible(true);
    }

    private void updateMovieListPanel() {
        movieListPanel.removeAll();
        List<Movie> movies = movieDatabase.getMovies();
        for (Movie movie : movies) {
            JButton movieButton = new JButton(movie.getTitle());
            movieButton.addActionListener(e -> showMovieDetails(movie, null,false));
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
            movieButton.addActionListener(e -> showMovieDetails(movie, currentUser,true));
            watchlistPanel.add(movieButton);
        }
        watchlistPanel.revalidate();
        watchlistPanel.repaint();
    }

    private void showMovieDetails(Movie movie, User currentUser,boolean forWatchlist) {
        detailsPanel.removeAll();

        JLabel titleLabel = new JLabel("Title: " + movie.getTitle());
        JLabel directorLabel = new JLabel("Director: " + movie.getDirector());
        JLabel releaseLabel = new JLabel("Release Year: " + movie.getReleaseYear());
        JLabel runningTimeLabel = new JLabel("Running Time: " + movie.getRunningTime() + " minutes");
        if(!forWatchlist){
            JButton addToWatchlistButton = new JButton("Add to Watchlist");
            addToWatchlistButton.setFocusable(true);
            addToWatchlistButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(currentUser.getWatchList().contains(movie)){
                        JOptionPane.showMessageDialog(watchlistPanel, "Movie is already in the Watch List");
                    }
                    else{
                        getCurrentUser().addToWatchList(movie);
                        File path = new File("Database/"+getCurrentUser().getUsername()+".dat");
                        try (FileWriter fw = new FileWriter(path, true)) {
                        fw.write(movie.getDetails()+"\n");
                        } catch (Exception exception) {
                        System.out.println("Something went wrong: " + exception.getMessage());
                        }
                        updateWatchlistPanel(currentUser);   
                    }
                }
            });
            
            detailsPanel.add(addToWatchlistButton);
        }

        JButton removeFromWatchlistButton = new JButton("Remove from Watchlist");
        removeFromWatchlistButton.setFocusable(true);
        removeFromWatchlistButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(currentUser.getWatchList().contains(movie)){
                        getCurrentUser().removeFromWatchlist(movie);
                        updateWatchlistPanel(currentUser);
                }
                else{JOptionPane.showMessageDialog(detailsPanel, "Movie is not in the Watch List");}
            }
        });

        detailsPanel.add(titleLabel);
        detailsPanel.add(directorLabel);
        detailsPanel.add(releaseLabel);
        detailsPanel.add(runningTimeLabel);
        detailsPanel.add(removeFromWatchlistButton);

        detailsPanel.revalidate();
        detailsPanel.repaint();
    }
}
