package at.fhv.sysarch.lab1.filters;

import at.fhv.sysarch.lab1.obj.FaceWithColor;
import at.fhv.sysarch.lab1.pipe.Pipe;
import javafx.scene.paint.Color;

public class ColorFilter implements IFilter{
    private Pipe successor;
    private Pipe predecessor;
    private Color color;

    public ColorFilter(Color color) {
        this.color = color;
    }

    @Override
    public void setSuccessor(Pipe successor) {
        this.successor = successor;
    }

    @Override
    public void setPredecessor(Pipe predecessor) {
        this.predecessor = predecessor;
    }

    @Override
    public void write(FaceWithColor face) {
        face.setColor(color);
        successor.write(face);
    }

    @Override
    public FaceWithColor read() {
        FaceWithColor face = predecessor.read();

        if (face != null) {
            if (face.getColor() != Color.PINK) {
                face.setColor(color);
            }
        }
        return face;
    }
}
