import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class TTTHub extends JFrame{

	JButton ogGame;
	
	public static void main(String[] args) 
	{
		new TTTHub();
	}
	
	public TTTHub()
	{
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(300, 300);
		ogGame = new JButton("Play Tic Tac Toe");
		ogGame.addActionListener((ActionEvent event) -> {
			new Game();
		});
		ogGame.setLocation(100, 100);
		ogGame.setSize(100,100);
		add(ogGame);
		
		
		add(new JLabel());
		setVisible(true);
	}

}
