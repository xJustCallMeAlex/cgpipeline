package at.fhv.sysarch.lab1.filters;

import at.fhv.sysarch.lab1.obj.FaceWithColor;
import at.fhv.sysarch.lab1.pipe.Pipe;
import com.hackoeur.jglm.Vec4;

public class BFCullingFilter implements IFilter {
    private Pipe successor;
    private Pipe predecessor;


    @Override
    public void setSuccessor(Pipe successor) {
        this.successor = successor;
    }

    @Override
    public void write(FaceWithColor face) {
        Vec4 v1 = face.getFace().getV1();
        Vec4 n1 = face.getFace().getN1();
        if (v1.dot(n1) < 0) {
            successor.write(face);
        }
    }

    @Override
    public void setPredecessor(Pipe predecessor) {
        this.predecessor = predecessor;
    }

    @Override
    public FaceWithColor read() {
        FaceWithColor face = predecessor.read();

        Vec4 v1 = face.getFace().getV1();
        Vec4 n1 = face.getFace().getN1();
        if (v1.dot(n1) < 0) {
            return face;
        }
        return null;
    }
}
