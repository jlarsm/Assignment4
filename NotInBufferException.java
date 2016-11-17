package java.util.concurrent;
	/**
	 * This is an exception thrown when one tries to load a variable not in the buffer
	 * @author Nicolas Gonzalez
	 * @version March 24th
	 */
public class NotInBufferException extends Exception{
	
	/**
	 * no argument constructor
	 */
	public NotInBufferException()
	{
		super("Not in buffer.");
	}
	
	/**
	 * @param String message
	 */
	public NotInBufferException(String message)
	{
		super(message);
	}
}

	
