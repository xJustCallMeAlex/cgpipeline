package at.fhv.sysarch.lab1.filters;

import at.fhv.sysarch.lab1.obj.FaceWithColor;
import at.fhv.sysarch.lab1.pipe.Pipe;
import at.fhv.sysarch.lab1.rendering.RenderingMode;
import com.hackoeur.jglm.Vec4;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Sink implements IFilter {
    private Pipe predecessor;
    private GraphicsContext context;
    private RenderingMode rm;
    private boolean done = false;


    public Sink(GraphicsContext context, RenderingMode rm) {
        this.context = context;
        this.rm = rm;
    }

    @Override
    public void setSuccessor(Pipe successor) {
        // NOT IMPLEMENTED
    }

    @Override
    public void write(FaceWithColor face) {
        context.setFill(face.getColor());
        context.setStroke(face.getColor());

        double[] xpoints = new double[3];
        double[] ypoints = new double[3];
        xpoints[0] = face.getFace().getV1().getX();
        xpoints[1] = face.getFace().getV2().getX();
        xpoints[2] = face.getFace().getV3().getX();
        ypoints[0] = face.getFace().getV1().getY();
        ypoints[1] = face.getFace().getV2().getY();
        ypoints[2] = face.getFace().getV3().getY();

        if (rm == RenderingMode.POINT) {
            context.strokeOval(face.getFace().getV1().getX(), face.getFace().getV1().getY(), 1, 1);
        } else if (rm == RenderingMode.WIREFRAME) {
            context.strokeLine(face.getFace().getV1().getX(), face.getFace().getV1().getY(), face.getFace().getV2().getX(), face.getFace().getV2().getY());
            context.strokeLine(face.getFace().getV1().getX(), face.getFace().getV1().getY(), face.getFace().getV3().getX(), face.getFace().getV3().getY());
            context.strokeLine(face.getFace().getV2().getX(), face.getFace().getV2().getY(), face.getFace().getV3().getX(), face.getFace().getV3().getY());
        } else if (rm == RenderingMode.FILLED) {
            context.strokePolygon(xpoints, ypoints, 3);
            context.fillPolygon(xpoints, ypoints, 3);
        }
    }


    @Override
    public void setPredecessor(Pipe predecessor) {
        this.predecessor = predecessor;
    }

    @Override
    public FaceWithColor read(){
        do {
            FaceWithColor face = predecessor.read();

            if (face != null) {
                if (face.getColor() == Color.PINK) {
                    break;
                } else {
                    context.setFill(face.getColor());
                    context.setStroke(face.getColor());
                    double[] xpoints = new double[3];
                    double[] ypoints = new double[3];
                    xpoints[0] = face.getFace().getV1().getX();
                    xpoints[1] = face.getFace().getV2().getX();

                    xpoints[2] = face.getFace().getV3().getX();
                    ypoints[0] = face.getFace().getV1().getY();
                    ypoints[1] = face.getFace().getV2().getY();
                    ypoints[2] = face.getFace().getV3().getY();

                    if (rm == RenderingMode.POINT) {
                        context.strokeOval(face.getFace().getV1().getX(), face.getFace().getV1().getY(), 1, 1);
                    } else if (rm == RenderingMode.WIREFRAME) {
                        context.strokeLine(face.getFace().getV1().getX(), face.getFace().getV1().getY(), face.getFace().getV2().getX(), face.getFace().getV2().getY());
                        context.strokeLine(face.getFace().getV1().getX(), face.getFace().getV1().getY(), face.getFace().getV3().getX(), face.getFace().getV3().getY());
                        context.strokeLine(face.getFace().getV2().getX(), face.getFace().getV2().getY(), face.getFace().getV3().getX(), face.getFace().getV3().getY());
                    } else if (rm == RenderingMode.FILLED) {
                        context.strokePolygon(xpoints, ypoints, 3);
                        context.fillPolygon(xpoints, ypoints, 3);
                    }
                }
            }
        } while(true);
        return null;
    }
}
