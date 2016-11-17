package java.util.concurrent;

public class Processor extends Thread {

	public WriteBuffer buffer;
	public MainMemory ram;
	public MemoryAgent agent;

	public Processor(MainMemory ram, WriteBuffer buffer, MemoryAgent agent){
		this.ram = ram;
		this.buffer = buffer;
		this.agent = agent;
	}
	public void run(){
		agent.start();
	}
	
}