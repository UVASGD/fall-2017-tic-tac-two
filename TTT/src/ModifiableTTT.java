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
	JLabel player;
	JLabel gameInfo;
	
	int[][] values;
	JButton[][] buttons;
	int[][] locations;
	int[][] sizes;
	
	int winner;
	boolean winning;
	int longest;
	
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
		
		int rightbuffer = WIDTH - rightedge;
		int vertsubsection = HEIGHT/10;
		
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
				dropButton(locations[i*dimension + j], sizes[i*dimension + j], new int[]{i, j}, true);
			}
		}
		
		//put the rest of the UI on the board
		player = new JLabel("Active player: " + activePlayer);
		drop(new int[]{rightedge, topedge}, new int[]{(int)(rightbuffer * 0.75), (int)(vertsubsection * 0.75)}, true, player);
		gameInfo = new JLabel();
		updateGameInfo(0);
		drop(new int[]{rightedge, topedge + vertsubsection}, new int[]{(int)(rightbuffer * 0.75), (int)(vertsubsection * 5 + vertsubsection * 0.75)}, true, gameInfo);
		
		add(new JLabel());
		setVisible(true);
	}
	
	public void drop(int[] pos, int[] size, boolean visibility, JComponent thing)
	{
		thing.setSize(size[0], size[1]);
		thing.setLocation(pos[0], pos[1]);
		thing.setVisible(visibility);
		add(thing);
	}
	
	public void dropButton(int[] pos, int[] size, int[] coords, boolean visibility)
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

			checkWin(coords[0], coords[1]);
			
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
			player.setText("Active Player: " + activePlayer);
		}
	}
	
	
	/* EDGE CASES:
	 * T: only check 2+6, 3, 4, and 5
	 * TR: only check 4, 5, and 6
	 * R: only check 0+4, 5, 6, and 7
	 * BR: only check 0, 6, and 7
	 * B: only check 0, 1, 2+6, and 7
	 * BL: only check 0, 1, and 2
	 * L: only check 0+4, 1, 2, and 3
	 * TL: only check 2, 3, and 4
	 * 
	 * Otherwise, check 0+4, 1+5, 2+6, and 3+7
	 */
	public void checkWin(int width, int height)
	{
		//make boolean variables signifying our types of edge cases
		boolean t = false, b = false, r = false, l = false;
		if (width == 0)
			l = true;
		if (width == dimension - 1)
			r = true;
		if (height == 0)
			t = true;
		if (height == dimension - 1)
			b = true;
		
		int streak = 0;
		if (l || r || t || b)
		{
			if (l && t)
			{
				System.out.println("Doing the top left comparison at [" + width + "][" + height + "]");
				streak = Integer.max(Integer.max(checkWin(width, height, 0, 2), checkWin(width, height, 0, 3)), checkWin(width, height, 0, 4));
			}
			else if (l && b)
			{
				System.out.println("Doing the bottom left comparison at [" + width + "][" + height + "]");
				streak = Integer.max(Integer.max(checkWin(width, height, 0, 0), checkWin(width, height, 0, 1)), checkWin(width, height, 0, 2));
			}
			else if (r && t)
			{

				System.out.println("Doing the top right comparison at [" + width + "][" + height + "]");
				streak = Integer.max(Integer.max(checkWin(width, height, 0, 4), checkWin(width, height, 0, 5)), checkWin(width, height, 0, 6));
			}
			else if (r && b)
			{

				System.out.println("Doing the bottom right comparison at [" + width + "][" + height + "]");
				streak = Integer.max(Integer.max(checkWin(width, height, 0, 6), checkWin(width, height, 0, 7)), checkWin(width, height, 0, 8));
			}
			else if (t) //make sure we hit the easy-to-satisfy cases last
			{

				System.out.println("Doing the top comparison at [" + width + "][" + height + "]");
				streak = Integer.max(Integer.max(checkWin(width, height, 0, 3), checkWin(width, height, 0, 5)), Integer.max(checkWin(width, height, 0, 4), checkWin(width, height, 0, 2) + checkWin(width, height, 0, 6)));
			}
			else if (b)
			{

				System.out.println("Doing the bottom comparison at [" + width + "][" + height + "]");
				streak = Integer.max(Integer.max(checkWin(width, height, 0, 1), checkWin(width, height, 0, 7)), Integer.max(checkWin(width, height, 0, 0), checkWin(width, height, 0, 2) + checkWin(width, height, 0, 6)));
			}
			else if (l)
			{

				System.out.println("Doing the left comparison at [" + width + "][" + height + "]");
				streak = Integer.max(Integer.max(checkWin(width, height, 0, 3), checkWin(width, height, 0, 1)), Integer.max(checkWin(width, height, 0, 2), checkWin(width, height, 0, 0) + checkWin(width, height, 0, 4)));
			}
			else if (r)
			{

				System.out.println("Doing the right comparison at [" + width + "][" + height + "]");
				streak = Integer.max(Integer.max(checkWin(width, height, 0, 5), checkWin(width, height, 0, 7)), Integer.max(checkWin(width, height, 0, 6), checkWin(width, height, 0, 0) + checkWin(width, height, 0, 4)));
			}
			else
			{

				System.out.println("Shouldn't be here at [" + width + "][" + height + "]");
			}
		}
		else
		{
			System.out.println("Doing the full comparison at [" + width + "][" + height + "]");
			streak = Integer.max(Integer.max(checkWin(width, height, 0, 0) + checkWin(width, height, 0, 4), checkWin(width, height, 0, 1) + checkWin(width, height, 0, 5)), Integer.max(checkWin(width, height, 0, 2) + checkWin(width, height, 0, 6), checkWin(width, height, 0, 3) + checkWin(width, height, 0, 7)));
		}
		
		if (streak + 1 >= winner)
		{
			winning = true;
		}
	}
	
	/* DIRECTION INDEX:
	 * 0 = T
	 * 1 = TR
	 * 2 = R
	 * 3 = BR
	 * 4 = B
	 * 5 = BL
	 * 6 = L
	 * 7 = TL
	 */
	public int checkWin(int width, int height, int streak, int direction)
	{
		switch(direction)
		{
		case 0:
			System.out.println("Checking ^ at [" + width + "][" + height + "]");
			if (height != 0)
			{
				if (values[width][height] == values[width][height - 1])
				{
					streak ++;
					System.out.println("Still going. " + streak);
					return checkWin(width, height - 1, streak, direction);
				}
			}
			System.out.println("Stopping. " + streak);
			return streak;
		case 1:
			System.out.println("Checking /^ at [" + width + "][" + height + "]");
			if (height != 0 && width != dimension - 1)
			{
				if (values[width][height] == values[width + 1][height - 1])
				{
					streak ++;
					System.out.println("Still going. " + streak);
					return checkWin(width + 1, height - 1, streak, direction);
				}
			}
			System.out.println("Stopping. " + streak);
			return streak;
		case 2:
			System.out.println("Checking > at [" + width + "][" + height + "]");
			if (width != dimension - 1)
			{
				if (values[width][height] == values[width + 1][height])
				{
					streak ++;
					System.out.println("Still going. " + streak);
					return checkWin(width + 1, height, streak, direction);
				}
			}
			System.out.println("Stopping. " + streak);
			return streak;
		case 3:
			System.out.println("Checking \\v at [" + width + "][" + height + "]");
			if (height != dimension - 1 && width != dimension - 1)
			{
				if (values[width][height] == values[width + 1][height + 1])
				{
					streak ++;
					System.out.println("Still going. " + streak);
					return checkWin(width + 1, height + 1, streak, direction);
				}
			}
			System.out.println("Stopping. " + streak);
			return streak;
		case 4:
			System.out.println("Checking v at [" + width + "][" + height + "]");
			if (height != dimension - 1)
			{
				if (values[width][height] == values[width][height + 1])
				{
					streak ++;
					System.out.println("Still going. " + streak);
					return checkWin(width, height + 1, streak, direction);
				}
			}
			System.out.println("Stopping. " + streak);
			return streak;
		case 5:
			System.out.println("Checking v/ at [" + width + "][" + height + "]");
			if (height != dimension - 1 && width != 0)
			{
				if (values[width][height] == values[width - 1][height + 1])
				{
					streak ++;
					System.out.println("Still going. " + streak);
					return checkWin(width - 1, height + 1, streak, direction);
				}
			}
			System.out.println("Stopping. " + streak);
			return streak;
		case 6:
			System.out.println("Checking < at [" + width + "][" + height + "]");
			if (width != 0)
			{
				if (values[width][height] == values[width - 1][height])
				{
					streak ++;
					System.out.println("Still going. " + streak);
					return checkWin(width - 1, height, streak, direction);
				}
			}
			System.out.println("Stopping. " + streak);
			return streak;
		case 7:
			System.out.println("Checking ^\\ at [" + width + "][" + height + "]");
			if (height != 0 && width != 0)
			{
				if (values[width][height] == values[width - 1][height - 1])
				{
					streak ++;
					System.out.println("Still going. " + streak);
					return checkWin(width - 1, height - 1, streak, direction);
				}
			}
			System.out.println("Stopping. " + streak);
			return streak;
		}
		return 0;
	}
	
	public void reset()
	{
		System.out.println("Resetting...");
		for (int i = 0; i < values.length; i++)
		{
			for (int j = 0; j < values[i].length; j++)
			{
				values[i][j] = 0;
				buttons[i][j].setBackground(Color.WHITE);
			}
		}
	}
	
	public void updateGameInfo(int longest)
	{
		String info = "";
		info += "Grid size: " + dimension + "x" + dimension + "<br/>";
		info += "Score to win: " + winner + " in a row.<br/>";
		info += "Longest current line: " + longest + " spaces.";
		gameInfo.setText("<html>" + info + "<html>");
	}
}






