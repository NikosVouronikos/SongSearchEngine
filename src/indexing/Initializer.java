package indexing;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import dataparser.CSVReader;

public class Initializer {
	
	private Analyzer analyzer = new StandardAnalyzer();
	private Path p = Paths.get("C:\\Users\\User\\Desktop\\Anaktisi");
	private Directory index;
	private Searcher searcher;

	public ArrayList<ArrayList<ArrayList<String>>> initializeDocumentation(){
		CSVReader csvReader = new CSVReader();
		File folder = new File("C:\\Users\\User\\Desktop\\Anaktisi\\Corpus");
		ArrayList<ArrayList<ArrayList<String>>> allContents =  csvReader.read(folder);
		return allContents;
	}
	
	public Directory initializeIndex() throws IOException {
		Directory index = FSDirectory.open(p);
		this.setIndex(index);
		return index;
	}
	
	public IndexWriter initializeIndexWriter(ArrayList<ArrayList<ArrayList<String>>> 
											 allContents) throws IOException
	{
		Indexer indexer = new Indexer(allContents);
		IndexWriter writer = indexer.createIndex(analyzer, p, this.getIndex());
		searcher = new Searcher(this.getIndex());
		return writer;
	}
	
	public IndexSearcher initializeIndexSearcher(IndexWriter iwriter) throws IOException, ParseException {
		IndexSearcher isearcher = searcher.createSearcher(iwriter);
		return isearcher;
	}
	
	public Analyzer getAnalyzer() {
		return analyzer;
	}

	public void setAnalyzer(Analyzer analyzer) {
		this.analyzer = analyzer;
	}

	public Path getP() {
		return p;
	}

	public void setP(Path p) {
		this.p = p;
	}
	
	public Directory getIndex() {
		return index;
	}

	public void setIndex(Directory index) {
		this.index = index;
	}
	
	public Searcher getSearcher() {
		return searcher;
	}
	
	public void setSearcher(Searcher searcher) {
		this.searcher = searcher;
	}
}
