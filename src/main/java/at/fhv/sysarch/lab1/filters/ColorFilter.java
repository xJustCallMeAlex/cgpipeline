package at.fhv.sysarch.lab1.filters;

import at.fhv.sysarch.lab1.obj.FaceWithColor;
import at.fhv.sysarch.lab1.pipe.Pipe;
import javafx.scene.paint.Color;

public class ColorFilter implements IFilter{
    private Pipe successor;
    private Color color;

    public ColorFilter(Color color) {
        this.color = color;
    }

    @Override
    public void setSuccessor(Pipe successor) {
        this.successor = successor;
    }

    @Override
    public void write(FaceWithColor face) {
        face.setColor(color);
        successor.write(face);
    }
}
