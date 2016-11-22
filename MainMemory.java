
import java.util.concurrent.*;
public class MainMemory {
	public static ConcurrentHashMap<String,Integer> memory = new ConcurrentHashMap();
	
	public MainMemory(){
		
	}
	public synchronized int load(String x)throws NotInMemoryException{
		Semaphores.memLoad.acquireUninterruptibly(); // to make sure swapAtomic executes in isolation
		Integer num = memory.get(x);
		if(num == null){ // if x not found in the dictionary
			Semaphores.memLoad.release();
			throw new NotInMemoryException();
		}
		else // x found int the dictionary
			Semaphores.memLoad.release();
			return num;  // return its value
	}
	public synchronized void store(String x, int v){
		Semaphores.memStore.acquireUninterruptibly(); // to make sure swapAtomic executes in isolation
		memory.put(x,v); // insert into the dictionary
		Semaphores.memStore.release();
	}

}