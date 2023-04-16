package at.fhv.sysarch.lab1.pipe;

import at.fhv.sysarch.lab1.filters.IFilter;
import at.fhv.sysarch.lab1.obj.Face;
import at.fhv.sysarch.lab1.obj.FaceWithColor;

public class Pipe {
    private IFilter successor;
    private IFilter predecessor;
    public void setSuccessor(IFilter successor) {
        this.successor = successor;
    }
    public void setPredecessor(IFilter predecessor) {
        this.predecessor = predecessor;
    }
    public void write(FaceWithColor face) {
        successor.write(face);
    }
    public FaceWithColor read(){
        return predecessor.read();
    }
}
