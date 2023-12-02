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
    }

}
