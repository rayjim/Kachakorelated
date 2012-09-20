package kachako.ml.crf;
import iitb.CRF.*;
/**
 *
 * @author Sunita Sarawagi
 * This interface is used to access training file
 */ 

public interface TrainData extends DataIter {
    int size();   // number of training records
    void startScan(); // start scanning the training data
    boolean hasMoreRecords(); 
    public TrainRecord nextRecord();
    boolean hasNext(); 
    public DataSequence next();
};

