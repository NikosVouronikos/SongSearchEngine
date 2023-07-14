package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.LockObtainFailedException;
import indexing.Initializer;

@SuppressWarnings({ "unused", "serial" })
public class GraphicalInterface extends JFrame {
	
	private JPanel contentPanel;
	private ArrayList<String> watchHistory = new ArrayList<String>();
	
	public static void main(String[] args) throws IOException, ParseException {
	  
	    EventQueue.invokeLater(new Runnable() {
	      public void run() {
	        try {
	          GraphicalInterface frame = new GraphicalInterface();
	          frame.setVisible(true);
	        } catch (Exception e) {
	          e.printStackTrace();
	        }
	      }
	    });
	}

	public GraphicalInterface() throws IOException, ParseException {
		super("Lucene Search Engine for Songs");
		readHistoryLog();
		Initializer initializer = new Initializer();
		ArrayList<ArrayList<ArrayList<String>>> allDocuments = initializer.initializeDocumentation();
		Directory index = initializer.initializeIndex();
		IndexWriter iwriter = initializer.initializeIndexWriter(allDocuments);
		IndexSearcher isearcher = initializer.initializeIndexSearcher(iwriter);
		
		contentPanel = new JPanel();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(700, 200, 650, 600);
		contentPanel.setBackground(Color.GRAY);
		setContentPane(contentPanel);
		contentPanel.setLayout(null);
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
			| UnsupportedLookAndFeelException e2) {
			e2.printStackTrace();
		}
		
		
		JLabel title = new JLabel("Lucene Search Engine", SwingConstants.CENTER);
		title.setBounds(110, 120, 420, 100);
		title.setForeground(Color.BLACK);
		title.setBackground(Color.WHITE);
		title.setOpaque(true);
		title.setFont(title.getFont().deriveFont(40f));
		contentPanel.add(title);
		
		
		JTextField textField = new JTextField(25);
		textField.setBounds(70, 300, 490, 32);  
		textField.setFont(textField.getFont().deriveFont(20f));
		textField.setBackground(Color.LIGHT_GRAY);
        contentPanel.add(textField);
        
        this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				try {
					updateHistoryLog();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			public void windowOpened(WindowEvent e) {
                textField.requestFocusInWindow();
            }
		});
        
        JRadioButton checkboxArtist = new JRadioButton("Artist");
		JRadioButton checkboxTitle = new JRadioButton("Title");
		JRadioButton checkboxAlbum = new JRadioButton("Album");
		JRadioButton checkboxYear = new JRadioButton("Year");
		JRadioButton checkboxLyrics = new JRadioButton("Lyrics");
		JRadioButton checkboxOther = new JRadioButton("Other");
		ButtonGroup group = new ButtonGroup();
		group.add(checkboxArtist);
		group.add(checkboxTitle);
		group.add(checkboxAlbum);
		group.add(checkboxYear);
		group.add(checkboxLyrics);
		group.add(checkboxOther);
		checkboxArtist.setBounds(110, 337, 60, 20);
		contentPanel.add(checkboxArtist);
		checkboxTitle.setBounds(180, 337, 60, 20);
		contentPanel.add(checkboxTitle);
		checkboxAlbum.setBounds(250, 337, 60, 20);
		contentPanel.add(checkboxAlbum);
		checkboxYear.setBounds(320, 337, 60, 20);
		contentPanel.add(checkboxYear);
		checkboxLyrics.setBounds(390, 337, 60, 20);
		contentPanel.add(checkboxLyrics);
		checkboxOther.setBounds(460, 337, 60, 20);
		contentPanel.add(checkboxOther);
        
        JButton buttonSearch = new JButton("Search Docs");
        buttonSearch.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		String textFieldValue = textField.getText();
        		String field = "";
        		String text = "";
        		ArrayList<Document> documents;
        		if(checkboxArtist.isSelected()) {
        			field = field + "Artist";
        			text = field + " : " + textFieldValue;
        		}
        		else if(checkboxTitle.isSelected()) {
        			field = field + "Title";
        			text = field + " : " + textFieldValue;
        		}
        		else if(checkboxAlbum.isSelected()) {
        			field = field + "Album";
        			text = field + " : " + textFieldValue;
        		}
				else if(checkboxYear.isSelected()) {
					field = field + "Year";
					text = field + " : " + textFieldValue;
				}
				else if(checkboxLyrics.isSelected()) {
					field = field + "Lyric";
					text = field + " : " + textFieldValue;
				}
				else {
					// Default field if nothing is selected
					field = field + "Lyric";
					text = text + textFieldValue;
				}
        		try {
        			watchHistory.add(text);
	        		documents = initializer.getSearcher().parseQueryAndGetResults(isearcher, 
							initializer.getAnalyzer(), text, field, 100);
	        		ResultsInterface result = new ResultsInterface(documents, initializer, text, field);
	        		result.setVisible(true);
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (ParseException e2) {
					e2.printStackTrace();
				}
        }});
        buttonSearch.setBackground(Color.RED);
        buttonSearch.setOpaque(true);
        buttonSearch.setBorderPainted(false);
        buttonSearch.setFont(new Font("Bold", Font.BOLD, 15));
        buttonSearch.setBounds(210, 367, 200, 30);
        contentPanel.add(buttonSearch);
        
        JButton buttonHistory = new JButton("Search History");
        buttonHistory.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		HistoryInterface history = new HistoryInterface(watchHistory);
        		history.setVisible(true);
        	}
        });
        buttonHistory.setBackground(Color.RED);
        buttonHistory.setOpaque(true);
        buttonHistory.setBorderPainted(false);
        buttonHistory.setFont(new Font("Bold", Font.BOLD, 15));
        buttonHistory.setBounds(5, 5, 150, 30);
        contentPanel.add(buttonHistory);
	}
	
	public void updateHistoryLog() throws IOException {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("history.txt"));
			for(String query : watchHistory) {
				writer.write(query);
				writer.newLine();
			}
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void readHistoryLog() throws IOException {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("history.txt"));
			String line;
			while ((line = reader.readLine()) != null) {
	            watchHistory.add(line);
	        }
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}