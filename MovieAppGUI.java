import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class MovieAppGUI {
    private JFrame frame;
    private MovieDatabase movieDatabase;
    private static User currentUser;

    public MovieAppGUI(User user) {
        this.currentUser = user;
        movieDatabase = MovieDatabase.loadFromFile("movies_database.txt");
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Movie Database");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 800);

        JPanel moviePanel = new JPanel(new GridLayout(0,1));
        List<Movie>movies = movieDatabase.getMovies(null);
        for(Movie movie : movies) {
            JButton movieButton = new JButton(movie.getTitle());
            movieButton.addActionListener(new ActionListener(){

                @Override
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(null, movie.getDetails());
                }
            });
            moviePanel.add(movieButton);
        }

        JScrollPane scrollPane = new JScrollPane(moviePanel);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        JPanel watchlistPanel = new JPanel(new GridLayout());

        JButton addToWatchListButton = new JButton("Add to watchlist");
        addToWatchListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                Movie selectedMovie = getSelectedMovie(movies);
                if(selectedMovie != null) {
                    currentUser.addToWatchListButton(selectedMovie);
                    JOptionPane.showMessageDialog(null, "Added to watchlist: ");
                }
            }
        });
        Container WatchlistPanel;
        watchlistPanel.add(addToWatchListButton);

        JButton removeFromWatchlistButton = new JButton("Remove from Watchlist: ");
        AbstractButton removerFromWatchlistButton;
        removeFromWatchlistButton.addActionListener(new ActionListener()  {
            @Override
            public void actionPerformed(ActionEvent e) {

                Movie selectedMovie = getSelectedMovie(currentUser.getWatcList());
                if(selectedMovie != null) {
                    currentUser.removeFromWatchlist(selectedMovie);
                    JOptionPane.showMessageDialog(null, "Removed from Watchlist: " + selectedMovie.getTitle());
                }
            }
        });

        watchlistPanel.add(removeFromWatchlistButton);
        frame.getContentPane().add(watchlistPanel, BorderLayout.EAST);

        frame.setVisible(true);

    }

    private Movie getSelectedMovie(List<Movie> movies) {
        int selectedMovie = JOptionPane.showOptionDialog(null,
        "Selected a movie: ",
        "Movie Selection",
        JOptionPane.DEFAULT_OPTION,
        JOptionPane.PLAIN_MESSAGE,
        null,
        movies.toArray(),
        null);

       
        if(selectedMovie >= 0 && selectedMovie < movies.size()) {
            return movies.get(selectedMovie);
        }
        return null;
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
