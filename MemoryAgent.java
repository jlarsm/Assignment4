import java.util.concurrent.*;
public class MemoryAgent extends Thread {

	public MainMemory ram;
	public WriteBuffer buffer;
	
	public MemoryAgent(MainMemory ram, WriteBuffer buffer){
		this.ram = ram;
		this.buffer = buffer;
	}
	public void run(){
		
		int size;
		size = buffer.storeQueue.size();
		
		while(!buffer.storeQueue.isEmpty()){
			Pair p = buffer.storeQueue.remove(); //try poll if there's issues
			String variable = p.getVar();
			int value = p.getVal();
			ram.store(variable, value);
		}
		
		
	}
	
}
