import java.util.concurrent.*;
public class MemoryAgent extends Thread {

	public MainMemory ram;
	public WriteBuffer buffer;
	public boolean exitNow;
	
	public MemoryAgent(MainMemory ram, WriteBuffer buffer){
		this.ram = ram;
		this.buffer = buffer;
	}
	public void run(){
		
		while(true)
		{
			if (!buffer.storeQueue.isEmpty())
			{	
				Semaphores.mutex.acquireUninterruptibly(); // use mutex to prevent the scenario where
														// a stale value is loaded from memory because memoryAgent was moving
														// a store from the writeBuffer to RAM
				Semaphores.memoryAgent.acquireUninterruptibly();
				Pair p = buffer.storeQueue.pollFirst();
				if(p!=null){
					String variable = p.getVar();
					int value = p.getVal();
					ram.store(variable, value);
				}
				Semaphores.memoryAgent.release();
				Semaphores.mutex.release();
				
				
			}			
		}
		
		
	}
	
}