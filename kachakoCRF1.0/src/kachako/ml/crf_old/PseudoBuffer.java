package kachako.ml.crf_old;

import java.util.StringTokenizer;

public class PseudoBuffer implements Cloneable{
	private StringTokenizer bufferVector;
    private String record;  // This one is important to be deleted
    private String delim;
    
	public PseudoBuffer(String record, String delim) {
		this.record = record;
		this.delim = delim;
		bufferVector = new StringTokenizer(record,delim);
		// TODO Auto-generated constructor stub
	}
	public String readLine(){
		if (bufferVector.hasMoreTokens()){
				return bufferVector.nextToken();
			
		}
		else
			return null;
		
	}
	public PseudoBuffer clone()
	{
			return new PseudoBuffer(record, delim);
		
	}
	public String getValue()
	{
		return record;
		
	}
	public void reset()
	{	
		bufferVector = new StringTokenizer(record,delim);
		
	}
	
	

}
