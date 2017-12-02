import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class ModifiableTTT extends JFrame
{
	int dimension;
	
	final int WIDTH = (int) (Toolkit.getDefaultToolkit().getScreenSize().width / 3);
	final int HEIGHT = (int) (Toolkit.getDefaultToolkit().getScreenSize().height / 2);
	
	//activePlayer is always either "X" or "O"
	String activePlayer;
	//playerIndex is always either -1 or 1
	int playerIndex;
	
	int[][] values;
	JButton[][] buttons;
	int[][] locations;
	int[][] sizes;
	
	int winner;
	boolean winning;
	
	public ModifiableTTT()
	{
		setSize(WIDTH, HEIGHT);
		setTitle("Mod TTT");
		dimension = Integer.parseInt(JOptionPane.showInputDialog("What's the width of the grid? ENTER a number."));
		setLayout(null);
		
		winning = false;
		winner = dimension;
		if (dimension > 3)
		{
			winner = (int)(dimension * (0.75));
		}
		
		locations = new int[dimension*dimension][2];
		sizes = new int[dimension*dimension][2];
		buttons = new JButton[dimension][dimension];
		values = new int[dimension][dimension];
		activePlayer = "X";
		playerIndex = 1;
		
		//add buffers
		int leftedge = WIDTH/12;
		int rightedge = 8*WIDTH/12;
		int topedge = HEIGHT/12;
		int bottomedge = 9*HEIGHT/12;
		
		//calculate some numbers
		int horizspace = rightedge - leftedge;
		int vertspace = bottomedge - topedge;
		
		int buttonhorizspace = horizspace / dimension;
		System.out.println(leftedge + " " + horizspace + " " + buttonhorizspace);
		int buttonvertspace = vertspace / dimension;
		
		for (int i = 0; i < dimension; i++)
		{
			for (int j = 0; j < dimension; j++)
			{
				locations[i*dimension + j][0] = leftedge + i*buttonhorizspace;
				System.out.println(locations[i*dimension + j][0]);
				locations[i*dimension + j][1] = topedge + j*buttonvertspace;
				sizes[i*dimension + j][0] = (int)(buttonhorizspace*0.75);
				System.out.println(sizes[i*dimension + j][0]);
				sizes[i*dimension + j][1] = (int)(buttonvertspace*0.75);
			}
		}
		
		//put the things on the board
		for (int i = 0; i < dimension; i++)
		{
			for (int j = 0; j < dimension; j++)
			{
				buttons[i][j] = new JButton();
				dropButton(locations[i*dimension + j], sizes[i*dimension + j], new int[]{i, j}, true, true);
			}
		}
		
		add(new JLabel());
		setVisible(true);
	}
	
	
	public void dropButton(int[] pos, int[] size, int[] coords, boolean visibility, boolean isButton)
	{
		JButton thing = buttons[coords[0]][coords[1]];
		
		thing.setSize(size[0], size[1]);
		thing.setLocation(pos[0], pos[1]);
		thing.setVisible(visibility);
		
		thing.addActionListener((ActionEvent event)->{
			buttonPress(coords);
		});
		
		add(thing);
	}
	
	public void buttonPress(int[] coords)
	{
		JButton activated = buttons[coords[0]][coords[1]];
		
		//is the space unoccupied?
		if (Math.abs(values[coords[0]][coords[1]]) == 0) 
		{
			//good for you, here's your activation notice
			if (activePlayer.equals("X"))
			{
				activated.setBackground(Color.CYAN);
			}
			else
			{
				activated.setBackground(Color.MAGENTA);
			}
			//activated.setText(activePlayer);
			values[coords[0]][coords[1]] = playerIndex;

			checkWin(0,0,0);
			if (winning)
			{
				JOptionPane.showMessageDialog(null, activePlayer + " has won!");
				reset();
			}
			
			//now change turns
			playerIndex = playerIndex * -1;
			if (activePlayer.equals("X"))
			{
				activePlayer = "O";
			}
			else
			{
				activePlayer = "X";
			}	
		}
	}
	
	public void checkWin(int width, int height, int streak)
	{
		boolean w = false;
		boolean h = false;
		if (width + 1 < values.length)
		{
			w = true;
		}
		if (height + 1 < values[width].length)
		{
			h = true;
		}
		if (h && w)
		{
			if (values[width][height] == values[width + 1][height + 1] && values[width][height] != 0)
			{
				streak++;
			}
			else
			{
				streak = 0;
			}
			if (streak == winner)
			{
				winning = true;
			}
			checkWin(width + 1, height + 1, streak);
		}
		if (h)
		{
			if (values[width][height] == values[width][height+1] && values[width][height] != 0)
			{
				streak++;
			}
			else
			{
				streak = 0;
			}
			if (streak == winner)
			{
				winning = true;
			}
			checkWin(width, height + 1, streak);
		}
		if (w)
		{
			if (values[width][height] == values[width+1][height] && values[width][height] != 0)
			{
				streak++;
			}
			else
			{
				streak = 0;
			}
			if (streak == winner)
			{
				winning = true;
			}
			checkWin(width + 1, height, streak);
		}
		
	}
	
	public void reset()
	{
		System.out.println("Resetting...");
	}
}






