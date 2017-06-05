package ClientInterface;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ServerGUI
{
	private static JFrame sInterface;
	private static JScrollPane scrollPane;
	private static JTextArea textArea;

	public static JScrollPane getScrollPane()
	{
		return scrollPane;
	}

	public static JTextArea getTextArea()
	{
		return textArea;
	}

	public static JFrame getsInterface()
	{
		return sInterface;
	}

	public ServerGUI()
	{

		initialize();
		sInterface.setVisible(true);
	}

	private void initialize()
	{
		sInterface = new JFrame();
		sInterface.setBounds(100, 100, 450, 300);
		sInterface.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		sInterface.getContentPane().setLayout(null);
		sInterface.setTitle("Server");
		sInterface.setResizable(false);
		scrollPane = new JScrollPane();
		scrollPane.setBounds(27, 21, 400, 235);
		sInterface.getContentPane().add(scrollPane);
		textArea = new JTextArea();
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
		

	}
}
