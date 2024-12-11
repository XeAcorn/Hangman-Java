/*
 * Lead developers: Jacob Daniels
 */

package Hangman;

import java.util.*;
import java.io.*;

public class Words
{
	//Words has-a words array list
	private static ArrayList<String> words = new ArrayList<String>();
	
	//Words has-a word file
	static File wordFile = new File("words.txt");
	
	//Words has-a scanner
	static Scanner scan = null;
	
	//Words has-a random number for the words array list
	private static Random rand = new Random();
	
	public static void initList()
	{
		//Try to open a word file and add all of the
		//words to the words array list
		try
		{
			scan = new Scanner(wordFile);
			while (scan.hasNext())
			{
				words.add(scan.next().toUpperCase());
			}
		}
		//If file is not found, send to console
		catch (FileNotFoundException e)
		{
			System.out.println("Can not locate file!");
		}
		finally
		{
			if (scan != null)
			{
				scan.close();
			}
		}
	}
	
	public static String getWord()
	{
		//If we have at least one word, return a random word
		//from the words array list
		if (words.size() > 0)
		{
			return words.get(rand.nextInt(words.size()));
		}
		
		//If we have no words, return "NOWORDFILE"
		else
		{
			return "NOWORDFILE";
		}
	}
}
