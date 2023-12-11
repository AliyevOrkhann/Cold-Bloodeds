import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

public class UserLoginWindow{
    private JPanel movieListPanel;
    private JPanel watchlistPanel;
    private JPanel detailsPanel;
    private MovieDatabase movieDatabase;

    UserLoginWindow(User currentUser){
        JFrame frame = new JFrame(currentUser.getUsername()+" welcome<3");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        this.movieDatabase = new MovieDatabase();

        JPanel tabPanel = new JPanel();
        tabPanel.setLayout(new BorderLayout());

        movieListPanel = new JPanel(new GridLayout(0,1));
        watchlistPanel = new JPanel(new GridLayout(0,1));
        detailsPanel = new JPanel(new GridLayout(0,1));

        updateWatchlistPanel(currentUser);
        List<Movie> movies = movieDatabase.getMovies();
        for (Movie movie : movies) {
            JButton movieButton = new JButton(movie.getTitle());
            movieButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    showMovieDetails(movie, currentUser);
                }
            });
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

        tabPanel.add(movieListPanel, BorderLayout.NORTH);
        tabPanel.add(watchlistPanel, BorderLayout.EAST);
        tabPanel.add(detailsPanel, BorderLayout.CENTER);

        frame.add(tabPanel);

        frame.setVisible(true);
    }

    private void updateWatchlistPanel(User currentUser) {
        watchlistPanel.removeAll();
        List<Movie> watchList = currentUser.getWatchList();
        for(Movie movie : watchList) {
            JButton movieButton = new JButton(movie.getTitle());
            movieButton.setFocusable(true);
            movieButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    currentUser.removeFromWatchlist(movie);
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
                currentUser.addToWatchList(movie);
                File path = new File("Database/"+currentUser.getUsername()+".dat");
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
                currentUser.removeFromWatchlist(movie);
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