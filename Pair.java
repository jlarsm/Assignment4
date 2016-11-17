package java.util.concurrent;

public class Pair {
	private String var;
	private int val;
	
	public Pair(String var, int val){
		this.var = var;
		this.val = val;
	}
	
	// all methods are synchronized because we do not want different threads trying to modify
	// the same variable at the same time.
	
	public synchronized int getVal(){
		return this.val;
	}
	public synchronized String getVar(){
		return this.var;
	}
	public synchronized void setVal(int val){
		this.val = val;
	}
	public synchronized void setVar(String var){
		this.var = var;
	}
	public synchronized void setPair(String var, int val){
		this.var = var;
		this.val = val;
	}
	public synchronized boolean equals(Pair p){
		return p.var==this.var;
	}
}
