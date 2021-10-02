/*
 * IO class
 * Ms. Coderre
 * Feb 12, 2020
 */

	//Not sure if we were supposed to but I just used the class from the lesson
import java.io.*;

public class IO {
	
	// VARIABLES AND METHODS NEEDED FOR WRITING TO A FILE
	
	private static PrintWriter fileOut;
	
	// Creates a new file called filename in the current folder and places a reference to it in the object fileOut
	// The argument filename represents the name of the text file
	public static void createOutputFile(String filename)
	{
		createOutputFile(filename,false);
		try {
			fileOut = new PrintWriter(new BufferedWriter(new FileWriter(filename)));
		}
		catch(IOException e) {
			System.out.println("***Cannot create file: " + filename + " ***");
		}
	}
	
	// Creates a new file in the current folder and places a reference to it in fileOut
	// Append TRUE if you want to ADD to the existing information in the file
	// Append FALSE if you want to rewrite the entire file
	public static void createOutputFile(String fileName, boolean append) {
		try {
			fileOut = new PrintWriter(new BufferedWriter(new FileWriter(fileName, append)));
		}
		catch(IOException e) {
			System.out.println("***Cannot create file: " + fileName + " ***");
		}
	}
	
	// Text is added to the current file
	public static void print(String text)
	{
		fileOut.print(text);
	}
	
	// Text is added to the current file and them a new line (hitting 'enter' on keyboard)
	public static void println(String text)
	{
		fileOut.println(text);
	}
	
	// Closes the file currently being written to
	// NOTE: MUST BE CALLED WHEN YOU ARE FINISHED WRITING IN ORDER TO SAVE THE FILE
	public static void closeOutputFile()
	{
		fileOut.close();
	}
	
	// VARIABLES AND METHODS NEEDED FOR READING FROM A FILE
	
	private static BufferedReader fileIn;
	
	// Opens a file called fileName (file must be in the current project folder) and it places a reference to it in the file
	public static void openInputFile(String fileName)
	{
		try {
			fileIn = new BufferedReader(new FileReader(fileName));
		}
		catch(FileNotFoundException e) {
			System.out.println("***Cannot open " + fileName + " ***");
		}
	}
	
	// Reads the next line from the file and returns it to be stored in a string object/variable
	public static String readLine()
	{
		try {
			return fileIn.readLine();
		}
		catch(IOException e) {
			return null;
		}
	}
	
	// Closes the file that is currently being read from
	public static void closeInputFile()
	{
		try {
			fileIn.close();
		}
		catch(IOException e) {}
	}
}
