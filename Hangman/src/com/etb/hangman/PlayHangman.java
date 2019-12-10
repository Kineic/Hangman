package com.etb.hangman;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class PlayHangman {

	public static void main(String[] args) {
		startPlayingHangman();
	}
	
	//This is the method that holds all the other methods that make the game run
	public static void startPlayingHangman() {
		ArrayList<String> movieList = convertingTextFileToAStringArrayList();
		
		String movieChoice = choosingARandomMovieFromTheListArray(movieList);
		
		String movieChoiceWithWhitespaceAfterEveryLetter = addingWhitespaceAfterEveryCharInMovieChoice(movieChoice);
		
		String movieUnderscore = convertingMovieArrayToUnderscoreArray(movieChoice);
		
		printToScreen(movieUnderscore);
		
		String movieListWithUserLetter = "";
		
		boolean exit = true;
		
		int runningCount = 0;	//This is here because the first printToScreen is not populated until the checkingIfUserInput
								//MatchesALetterInTheMovieTitle is run.
		
		do {
			if(runningCount < 1) {
				printToScreen(movieListWithUserLetter);
			}
			
			String userGuess = checkWhatTheUsersGuessIs();
			
			char[] movieChoiceUnderscoreChar = convertingStringToCharArray(movieUnderscore);
			
			char[] movieChoiceChar = convertingStringToCharArray(movieChoiceWithWhitespaceAfterEveryLetter);
			
			char[] userGuessChar = convertingStringToCharArray(userGuess);
			
			movieListWithUserLetter = checkingIfUserInputMatchesALetterInTheMovieTitle(userGuessChar, movieChoiceChar, movieChoiceUnderscoreChar, movieListWithUserLetter);
			
			if(runningCount == 1) {
				System.out.println(movieListWithUserLetter);
			}
			
			runningCount = 1;
			
			addWrongChoiceToPlayerScore(movieChoice);	//Checks if user mistakes are equal to 10.
			
			if(gameOverYouWin(movieListWithUserLetter)){
				System.out.println("Congrats! You won!");
			}
		}while(exit);
	}
	
	//This method will convert a text file into an Array List <String> & return it back.
	public static ArrayList<String> convertingTextFileToAStringArrayList() {
		FileReader fr = null;
		
		try {
			fr = new FileReader("S:\\Gareth\\Gareth\\Movies.txt");
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			System.exit(0);
		}
		
		BufferedReader br = new BufferedReader(fr);
		
		String currentLine = null;
		
		try {
			currentLine = br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ArrayList<String> movieList = new ArrayList<String>();
		
		
		
		while(currentLine != null){
			movieList.add(currentLine);
			try {
				currentLine = br.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return movieList;
		
	}
	
	
	//This method will generate a random number that will be used to select
	//A movie from the List Array. Then return a string from the movie Array List.
	public static String choosingARandomMovieFromTheListArray(ArrayList<String> movieList) {
		int numberRange = movieList.size();
		
		Random random = new Random();
		
		int rand = 0;
		
		while (true){
		    rand = random.nextInt(numberRange);
		    if(rand !=0) break;
		}
		
		String movieChoice = movieList.get(rand - 1);
		
		return movieChoice.toLowerCase();
	}
	
	//This method will add a whitespace after every letter in the String and return
	//A new string.
	public static String addingWhitespaceAfterEveryCharInMovieChoice(String movieChoice) {
		movieChoice = movieChoice.replaceAll(".(?!$)", "$0 ");
		
		return movieChoice;
	}
	
	//This method converts all of the movies in the String to underscores &
	//Replaces whitespace with two white spaces. Then returns the String back.
	public static String convertingMovieArrayToUnderscoreArray(String movie) {
		String movieUnderscore = "";
		
		for(int i = 0; i < movie.length(); i++) {
			
			if(movie.charAt(i) == ' ') {
				movieUnderscore += "  ";
			}else {
				movieUnderscore += "_ ";;
			}
		}
		
		return movieUnderscore;
	}
	
	public static void printToScreen(String movieUnderscore) {
		System.out.println(movieUnderscore);
	}
	
	static String usedLetters = ""; //This keeps the running input of the users guesses
	
	//This method looks at what the user inputed for their choice and
	//Returns back a character that they entered.
	public static String checkWhatTheUsersGuessIs() {
		@SuppressWarnings("resource")
		Scanner scanner =  new Scanner(System.in);
		
		String userGuess = "";
		
		boolean exit = true;
		
		while(exit) {
			try {
				do {
				System.out.println("Please enter a guess.");
				
				userGuess = scanner.next();
				
				}while(checkIfUserGuessWasAlreadyEntered(userGuess, usedLetters));
				if(userGuess.matches(".*\\d.*")) {									//If input matches a number.
					System.out.println("Please enter an alphabetical character.");
				}else if(userGuess.matches("[^\\w\\s]")){							//If input matches a non-alphamumerical character.
					System.out.println("Please enter an alphabetical character.");
				}else if(userGuess.length() < 2) {									//If input is less than 2.
						exit = false;
				}else{
					System.out.println("Please enter one letter.");
					scanner.nextLine();
				}
				
			}catch(InputMismatchException e) {
				e.printStackTrace();
			}
		}
		
		
		return userGuess.toLowerCase();
	}
	
	public static char[] convertingStringToCharArray(String movieChoice) {
		char[] movieChoiceChar = movieChoice.toCharArray();
		
		return movieChoiceChar;
	}
	
	static String usedLettersPlusInfo = ""; //This is what is printed out to the user after every guess
	
	static String movieChoiceUnderscoreString = ""; //This is what gets returned from the checkingIfUserInputMatchesALetterInTheMovieTitle
	
	static char[] movieListUpdate; //This stores the users guesses if it is correct, starts of as a null until user guesses one letter correct
	
	static int count = 0; //This is the users running count, if they get 10 wrong, the game ends in a loss
	
	//This method checks if the user input matches any of the letters that are
	//In the movie title. Then returns a char array of the replaced letters.
	public static String checkingIfUserInputMatchesALetterInTheMovieTitle(char[] userGuess, char[] movieChoice, char[] movieChoiceUnderscore, String movieListWithUserLetter) {
		int position = 0;
		
		String userGuessString = new String(userGuess);
		
		int incorrect = 0;
		
		usedLetters += (userGuessString + ", ");
		
		for(int i = 0; i < movieChoice.length; i++) {
			if(userGuess[0] == movieChoice[i]) {
				position = i;
				
				movieListUpdate = replaceUnderscoreWithUserInputCharacter(position, userGuess, movieChoiceUnderscore, movieChoice, movieListUpdate, count);
				
				movieChoiceUnderscoreString = new String(movieListUpdate);
				
			}else {
				incorrect++;
			}
		}
		
		if(incorrect == movieChoice.length) {
			System.out.println("You did not find any matching letters");
			
			usedLettersPlusInfo = "\nUSED LETTERS = " + usedLetters + "\n";
			
			count++;
			
			System.out.println("Guesses Wrong: " + count + "\n");
			
			System.out.println("\nUSED LETTERS = " + usedLetters + "\n");
			
			return movieChoiceUnderscoreString;
		}
		
		System.out.println("Guesses Wrong: " + count);
		
		System.out.println("\nUSED LETTERS = " + usedLetters + "\n");
		
		return movieChoiceUnderscoreString;
	}
	
	//This method will run when the user has entered a character that matches a
	//A character in the movie array. It then checks the original movie array for
	//The position of that character and replaces the underscore with the users
	//Inputed character choice.
	public static char[] replaceUnderscoreWithUserInputCharacter(int position, char[] userGuessChar, char[] movieChoiceUnderscore, char[] movieChoice, char[] movieListUpdate, int count) {
		char userGuess = userGuessChar[0];
			
		for(int i = 0; i < movieChoiceUnderscore.length; i++) {
			if(movieListUpdate != null) {
				movieListUpdate[position] = userGuess;
				
				return movieListUpdate;
			}
		}
			
		movieChoiceUnderscore[position] = userGuess;
			
		return movieChoiceUnderscore;
	}
	
	//This method will check if the user has already input the guess into the game
	//& returns true if they have already used that character or false else wise.
	public static boolean checkIfUserGuessWasAlreadyEntered(String userGuess, String usedLetters) {
		if(usedLetters.contains(userGuess)) {
			System.out.println("You have already used letter " + userGuess + ". Enter a new letter");
			
			return true;
		}
		
		return false;
	}
	
	//This method will add an incorrect guess to the players score to show them that
	//They entered a character into the game that doesn't match a character in the
	//movie.
	public static void addWrongChoiceToPlayerScore(String movieChoice) {
		if(count == 10) {
			gameOverYouLose(movieChoice);
		}
	}
	
	//This method will run when the user has guessed wrong 10 times, it will tell them
	//That they entered too many incorrect characters.
	public static void gameOverYouLose(String movieChoice) {
		System.out.println("Sorry, you lose :(");
		
		System.out.println("\n\nThe movie was " + movieChoice);
	}
	
	//This method will run when the user has entered all characters into the array and
	//Guessed correctly.
	public static boolean gameOverYouWin(String movie) {
		if(movie == "") {
			return false;
		}else if(movie.contains("_")) {
			return false;
		}
		
		return true;
	}
	
}
