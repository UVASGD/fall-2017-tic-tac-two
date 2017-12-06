import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;

public class CNCT4 extends JFrame
{
	final int WIDTH = (int) (Toolkit.getDefaultToolkit().getScreenSize().width / 3);
	final int HEIGHT = (int) (Toolkit.getDefaultToolkit().getScreenSize().height / 2);
	
	JButton[][] spots;
	int[][] values;
	JButton[] inputs;
	
	int playerIndex;
	boolean winning;
	
	JButton returnToMenu;
	
	public CNCT4()
	{
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(WIDTH, HEIGHT);
		setTitle("Tic Tac Two");
		
		System.out.println(WIDTH/10 + " " + HEIGHT/10);
		
		playerIndex = 1;
		winning = false;
		spots = new JButton[7][7];
		values = new int[7][7];
		for (int i = 0; i < spots.length; i++)
		{
			for (int j = 0; j < spots[i].length; j++)
			{
				values[i][j] = 0;
				spots[i][j] = new JButton(" ");
				spots[i][j].setSize(9*WIDTH/100, 9*HEIGHT/100);
				spots[i][j].setLocation((i)*WIDTH/10 + 10, (j+2)*HEIGHT/10);
				System.out.println(i + " " + j + " " + ((i)*WIDTH/10 + 10) + " " + ((j+2)*HEIGHT/10));
				spots[i][j].setBackground(Color.BLUE);
				spots[i][j].setVisible(true);
				spots[i][j].setEnabled(false);
				add(spots[i][j]);
				//dropLabel(new int[]{(i+1)*WIDTH/10 + 10, (j+2)*HEIGHT/10}, new int[]{9*WIDTH/100, 9*HEIGHT/100}, new int[]{i, j}, true);
			}
		}
		
		inputs = new JButton[7];
		for (int i = 0; i < inputs.length; i++)
		{
			final int mi = i;
			inputs[i] = new JButton("Drop!");
			inputs[i].setSize(9*WIDTH/100, 9*HEIGHT/100);
			inputs[i].setLocation(i * WIDTH/10 + 10, HEIGHT/10);
			inputs[i].setBackground(Color.WHITE);
			inputs[i].addActionListener((ActionEvent event)->{
				System.out.println("Button number " + mi + " pressed.");
				drop(mi);
			});
			System.out.println(i + " " + ((i+1)*WIDTH/10 + 10) + " " + (HEIGHT/10));
			inputs[i].setVisible(true);
			add(inputs[i]);
		}
		
		returnToMenu = new JButton("Return");
		returnToMenu.setLocation(8*WIDTH/10, 2*HEIGHT/10);
		returnToMenu.setSize(9*WIDTH/100, 9*HEIGHT/100);
		returnToMenu.setVisible(true);
		returnToMenu.addActionListener((ActionEvent event) -> {
			returnToMenu();
		});
		add(returnToMenu);

		add(new JLabel());
		setVisible(true);
	}
	
	public void drop(int column)
	{
		for (int i = 6; i >= -1; i--)
		{
			System.out.println("Trying drop on column " + column + ", i = " + i);
			if (i == -1)
			{
				System.out.println("Column full! Try another column!");
			}
			else if (values[column][i] == 0)
			{
				if (playerIndex == 1)
				{
					spots[column][i].setBackground(Color.RED);
					values[column][i] = 1;
					checkWin(column, i);
					playerIndex = 2;
					i = -1;
				}
				else
				{
					spots[column][i].setBackground(Color.YELLOW);
					values[column][i] = 2;
					checkWin(column, i);
					playerIndex = 1;
					i = -1;
				}
			}
			
			if (winning)
			{
				JOptionPane.showMessageDialog(null, "<html><font face='Arial' size = 15>Player " + playerIndex + " has won!");
				reset();
			}
		}
	}
	
//	public void checkWin(int column, int row)
//	{
//		int streak = Integer.max(Integer.max(Integer.max(checkWin(column, row, 0, 0), checkWin(column, row, 1, 0)), Integer.max(checkWin(column, row, 2, 0), checkWin(column, row, 3, 0))), checkWin(column, row, 4, 0));
//		System.out.println("Streak: " + streak);
//		if (streak >= 3)
//		{
//			winning = true;
//		}
//	}
	
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
		
		int streak = 0;
		int[] directions = {0, 0, 0, 0, 0, 0, 0, 0};
		try {
			directions[0] = checkWin(width, height, 0, 0);
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			t = true;
		}
		try {
			directions[1] = checkWin(width, height, 0, 1);
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			t = true;
			r = true;
		}
		try {
			directions[2] = checkWin(width, height, 0, 2);
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			r = true;
		}
		try {
			directions[3] = checkWin(width, height, 0, 3);
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			b = true;
			r = true;
		}
		try {
			directions[4] = checkWin(width, height, 0, 4);
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			b = true;
		}
		try {
			directions[5] = checkWin(width, height, 0, 5);
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			b = true;
			l = true;
		}
		try {
			directions[6] = checkWin(width, height, 0, 6);
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			l = true;
		}
		try {
			directions[7] = checkWin(width, height, 0, 7);
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			t = true;
			l = true;
		}
		
		for (int i = 0; i < directions.length; i++)
		{
			System.out.println(i + ": " + directions[i]);
		}
		
		if (l || r || t || b)
		{
			if (l && t)
			{
				System.out.println("Doing the top left comparison at [" + width + "][" + height + "]");
				streak = Integer.max(Integer.max(directions[2], directions[3]), directions[4]);
			}
			else if (l && b)
			{
				System.out.println("Doing the bottom left comparison at [" + width + "][" + height + "]");
				streak = Integer.max(Integer.max(directions[0], directions[1]), directions[2]);
			}
			else if (r && t)
			{
				System.out.println("Doing the top right comparison at [" + width + "][" + height + "]");
				streak = Integer.max(Integer.max(directions[4], directions[5]), directions[6]);
			}
			else if (r && b)
			{
				System.out.println("Doing the bottom right comparison at [" + width + "][" + height + "]");
				streak = Integer.max(Integer.max(directions[5], directions[6]), directions[7]);
			}
			else if (t) //make sure we hit the easy-to-satisfy cases last
			{
				System.out.println("Doing the top comparison at [" + width + "][" + height + "]");
				streak = Integer.max(Integer.max(directions[4], directions[2] + directions[6]), Integer.max(directions[3], directions[5]));
			}
			else if (b)
			{
				System.out.println("Doing the bottom comparison at [" + width + "][" + height + "]");
				streak = Integer.max(Integer.max(directions[0], directions[2] + directions[6]), Integer.max(directions[7], directions[1]));
			}
			else if (l)
			{
				System.out.println("Doing the left comparison at [" + width + "][" + height + "]");
				streak = Integer.max(Integer.max(directions[2], directions[4] + directions[0]), Integer.max(directions[1], directions[3]));
			}
			else if (r)
			{
				System.out.println("Doing the right comparison at [" + width + "][" + height + "]");
				streak = Integer.max(Integer.max(directions[6], directions[4] + directions[0]), Integer.max(directions[5], directions[7]));
			}
			else
			{
				System.out.println("Shouldn't be here at [" + width + "][" + height + "]");
			}
		}
		else
		{
			System.out.println("Doing the full comparison at [" + width + "][" + height + "]");
			streak = Integer.max(Integer.max(directions[4] + directions[0], directions[2] + directions[6]), Integer.max(directions[3] + directions[7], directions[5] + directions[1]));
		}
		
		
		if (streak + 1 >= 4)
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
		if (values[width][height] != 0)
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
				if (height != 0 && width != 6)
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
				if (width != 6)
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
				if (height != 6 && width != 6)
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
				if (height != 6)
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
				if (height != 6 && width != 0)
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
		}
		return 0;
	}
	
	public void reset()
	{
		for (int i = 0; i < spots.length; i++)
		{
			for (int j = 0; j < spots[i].length; j++)
			{
				if (values[i][j] == 2)
				{
					spots[i][j].setBackground(Color.GREEN);
				}
				if (values[i][j] == 1)
				{
					spots[i][j].setBackground(Color.PINK);
				}
				if (values[i][j] == 0)
				{
					spots[i][j].setBackground(Color.WHITE);
				}
				spots[i][j].setEnabled(false);
				returnToMenu.setVisible(true);
			}
		}
	}
	
	public void returnToMenu()
	{
		dispose();
	}
}

























