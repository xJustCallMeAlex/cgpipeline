package at.fhv.sysarch.lab1.filters;

import at.fhv.sysarch.lab1.obj.FaceWithColor;
import at.fhv.sysarch.lab1.pipe.Pipe;

public class DepthSortingFilter implements IFilter{
    private Pipe predecessor;


    @Override
    public void setSuccessor(Pipe successor) {
        //Empty because it is only used for pull.
    }

    @Override
    public void write(FaceWithColor face) {
        //Empty because it is only used for pull.
    }

    @Override
    public void setPredecessor(Pipe predecessor) {
        this.predecessor = predecessor;
    }

    @Override
    public FaceWithColor read() {
        FaceWithColor face = predecessor.read();

        if (face == null){

        }

        return face;
    }
}
