import java.util.Arrays;
import java.util.concurrent.*;
public class Processor extends Thread {

	public WriteBuffer buffer;
	public MainMemory ram;
	public MemoryAgent agent;
	private boolean buffersOn; // true if buffers are turned on
	public int i; // i is pid
	public static int[] raceCondition = new int[10];
	public boolean swapAtomicOn;

	public Processor(MainMemory ram, WriteBuffer buffer, MemoryAgent agent, int pid, boolean buffersOn, boolean swapAtomicOn){
		this.ram = ram;
		this.buffer = buffer;
		this.agent = agent;
		this.buffersOn = buffersOn;
		this.i = pid;
		this.swapAtomicOn = swapAtomicOn;
		//default value
		for(int x = 0; x < 10; x++){
			raceCondition[x] = -1;
		}
	}
	
	public void run(){
		agent.start();

		
		// peterson's algorithm data (for n=10):
		for(int z = 0; z <= 10; z++){
			if(buffersOn){
				buffer.store("flag"+Integer.toString(z), -1);
				buffer.store("turn"+Integer.toString(z), 0);
			}
			else{
				ram.store("flag"+Integer.toString(z), -1);
				ram.store("turn"+Integer.toString(z), 0);
			}
		}
		
		if(swapAtomicOn == false){
			// <Entry Section>>
			for (int k = 0; k <= 8; k++)  // k is stored in a register in the processor
			{
				if(buffersOn){
					buffer.store("flag"+Integer.toString(i), k);
					buffer.store("turn"+Integer.toString(k), i);
				}
				else{
					ram.store("flag"+Integer.toString(i), k);
					ram.store("turn"+Integer.toString(k), i);
				}
				for (int j = 0; j < 10; j++)// j is stored in a processor register
				{
					if (i == j) continue;
					int flag_j=-1;
					int turn_k=0;
					try{
						if(buffersOn){
							flag_j = buffer.load("flag"+Integer.toString(j));
							}
						else{
							flag_j = ram.load("flag"+Integer.toString(j));
							}
					}
					catch(NotInBufferException e){
						try{flag_j = ram.load("flag"+Integer.toString(j));}
						catch(NotInMemoryException ex){System.out.println(ex.getMessage());}
					}
					catch(NotInMemoryException ex){System.out.println(ex.getMessage());}
					try{
						if(buffersOn){
							turn_k = buffer.load("turn"+Integer.toString(k));
						}
						else{
							turn_k = ram.load("turn"+Integer.toString(k));
						}
					}
					catch(NotInBufferException e){
						try{turn_k = ram.load("turn"+Integer.toString(k));}
						catch(NotInMemoryException ex){System.out.println(ex.getMessage());}
					}
					catch(NotInMemoryException ex){System.out.println(ex.getMessage());}
					while (flag_j >= k && turn_k == i) ; //nop
				}
			}
			//Critical Section
			 
			
			for(int x=0; x<10;x++){
				raceCondition[x] = i;  // fill raceCondition array with pid
				
				
				// threads that start first (lower ID) have to wait longer
				// (to increase probability of different values in raceCondition array
				
				/*
				try {
					Thread.currentThread().sleep(1000/(i+3));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				*/
				
			}
			
			
			
			//<<Exit Section>>
			if(buffersOn){
				buffer.store("flag"+Integer.toString(i), -1);
			}
			else{
				ram.store("flag"+Integer.toString(i), -1);
			}
		}
		
		
		else{
			// <Entry Section>>
			
			for (int k = 0; k <= 8; k++)  // k is stored in a register in the processor
			{
				if(buffersOn){
					buffer.swapAtomic("flag"+Integer.toString(i), k);
					buffer.swapAtomic("turn"+Integer.toString(k), i);
				}
				else{
					ram.store("flag"+Integer.toString(i), k);
					ram.store("turn"+Integer.toString(k), i);
				}
				for (int j = 0; j < 10; j++)// j is stored in a processor register
				{
					
					if (i == j) continue;
					int flag_j=-1;
					int turn_k=0;
					try{
						if(buffersOn){
							flag_j = buffer.load("flag"+Integer.toString(j));
							}
						else{
							flag_j = ram.load("flag"+Integer.toString(j));
							}
					}
					catch(NotInBufferException e){
						try{flag_j = ram.load("flag"+Integer.toString(j));}
						catch(NotInMemoryException ex){System.out.println(ex.getMessage());}
					}
					catch(NotInMemoryException ex){System.out.println(ex.getMessage());}
					try{
						if(buffersOn){
							turn_k = buffer.load("turn"+Integer.toString(k));
						}
						else{
							turn_k = ram.load("turn"+Integer.toString(k));
						}
					}
					catch(NotInBufferException e){
						try{turn_k = ram.load("turn"+Integer.toString(k));}
						catch(NotInMemoryException ex){System.out.println(ex.getMessage());}
					}
					catch(NotInMemoryException ex){System.out.println(ex.getMessage());}
					while (flag_j >= k && turn_k == i) ; //nop
				}
			}
			//Critical Section
	
	
			
			for(int x=0; x<10;x++){
				raceCondition[x] = i;  // fill raceCondition array with pid
				
				
				// threads that start first (lower ID) have to wait longer
				// (to increase probability of different values in raceCondition array
				
				/*
				try {
					Thread.currentThread().sleep(1000/(i+3));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				*/
				
			}
			
			
			
			//<<Exit Section>>
			if(buffersOn){
				buffer.swapAtomic("flag"+Integer.toString(i), -1);
			}
			else{
				ram.store("flag"+Integer.toString(i), -1);
			}
		}
	}
	
}
