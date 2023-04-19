package at.fhv.sysarch.lab1.filters;

import at.fhv.sysarch.lab1.obj.Face;
import at.fhv.sysarch.lab1.obj.FaceWithColor;
import at.fhv.sysarch.lab1.pipe.Pipe;
import com.hackoeur.jglm.Mat4;
import javafx.scene.paint.Color;

public class PerspectiveDivisionFilter implements IFilter{
    private Pipe successor;
    private Pipe predecessor;
    private Mat4 viewPortTransform;


    public PerspectiveDivisionFilter(Mat4 viewPortTransform) {
        this.viewPortTransform = viewPortTransform;
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
        FaceWithColor newFace = new FaceWithColor(new Face(
                        viewPortTransform.multiply(face.getFace().getV1().multiply(1.0f / face.getFace().getV1().getW())),
                        viewPortTransform.multiply(face.getFace().getV2().multiply(1.0f / face.getFace().getV2().getW())),
                        viewPortTransform.multiply(face.getFace().getV3().multiply(1.0f / face.getFace().getV3().getW())),
                        face.getFace()),
                face.getColor());
        successor.write(newFace);
    }


    @Override
    public void setPredecessor(Pipe predecessor) {
        this.predecessor = predecessor;
    }

    @Override
    public FaceWithColor read() {
        FaceWithColor face = predecessor.read();

        if (face != null) {
            if (face.getColor() != Color.PINK){
                face = new FaceWithColor(new Face(
                        viewPortTransform.multiply(face.getFace().getV1().multiply(1.0f / face.getFace().getV1().getW())),
                        viewPortTransform.multiply(face.getFace().getV2().multiply(1.0f / face.getFace().getV2().getW())),
                        viewPortTransform.multiply(face.getFace().getV3().multiply(1.0f / face.getFace().getV3().getW())),
                        face.getFace()),
                        face.getColor()
                );
            }
        }
        return face;
    }
}
