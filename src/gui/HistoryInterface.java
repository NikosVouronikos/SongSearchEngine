package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

@SuppressWarnings("serial")
public class HistoryInterface extends JFrame{
	
	private JPanel contentPanel;
	private ArrayList<String> watchHistory = new ArrayList<String>();
	
	public HistoryInterface(ArrayList<String> watchHistory) {
		super("Search History");
		setWatchHistory(watchHistory);
		
		contentPanel = new JPanel();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(700, 200, 240, 300);
		contentPanel.setBackground(Color.GRAY);
		setContentPane(contentPanel);
		contentPanel.setLayout(null);
		
		DefaultListModel<String> listModel = new DefaultListModel<String>();
	    JList<String> docsList = new JList<String>(listModel); 
	    docsList.setBackground(new Color(153, 204, 204));
	    docsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    docsList.setSelectedIndex(0);
	    docsList.setVisibleRowCount(3);

	    JScrollPane docsScrollPane = new JScrollPane(docsList);
	    docsScrollPane.setSize(200, 200);
	    docsScrollPane.setLocation(5, 5);
	    contentPanel.add(docsScrollPane);
	    
	    System.out.println(watchHistory.size());
	    
	    for(int i = 0; i < watchHistory.size(); i++) {
	    	listModel.addElement(watchHistory.get(i));
	    	listModel.addElement("");
	    }
	    
	    JButton buttonClearHistory = new JButton("Clear History");
	    buttonClearHistory.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		watchHistory.clear();
        		listModel.clear();
        	}
        });
	    buttonClearHistory.setBackground(Color.RED);
	    buttonClearHistory.setOpaque(true);
	    buttonClearHistory.setBorderPainted(false);
	    buttonClearHistory.setFont(new Font("Bold", Font.BOLD, 15));
	    buttonClearHistory.setBounds(5, 210, 135, 20);
        contentPanel.add(buttonClearHistory);
	}

	public ArrayList<String> getWatchHistory() {
		return watchHistory;
	}

	public void setWatchHistory(ArrayList<String> watchHistory) {
		this.watchHistory = watchHistory;
	}
}
