import java.util.*;
import java.util.concurrent.*;
public class WriteBuffer{
	public boolean pso;
	public ConcurrentLinkedDeque<Pair> storeQueue = new ConcurrentLinkedDeque();
	
	
	public WriteBuffer(boolean t){
		this.pso = t;
	}
	
	public void set_pso(boolean b)
	{
		this.pso = true;
	}
	
	public void set_tso(boolean b)
	{
		this.pso = false;
	}

	public synchronized int swapAtomic(String x, int v){
		Semaphores.memLoad.acquireUninterruptibly();
		Semaphores.memStore.acquireUninterruptibly();
		Semaphores.memoryAgent.acquireUninterruptibly();
		Integer tmp;
		boolean found = false; // assume it is not in the queue
		Iterator<Pair> q = storeQueue.descendingIterator();  // descending iterator so that we get the most recent store
		while(q.hasNext()){
			Pair n = q.next();
			if(x.equals(n.getVar())){
				found = true;
				tmp = n.getVal();
				break;
			}
		}
		System.out.println("found?"+found);
		if(found == false){
			tmp = MainMemory.memory.get(x);
			if(tmp == null){
				tmp = v;
			}
		}
		Pair pair = new Pair(x,v);
		if(this.pso == true){
			if(found == false){
				// CASE PSO AND VAR NOT IN THE QUEUE
				System.out.println("pso and var not in queue");
				storeQueue.addFirst(pair);
				Pair p = storeQueue.pollFirst();
				System.out.println("HERE");

				MainMemory.memory.put(p.getVar(), p.getVal());
			}
			else{
				// CASE PSO AND VAR ALREADY IN THE QUEUE
				System.out.println("pso and var in queue already");

				storeQueue.addLast(pair);
				while (!storeQueue.isEmpty())
				{	

					Pair p1 = storeQueue.pollFirst();
					if(p1!=null){
						MainMemory.memory.put(p1.getVar(), p1.getVal());
					}
				}	
			}
		}
		else{ // CASE TSO
			System.out.println("TSO");

			storeQueue.addLast(pair);
			while (!storeQueue.isEmpty())
			{	
				Pair p1 = storeQueue.pollFirst();
				if(p1!=null){
					MainMemory.memory.put(p1.getVar(), p1.getVal());
				}
			}
		}
		
		System.out.println("got to end");

		
		Semaphores.memLoad.release();
		Semaphores.memStore.release();
		Semaphores.memoryAgent.release();
		
		return 0;
		
	}

	public synchronized int load(String x)throws NotInBufferException{ //throws not found in buffer exception
		System.out.println("attempting to acquire"+x);
		Semaphores.mutex.acquireUninterruptibly(); // use mutex to prevent the scenario where
		System.out.println("acquired"+x);
		// a stale value is loaded from memory because memoryAgent was moving
		// a store from the writeBuffer to RAM
		
		
		// check if variable x is in the queue
		boolean found = false; // assume it is not in the queue
		Iterator<Pair> q = storeQueue.descendingIterator();  // descending iterator so that we get the most recent store
		while(q.hasNext()){
			Pair n = q.next();
			if(x.equals(n.getVar())){
				found = true;
				System.out.println("Released "+x);
				Semaphores.mutex.release();
				return n.getVal();
			}
		}
		if(found == false){
			throw new NotInBufferException();
		}
		
		Semaphores.mutex.release();
		
		return 0;
	}
	
	public int size()
	{
		return storeQueue.size();
	}
	
	public synchronized void store(String x, int v){
		Semaphores.mutex.acquireUninterruptibly(); // use mutex to prevent the scenario where
		// a stale value is loaded from memory because memoryAgent was moving
		// a store from the writeBuffer to RAM
		Pair pair = new Pair(x,v);
		if(this.pso == true){
			// first check if variable x is already in the queue
			boolean found = false; // assume it is not in the queue
			Iterator<Pair> q = storeQueue.iterator();
			while(q.hasNext()){
				if(pair.equals(q.next())){
					storeQueue.addLast(pair);
					found = true;
					break;
				}
			}
			if(found == false){
				storeQueue.addFirst(pair);
			}
		}
		else{ // TSO
			storeQueue.addLast(pair);
		}
		Semaphores.mutex.release();
	 }

}