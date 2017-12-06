import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;

public class Credits extends JFrame
{
	final int WIDTH = (int) (Toolkit.getDefaultToolkit().getScreenSize().width / 3);
	final int HEIGHT = (int) (Toolkit.getDefaultToolkit().getScreenSize().height / 2);

	JList contributors;
	JButton quit;
	ArrayList<String> data;

	Font bigfont = new Font("Times", Font.PLAIN, 30);
	
	public Credits()
	{
		setSize(WIDTH, HEIGHT);
		setTitle("Credits");

		data = new ArrayList<String>();

		data.add("Contributors:");

		data.add("Jimmy Patterson, <<GAME DIRECTOR>>");
		data.add("David Hall, Designer");
		data.add("Jackson Ekis, Moana (TM) Fanatic");
		data.add("Alexander Kwakye");
		data.add("Jack Herd, <<CHARISMATIC LEADER>>");
		data.add("Lauren Johnson, Beet Expert");
		String[] contents = new String[data.size()];

		for (int i = 0; i < contents.length; i++)
		{
			contents[i] = data.get(i);
		}

		contributors = new JList(contents);

		contributors.setSize(9*WIDTH/10, 6*HEIGHT/10);
		contributors.setLocation(10, 10);
		if (WIDTH * 3 > 3200)
			contributors.setFont(bigfont);
		
		add(contributors);

		quit = new JButton("QUIT");
		quit.addActionListener((ActionEvent event) -> {
			System.exit(0);
		});
		quit.setLocation(8*WIDTH/10, 8*HEIGHT/10);
		quit.setSize(WIDTH/10, HEIGHT/12);
		add(quit);
		if (WIDTH * 3 > 3200)
			quit.setFont(bigfont);
		
		add(new JLabel(""));
		setVisible(true);
	}

}
