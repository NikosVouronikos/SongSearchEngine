package indexing;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;

public class Indexer {
	
	private ArrayList<ArrayList<ArrayList<String>>> allContents = new ArrayList<ArrayList<ArrayList<String>>>();
	
	public Indexer(ArrayList<ArrayList<ArrayList<String>>> allContents) {
		this.allContents = allContents;
	}
	
	public IndexWriter createIndex(Analyzer analyzer, Path p, Directory index) throws IOException {
		IndexWriterConfig config = new IndexWriterConfig(analyzer);
		IndexWriter writer = new IndexWriter(index, config);
		
		for(int i = 0; i < allContents.size(); i++) {
			ArrayList<ArrayList<String>> contentsOfFiles = allContents.get(i);
			for(int j = 1; j < contentsOfFiles.size(); j++) {
				Document doc = new Document();
				ArrayList<String> rowContent = contentsOfFiles.get(j);
				if(rowContent.size() == 6) {
					String artist = rowContent.get(1);
					String title = rowContent.get(2);
					String album = rowContent.get(3);
					String year = rowContent.get(4);
					String date = rowContent.get(5);
					doc.add(new TextField("Artist", artist, Field.Store.YES));
					doc.add(new TextField("Title", title, Field.Store.YES));
					doc.add(new TextField("Album", album, Field.Store.YES));
					doc.add(new TextField("Year", year, Field.Store.YES));
					doc.add(new TextField("Date", date, Field.Store.YES));
				}else {
					String artist = rowContent.get(1);
					String title = rowContent.get(2);
					String album = rowContent.get(3);
					String year = rowContent.get(4);
					String date = rowContent.get(5);
					String lyric = rowContent.get(6);
					doc.add(new TextField("Artist", artist, Field.Store.YES));
					doc.add(new TextField("Title", title, Field.Store.YES));
					doc.add(new TextField("Album", album, Field.Store.YES));
					doc.add(new TextField("Year", year, Field.Store.YES));
					doc.add(new TextField("Date", date, Field.Store.YES));
					doc.add(new TextField("Lyric", lyric, Field.Store.YES));
				}
				writer.addDocument(doc);
			}
		}
		return writer;
	}
}
