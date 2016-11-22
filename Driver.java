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
			 bufferArray[x] = new WriteBuffer(false); // tso mode
			 agentArray[x] = new MemoryAgent(ram, bufferArray[x]);
			 processorArray[x] = new Processor(ram, bufferArray[x], agentArray[x], x);
			 //processorArray[x].bufferSwitch(true);
		}
		
		for(int y = 0; y < 10; y++){
			processorArray[y].start();
		}
		/*
		try{
			processorArray[0].join();
			processorArray[1].join();
			processorArray[2].join();
			processorArray[3].join();
			processorArray[4].join();
			processorArray[5].join();
			processorArray[6].join();
			processorArray[7].join();
			processorArray[8].join();
			processorArray[9].join();
		}
		catch (Exception ex){
			ex.printStackTrace();
		}
		*/
		
		
		try {
			Thread.currentThread().sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("driver");
		System.out.println(Arrays.toString(Processor.raceCondition));

		
	}
	
	
}