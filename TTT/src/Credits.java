import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;

public class Credits extends JFrame
{
	final int WIDTH = (int) (Toolkit.getDefaultToolkit().getScreenSize().width / 3);
	final int HEIGHT = (int) (Toolkit.getDefaultToolkit().getScreenSize().height / 2);
	
	JList contributors;
	ArrayList<String> data;
	
	public Credits()
	{
		setSize(WIDTH, HEIGHT);
		setTitle("Credits");
		
		data = new ArrayList<String>();
		
		data.add("Contributors:");
		
		data.add("Jimmy Patterson, <<GAME DIRECTOR>>");
		data.add("David Hall, Designer");
		data.add("Jackson Ekis, Moana (TM) Fanatic");
		
		String[] contents = new String[data.size()];
		
		for (int i = 0; i < contents.length; i++)
		{
			contents[i] = data.get(i);
		}
		
		contributors = new JList(contents);
		
		contributors.setSize(9*WIDTH/10, 9*HEIGHT/10);
		contributors.setLocation(10, 10);
		
		add(contributors);
		
		
		add(new JLabel(""));
		setVisible(true);
	}
	
}
