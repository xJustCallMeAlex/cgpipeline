package at.fhv.sysarch.lab1.filters;

import at.fhv.sysarch.lab1.obj.FaceWithColor;
import at.fhv.sysarch.lab1.obj.Model;
import at.fhv.sysarch.lab1.pipe.Pipe;

public class Source {
    private Pipe successor;

    public void setSuccessor(Pipe pipe) {
        this.successor = pipe;
    }


    public void write(Model model) {
        model.getFaces().forEach(face -> this.successor.write(new FaceWithColor(face, null)));
    }
}
