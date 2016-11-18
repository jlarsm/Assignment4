package java.util.concurrent;
import java.util.concurrent.*;
public class MemoryAgent extends Thread {

	public MainMemory ram;
	public WriteBuffer buffer;
	
	public MemoryAgent(MainMemory ram, WriteBuffer buffer){
		this.ram = ram;
		this.buffer = buffer;
	}
	public void run(){
		while(!buffer.storeQueue.isEmpty()){
			Pair p = buffer.storeQueue.remove();
			String variable = p.getVar();
			int value = p.getVal();
			ram.store(variable, value);
		}
	}
	
}