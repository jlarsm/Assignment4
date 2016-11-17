package java.util.concurrent;

public class WriteBuffer<T extends Object> {
	public boolean tso;
	public Object[] array;
	public boolean[] inQueue;
	public dequeue<Object
	
	
	public WriteBuffer(boolean t, int n){
		this.tso = t;
		this.array= new Object[n];
		this.inQueue = new boolean[n];
	}
	public Object load(int x){ //throws not found in buffer exception
		Object tmp = array[x];
		return tmp;
	}
	public void store(int x, Object v){
		
	}

}
