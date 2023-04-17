package at.fhv.sysarch.lab1.filters;

import at.fhv.sysarch.lab1.obj.FaceWithColor;
import at.fhv.sysarch.lab1.pipe.Pipe;

public class DepthSortingFilter implements IFilter{
    private Pipe predecessor;


    @Override
    public void setSuccessor(Pipe successor) {
        // NOT IMPLEMENTED
    }

    @Override
    public void write(FaceWithColor face) {
        // NOT IMPLEMENTED
    }


    @Override
    public void setPredecessor(Pipe predecessor) {
        this.predecessor = predecessor;
    }

    @Override
    public FaceWithColor read() {
        FaceWithColor face = predecessor.read();

        if (face != null){
            // Do something
        }
        return face;
    }
}
