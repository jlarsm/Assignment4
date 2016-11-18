package java.util.concurrent;
import java.util.concurrent.*;
public class MainMemory {
	public ConcurrentHashMap<String,Integer> memory;
	
	public MainMemory(){
		
	}
	public synchronized int load(String x)throws NotInMemoryException{
		Integer num = memory.get(x);
		if(num == null){ // if x not found in the dictionary
			throw new NotInMemoryException();
		}
		else // x found int the dictionary
			return num;  // return its value
	}
	public synchronized void store(String x, int v){
		memory.put(x,v); // insert into the dictionary
	}

}
