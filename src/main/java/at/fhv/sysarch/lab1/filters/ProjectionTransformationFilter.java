package at.fhv.sysarch.lab1.filters;

import at.fhv.sysarch.lab1.obj.Face;
import at.fhv.sysarch.lab1.obj.FaceWithColor;
import at.fhv.sysarch.lab1.pipe.Pipe;
import com.hackoeur.jglm.Mat4;

public class ProjectionTransformationFilter implements IFilter{
    private Pipe successor;
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
}
