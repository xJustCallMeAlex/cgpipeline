package at.fhv.sysarch.lab1.filters;

import at.fhv.sysarch.lab1.obj.FaceWithColor;
import at.fhv.sysarch.lab1.pipe.Pipe;
import javafx.scene.paint.Color;

import java.util.PriorityQueue;
import java.util.Queue;

public class DepthSortingFilter implements IFilter{
    private Pipe predecessor;
    private Queue<FaceWithColor> buffer;
    private boolean isBuffering;


    public DepthSortingFilter() {
        isBuffering = true;
        buffer = new PriorityQueue<>((face1, face2) -> {
            float[] face1Z = new float[3];

            face1Z[0] = face1.getFace().getV1().getZ();
            face1Z[1] = face1.getFace().getV2().getZ();
            face1Z[2] = face1.getFace().getV3().getZ();

            float[] face2Z = new float[3];
            face2Z[0] = face2.getFace().getV1().getZ();
            face2Z[1] = face2.getFace().getV2().getZ();
            face2Z[2] = face2.getFace().getV3().getZ();

            return Float.compare(average(face1Z), average(face2Z));
        });
    }

    public float average(float[] numbers) {
        float sum = 0;
        for (float number : numbers) {
            sum += number;
        }
        return sum / numbers.length;
    }

    @Override
    public void setSuccessor(Pipe successor) {
        // NOT IMPLEMENTED
    }

    @Override
    public void write(FaceWithColor face) {
        // NOT IMPLEMENTED
    }


    @Override
    public void setPredecessor(Pipe predecessor) {
        this.predecessor = predecessor;
    }

    @Override
    public FaceWithColor read() {
        while (isBuffering) {
            FaceWithColor face = predecessor.read();

            if (face != null) {
                if (face.getColor() == Color.PINK) {
                    isBuffering = false;
                }
                buffer.add(face);
            }
        }

        if (buffer.size() <= 1) {
            isBuffering = true;
        }

        return buffer.poll();
    }
}
