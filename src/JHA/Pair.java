package JHA;
//A simple class that creates a Pair object containing a key and a value
public class Pair {
	private int Number;
	private String String;
	
	//Class constructor
	Pair (String inString, int inInt) 
	{
		String = inString;
		Number = inInt;
	}

	//increments the value to the pair
	public void increment()
	{
		Number++;
	}
	
	//returns the pair in string format
	public String getPair()
	{
		return "[" + String + "," + Number + "]" ;
	}

	//returns the key to the pair
	public String getKey()
	{
		return String;
	}
	
	//returns the value to the pair
	public int getPairValue()
	{
		return Number;
	}
}
