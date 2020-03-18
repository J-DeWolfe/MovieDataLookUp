package MovieDataLookup;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
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
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MovieDataLookupGUI extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private String defaultPosterURL = "https://2.bp.blogspot.com/_Yt3M33fzOLA/SGDCeUlyXbI/AAAAAAAAF3I/hnxQJ0rfiNk/s400/ew25poster01.jpg";
	private JLabel apiKeyPrompt = new JLabel("Enter your OMDB API key:");
	private JLabel titlePrompt = new JLabel("Enter a title to search for:");
	private JLabel yearPrompt = new JLabel("Enter the year of release (optional):");
	private JTextField apiKeyField = new JTextField(8);
	private JTextField yearField = new JTextField(4);
	private JTextField titleField = new JTextField(24);
	private JButton searchButton = new JButton("SEARCH");
	private JTextArea movieDataOutput = new JTextArea("To retrieve data, enter your API key and a movie title .");
	private BufferedImage moviePoster;
	private JLabel moviePosterOutput = new JLabel();
	
	Color PALE = new Color(249, 233, 172); //Still deciding on a color scheme
	Color DARK = new Color(61, 84, 108);
	int width;
	int margin = 4;
	int border = 8;
	
	MovieDataLookupGUI()
	{
		super("Movie Data Lookup");
		setSize(1000, 1000); //Larger than needed
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setVisible(true);
		width = this.getContentPane().getWidth();
		
		//NORTH SECTION
		JPanel userPanel = new JPanel();
		userPanel.setBorder(BorderFactory.createEmptyBorder(border, border, margin, border)); 
		userPanel.setLayout(new GridLayout(2, 1));
		userPanel.setBackground(DARK);
		
		JPanel inputSubpanel = new JPanel();
		inputSubpanel.setLayout(new FlowLayout());
		
		//TextFields
		inputSubpanel.add(apiKeyPrompt);
		inputSubpanel.add(apiKeyField);
		inputSubpanel.add(titlePrompt);
		inputSubpanel.add(titleField);
		inputSubpanel.add(yearPrompt);
		inputSubpanel.add(yearField);	
		titleField.addActionListener(this);
		apiKeyField.addActionListener(this);
		yearField.addActionListener(this);
		
		inputSubpanel.setBackground(PALE);
		userPanel.add(inputSubpanel);
		
		//Button
		JPanel buttonSubpanel = new JPanel();
		buttonSubpanel.setLayout(new FlowLayout());
		buttonSubpanel.add(searchButton);
		searchButton.addActionListener(this);
		buttonSubpanel.setBackground(PALE);
		userPanel.add(buttonSubpanel);
		
		add(userPanel, BorderLayout.NORTH);
		
		//SOUTH SECTION
		JPanel resultPanel = new JPanel();
		resultPanel.setBorder(BorderFactory.createEmptyBorder(margin, border, border, border)); 
		resultPanel.setLayout(new GridLayout(1, 2, border, 0));
		
		resultPanel.add(movieDataOutput);
		movieDataOutput.setEditable(false);
		movieDataOutput.setLineWrap(true);
		movieDataOutput.setWrapStyleWord(true);
		
		resultPanel.add(moviePosterOutput);
		moviePosterOutput.setVerticalAlignment(JLabel.NORTH);
		showPoster();
		resultPanel.setBackground(DARK);
		
		add(resultPanel, BorderLayout.CENTER);
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == searchButton)
		{
			if (apiKeyField.getText().trim().length() != 0 && titleField.getText().trim().length() != 0)
			{
				String y = "";
				String k = apiKeyField.getText();
				String t = titleField.getText();
				if (yearField.getText().trim().length() != 0)
					y = yearField.getText();
				
				try
				{ 
					movieDataOutput.setText(MovieDataLookup.getMovieDataString(k, t, y)); 
				}		
				catch (IOException e1) {e1.printStackTrace(); }
				showPoster((String)MovieDataLookup.getPosterURL());
			}
		}
	}
	
	public String getDefaultPosterURL() { return defaultPosterURL; }
	
	public void showPoster() { showPoster(defaultPosterURL); }
	
	public void showPoster(String p)
	{
		URL posterURL;
		
		try
		{ 
			posterURL = new URL(p);
			moviePoster = ImageIO.read(posterURL);
			int w = (width/2)-border-margin;
			float scaleRatio = (float)w/(float)moviePoster.getWidth();
			int h = (int)(scaleRatio * moviePoster.getHeight());
			
			Image scaledMoviePosted = moviePoster.getScaledInstance(w, h, Image.SCALE_SMOOTH);
			moviePosterOutput.setIcon(new ImageIcon(scaledMoviePosted));
		} 
		catch (MalformedURLException e1) { if (p != defaultPosterURL) showPoster(); }
		catch (IOException e1) { e1.printStackTrace(); }
	}	
}
//c87439fd

