package at.fhv.sysarch.lab1.filters;

import at.fhv.sysarch.lab1.obj.FaceWithColor;
import at.fhv.sysarch.lab1.pipe.Pipe;
import com.hackoeur.jglm.Vec3;
import com.hackoeur.jglm.Vec4;
import javafx.scene.paint.Color;

public class LightingFilter implements IFilter{
    private Pipe successor;
    private Vec3 lightPos;

    public LightingFilter(Vec3 lightPos) {
        this.lightPos = lightPos;
    }

    @Override
    public void setSuccessor(Pipe successor) {
        this.successor = successor;
    }

    @Override
    public void write(FaceWithColor face) {
        float dotProduct = face.getFace().getN1().toVec3().dot(lightPos.getUnitVector());
        if (dotProduct <= 0) {
            face.setColor(Color.BLACK);
        } else {
            face.setColor(face.getColor().deriveColor(0,1,dotProduct,1));
        }
        successor.write(face);
    }
}
