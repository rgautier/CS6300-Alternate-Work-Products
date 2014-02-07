import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * 
 */


/**
 * @author Rich Gautier
 *
 */
public class AvgSentenceLength {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length != 1 && args.length != 3 && args.length != 5)
		{
			usage();
		}
		
		String delimiters = ".?";		//Default values per requirements
		int wordLength = 4;				//Default value per requirements
		String fileName = "";			//Buffer pointer for filename
		int wordCount = 0;
		int sentenceCount = 0;
		String word="";
		
		boolean gotFileName = false;
		// We're going to be flexible and accept the arguments in any order
		// But only one of them can be the filename, which could look like an argument
		// so we're tracking when we've accepted one of the arguments as a filename.
		
		for (int i=0;i < args.length; i++)
		{
			
			if ("-h".equals(args[i]))
			{
				//The next argument will be treated as the delimiters
				usage();
				break;
			}
			else if ("-d".equals(args[i]))
			{
				//The next argument will be treated as the delimiters
				delimiters = args[++i];
				//System.out.println("Delimiters: "+ delimiters);
				
			}
			else if ("-l".equals(args[i])) {
				//The next argument will be treated as the word length
				try {
					wordLength = Integer.parseInt(args[++i]);
				}
				catch (RuntimeException e){
					System.err.println("Word length is invalid: Please use an integer after -l");
					usage();
					break;
				}
				if (wordLength <1)
				{
					System.err.println("Word length is invalid: Please provide a positive integer.");
					usage();
					break;
				} else {
					//System.out.println("Word length: "+wordLength);
				}
			}
			else if(gotFileName == false) 
			{
				// This argument will be treated as the filename
				fileName = args[i];
				//System.out.println("File is: "+fileName);
				gotFileName = true;
			}	
			else
			{
				//We already had one argument that was unknown and it was accepted as a filename
				System.err.println("You've provided a bad parameter somewhere.");
				usage();
				break;
			}
		}

		try {
			Scanner sc = new Scanner(new File(fileName));
		
			while (sc.hasNext()) {
	          word =  sc.next();
	          if (word.length() >= wordLength) {
	        	  //Count the word unless it ends in a delimiter
	        	  if (checkWord(word, delimiters) == true) {
	        		  //If true - ends in delimiter
	        		  sentenceCount++;
	        		  if (word.length() > wordLength) {
	        			  //Optional logic to determine if word has other delimiters inside to count U.S.A. as 6 letters not 5
	        			  wordCount++;
	        			  //System.out.println("Counting [delim]: "+word);
	        		  }
	        	  }
	        	  else
	        	  {
	        		  //If false
	        		  wordCount++;
	        		  //System.out.println("Counting: "+word);
	        	  }
	          }
	      }
	      //Even if the last word does NOT contain delimiter at the end,
	      //count as a sentence.
	      if (checkWord(word,delimiters) == false) {
	    	  //System.out.println("Counting sentence ending with: "+word);
	    	  sentenceCount++;
	      }
	      //System.out.println("The word count is: "+wordCount);
	      //System.out.println("The sentence count is: "+sentenceCount);
	      System.out.println("The average words per sentence, rounded down is: "+ (int) Math.floor(wordCount/sentenceCount));

	      sc.close();
		}
		catch (IOException e) {
			System.err.println("I could not access file: "+ fileName +".\nPlease make sure you've specified the right file to read.");
			usage();
		}
		
	}
	public static void usage() {
		System.err.println("Usage: Java AvgSentenceLength [filename] <-d \"[delimiters]\"> <-l [wordlength]>");
	}

	public static boolean checkWord(String word,String delimiters) 
	{
		String lastchar = word.substring(word.length()-1);
		if (delimiters.lastIndexOf(lastchar) == -1)
		{
			return false;
		} else {
			return true;
		}
	}
}
