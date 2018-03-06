/*
 * A class that creates a pair array of all characters and numbers
 */
package JHA;



public class AlphaNumeric {
	private String AlphaNumericCharacter = "abcdefghijklmnopqrstuvwxyz123456789";	
	private Pair a = new Pair("a",0); 
	private Pair b = new Pair("b",0); 
	private Pair c = new Pair("c",0); 
	private Pair d = new Pair("d",0);		 
	private Pair e = new Pair("e",0); 
	private Pair f = new Pair("f",0); 
	private Pair g = new Pair("g",0); 
	private Pair h = new Pair("h",0);
	private Pair i = new Pair("i",0); 
	private Pair j = new Pair("j",0); 
	private Pair k = new Pair("k",0); 
	private Pair l = new Pair("l",0);
	private Pair m = new Pair("m",0); 
	private Pair n = new Pair("n",0); 
	private Pair o = new Pair("o",0); 
	private Pair p = new Pair("p",0);
	private Pair q = new Pair("q",0); 
	private Pair r = new Pair("r",0); 
	private Pair s = new Pair("s",0); 
	private Pair t = new Pair("t",0);
	private Pair u = new Pair("u",0); 
	private Pair v = new Pair("v",0); 
	private Pair w = new Pair("w",0); 
	private Pair x = new Pair("x",0);
	private Pair y = new Pair("y",0); 
	private Pair z = new Pair("z",0); 
	private Pair zero = new Pair("0",0); 
	private Pair one = new Pair("1",0); 
	private Pair two = new Pair("2",0); 
	private Pair three = new Pair("3",0);
	private Pair four = new Pair("4",0); 
	private Pair five = new Pair("5",0); 
	private Pair six = new Pair("6",0); 
	private Pair seven = new Pair("7",0);
	private Pair eight = new Pair("8",0); 
	private Pair nine = new Pair("9",0);
	private Pair[] pairArray = {zero,one,two,three,four,five,six,seven,eight,nine,a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z,};
	private Pair mostOccurringCharacter = a;
	private Pair secondMostOccurringCharacter = a;
	private Pair thirdMostOccurringCharacter = a;
	 
	//Constructs and array of pairs of alphanumeric characters
	public AlphaNumeric()
	{
		Pair[] pairArray = {zero,one,two,three,four,five,six,seven,eight,nine,a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z,};		
	}	
	
	 


	 
	 //checks if the passed in character is alphanumeric	
	public boolean isAlphaNumeric(String letter)
	{
		 
		if (AlphaNumericCharacter.contains(letter))
		{
			return true;
		}
		 
		else
		{
			return false;
		}
	}	
	 		 			 
	//updates the pair to the correct value by using Pair's increment method
	public void update(String key)
	{	 		
		
		for (int count = 0; count  < pairArray.length; count++)
	 	{	
			boolean KeyEqualsKeyInPairArray = key.equals(pairArray[count].getKey());
			if (KeyEqualsKeyInPairArray)
			{
				pairArray[count].increment();
			}	
		}
	}
	 	
	//Returns the top 3 occurring alphanumeric characters based on value
	public Pair[] topThreeOccurringAlphanumericCharacters()
	{
	 		
		for (int pairIndex = 0; pairIndex  < pairArray.length; pairIndex++)
	 	{
	 			
	 		int valueAtPairIndex = pairArray[pairIndex].getPairValue();
			//If the value of the pair at pairIndex is greater than pair value 
			//of the current most occurring pair, shift the current top 3 pairs
			//down one spot and set most occurring to pairArray[pairIndex]						
			if (valueAtPairIndex >= mostOccurringCharacter.getPairValue())
	 		{
	 			thirdMostOccurringCharacter = secondMostOccurringCharacter;
	 			secondMostOccurringCharacter = mostOccurringCharacter;
	 			mostOccurringCharacter = pairArray[pairIndex];
	 		}
	 		
			//If the value of the pair at pairArray[pairIndex] is greater than pair value 
			//of secondMostOccurringCharacter, shift the 2nd and 3rd characters down
			//one spot and set secondMostOccurringCharacter to pairArray[pairIndex]	
	 		else if (valueAtPairIndex >= secondMostOccurringCharacter.getPairValue())
	 		{
	 			thirdMostOccurringCharacter = secondMostOccurringCharacter;
	 			secondMostOccurringCharacter = pairArray[pairIndex];
	 		}
	 			
	 		//If pairArray[pairIndex] is greater than thirdMostOccurringCharacter
			//set thirdMostOccurringCharacter to pairArray[pairIndex]
	 		else if (valueAtPairIndex >= thirdMostOccurringCharacter.getPairValue())
	 		{
	 			thirdMostOccurringCharacter = pairArray[pairIndex];
	 		}
	 	}	 			 			 		
	 		
	 	Pair[] top3Characters = {mostOccurringCharacter,secondMostOccurringCharacter,thirdMostOccurringCharacter};
	 	return (top3Characters);
	}	 	
}
