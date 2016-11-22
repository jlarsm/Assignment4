import java.util.concurrent.*;
public class Semaphores {
	public static Semaphore mutex = new Semaphore(1);  // use mutex to prevent the scenario where
	// a stale value is loaded from memory because memoryAgent was moving
	// a store from the writeBuffer to RAM
	public static Semaphore s = new Semaphore(1);  // to make sure swapAtomic executes in isolation
	public static Semaphore memLoad = new Semaphore(1);
	public static Semaphore memStore = new Semaphore(1);
	public static Semaphore memoryAgent = new Semaphore(1);

}
