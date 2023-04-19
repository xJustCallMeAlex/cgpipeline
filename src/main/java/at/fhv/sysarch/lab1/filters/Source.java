package at.fhv.sysarch.lab1.filters;

import at.fhv.sysarch.lab1.obj.Face;
import at.fhv.sysarch.lab1.obj.FaceWithColor;
import at.fhv.sysarch.lab1.obj.Model;
import at.fhv.sysarch.lab1.pipe.Pipe;
import com.hackoeur.jglm.Vec4;
import javafx.scene.paint.Color;

import java.util.List;

public class Source implements IFilter {
    private Pipe successor;
    private Model model;
    private int index = 0;


    public Source(Model model) {
        this.model = model;
    }

    @Override
    public void setSuccessor(Pipe pipe) {
        this.successor = pipe;
    }

    @Override
    public void write(FaceWithColor face) {
        model.getFaces().forEach(f -> this.successor.write(new FaceWithColor(f, null)));
        this.successor.write(new FaceWithColor(new Face(
                new Vec4(0,0,0,0),
                new Vec4(0,0,0,0),
                new Vec4(0,0,0,0),
                new Vec4(0,0,0,0),
                new Vec4(0,0,0,0),
                new Vec4(0,0,0,0)
        ), Color.PINK));
    }


    @Override
    public void setPredecessor(Pipe pipe) {
        // NOT IMPLEMENTED
    }

    @Override
    public FaceWithColor read() {
        List<Face> faces = model.getFaces();
        if (index >= faces.size()) {
            return new FaceWithColor(new Face(
                    new Vec4(0,0,0,0),
                    new Vec4(0,0,0,0),
                    new Vec4(0,0,0,0),
                    new Vec4(0,0,0,0),
                    new Vec4(0,0,0,0),
                    new Vec4(0,0,0,0)
            ), Color.PINK);
        }
        return new FaceWithColor(faces.get(index++), null);
    }

    public void reset() {
        index = 0;
    }
}
