import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JOptionPane;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;


public class Convert {
	
	public static String extract(File file) {
		POIFSFileSystem fs;
		HWPFDocument doc;
		String retVal = "";
		try {
			fs = new POIFSFileSystem(new FileInputStream(file));
			doc = new HWPFDocument(fs);
			retVal = doc.getText().toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return retVal;
	}
	
	public static void convertToXML(String title, String string) {
		try {
			Scanner scanner = new Scanner(string);
			PrintWriter out = new PrintWriter(new FileWriter("./XMLFiles/" + title + ".xml"));
			out.println("<root>");
			while (scanner.hasNext()) {
				String currentLine = scanner.nextLine();
				Scanner lineScanner = new Scanner(currentLine);
				String firstWord = "";
				if (lineScanner.hasNext()) firstWord = lineScanner.next();
				String rest = currentLine.replace(firstWord, "");
				out.println("<" + firstWord + ">" + rest + "</" + firstWord + ">");
			}
			out.println("</root>");
			out.close();
			JOptionPane.showMessageDialog(null, "File converted!");
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	public static void main(String[] args) {
		File file = new File("hello.doc");
		String extractedText = extract(file);
		convertToXML("hello", extractedText);
	}
}
