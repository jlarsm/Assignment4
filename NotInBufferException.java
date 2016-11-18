

import java.util.concurrent.*;
	/**
	 * This is an exception thrown when one tries to load a variable not in the buffer
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
