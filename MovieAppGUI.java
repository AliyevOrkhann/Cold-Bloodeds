import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MovieAppGUI {
    private JFrame frame;
    private MovieDatabase movieDatabase;
    private static User currentUser;
    private JPanel movieListPanel;
    private JPanel watchlistPanel;
    private JPanel detailsPanel;

    public MovieAppGUI(User user) {
        MovieAppGUI.currentUser = user;
        this.movieDatabase = new MovieDatabase();
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Movie Database");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        movieListPanel = new JPanel(new GridLayout(0,1));
        watchlistPanel = new JPanel(new GridLayout(0,1));
        detailsPanel = new JPanel(new GridLayout(0,1));

        List<Movie> movies = movieDatabase.getMovies();
        for (Movie movie : movies) {
            JButton movieButton = new JButton(movie.getTitle());
            movieButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    showMovieDetails(movie);
                }
            });
            movieListPanel.add(movieButton);
        }

        JScrollPane movieListScrollPane = new JScrollPane(movieListPanel);
        JScrollPane watchScrollPane = new JScrollPane(watchlistPanel);
        JScrollPane detailsScrollPane = new JScrollPane(detailsPanel);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, movieListScrollPane, detailsScrollPane);
        splitPane.setResizeWeight(0.5);

        JSplitPane rightSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, splitPane, watchScrollPane);
        rightSplitPane.setResizeWeight(0.8);

        frame.getContentPane().add(rightSplitPane);
        frame.setVisible(true);
    }

    private void showMovieDetails(Movie movie) {
        detailsPanel.removeAll();

        JLabel titleLabel = new JLabel("Title: " + movie.getTitle());
        JLabel directorLabel = new JLabel("Director: " + movie.getDirector());
        JLabel releaseLabel = new JLabel("Release Year: " + movie.getReleaseYear());
        JLabel runningTimeLabel = new JLabel("Running Time: " + movie.getRunningTime() + " minutes");

        JButton addToWatchlistButton = new JButton("Add to Watchlist");
        addToWatchlistButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentUser.addToWatchList(movie);
                updateWatchlistPanel();
            }
        });

        JButton removeFromWatchlistButton = new JButton("Remove from Watchlist");
        removeFromWatchlistButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentUser.removeFromWatchlist(movie);
                updateWatchlistPanel();
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

    private void updateWatchlistPanel() {
        watchlistPanel.removeAll();
        List<Movie> watchList = currentUser.getWatchList();
        for(Movie movie : watchList) {
            JButton movieButton = new JButton(movie.getTitle());
            movieButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    currentUser.removeFromWatchlist(movie);
                    updateWatchlistPanel();
                }
            });
            watchlistPanel.add(movieButton);
        }
        watchlistPanel.revalidate();
        watchlistPanel.repaint();
    }

    public static void main(String[] args) {
        User user = new User("username", "password");
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MovieAppGUI(user);
            }
        });
    }

}