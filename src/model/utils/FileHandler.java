package model.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileHandler {
	public static String fileReader(String path) {
		String fileText = "";
		try(BufferedReader br = new BufferedReader(new FileReader(path))){
			String line = br.readLine();
			while(line != null) {
				fileText += line + "\n";
				line = br.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileText;
	}
}
