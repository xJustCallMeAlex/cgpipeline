package at.fhv.sysarch.lab1.filters;

import at.fhv.sysarch.lab1.obj.Face;
import at.fhv.sysarch.lab1.obj.FaceWithColor;
import at.fhv.sysarch.lab1.pipe.Pipe;
import com.hackoeur.jglm.*;
import javafx.scene.paint.Color;

public class MVTransformationFilter implements IFilter {
    private Pipe successor;
    private Pipe predecessor;
    private Mat4 transformation;


    public void setTransformation(Mat4 transformation) {
        this.transformation = transformation;
    }

    @Override
    public void setSuccessor(Pipe successor) {
        this.successor = successor;
    }

    @Override
    public void write(FaceWithColor face) {
        if (face.getColor() == Color.PINK) {
            successor.write(face);
            return;
        }
        Vec4 v1 = transformation.multiply(face.getFace().getV1());
        Vec4 v2 = transformation.multiply(face.getFace().getV2());
        Vec4 v3 = transformation.multiply(face.getFace().getV3());
        Vec4 n1 = transformation.multiply(face.getFace().getN1());
        Vec4 n2 = transformation.multiply(face.getFace().getN2());
        Vec4 n3 = transformation.multiply(face.getFace().getN3());
        Face newFace = new Face(v1,v2,v3, n1,n2,n3);

        successor.write(new FaceWithColor(newFace, null));
    }


    @Override
    public void setPredecessor(Pipe predecessor) {
        this.predecessor = predecessor;
    }

    @Override
    public FaceWithColor read() {
        FaceWithColor face = predecessor.read();

        if (face != null) {
            if (face.getColor() == Color.PINK){
                return face;
            }

            Vec4 v1 = transformation.multiply(face.getFace().getV1());
            Vec4 v2 = transformation.multiply(face.getFace().getV2());
            Vec4 v3 = transformation.multiply(face.getFace().getV3());
            Vec4 n1 = transformation.multiply(face.getFace().getN1());
            Vec4 n2 = transformation.multiply(face.getFace().getN2());
            Vec4 n3 = transformation.multiply(face.getFace().getN3());
            return new FaceWithColor(new Face(v1,v2,v3, n1,n2,n3), face.getColor());
        }
        return null;
    }
}
