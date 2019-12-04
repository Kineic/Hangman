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
		// TODO Auto-generated method stub
		//1.Write some code that will simply read the movie list and display the whole list.
		//2.Then add to your code to randomly pick one movie and display that title only.
		//3.Then convert its letters to underscores (_) and display that instead, and so on.
		//4.Once you've got that part done start reading the user's input and search for it in the title.
		//5.Work on revealing the correct letters and displaying them.
		//6.Add the logic to keep track of wrong letters so they don't lose points for guessing the same letter twice.
		//7.After that, you can keep track of how many wrong guesses and end the game if they lose.
		//8.Finally, detect when they have guessed all the letters and let them know they've won!
		
		startPlayingHangman();
	}
	
	public static void startPlayingHangman() {
		ArrayList<String> movieList = convertingTextFileToAStringArrayList();
		String movieChoice = choosingARandomMovieFromTheListArray(movieList);
		String movieUnderscore = convertingMovieArrayToUnderscoreArray(movieChoice);
		printOutUnderscoreMovieToScreen(movieUnderscore);
		String userGuess = checkWhatTheUserInput();
		char[] movieChoiceChar = convertingStringToCharArray(movieChoice);
		char[] userGuessChar = convertingStringToCharArray(userGuess);
		int letterFound = checkingIfUserInputMatchesALetterInTheMovieTitle(userGuessChar, movieChoiceChar, movieUnderscore);
	}
	
	//This method will convert a text file into an Array List <String> & return it back.
	public static ArrayList<String> convertingTextFileToAStringArrayList() {
		FileReader fr = null;
		
		try {
			fr = new FileReader("S:\\Gareth\\Gareth\\Movies.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
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
		    rand = random.nextInt(numberRange + 1);
		    if(rand !=0) break;
		}
		
		String movieChoice = movieList.get(rand - 1);
		
		return movieChoice;
	}
	
	//This method converts all of the movies in the String to underscores &
	//Replaces whitespaces with two white spaces. Then returns the String back.
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
	
	public static void printOutUnderscoreMovieToScreen(String movieUnderscore) {
		System.out.println(movieUnderscore);
	}
	
	//This method looks at what the user inputed for their choice and
	//Returns back a character that they entered.
	public static String checkWhatTheUserInput() {
		@SuppressWarnings("resource")
		Scanner scanner =  new Scanner(System.in);
		
		String userGuess = "";
		
		boolean exit = true;
		
		while(exit) {
			try {
				System.out.println("Please enter a guess.");
				
				userGuess = scanner.next();
				
				if(userGuess.matches(".*\\d.*")) {
					System.out.println("Please enter an alphabetical character.");
				}else if(userGuess.length() < 2) {
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
	
	//This method checks if the user input matches any of the letters that are
	//In the movie title. Then returns a char array of the replaced letters.
	public static char[] checkingIfUserInputMatchesALetterInTheMovieTitle(char[] userGuess, char[] movieChoice, String movieChoiceUnderscore) {
		int position = 0;
		
		int incorrect = 0;
		
		for(int i = 0; i < movieChoice.length; i++) {
			if(userGuess[0] == movieChoice[i]) {
				System.out.println("You found a letter");
				position = i;
				replaceUnderscoreWithUserInputCharacter(position, userGuess, movieChoiceUnderscore);
			}else {
				incorrect++;
			}
		}
		
		if(incorrect == movieChoice.length) {
			System.out.println("You did not find any matching letters");
			
			
		}
		
	}
	
	//This method will run when the user has entered a character that matches a
	//A character in the movie array. It then checks the orginal movie array for
	//The position of that character and replaces the underscore with the users
	//Inputted character choice.
	public static String[] replaceUnderscoreWithUserInputCharacter(int position, char[] userGuess, String movieUnderscore) {
		
	}
	
	//This method will check if the user has already input the guess into the game
	//& returns true if they have already used that character or false else wise.
	public static boolean checkIfUserGuessWasAlreadyEntered() {
		
	}
	
	//This method will add an incorrect guess to the players score to show them that
	//They entered a character into the game that doesn't match a character in the
	//movie.
	public static int addWrongChoiceToPlayerScore() {
		
	}
	
	//This method will run when the user has guessed wrong 10 times, it will tell them
	//That they entered too many incorrect characters.
	public static void gameOverYouLose() {
		
	}
	
	//This method will run when the user has entered all characters into the array and
	//Guessed correctly.
	public static void gameOverYouWin() {
		
	}
	
}
