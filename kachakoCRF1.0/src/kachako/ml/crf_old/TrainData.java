package kachako.ml.crf_old;

import iitb.CRF.*;

import iitb.CRF.DataIter;
import iitb.CRF.DataSequence;


public interface TrainData extends DataIter {
    int size();   // number of training records
    void startScan(); // start scanning the training data
    boolean hasMoreRecords(); 
    public TrainRecord nextRecord();
    boolean hasNext(); 
    public DataSequence next();
};

