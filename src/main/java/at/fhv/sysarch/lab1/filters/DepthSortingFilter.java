package at.fhv.sysarch.lab1.filters;

import at.fhv.sysarch.lab1.obj.FaceWithColor;
import at.fhv.sysarch.lab1.pipe.Pipe;
import javafx.scene.paint.Color;

import java.util.PriorityQueue;
import java.util.Queue;

public class DepthSortingFilter implements IFilter{
    private Pipe predecessor;
    private Queue<FaceWithColor> queue;
    private boolean isBuffering;


    public DepthSortingFilter() {
        isBuffering = true;
        queue = new PriorityQueue<>((face1, face2) -> {
            float face1AVG = ((face1.getFace().getV1().getZ() + face1.getFace().getV2().getZ() + face1.getFace().getV3().getZ())/3);
            float face2AVG = ((face2.getFace().getV1().getZ() + face2.getFace().getV2().getZ() + face2.getFace().getV3().getZ())/3);

            return Float.compare(face1AVG, face2AVG);
        });
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
                queue.add(face);
            }
        }

        if (queue.size() <= 1) {
            isBuffering = true;
        }

        return queue.poll();
    }
}
