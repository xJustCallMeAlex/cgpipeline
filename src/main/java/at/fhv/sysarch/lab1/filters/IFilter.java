package at.fhv.sysarch.lab1.filters;

import at.fhv.sysarch.lab1.obj.Face;
import at.fhv.sysarch.lab1.obj.FaceWithColor;
import at.fhv.sysarch.lab1.pipe.Pipe;

public interface IFilter {
    public void setSuccessor(Pipe successor);
    public void write(FaceWithColor face);
}
