package at.fhv.sysarch.lab1.obj;

import javafx.scene.paint.Color;

public class FaceWithColor {
    private Face face;
    private Color color;

    public FaceWithColor(Face face, Color color) {
        this.face = face;
        this.color = color;
    }

    public Face getFace() {
        return face;
    }

    public void setFace(Face face) {
        this.face = face;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
