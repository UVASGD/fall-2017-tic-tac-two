import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;

public class TTTHub extends JFrame{

	JButton game1;
	JButton game2;
	JButton game3;
	JButton game4;
	JButton game5;
	JButton game6;
	JButton game7;
	JButton game8;
	JButton game9;
	JButton[] ogGames = {game1, game2, game3, game4, game5, game6, game7, game8, game9};
	JButton quit;
	JList<String> gamesList;
	JLabel welcome;

	final int WIDTH = (int) (Toolkit.getDefaultToolkit().getScreenSize().width / 3);
	final int HEIGHT = (int) (Toolkit.getDefaultToolkit().getScreenSize().height / 2);
	
	int[][] locations = {{WIDTH/12, HEIGHT/10}, {3*WIDTH/12, HEIGHT/10}, {5*WIDTH/12, HEIGHT/10}, {WIDTH/12, 3*HEIGHT/10}, {3*WIDTH/12, 3*HEIGHT/10}, {5*WIDTH/12, 3*HEIGHT/10}, {WIDTH/12, 5*HEIGHT/10}, {3*WIDTH/12, 5*HEIGHT/10}, {5*WIDTH/12, 5*HEIGHT/10}};
	
	public static void main(String[] args) 
	{
		new TTTHub();
	}
	
	public TTTHub()
	{
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(WIDTH, HEIGHT);
		setTitle("Tic Tac Two");
		
		
		for (int i = 0; i < ogGames.length; i++)
		{
			ogGames[i] = new JButton("Game " + (i + 1));
			ogGames[i].setSize(2*WIDTH/14, 2*HEIGHT/12);
			ogGames[i].setLocation(locations[i][0], locations[i][1] + HEIGHT/20);
			add(ogGames[i]);
		}
		
		ogGames[0].setText("Original");
		ogGames[1].setText("Modifiable");
		ogGames[8].setText("Credits");
		addActions();
		
		quit = new JButton("QUIT");
		quit.addActionListener((ActionEvent event) -> {
			System.exit(0);
		});
		quit.setLocation(8*WIDTH/10, 8*HEIGHT/10);
		quit.setSize(WIDTH/10, HEIGHT/12);
		add(quit);
		
		welcome = new JLabel("Welcome to Tic Tac Two!");
		welcome.setLocation(WIDTH/20, HEIGHT/22);
		welcome.setSize(WIDTH/4, HEIGHT/15);
		welcome.setFont(new Font("Times", Font.BOLD, 20));
		add(welcome);
		
		add(new JLabel());
		setVisible(true);
	}
	
	public void addActions()
	{
		ogGames[0].addActionListener((ActionEvent event) -> {
			new Game();
		});
		ogGames[1].addActionListener((ActionEvent event) -> {
			new ModifiableTTT();
		});
		//WHEN YOU INSTALL A NEW GAME, ADD AN ACTIONLISTENER TO THE APPROPRIATE BUTTON THAT CONSTRUCTS THE GAME.
		//ADDITIONALLY, MAKE SURE THE BUTTON TO START THE GAME ISN'T SET TO BE INVISIBLE
		ogGames[2].setVisible(false);
		ogGames[3].setVisible(false);
		ogGames[4].setVisible(false);
		ogGames[5].setVisible(false);
		ogGames[6].setVisible(false);
		ogGames[7].setVisible(false);
		
		ogGames[8].addActionListener((ActionEvent event) -> {
			new Credits();
		});
		
	}
}
