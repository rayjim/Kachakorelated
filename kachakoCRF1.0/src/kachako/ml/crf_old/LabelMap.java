package kachako.ml.crf_old;



public class LabelMap {
    public int map(int l) {return l-1;}
    public int revMap(int l) {return l+1;}
};

class BinaryLabelMap extends LabelMap {
    int posClass;
    BinaryLabelMap(int sel) {
	posClass = sel;
    }
    public int map(int el) {return (posClass == el)?1:0;}
    public int revMap(int label) {return (label==1)?posClass:0;}
};
