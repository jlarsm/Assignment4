

	/**
	 * This is an exception thrown when one tries to load a variable not in ram
	 */
public class NotInMemoryException extends Exception{
	
	/**
	 * no argument constructor
	 */
	public NotInMemoryException()
	{
		super("Not in buffer.");
	}
	
	/**
	 * @param String message
	 */
	public NotInMemoryException(String message)
	{
		super(message);
	}
}
