package java.util.concurrent;
import java.util.*;
public class WriteBuffer<T extends Object> {
	public boolean pso;
	public Object[] buffer;
	public boolean[] inQueue;
	public Deque<Object> storeQueue;
	
	
	public WriteBuffer(boolean t, int n){
		this.pso = t;
		this.buffer= new Object[n];
		this.inQueue = new boolean[n];
	}
	
	public Object load(int x){ //throws not found in buffer exception
		try{
			Object tmp = buffer[x];
			return tmp;
		}
		catch (IndexOutOfBoundsException e){
			System.err.println("Error item not in buffer" + e.getMessage());
			return -1;
		}
		
	}
	
	public void store(int x, Object v){
		if(this.pso == true){
			
		}
		else{
			
		}
	}

}
