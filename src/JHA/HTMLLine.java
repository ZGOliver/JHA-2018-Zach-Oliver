package JHA;

import java.util.HashMap;

/*
 *A class created to extend string
 */
public class HTMLLine 
{
	private String currentHTMLLine;
	private String twitter;
	private int count;
	private int wordLength;
	private int currentHTMLLineLength;
	private String currentCharacter;
	private String alphanumericCharacters = "abcdefghijklmnopqrstuvwxyz123456789";
	
	HTMLLine(String stringTocurrentHTMLLine)
	{
		currentHTMLLine = stringTocurrentHTMLLine;
		currentHTMLLineLength = currentHTMLLine.length();
	}
	
	//Counts the occurrence of alphanumeric characters in a custom string
	public HashMap<String, Integer> AlphanumericCounter(HashMap<String, Integer> characterMap)
	{
		//Cycle through for length of currentHTMLLine
		for(int characterIndex = 0; characterIndex < currentHTMLLineLength; characterIndex++)
		{
			//Storing the current character in a variable
			currentCharacter = currentHTMLLine.substring(characterIndex,characterIndex+1);
			
			//If current character is in the string of alphanumeric characters
			//update value in character map, if it doesn't exist then make and entry and set it to 1.
			if(alphanumericCharacters.contains(currentCharacter))
			{
				if (characterMap.containsKey(currentCharacter))
				{
					characterMap.put(currentCharacter, characterMap.get(currentCharacter)+1);
				}
				else
				{
					characterMap.put(currentCharacter, 1);
				}
			}
		}
		return characterMap;
	}
	
	//Searches currentHTMLLine for number of times the passed in word appears in currentHTMLLine
	public int wordCount(String word)
	{
		count = 0;		
		
		//Cycle through for length of currentHTMLLine
		for(int characterIndex = 0; characterIndex < currentHTMLLineLength; characterIndex++)
		{
			wordLength = word.length();
			
			//Checks to see if the word is in index range
    		if((characterIndex + wordLength) < currentHTMLLineLength)
    		{	    				    			
    			//If the substring of currentHTMLLine equals the word then increment count
    			if (currentHTMLLine.substring(characterIndex,characterIndex + wordLength).equals(word))
    			{
    				count++;	    		
    			}	
    		}
		}
		return count;
	}
	
	//Gets the twitter handle
	public String getTwitterHandle()
	{
		
		if(currentHTMLLine.contains("twitter:site"))
    	{	    		
    		twitter = currentHTMLLine.substring(35,currentHTMLLineLength -2);
    	}
		
		else
		{
			twitter = null;
		}
		
		return twitter;
	}
	
	//Checks if the currentHTMLLine is a platform feature
	//A line is a platform feature if it only consists of alphanumeric characters
	public boolean isPlatformFeature()
	{
		boolean isPlatformFeature = true;
		
		//Check to see if the currentHTMLLine is a valid line
		if (currentHTMLLine.isEmpty() || currentHTMLLine.equals("web"))
		{
			isPlatformFeature = false;
		}
		

		else
		{	
			//Cycle through length of line and examine each character
			for(int characterIndex = 0; characterIndex < currentHTMLLineLength; characterIndex++)
			{	
				currentCharacter = currentHTMLLine.substring(characterIndex,characterIndex+1);												
				//If the current character is alphanumeric, set boolean to true
				if(!alphanumericCharacters.contains(currentCharacter) && !currentCharacter.equals(" "))
				{
					isPlatformFeature = false;
					break;	
				}						
			}
		}
		return isPlatformFeature;
	}				
}
