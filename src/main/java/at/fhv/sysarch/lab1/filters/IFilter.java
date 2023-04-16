package at.fhv.sysarch.lab1.filters;

import at.fhv.sysarch.lab1.obj.FaceWithColor;
import at.fhv.sysarch.lab1.pipe.Pipe;

public interface IFilter {
    void setSuccessor(Pipe successor);
    void write(FaceWithColor face);

    void setPredecessor(Pipe predecessor);
    FaceWithColor read();
}
