import java.util.Arrays;
import java.util.concurrent.*;

public class Driver {

	public static void main(String[] args)
	{
		MainMemory ram = new MainMemory();
		
		WriteBuffer[] bufferArray = new WriteBuffer[10];
		MemoryAgent[] agentArray = new MemoryAgent[10];
		Processor[] processorArray = new Processor[10];
		
		//make 10 processors
		for(int x = 0; x < 10; x++){
			 bufferArray[x] = new WriteBuffer(true); // is argument is false, tso. if true, pso.
			 agentArray[x] = new MemoryAgent(ram, bufferArray[x]);
			 processorArray[x] = new Processor(ram, bufferArray[x], agentArray[x], x,     true,     true);
			 //                                                                   pid, buffersOn, swapAtomicOn
		}
		
		for(int y = 0; y < 10; y++){
			processorArray[y].start();
		}

			
		try {
			Thread.currentThread().sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("driver");
		System.out.println(Arrays.toString(Processor.raceCondition));

		
	}
	
	
}