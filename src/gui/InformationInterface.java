package gui;

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import org.apache.lucene.document.Document;

@SuppressWarnings("serial")
public class InformationInterface extends JFrame{
	
	private JPanel contentPanel;
	private ArrayList<Document> documents = new ArrayList<Document>();
	private int docID;
	private String field;
	private String text;
	
	public InformationInterface(ArrayList<Document> documents, int docID, String field, String text) {
		super("Lucene Search Engine for Songs");
		
		this.docID = docID;
		this.setDocuments(documents);
		this.field = field;
		this.text = text;
		
		contentPanel = new JPanel();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(700, 200, 360, 540);
		contentPanel.setBackground(Color.GRAY);
		setContentPane(contentPanel);
		contentPanel.setLayout(null);
		
		DefaultListModel<String> listModel = new DefaultListModel<String>();
	    JList<String> infoList = new JList<String>(listModel); 
	    infoList.setBackground(new Color(153, 204, 204));
	    infoList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    infoList.setSelectedIndex(0);
	    infoList.setVisibleRowCount(3);
	    
	    

	    JScrollPane infoScrollPane = new JScrollPane(infoList);
	    infoScrollPane.setSize(330, 500);
	    infoScrollPane.setLocation(1, 0);
	    contentPanel.add(infoScrollPane);
	    
	    Document doc = documents.get(docID - 1);
	    listModel.addElement(checkArtist(text, doc));
	    listModel.addElement(checkTitle(text, doc));
	    listModel.addElement(checkAlbum(text, doc));
	    listModel.addElement(checkYear(text, doc));
	    listModel.addElement(checkDate(text, doc));
	    listModel.addElement("------------------------------- Lyrics -------------------------------");
	    
	    String lyrics = doc.get("Lyric");
	    String[] lyr = lyrics.split(" ");
	    String[] f = field.split(" ");
	    ArrayList<String> fField = new ArrayList<String>();
	    for(int i = 2; i < f.length; i++) {
	    	if(f[i].equals(" ")) {
	    		continue;
	    	}
	    	else {
	    		fField.add(f[i]);
	    	}
	    }
	   
	    for(int i = 0; i < lyr.length - 5; i = i + 5) {
	    	String element = "";
	    	for(int j = 0; j < 5; j++) {
	    		if(fField.contains(lyr[i+j])) {
	    			element = element + lyr[i+j].toUpperCase() + " ";
	    		}else {
	    			element = element + lyr[i+j] + " ";
	    		}
	    	}
	    	listModel.addElement(element);
	    }
	}
	
	public String checkArtist(String text, Document doc) {
		if(text.equals("Artist")) {
			return "Artist".toUpperCase() + " : " + doc.get("Artist").toUpperCase();
		}
		return "Artist" + " : " + doc.get("Artist");
	}
	
	public String checkTitle(String text, Document doc) {
		if(text.equals("Title")) {
			return "Title".toUpperCase() + " : " + doc.get("Title").toUpperCase();
		}
		return "Title" + " : " + doc.get("Title");
	}
	
	public String checkAlbum(String text, Document doc) {
		if(text.equals("Album")) {
			return "Album".toUpperCase() + " : " + doc.get("Album").toUpperCase();
		}
		return "Album" + " : " + doc.get("Album");
	}
	
	public String checkYear(String text, Document doc) {
		if(text.equals("Year")) {
			return "Year".toUpperCase() + " : " + doc.get("Year").toUpperCase();
		}
		return "Year" + " : " + doc.get("Year");
	}
	
	public String checkDate(String text, Document doc) {
		if(text.equals("Date")) {
			return "Date".toUpperCase() + " : " + doc.get("Date").toUpperCase();
		}
		return "Date" + " : " + doc.get("Date");
	}

	public ArrayList<Document> getDocuments() {
		return documents;
	}

	public void setDocuments(ArrayList<Document> documents) {
		this.documents = documents;
	}

	public int getDocID() {
		return docID;
	}

	public void setDocID(int docID) {
		this.docID = docID;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}