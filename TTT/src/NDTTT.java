import javax.swing.*;
import javax.swing.JOptionPane;
import java.awt.*;
import java.awt.event.ActionEvent;

import java.util.ArrayList;

public class NDTTT extends JFrame
{

	public static ArrayList<NDTTT> currgames = new ArrayList<NDTTT>();

	final int WIDTH = (int) (Toolkit.getDefaultToolkit().getScreenSize().width / 3);
	final int HEIGHT = (int) (Toolkit.getDefaultToolkit().getScreenSize().height / 2);

	JButton b1 = new JButton();
	JButton b2 = new JButton();
	JButton b3 = new JButton();
	JButton b4 = new JButton();
	JButton b5 = new JButton();
	JButton b6 = new JButton();
	JButton b7 = new JButton();
	JButton b8 = new JButton();
	JButton b9 = new JButton();
	JButton[] buttons = {b1, b2, b3, b4, b5, b6, b7, b8, b9};

	JTextArea a1 = new JTextArea();
	JTextArea a2 = new JTextArea();
	JTextArea a3 = new JTextArea();
	JTextArea a4 = new JTextArea();
	JTextArea a5 = new JTextArea();
	JTextArea a6 = new JTextArea();
	JTextArea a7 = new JTextArea();
	JTextArea a8 = new JTextArea();
	JTextArea a9 = new JTextArea();
	JTextArea[] texts = {a1, a2, a3, a4, a5, a6, a7, a8, a9};

	JButton btnAI = new JButton("AI Settings");
	JButton restart = new JButton("Restart");

	JTextArea turn = new JTextArea();
	JTextArea winner = new JTextArea();
	JTextArea settingsAI = new JTextArea("AI: Player 2");

	int computerPlayer = -1;
	int AILevel = 1;

	public boolean gamesOver = false;

	public int endState = 0;

	int turns = 0;
	String activePlayer = "Player 1";
	int[] values = new int[9]; //0 means no value- 1 means player1, -1 means player2
	boolean whosTurn = true; //true = player1, false = player2
	int[][] locations = {{WIDTH/10, HEIGHT/10}, {3*WIDTH/10, HEIGHT/10}, {5*WIDTH/10, HEIGHT/10}, {WIDTH/10, 3*HEIGHT/10}, {3*WIDTH/10, 3*HEIGHT/10}, {5*WIDTH/10, 3*HEIGHT/10}, {WIDTH/10, 5*HEIGHT/10}, {3*WIDTH/10, 5*HEIGHT/10}, {5*WIDTH/10, 5*HEIGHT/10}, {10, 10}, {10, 8*HEIGHT/10}, {8*WIDTH/10, 8*HEIGHT/10}, {6*WIDTH/10,8*HEIGHT/10}, {7*WIDTH/10,HEIGHT/10}};

	public NDTTT() //more code
	{
		while(currgames.size() > 0) {
			NDTTT game = currgames.remove(0);
			game.dispose();
		}
		setSize(WIDTH, HEIGHT);
		setTitle("Tic Tac Toe");
		addObjects();
		addActions();
		setVisible(true);
		currgames.add(this);
	}

	public NDTTT(int[] currentState, int newMove, String current_player) {
		setSize(WIDTH, HEIGHT);
		setTitle("Tic Tac Toe");
		addObjects();
		addActions();
		setVisible(true);
		activePlayer = current_player;
		for(int i = 0; i < currentState.length; i++) {
			values[i] = currentState[i];
			buttons[i].setText((currentState[i] == 1 ? "X" : (currentState[i] == -1 ? "O" : "")));
			if(values[i] != 0) {
				turns++;
			}
		}
		buttonPress(buttons[newMove], newMove, true);
	}

	public void addObjects()
	{
		for (int i = 0; i < buttons.length; i++)
		{
			drop(WIDTH/12, HEIGHT/10, locations[i], true, buttons[i]);
			drop(WIDTH/12, HEIGHT/10, locations[i], false, texts[i]);
			texts[i].setEditable(false);
			texts[i].setBackground(Color.WHITE);
		}
		drop(WIDTH/16, 3*HEIGHT/10, locations[9], true, turn);
		turn.setEditable(false);
		turn.setBackground(this.getBackground());
		turn.setText("Current Player: " + activePlayer);
		drop(WIDTH/16, 3*HEIGHT/10, locations[10], true, winner);
		winner.setEditable(false);
		winner.setBackground(this.getBackground());
		winner.setText("No winner yet!");
		drop(WIDTH/16, 3*HEIGHT/40, locations[11], false, restart);
		drop(WIDTH/16, HEIGHT/10, locations[12], true, btnAI);
		drop(WIDTH/16, HEIGHT/8, locations[13], true, settingsAI);

		JLabel l = new JLabel(/*WIDTH + " " + HEIGHT*/); //for some reason, adding a stupid label makes it all come together.
		add(l);
	}

	public void addActions()
	{
		buttons[0].addActionListener((ActionEvent event)->{
			buttonPress(buttons[0], 0);
		});
		buttons[1].addActionListener((ActionEvent event)->{
			buttonPress(buttons[1], 1);
		});
		buttons[2].addActionListener((ActionEvent event)->{
			buttonPress(buttons[2], 2);
		});
		buttons[3].addActionListener((ActionEvent event)->{
			buttonPress(buttons[3], 3);
		});
		buttons[4].addActionListener((ActionEvent event)->{
			buttonPress(buttons[4], 4);
		});
		buttons[5].addActionListener((ActionEvent event)->{
			buttonPress(buttons[5], 5);
		});
		buttons[6].addActionListener((ActionEvent event)->{
			buttonPress(buttons[6], 6);
		});
		buttons[7].addActionListener((ActionEvent event)->{
			buttonPress(buttons[7], 7);
		});
		buttons[8].addActionListener((ActionEvent event)->{
			buttonPress(buttons[8], 8);
		});
		restart.addActionListener((ActionEvent event)->{
			restart();
		});
		btnAI.addActionListener((ActionEvent event)->{
			setAI();
		});
	}

	public void buttonPress(JButton b, int index) {
		buttonPress(b, index, false);
	}

	public void buttonPress(JButton b, int index, boolean setup)
	{
		//System.out.println("Processing turn for " + activePlayer + " accessing space #" + index);
		if (b.getText().equals("X") == false && b.getText().equals("O") == false) //space must be unoccupied
		{
			//System.out.println("Space #" + index + " unoccupied for " + activePlayer);
			if (activePlayer.equals("Player 1"))
			{
				b.setText("X");
				values[index] = 1;
			}
			else
			{
				b.setText("O");
				values[index] = -1;
			}
			turns++;
			if (turns == 9)
			{
				testWinConditions(true);
			}
			testWinConditions(false);
			if(!gamesOver)
			{
				if (activePlayer.equals("Player 1"))
				{
					activePlayer = "Player 2";
				}
				else
				{
					activePlayer = "Player 1";
				}
				turn.setText("Current Player: " + activePlayer);
				if (!setup) {
					checkAITurn();
				}
				btnAI.setVisible(false);
			}
		}
	}

	public void AITurn(int level)
	{
		boolean tripwire = false;
		int firstMove = 10;
		for(int i = 0; i < values.length; i++) {
			int temp = values[i];
			if(temp == 0) {
				if(tripwire) {
					currgames.add(new NDTTT(values, i, activePlayer));
				} else {
					tripwire = true;
					firstMove = i;
				}
			}
		}
		if(tripwire) {
			buttonPress(buttons[firstMove], firstMove);
		}
	}


	public void drop(int h, int w, int[] pt, boolean visible, JComponent comp)
	{
		comp.setLocation(pt[0], pt[1]);
		comp.setSize(w, h);
		add(comp);
		comp.setVisible(visible);
	}

	public void testWinConditions(boolean last)
	{
		for (int i = 0; i < values.length; i++)
		{
			if (i == 0)
			{
				if (Math.abs(values[i] + values[i+4] + values[i+8]) == 3) //L to R diagonal
				{
					//win
					winner.setText(activePlayer + " has won!");
					if((computerPlayer > 0 && activePlayer.equals("Player 1")) || (computerPlayer < 0 && activePlayer.equals("Player 2"))) {
						endState = -1;
					} else {
						endState = 1;
					}
					gameOver();
				}
			}
			if (i == 2)
			{
				if (Math.abs(values[i] + values[i+2] + values[i+4]) == 3) //R to L diagonal
				{
					//win
					winner.setText(activePlayer + " has won!");
					if((computerPlayer > 0 && activePlayer.equals("Player 1")) || (computerPlayer < 0 && activePlayer.equals("Player 2"))) {
						endState = -1;
					} else {
						endState = 1;
					}
					gameOver();
				}
			}
			if (i < 3)
			{
				if (Math.abs(values[i] + values[i+3] + values[i+6]) == 3) //vertical
				{
					//win
					winner.setText(activePlayer + " has won!");
					if((computerPlayer > 0 && activePlayer.equals("Player 1")) || (computerPlayer < 0 && activePlayer.equals("Player 2"))) {
						endState = -1;
					} else {
						endState = 1;
					}
					gameOver();
				}
			}
			if (i % 3 == 0)
			{
				if (Math.abs(values[i] + values[i+1] + values[i+2]) == 3) //horizontal
				{
					//win
					winner.setText(activePlayer + " has won!");
					if((computerPlayer > 0 && activePlayer.equals("Player 1")) || (computerPlayer < 0 && activePlayer.equals("Player 2"))) {
						endState = -1;
					} else {
						endState = 1;
					}
					gameOver();
				}
			}
		}
		if (last)
		{
			//cat game
			winner.setText("It's a cat game.");
			endState = 0;
			gameOver();
		}
	}

	public void gameOver()
	{
		for (int i = 0; i < buttons.length; i++)
		{
			buttons[i].setEnabled(false);
		}
		gamesOver = true;
		btnAI.setVisible(true);
		restart.setVisible(true);
		boolean notFinishedYet = false;
		int standing = 0;
		for(int i = 0; i < currgames.size(); i++) {
			if(!currgames.get(i).gamesOver) {
				notFinishedYet = true;
			} else {
				standing += currgames.get(i).endState;
			}
		}
		if(!notFinishedYet) {
			JOptionPane.showMessageDialog(null, "Fuck you!");
			if(standing > 0) {
				JOptionPane.showMessageDialog(null, "You won more matches than you lost. What a smart, smart man.");
			} else {
				JOptionPane.showMessageDialog(null, "FUCK YOU BALTIMORE! if you're dumb enough to buy a new car this weekend, you're a big enough schmuck to come to Big Bill Hell's Cars. Bad Deals! Cars that break down! Thieves! If you think you can find a bargain at Big Bill's, you can kiss my ass! It's our belief that you're such a stupid motherfucker, that you'll fall for this bullshit GUARANTIED! If you find a better deal, shove it up your ugly ass! you heard us right, SHOVE IT UP YOUR UGLY ASS! Bring your tray, bring your title, bring your wife, WE'LL FUCK HER. That's right, we'll fuck your wife! Because at Big Bill Hell's, you're fucked six ways from sunday. Take a hike, to Big Bill Hell's: home of CHALLENGE PISSING - that's right - CHALLENGE PISSING. How does it work? If you can piss six feet into the air straight up, and not get wet, you get no down payment! Don't wait! Don't delay! DON'T FUCK WITH US, or we'll rip your nuts off. Only at Big Bill Hell's: the only dealer that tells you to FUCK OFF! HURRY UP ASSHOLE! This event ends the minute after you write us a check, and it better not bounce, or you're a dead motherfucker. GO TO HELL! Big Bill Hell's cars. From the most filthy and exclusive the meanest sons-of-bitches in the state of Maryland - GUARANTIED!");
			}
			while(currgames.size() > 0) {
				NDTTT game = currgames.remove(0);
				game.dispose();
			}
			System.out.println("Standing was " + standing);
		}
	}

	public void restart()
	{
		for (int i = 0; i < buttons.length; i++)
		{
			buttons[i].setEnabled(true);
			buttons[i].setText("");
			values[i] = 0;
			turns = 0;
			activePlayer = "Player 1";
			turn.setText("Current Player: " + activePlayer);
		}
		winner.setText("No winners yet...");
		gamesOver = false;
		restart.setVisible(false);
		checkAITurn();
	}

	public void setAI()
	{
		String input = JOptionPane.showInputDialog("How should the AI be set?\n0: No AI\n1: AI Level 1\n2: AI Level 2\n3: AI Level 3\nA negative value makes the AI Player 2.");
		computerPlayer = Integer.parseInt(input);
		String AISet = "";
		if (computerPlayer == 0)
		{
			AISet = "off";
		}
		else if (computerPlayer > 1)
		{
			AILevel = Math.abs(computerPlayer);
			AISet = "Player 1";
		}
		else
		{
			AILevel = Math.abs(computerPlayer);
			AISet = "Player 2";
		}
		settingsAI.setText("AI: " + AISet + " level " + Math.abs(computerPlayer));
		checkAITurn();
	}

	public void checkAITurn()
	{
		if (computerPlayer > 0 && activePlayer.equals("Player 1"))
		{
			AITurn(AILevel);
		}
		else if (computerPlayer < 0 && activePlayer.equals("Player 2"))
		{
			AITurn(AILevel);
		}
	}

	public static void main(String[] args)
	{
//		EventQueue.invokeLater(() -> {
//			Game go = new Game();
//			go.setVisible(true);
//		});
	}

}
