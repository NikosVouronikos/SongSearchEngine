package dataparser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CSVReader {
	
	public static final String delimeter = ",";
	
	public ArrayList<ArrayList<ArrayList<String>>> read(final File folder) {
		File[] allCSVFiles = folder.listFiles();
		ArrayList<ArrayList<ArrayList<String>>> allCSVFilesContents = new ArrayList<ArrayList<ArrayList<String>>>();
		ArrayList<ArrayList<String>> fileContents = new ArrayList<ArrayList<String>>();
		ArrayList<String> rowContent = new ArrayList<String>();
		try {
			for(int i = 0; i < allCSVFiles.length; i++) {
				File file = allCSVFiles[i];
				FileReader fileReader = new FileReader(file);
				BufferedReader buffer = new BufferedReader(fileReader);
				String line = " ";
				String tempArr[];
				while((line = buffer.readLine()) != null) {
					tempArr = line.split(delimeter);
					for(String tempStr : tempArr) {
						rowContent.add(tempStr);
					}
					fileContents.add(rowContent);
					rowContent = new ArrayList<String>();
				}
				allCSVFilesContents.add(fileContents);
				fileContents = new ArrayList<ArrayList<String>>();
				buffer.close();
			}
		}
		catch(IOException io) {
			io.printStackTrace();
		}
		return allCSVFilesContents;
	}
}
