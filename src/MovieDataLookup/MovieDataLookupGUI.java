package MovieDataLookup;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class MovieDataLookupGUI extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;
	
	private JLabel apiKeyPrompt = new JLabel("Enter your OMDB API key:");
	private JLabel titlePrompt = new JLabel("Enter a title to search for:");
	private JTextField apiKeyField = new JTextField(8);
	private JTextField titleField = new JTextField(24);
	private JButton searchButton = new JButton("SEARCH");
	private JTextArea movieDataOutput = new JTextArea("Placeholder Text");
	private JTextArea placeholder = new JTextArea("Placeholder Image");
	
	MovieDataLookupGUI()
	{
		super("Movie Data Lookup");
		setSize(700, 700);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		Border margins = BorderFactory.createEmptyBorder(8, 8, 8, 8);
		
		//TOP
		JPanel userPanel = new JPanel();
		userPanel.setBorder(margins);
		userPanel.setLayout(new GridLayout(3, 1));
		
		JPanel akpIkeySubpanel = new JPanel();
		akpIkeySubpanel.setLayout(new FlowLayout());
		akpIkeySubpanel.add(apiKeyPrompt);
		akpIkeySubpanel.add(apiKeyField);
		apiKeyField.addActionListener(this);
		userPanel.add(akpIkeySubpanel);
		
		JPanel titleSubpanel = new JPanel();
		titleSubpanel.setLayout(new FlowLayout());
		titleSubpanel.add(titlePrompt);
		titleSubpanel.add(titleField);
		titleField.addActionListener(this);
		userPanel.add(titleSubpanel);
		
		JPanel buttonSubpanel = new JPanel();
		buttonSubpanel.setLayout(new FlowLayout());
		buttonSubpanel.add(searchButton);
		searchButton.addActionListener(this);
		userPanel.add(buttonSubpanel);
		
		add(userPanel, BorderLayout.NORTH);
		
		//MIDDLE
		JPanel resultPanel = new JPanel();
		resultPanel.setBorder(margins);
		resultPanel.setLayout(new GridLayout(1, 2, 8, 0));
		resultPanel.add(movieDataOutput);
		movieDataOutput.setEditable(false);
		movieDataOutput.setLineWrap(true);
		movieDataOutput.setWrapStyleWord(true);
		resultPanel.add(placeholder);
		add(resultPanel, BorderLayout.CENTER);
		
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == searchButton)
		{
			String k = apiKeyField.getText();
			String t = titleField.getText();
			try { movieDataOutput.setText(MovieDataLookup.getMovieDataString(k, t)); }
			catch (IOException e1) {e1.printStackTrace(); }
		}
	}
}

//c87439fd
