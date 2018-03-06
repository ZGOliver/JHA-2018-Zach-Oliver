package JHA;

import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
5
/*
 * Zach Oliver
 * Banno JHA 2018 internship project
 * 2/20/2018
 * 
 */
public class bannoHTMLReader{
   
	
	public static void main(String[] args) throws IOException {
    	//All localized variables
    	HttpGet request = null;
    	String content = null;
		String currentLine = null;
		String twitter = null;
		String country = null;
		String state = null;
		String city = null;
		String address = null;
		int pngCount = 0;
		int finanInstCount = 0;
		int platformCount = 0;
		int currentLineLength = 0;
		AlphaNumeric alphaNumCount = new AlphaNumeric();

        try {

            String url = "http://www.banno.com";
            HttpClient client = HttpClientBuilder.create().build();
            request = new HttpGet(url);

            request.addHeader("User-Agent", "Apache HTTPClient");
            HttpResponse response = client.execute(request);

            HttpEntity entity = response.getEntity();
            content = EntityUtils.toString(entity);       

        } finally {

            if (request != null) {

                request.releaseConnection();
            }
        }
        		
		//Splitting content by \n to get into an array of lines
        String[] lines = content.split("\n");	
	    
        //loop though array of lines
        for (int lineIndex = 0; lineIndex < lines.length; lineIndex++)
	    {
	        
	    	//Save lines[lineIdex] and remove excess white space and set it to lower case 
	    	currentLine = lines[lineIndex].toLowerCase().trim();
	    	currentLineLength = currentLine.length();
        	
	    	//Grabs Banno's twitter handle from the line containing twitter:site
	    	if (currentLine.contains("twitter:site"))
	    	{	    		
	    		twitter = currentLine.substring(35,currentLineLength-2);
	    	}
	    	    		    			    	
	    	//if the the line at lineIndex contains financial institution then increment finanInstCount
	    	if (currentLine.contains("financial institution"))
	    	{	    			    		
	    		finanInstCount++;		    	
	    	}
	    	
			country = Location(currentLine, currentLineLength,"addresscountry");
			state = Location(currentLine, currentLineLength,"addressregion");
			city = Location(currentLine, currentLineLength,"addresslocality");
			address = Location(currentLine, currentLineLength,"streetaddress");
	    	
			/*
			if (currentLine.contains("addresscountry"))
	    	{	    			    		
	    		country = currentLine.substring(19,currentLineLength -2);		    	
	    	}
	    	
	    	if (currentLine.contains("addressregion"))
	    	{	    			    		
	    		state = currentLine.substring(18,currentLineLength -1);		    	
	    	}
	    	
	    	if (currentLine.contains("addresslocality"))
	    	{	    			    		
	    		city = currentLine.substring(20,currentLineLength -2);		    	
	    	}					
	    	
	    	if (currentLine.contains("streetaddress"))
	    	{	    			    		
	    		address = currentLine.substring(18,currentLineLength -2);		    	
	    	}
	    	*/
	    	
	    	//trigger for a line containing non alphanumeric characters    	 	
	    	boolean isNotPlatformFeauture = false;			
	    	
	    	//Cycle through for length of currentLine
	    	for (int letterIndex = 0; letterIndex < currentLineLength; letterIndex++)
	    	{	    		
	    		String currentCharacter = currentLine.substring(letterIndex,letterIndex+1);
	    		//if letterIndex is a . then check next four characters to see if it is .png and increment pngCount if true	    		
	    		//check to see if .png is in index range
	    		boolean letterEqualsPeriod = currentCharacter.equals(".");
	    		if(letterEqualsPeriod && (letterIndex + 4) < currentLineLength )
	    		{	    				    			
	    			if (currentLine.substring(letterIndex,letterIndex+4).equals(".png"))
	    			{
	    				pngCount++;	    		
	    			}	
	    		}

	    		//checks to see if the current character is alphanumeric	    			    		
	    		if (alphaNumCount.isAlphaNumeric(currentCharacter))
	    		{	    		 	
	    			//calls update on the the character to update the pair in the array	    			
	    			alphaNumCount.update(currentCharacter);
	    		}
	    		
	    		//trigger for a character not being alphanumeric, excluding white space
	    		else if (!currentCharacter.equals(" ") )
	    		{
	    			isNotPlatformFeauture = true;
	    		}	    		
	    	}
	    	
	    	//if current line contains no non alphanumeric characters, does not equal web, and isn't an empty string	   
    		if(isNotPlatformFeauture == false && !currentLine.equals("web") && !currentLine.isEmpty())
    		{    			
    			platformCount++;				
    		}	    
	    }	    		    
	    Pair[] topThree = alphaNumCount.topThreeOccurringAlphanumericCharacters();
	    System.out.println("The first most occuring alphanumeric character is: "+topThree[0].getKey()+" " + "at" +  " " + topThree[0].getPairValue());
	    System.out.println("The second most occuring alphanumeric character is: "+topThree[1].getKey()+" " + "at" +  " " + topThree[1].getPairValue());
	    System.out.println("The third most occuring alphanumeric character is: "+topThree[2].getKey()+" " + "at" +  " " + topThree[2].getPairValue());
	    System.out.println("You can tweet Banno "+ twitter);
	    System.out.println("The total number of .png images is "+ pngCount);
	    System.out.println("The number of times finacial institution appears in the HTML is "+ finanInstCount);	
	    System.out.println("The number of platforms Banno covers is: " + platformCount);  
	    System.out.println("Banno is located in the " +country+ " in the state of " +state+ " in the city of " +city+  " at " + address);
	}           

	public static String Location(String line, int lineLength, String address)
	{
		
		int addressLength = address.length();
		String addressInfo = null;
		if (line.contains(address))
		{
			
			if (line.endsWith(","))
			{
				
				addressInfo = line.substring(addressLength+5, lineLength-2);
				return addressInfo;

			}
			
			else
			{
				System.out.println(addressInfo);
				addressInfo = line.substring(addressLength+5, lineLength-1);
			}
		}
		
		
	}

}


