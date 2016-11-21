
import java.util.*;
import java.util.concurrent.*;
public class WriteBuffer{
	public boolean pso;
	public ConcurrentLinkedDeque<Pair> storeQueue;
	
	
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


	public synchronized int load(String x)throws NotInBufferException{ //throws not found in buffer exception

		// check if variable x is in the queue
		boolean found = false; // assume it is not in the queue
		Iterator<Pair> q = storeQueue.descendingIterator();  // descending iterator so that we get the most recent store
		while(q.hasNext()){
			Pair n = q.next();
			if(x.equals(n.getVar())){
				found = true;
				return n.getVal();
			}
		}
		if(found == false){
			throw new NotInBufferException();
		}
		return 0;
	}
	
	public int size()
	{
		return storeQueue.size();
	}
	
	public synchronized void store(String x, int v){
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
	}

}