package at.fhv.sysarch.lab1.filters;

import at.fhv.sysarch.lab1.obj.Face;
import at.fhv.sysarch.lab1.obj.FaceWithColor;
import at.fhv.sysarch.lab1.pipe.Pipe;
import com.hackoeur.jglm.Mat4;
import javafx.scene.paint.Color;

public class ProjectionTransformationFilter implements IFilter{
    private Pipe successor;
    private Pipe predecessor;
    private Mat4 projTransform;


    public ProjectionTransformationFilter(Mat4 projTransform) {
        this.projTransform = projTransform;
    }

    @Override
    public void setSuccessor(Pipe successor) {
        this.successor = successor;
    }

    @Override
    public void write(FaceWithColor face) {
        FaceWithColor newFace = new FaceWithColor(
                new Face(projTransform.multiply(face.getFace().getV1()),
                        projTransform.multiply(face.getFace().getV2()),
                        projTransform.multiply(face.getFace().getV3()), face.getFace()), face.getColor()
        );
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
                face = new FaceWithColor(
                        new Face(projTransform.multiply(face.getFace().getV1()),
                                projTransform.multiply(face.getFace().getV2()),
                                projTransform.multiply(face.getFace().getV3()), face.getFace()), face.getColor()
                );
            }
        }
        return face;
    }
}
