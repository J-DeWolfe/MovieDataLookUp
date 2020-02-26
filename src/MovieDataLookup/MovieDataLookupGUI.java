package MovieDataLookup;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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
	private JTextArea movieDataOutput = new JTextArea("Enter a title to retrieve movie data.");

	private BufferedImage moviePoster;
	private JLabel moviePosterOutput = new JLabel();
	
	MovieDataLookupGUI()
	{
		super("Movie Data Lookup");
		setSize(625, 675);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		Border margins = BorderFactory.createEmptyBorder(8, 8, 8, 8);
		
		//TOP
		JPanel userPanel = new JPanel();
		userPanel.setBorder(margins);
		userPanel.setLayout(new GridLayout(3, 1));
		setVisible(true);
		
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
		
		resultPanel.add(moviePosterOutput);
		moviePosterOutput.setVerticalAlignment(JLabel.NORTH);
		add(resultPanel, BorderLayout.CENTER);
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
			
			URL posterURL;
			try
			{ 
				posterURL = new URL(MovieDataLookup.getPosterURL());
				moviePoster = ImageIO.read(posterURL);
				moviePosterOutput.setIcon(new ImageIcon(moviePoster));
				
			} 
			catch (MalformedURLException e1) { e1.printStackTrace(); }
			catch (IOException e1) { e1.printStackTrace(); }
	
		}
	}
}


