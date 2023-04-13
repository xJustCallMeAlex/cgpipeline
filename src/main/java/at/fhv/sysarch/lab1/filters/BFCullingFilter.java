package at.fhv.sysarch.lab1.filters;

import at.fhv.sysarch.lab1.obj.Face;
import at.fhv.sysarch.lab1.obj.FaceWithColor;
import at.fhv.sysarch.lab1.pipe.Pipe;
import com.hackoeur.jglm.Vec4;

public class BFCullingFilter implements IFilter {
    private Pipe successor;

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
}
