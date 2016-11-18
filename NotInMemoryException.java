
package java.util.concurrent;
import java.util.concurrent.*;
	/**
	 * This is an exception thrown when one tries to load a variable not in ram
	 */
public class NotInMemoryException extends Exception{
	
	/**
	 * no argument constructor
	 */
	public NotInMemoryException()
	{
		super("Not in memory.");
	}
	
	/**
	 * @param String message
	 */
	public NotInMemoryException(String message)
	{
		super(message);
	}
}
