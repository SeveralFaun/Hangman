/*
 * HangmanGUI
 * Jiacheng Liu
 * March 13, 2020
 */
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

import javax.swing.*;

public class HangmanGUI extends JFrame implements ActionListener {
	
	private static JButton newGame, loadGame, addWords, restart, guess, menu; //Initializing Buttons
	private static JLabel title, head, body, arm1, arm2, leg1, leg2, win, loss, answerLetters; //Initializing Labels
	Random rand = new Random(); // Initializing random instance
	private String fileName, answer; // Initializing strings
	private int numErrors = 0; // Initializing number of errors (6 errors will be a loss)
	private static String[] letters = { "A", "B", "C", "D", "E", "F",
		    "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
		    "S", "T", "U", "V", "W", "X", "Y", "Z" }; //All letters of the alphabet
	private String[] letter; // String array for letters of the answer word
	private JButton[] letterButtons = new JButton[letters.length]; //Button Array for the alphabet
	private int length = 1280, width = 720; // Set dimensions for the GUI
	
	public HangmanGUI() {
		
		// Just a disclaimer some of the dimensions below seems kinda weird and hard coded but thats because
		// for some reason when I use math to keep symmetry it looks off for some reason, So kinda had
		// to wing it till it looked right.
		
		this.setSize(length,width);
		this.setResizable(false);
		this.setLayout(null);
		this.setTitle("Hangman");
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		//Creating window
		Container c = getContentPane();
		c.setBackground(new Color(204, 0, 255));
		
		//Title
		title = new JLabel("Hangman");
		title.setBounds(0,140,length,80);
		title.setFont(new Font("Gadugi",Font.BOLD,60));
		title.setForeground(Color.WHITE);
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setVisible(true);
		c.add(title);
		
		//Win label
		win = new JLabel("YOU WIN!!!");
		win.setBounds(0,200,length,80);
		win.setFont(new Font("Gadugi",Font.BOLD,60));
		win.setForeground(Color.WHITE);
		win.setHorizontalAlignment(JLabel.CENTER);
		win.setVisible(false);
		c.add(win);
		
		
		//Lose label
		loss = new JLabel("YOU LOSE!!!");
		loss.setBounds(0,200,length,80);
		loss.setFont(new Font("Gadugi",Font.BOLD,60));
		loss.setForeground(Color.WHITE);
		loss.setHorizontalAlignment(JLabel.CENTER);
		loss.setVisible(false);
		c.add(loss);
		
		//Will show the number of characters of the answer and the correctly guessed letters
		answerLetters = new JLabel("");
		answerLetters.setBounds(0,400,length,80);
		answerLetters.setFont(new Font("Gadugi",Font.BOLD,60));
		answerLetters.setForeground(Color.WHITE);
		answerLetters.setHorizontalAlignment(JLabel.CENTER);
		answerLetters.setVisible(false);
		c.add(answerLetters);
		
		//Button for new game
		newGame = new JButton("New Game");
		newGame.setBounds((length - 200)/2,320,200,100);
		newGame.setOpaque(false);
		newGame.addActionListener(this);
		newGame.setVisible(true);
		c.add(newGame);
		
		//Button to load words from an old save
		loadGame = new JButton("Load Game");
		loadGame.setBounds((length - 200)/2,420,200,100);
		loadGame.setOpaque(false);
		loadGame.addActionListener(this);
		loadGame.setVisible(true);
		c.add(loadGame);
		
		//Add words to the current save
		addWords = new JButton("Add a Word");
		addWords.setBounds(0,0,200,100);
		addWords.setOpaque(false);
		addWords.addActionListener(this);
		addWords.setVisible(false);
		c.add(addWords);
		
		//Guess the answer
		guess = new JButton("Guess the Word");
		guess.setBounds(1070,0,200,100);
		guess.setOpaque(false);
		guess.addActionListener(this);
		guess.setVisible(false);
		c.add(guess);
		
		//Restart the game from the current save
		restart = new JButton("Restart");
		restart.setBounds(0,585,200,100);
		restart.setOpaque(false);
		restart.addActionListener(this);
		restart.setVisible(false);
		c.add(restart);
		
		//Go back to main menu
		menu = new JButton("Menu");
		menu.setBounds(1070,585,200,100);
		menu.setOpaque(false);
		menu.addActionListener(this);
		menu.setVisible(false);
		c.add(menu);
		
		// Totally creative ways to make a stickman :)))
		head = new JLabel("O");
		head.setBounds(610,140,60,60);
		head.setFont(new Font("Gadugi",Font.BOLD,60));
		head.setForeground(Color.WHITE);
		head.setHorizontalAlignment(JLabel.CENTER);
		head.setVisible(false);
		c.add(head);
		;
		
		body = new JLabel("l");
		body.setBounds(610,180,60,60);
		body.setFont(new Font("Gadugi",Font.BOLD,60));
		body.setForeground(Color.WHITE);
		body.setHorizontalAlignment(JLabel.CENTER);
		body.setVisible(false);
		c.add(body);
		;
		
		arm1 = new JLabel("__");
		arm1.setBounds(585,140,60,80);
		arm1.setFont(new Font("Gadugi",Font.BOLD,60));
		arm1.setForeground(Color.WHITE);
		arm1.setHorizontalAlignment(JLabel.CENTER);
		arm1.setVisible(false);
		c.add(arm1);
		;
		
		arm2 = new JLabel("__");
		arm2.setBounds(635,140,60,80);
		arm2.setFont(new Font("Gadugi",Font.BOLD,60));
		arm2.setForeground(Color.WHITE);
		arm2.setHorizontalAlignment(JLabel.CENTER);
		arm2.setVisible(false);
		c.add(arm2);
		;
		
		leg1 = new JLabel("/");
		leg1.setBounds(600,220,60,60);
		leg1.setFont(new Font("Gadugi",Font.BOLD,60));
		leg1.setForeground(Color.WHITE);
		leg1.setHorizontalAlignment(JLabel.CENTER);
		leg1.setVisible(false);
		c.add(leg1);
		;
		
		leg2 = new JLabel("\\"); //Printing "\" is kinda dumb ngl
		leg2.setBounds(620,220,60,60);
		leg2.setFont(new Font("Gadugi",Font.BOLD,60));
		leg2.setForeground(Color.WHITE);
		leg2.setHorizontalAlignment(JLabel.CENTER);
		leg2.setVisible(false);
		c.add(leg2);
		;
		
		//Creating the alphabet buttons
		for (int i = 0; i < letters.length; i++) {
			letterButtons[i] = new JButton(letters[i]);
		    letterButtons[i].addActionListener(this);
		    letterButtons[i].setBounds(0 + (1280/letters.length)*i , 500, 1280/letters.length, 1280/letters.length);
		    letterButtons[i].setVisible(false);
		    c.add(letterButtons[i]);
		    }

	}
	
	public static void main(String[] args) {
		new HangmanGUI(); //Instance of the game
		
	}

	//ActionListener for all the buttons
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == newGame)
		{
			fileName = (JOptionPane.showInputDialog("Enter Save Name.") + ".txt");
			setWord();
			gameFile();
		}
		if(e.getSource() == loadGame)
		{
			fileName = (JOptionPane.showInputDialog("Enter Save Name.") + ".txt");
			gameFile();
		}
		if(e.getSource() == addWords)
		{
			setWord();
		}
		if(e.getSource() == restart)
		{
			gameFile();
		}
		if(e.getSource() == guess)
		{
			String guessedWord = (JOptionPane.showInputDialog("Enter Your Guess."));
			if(guessedWord.equalsIgnoreCase(answer))
				winLayout();
			else
			{
				numErrors ++;
				error();
			}
				
		}
		if(e.getSource() == menu)
		{
			menuLayout();
		}
		for (int i = 0; i < letters.length; i++) {
		    if(e.getSource() == letterButtons[i])
		    {
		    	letterButtons[i].setVisible(false);
		    	String letterButton = letterButtons[i].getText();
		    	checkLetter(letterButton);
		    	checkWin();
		    }
		}
	}
	
	//checks how many errors have been made to draw the correct number of parts for the stickman
	public void error() {
		if(numErrors == 1)
			head.setVisible(true);
		if(numErrors == 2)
			body.setVisible(true);
		if(numErrors == 3)
			arm1.setVisible(true);
		if(numErrors == 4)
			arm2.setVisible(true);
		if(numErrors == 5)
			leg1.setVisible(true);
		if(numErrors == 6)
		{
			leg2.setVisible(true);
			lossLayout();
		}	
	}
	
	//Checks if the letter is actually in the answer
	public void checkLetter(String letterButton) {
    	boolean hasLetter = false; //set to false
    	String answerLetter;
    	for(int j = 0; j < letter.length; j++) { //Repeats for each letter of the answer
    	if(letter[j].equals(letterButton)) // If the letter clicked is one of the letters in the answer
    	{
    		hasLetter = true;	
    		answerLetter = answerLetters.getText().substring(0, j) + letter[j] + answerLetters.getText().substring(j + 1);
    		answerLetters.setText(answerLetter); //Replaces the "-" with the letter in the correct index
    	}
    	}
    	if(hasLetter == false) //If the letter isn't in the answer, number of errors is increased
    	{
    		numErrors++;
    		error();
    	}
	}
	
	// Checks if player has won
	public void checkWin() {
		if(answerLetters.getText().indexOf("-") == -1)
			winLayout();
	}
	
	//Starts a game with the current game file
	public void gameFile() {
		answerLetters.setText(""); //Resets the answer label
		IO.openInputFile(fileName); //reads from game file
		String line = IO.readLine();
		int numLines = 0;
		while(line != null)
		{
			numLines++;
			line = IO.readLine();
		}
		IO.closeInputFile();
		String[] wordList = new String[numLines]; //Creates String array for the words in the save file
		IO.openInputFile(fileName);
		for(int i = 0; i < numLines; i++)
			wordList[i] = IO.readLine(); //Adds the words to the array
		IO.closeInputFile();
		answer = wordList[rand.nextInt(numLines)]; // Picks a random word form the array as the answer
		letter = new String[answer.length()];
		for(int j = 0; j < answer.length(); j++)
		{
			answerLetters.setText(answerLetters.getText() + "-"); //Adds a "-" for each letter of the answer
			letter[j] = String.valueOf(answer.charAt(j)); //Adds each letter of the answer to an array
		}
		numErrors = 0; //Resets the number of errors
		gameLayout(); //Sets the layout for the game state
	}
	
	//Adds a word to the save file
	public void setWord() {
		IO.createOutputFile(fileName,true); //Appends true to not overwrite the file
		String word = JOptionPane.showInputDialog("Enter Word.").toUpperCase();;
		IO.println(word);
		IO.closeOutputFile();
	}
	
	//Creates the win screen
	public void winLayout() {
		title.setVisible(false);
		newGame.setVisible(false);
		loadGame.setVisible(false);
		addWords.setVisible(false);
		restart.setVisible(true);
		guess.setVisible(false);
		menu.setVisible(true);
		head.setVisible(false);
		body.setVisible(false);
		arm1.setVisible(false);
		arm2.setVisible(false);
		leg1.setVisible(false);
		leg2.setVisible(false);
		for (int i = 0; i < letters.length; i++) {
		    letterButtons[i].setVisible(false);
		    }
		win.setVisible(true);
		loss.setVisible(false);
		answerLetters.setVisible(false);
	}
	
	//Creates the loss screen
	public void lossLayout() {
		title.setVisible(false);
		newGame.setVisible(false);
		loadGame.setVisible(false);
		addWords.setVisible(false);
		restart.setVisible(true);
		guess.setVisible(false);
		menu.setVisible(true);
		head.setVisible(false);
		body.setVisible(false);
		arm1.setVisible(false);
		arm2.setVisible(false);
		leg1.setVisible(false);
		leg2.setVisible(false);
		for (int i = 0; i < letters.length; i++) {
		    letterButtons[i].setVisible(false);
		    }
		win.setVisible(false);
		loss.setVisible(true);
		answerLetters.setVisible(false);
	}
		
	//Creates the game screen
	public void gameLayout() {
		title.setVisible(false);
		newGame.setVisible(false);
		loadGame.setVisible(false);
		addWords.setVisible(true);
		restart.setVisible(true);
		guess.setVisible(true);
		menu.setVisible(true);
		head.setVisible(false);
		body.setVisible(false);
		arm1.setVisible(false);
		arm2.setVisible(false);
		leg1.setVisible(false);
		leg2.setVisible(false);
		for (int i = 0; i < letters.length; i++) {
		    letterButtons[i].setVisible(true);
		    }
		win.setVisible(false);
		loss.setVisible(false);
		answerLetters.setVisible(true);
	}
	
	//Main menu
	public void menuLayout() {
		title.setVisible(true);
		newGame.setVisible(true);
		loadGame.setVisible(true);
		addWords.setVisible(false);
		restart.setVisible(false);
		guess.setVisible(false);
		menu.setVisible(false);
		head.setVisible(false);
		body.setVisible(false);
		arm1.setVisible(false);
		arm2.setVisible(false);
		leg1.setVisible(false);
		leg2.setVisible(false);
		for (int i = 0; i < letters.length; i++) {
		    letterButtons[i].setVisible(false);
		    }
		win.setVisible(false);
		loss.setVisible(false);
		answerLetters.setVisible(false);
	}

}
