/*
 * Lead developers: Jacob Daniels
 */

package Hangman;

import java.awt.*;
import java.io.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.*;

public class Hangman extends JFrame
{
	//Hangman has-a word
	private static String word = "";
	//Hangman has-a word array
	private static String[] wordArray;
	private static ArrayList<String> wordArrayList = new ArrayList<String>();
	//Hangman has-a wordArrayList
	private static ArrayList<String> guessArray = new ArrayList<String>();
	//Hangman has-a wrongs array list
	private static ArrayList<String> wrongs = new ArrayList<String>();
	//Hangman has-a strike count
	private static int strikes = 0;
	//Hangman has-a max strike count
	private static int maxStrikes = 5;
	//Hangman has-a letter that was guessed
	private static String letterGuess = "";
	//Found variable determines whether or not a user guessed correctly
	private static boolean found = false;
	//Hangman has-some image icons to cycle through
	private static ImageIcon strike0 = new ImageIcon("strike0.png");
	private static ImageIcon strike1 = new ImageIcon("strike1.png");
	private static ImageIcon strike2 = new ImageIcon("strike2.png");
	private static ImageIcon strike3 = new ImageIcon("strike3.png");
	private static ImageIcon strike4 = new ImageIcon("strike4.png");
	private static ImageIcon strike5 = new ImageIcon("strike5.png");
	private static ImageIcon strike6 = new ImageIcon("strike6.png");
	//Hangman has-some labels	
	private static JLabel strikeLbl = new JLabel("Strikes: " + strikes);
	private static JLabel wrongsLbl = new JLabel("Wrong: ");
	private static JLabel resultLbl = new JLabel();
	private static JLabel result2Lbl = new JLabel();
	private static JLabel blanks = new JLabel();
	//Hangman has-a text field for user input
	private static JTextField guessStr = new JTextField(1);
	//Hangman has-a JLabel for a picture
	private static JLabel pic = new JLabel(strike0);
	//Hangman has-a JFrame window
	private static JFrame frame = new JFrame();
	//Hangman has-an input panel
	private static JPanel input = new JPanel();
	//Hangman has-a restart button
	private static JButton restart = new JButton("Restart");
	//Hangman has-a show hangman checkbox
	private static JCheckBox show = new JCheckBox("Show hangman?");

	//Hangman has-a restart function
	public static void restart()
	{
		//restart function resets vars to their
		//initial values and obtains a new word
		strikes = 0;
		word = Words.getWord();
		wrongs.clear();
		wordArrayList.clear();
		guessArray.clear();
		wordArray = word.split("");
		for (int i = 0; i < wordArray.length; i++)
		{
			wordArrayList.add(wordArray[i]);
		}
		for (int i = 0; i < word.length(); i++)
		{
			guessArray.add("_ ");
		}
		strikeLbl.setText("Strikes: " + String.valueOf(strikes));
		wrongsLbl.setText("Wrong: ");
		resultLbl.setText("");
		result2Lbl.setText("");
		blanks.setText(guessArray.toString().replace(",", "").replace("[", "").replace("]", ""));
		pic.setIcon(strike0);
		input.remove(restart);
		frame.repaint();
		guessStr.setText("");
		//Restart method also focuses on the
		//text field so the user doesn't have
		//to click again to guess
		guessStr.grabFocus();
	}

	//Hangman has-a main method
	public static void main(String[] args)
	{
		//Set the title of the JFrame window
		frame.setTitle("Hangman | Made by Jacob Daniels");
		//Create word list that the game will be using
		Words.initList();
		//Set window size
		frame.setPreferredSize(new Dimension(650,450));
		//Get a word for the user to guess
		word = Words.getWord();
		//Add a series of blanks to the guess array
		//so that the user can see what is left to guess
		for (int i = 0; i < word.length(); i++)
		{
			guessArray.add("_ ");
		}
		//We need to set wordArray to word.split
		//and then add each element to the wordArrayList
		//This is because normal array sizes are unmodifiable
		//and you cannot set an arrayList to word.split("")
		wordArray = word.split("");
		for (int i = 0; i < wordArray.length; i++)
		{
			wordArrayList.add(wordArray[i]);
		}
		
		//Console hints if a user is stuck
		System.out.println("First letter is: " + wordArray[0]);
		System.out.println("Last letter is: " + wordArray[wordArray.length-1]);
		
		//A border layout for the frame allows us to
		//group each element and separate those groups
		//into the five positions
		frame.setLayout(new BorderLayout());
		
		//Flow layout seemed the best for input since there isn't much
		input.setLayout(new FlowLayout());
		
		JPanel north = new JPanel();
		
		//Grid layout seemed the best for the north
		//because of the picture
		north.setLayout(new GridLayout(1,2));
		north.add(pic);
		frame.add(north, BorderLayout.NORTH);

		//This sets the blanks label to the guessArray value
		//and then replaces the brackets and commas with nothing
		//so that it looks like a string instead of an array
		blanks.setText(guessArray.toString().replace(",", "").replace("[", "").replace("]", ""));
		resultLbl.setText("");

		//Contains information about the game
		JPanel labels = new JPanel();
		
		//Grid layout seemed best for the labels since there
		//are quite a few of them
		labels.setLayout(new GridLayout(3,3));
		labels.add(resultLbl);
		labels.add(result2Lbl);
		labels.add(strikeLbl);
		labels.add(blanks);
		labels.add(wrongsLbl);
		
		//This makes the font a bit bigger than default
		blanks.setFont(new Font("Arial", 0, 18));
		frame.add(labels, BorderLayout.CENTER);
		
		//This sets lets the user know to create a word file
		//for the game to grab words from. It also sets the
		//text color to red so that it draws more attention
		if (word == "NOWORDFILE")
		{
			resultLbl.setText("Word file not found!");
			result2Lbl.setText("Please create a words.txt in root");
			resultLbl.setForeground(new Color(255,0,0));
			frame.repaint();
		}
		
		//Contains options for user input
		JPanel options = new JPanel();
		options.setLayout(new FlowLayout());
		
		//Show should be selected by default
		show.setSelected(true);
		//When clicked, the hangman picture is removed
		//or added to the north panel and the whole
		//frame is resized to accomodate for the
		//empty space
		show.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						if (show.isSelected())
						{
							north.add(pic);
							frame.setPreferredSize(new Dimension(650,450));
							frame.pack();
							frame.repaint();
						}
						else
						{
							north.remove(pic);
							frame.setPreferredSize(new Dimension(650,175));
							frame.pack();
							frame.repaint();
						}
						guessStr.grabFocus();
					}
				});
		options.add(show);

		//Adds a key listener to the text field
		guessStr.addKeyListener(new KeyAdapter()
				{
					@Override
					public void keyTyped(KeyEvent e)
					{
						//If there is already a character typed
						//then deleted the next character typed
						if (guessStr.getText().length() >= 1)
						{
							e.consume();
						}
						//Allows the user to hit the enter key on
						//their keyboard instead of clicking the
						//guess button
						if (e.getKeyChar() == '\n')
						{
							guess();
						}
						//If the character that was typed is R
						//and the game is over, then restart()
						if ((e.getKeyChar() == 'r') && ((!guessArray.contains("_ ") ) || (strikes > maxStrikes)))
						{
							e.consume();
							restart();
						}
					}
				});
		
		//Put the text in the center of the text field
		guessStr.setHorizontalAlignment(JTextField.CENTER);
		//Enlarge the font size of the text field
		guessStr.setFont(new Font("Arial", Font.BOLD, 48));
		
		//Allows the user to click the restart button instead of
		//hitting R on their keyboard
		restart.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						restart();
					}
				});
		
		//JButton allows the user to click the guess button
		//instead of hitting enter on their keyboard
		JButton guessBtn = new JButton("Guess");
		guessBtn.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						guess();
					}
				});
		
		input.add(guessStr);
		input.add(guessBtn);

		frame.add(input, BorderLayout.SOUTH);
		frame.add(options, BorderLayout.EAST);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
		guessStr.grabFocus();
	}
	
	//Win function tells the user that they won
	//and asks if they'd like to play again
	//Also adds a restart button and changes
	//some text to green
	public static void win()
	{
		input.add(restart);
		resultLbl.setForeground(new Color(0,255,0));
		resultLbl.setText("Congratulations! You won!");
		result2Lbl.setText("Would you like to play again?");
		frame.repaint();
	}

	//Lose function tells the user that they lost
	//and asks if they'd like to play again
	//Also adds a restart button and changes
	//some text to green
	public static void lose()
	{
		resultLbl.setForeground(new Color(255,0,0));
		resultLbl.setText("The word was: " + word);
		result2Lbl.setText("You lost! Play again?");
		frame.repaint();
	}
	
	//Star of the show
	public static void guess()
	{
		//Found should be expected to be false
		//and only change when we find a hit
		found = false;
		//Checks to see if the user has already won lost
		if ((strikes < 6) && (guessArray.contains("_ ")))
		{
			//letterGuess is set to the value of the text field in upper case
			letterGuess = guessStr.getText().toUpperCase();
			
			//loops though the wordArray and checks each element
			//if it equals the letter that was guessed
			for (int i = 0; i < wordArray.length; i++)
			{
				if (letterGuess.equals(wordArray[i]))
				{
					found = true;
					//replaces each blank with the letter that was guessed
					guessArray.set(i, letterGuess);
					
					//updates the blanks label to the new guess array value
					//formatting it to look like a string
					blanks.setText(guessArray.toString().replace(",", "").replace("[", "").replace("]", ""));
					
					//Checks to see if there are any blanks left to uncover.
					//If not, then we won so we should tell the user
					if (!guessArray.contains("_ "))
					{
						win();
					}
				}
			}
			
			//Sets the text field to blank so the user doesn't have
			//to remove their own guess manually
			guessStr.setText("");
			
			//If still not found after checking
			if (!found)
			{
				//If checks to see if the user has already guessed the letter
				//and if the text field isn't just blank
				if ((!wrongs.contains(letterGuess)) && (letterGuess.length() > 0))
				{
					//Add the guessed letter to the wrongs array list
					wrongs.add(letterGuess);
					
					//Update the wrongs label
					wrongsLbl.setText("Wrong: " + wrongs.toString().replace(",", "").replace("[", "").replace("]", ""));
					
					//Increment the strikes by one
					strikes++;
					
					//Update the strikes label
					strikeLbl.setText("Strikes: " + strikes);
					
					//These Ifs set the JLabel pic to the correct image icon
					if (strikes == 1)
					{
						pic.setIcon(strike1);
					}
					if (strikes == 2)
					{
						pic.setIcon(strike2);
					}
					if (strikes == 3)
					{
						pic.setIcon(strike3);
					}
					if (strikes == 4)
					{
						pic.setIcon(strike4);
					}
					if (strikes == 5)
					{
						pic.setIcon(strike5);
					}
					if (strikes == 6)
					{
						pic.setIcon(strike6);
						
						//If we're passed 5, we should restart
						input.add(restart);
					}
				}
				frame.repaint();
			}
			guessStr.grabFocus();
		}
		if (strikes > 5)
		{
			//If the user has more than 5 strikes, we should tell them
			lose();
		}
	}
}
