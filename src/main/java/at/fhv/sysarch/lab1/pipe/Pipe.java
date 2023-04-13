package at.fhv.sysarch.lab1.pipe;

import at.fhv.sysarch.lab1.filters.IFilter;
import at.fhv.sysarch.lab1.obj.Face;
import at.fhv.sysarch.lab1.obj.FaceWithColor;

public class Pipe {
    private IFilter successor;
    public void setSuccessor(IFilter successor) {
        this.successor = successor;
    }
    public void write(FaceWithColor face) {
        successor.write(face);
    }
}
