package JHA;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

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
		String jsonString = "";
		boolean addressJson = false;
		boolean isPlatformFeature = true;	
		int pngCount = 0;
		int finanInstCount = 0;
		int platformCount = 0;
		int bracketPairs = 0;
		HashMap<String, Integer> characterMap = new HashMap<>();
		HTMLLine HTMLCurrentLine;

		//making the request to banno.com
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
	    
        //Loop though array of lines
        for (int lineIndex = 0; lineIndex < lines.length; lineIndex++)
	    {
	        
	    	//Saves lines[lineIdex], removes excess white space and sets it to lower case 
	    	currentLine = lines[lineIndex].toLowerCase().trim();
	    	
	    	//Saves current line into a customLine object
	    	HTMLCurrentLine =  new HTMLLine(currentLine);
	    	
	    	//Counts the occurrence of alphanumeric characters in HTMLCurrentLine
	    	characterMap = (HashMap<String, Integer>) HTMLCurrentLine.AlphanumericCounter(characterMap);        	
	    	
	    	//Grabs Banno's twitter handle if present in HTMLCurrentLine
	    	if (twitter == null)
	    	{	    		
	    		twitter = HTMLCurrentLine.getTwitterHandle();
	    	}
	    	
	    	isPlatformFeature = HTMLCurrentLine.isPlatformFeature();
	    	
	    	if(isPlatformFeature)
	    	{
	    		platformCount++;
	    	}
	    	
	    	//Checks HTMLCurrentLine to see how many times it contains financial institution
	    	finanInstCount = finanInstCount + HTMLCurrentLine.wordCount("financial institution");
	    	
	    	//Checks HTMLCurrentLine to see how many times it contains .png
	    	pngCount = pngCount + HTMLCurrentLine.wordCount(".png");
	    	
	    	//If at a section of JSON code, enter
	    	if (addressJson == true)
	    	{	    			
	    		//This line was missing a , in the HTML so i'm using this to add it in
	    		if(currentLine.contains("required html5 support"))
	    		{
	    			jsonString = jsonString.concat(currentLine.concat(","));
	    		}
	    		
	    		else
	    		{	
	    			//Adds json line to the jsonString
	    			jsonString = jsonString.concat(currentLine);
	    		}	    	    			    		
	    		
	    		//Count for knowing that we are inside a set of braces
	    		if (currentLine.contains("{"))
	    		{
	    			bracketPairs++;
	    		}
	    		
	    		//If } is encountered reduce bracketPairs by 1
	    		if (currentLine.contains("}"))
	    		{	    			 	    			
	    			bracketPairs--;
	    		}
	    		
	    		//If bracketPairs is reduced to zero it means we are no longer in the json
				if(bracketPairs == 0)
	    		{
	    			addressJson = false;
	    		}
	    	}	
	    	
	    	//Boolean for recognizing the next lines are a json
	    	if(currentLine.contains("json"))
	    	{
	    		addressJson = true;
	    	}	    		    		    			    		   			    		    	
	    }	    		    

        //Storing the json string into a JsonObject
	    JsonObject jsonObject = new Gson().fromJson(jsonString, JsonObject.class);
	    
	    //Getting the nested Address Json out of the JsonObject and into a map
	    Map<String, Object> addressMap = new Gson().fromJson(jsonObject.get("address"), new TypeToken<HashMap<String, Object>>() {}.getType());
	    
	    //Getting needed information out of addressMap	    
	    String city = (String) addressMap.get("addresslocality");
	    String state = (String) addressMap.get("addressregion");
	    String country = (String) addressMap.get("addresscountry");
	    String address = (String) addressMap.get("streetaddress");
	    
	    //Getting the top three occurring alphanumeric characters from the array
	    String[] topThree = getTopThreeAlphanumeric(characterMap);
	    
	    //Printing to console
	    System.out.println("The first most occuring alphanumeric character is: "+topThree[0]+ " at "+characterMap.get(topThree[0]));
	    System.out.println("The second most occuring alphanumeric character is: "+topThree[1]+" at "+characterMap.get(topThree[1]));
	    System.out.println("The third most occuring alphanumeric character is: "+topThree[2]+" at "+characterMap.get(topThree[2]));
	    System.out.println("You can tweet Banno "+twitter);
	    System.out.println("The total number of .png images is: "+pngCount);
	    System.out.println("The number of times finacial institution appears in the HTML is: "+finanInstCount);	
	    System.out.println("The number of platforms Banno covers is: "+ platformCount); 
	    System.out.println("Banno is located in the "+country+", in the state of "+state.toUpperCase()+", at the location "+address+ " " +city);	   	    							    		

	}
	
	//Helper function for getting the top three occurring elements of a map<String,int>
	static String[] getTopThreeAlphanumeric(HashMap<String, Integer> characterMap)
	{
		String first = "a";
		String second = "a";
		String third = "a";
		String currentLetter = "";
		String[] returnArray = new String[3];
		Object[] keyArray =  characterMap.keySet().toArray();
		int keyArrayLength = keyArray.length;
	    
		//Cycle though for length of keyArray
		for(int currentKey = 0; currentKey < keyArrayLength; currentKey++) 
	    {
			currentLetter = (String) keyArray[currentKey];
	    	
			//If the value paired with the character is greater than the value of the character value
			//of first, push all values down and set first to the current character
			if(characterMap.get(currentLetter) >= characterMap.get(first))
	    	{
	    		third = second;
	    		second = first;
	    		first = currentLetter;	    		
	    	}
	    	
			//If the value paired with the character is greater than the value of the character value
			//of second, push all values down and set second to the current character
			else if(characterMap.get(currentLetter) >= characterMap.get(second))
	    	{
	    		third = second;
	    		second = currentLetter;	    			    		
	    	}
	    	
			//If the value paired with the character is greater than the value of the character value
			//of third, set third to the current character
	    	else if(characterMap.get(currentLetter) >= characterMap.get(third))
	    	{
	    		third = currentLetter;
	    	}
	    }
	    //Load values into returnArray
		returnArray[0] = first;
	    returnArray[1] = second;
	    returnArray[2] = third;	    
	    return returnArray;
	}

}


