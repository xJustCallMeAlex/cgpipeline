package at.fhv.sysarch.lab1.filters;

import at.fhv.sysarch.lab1.obj.Face;
import at.fhv.sysarch.lab1.obj.FaceWithColor;
import at.fhv.sysarch.lab1.pipe.Pipe;
import com.hackoeur.jglm.*;

public class MVTransformationFilter implements IFilter {

    private Pipe successor;

    private Mat4 transformation;


    public void setSuccessor(Pipe successor) {
        this.successor = successor;
    }

    public void setTransformation(Mat4 transformation) {
        this.transformation = transformation;
    }

    public void write(FaceWithColor face) {
        Vec4 v1 = transformation.multiply(face.getFace().getV1());
        Vec4 v2 = transformation.multiply(face.getFace().getV2());
        Vec4 v3 = transformation.multiply(face.getFace().getV3());
        Face newFace = new Face(v1,v2,v3, face.getFace());

        successor.write(new FaceWithColor(newFace, null));
    }
}
