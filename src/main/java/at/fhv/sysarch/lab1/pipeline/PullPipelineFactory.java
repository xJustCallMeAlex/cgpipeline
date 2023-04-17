package at.fhv.sysarch.lab1.pipeline;

import at.fhv.sysarch.lab1.animation.AnimationRenderer;
import at.fhv.sysarch.lab1.filters.*;
import at.fhv.sysarch.lab1.obj.Model;
import at.fhv.sysarch.lab1.pipe.Pipe;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Matrices;
import javafx.animation.AnimationTimer;

public class PullPipelineFactory {
    public static AnimationTimer createPipeline(PipelineData pd) {
        // pull from the source (model)
        Source source = new Source(pd.getModel());
        Pipe beforeSource = new Pipe();
        beforeSource.setPredecessor(source);

        // 1. perform model-view transformation from model to VIEW SPACE coordinates
        MVTransformationFilter mvTransformationFilter = new MVTransformationFilter();
        Pipe beforeMVTransformation = new Pipe();
        beforeMVTransformation.setPredecessor(mvTransformationFilter);
        mvTransformationFilter.setPredecessor(beforeSource);

        // TODO 2. perform backface culling in VIEW SPACE
        BFCullingFilter bfCullingFilter = new BFCullingFilter();
        Pipe beforeBFCulling = new Pipe();
        beforeBFCulling.setPredecessor(bfCullingFilter);
        bfCullingFilter.setPredecessor(beforeMVTransformation);

        // TODO 3. perform depth sorting in VIEW SPACE
        DepthSortingFilter depthSortingFilter = new DepthSortingFilter();
        Pipe beforeDepthSorting = new Pipe();
        beforeDepthSorting.setPredecessor(depthSortingFilter);
        depthSortingFilter.setPredecessor(beforeBFCulling);

        // 4. add coloring (space unimportant)
        ColorFilter colorFilter = new ColorFilter(pd.getModelColor());
        Pipe beforeColor = new Pipe();
        beforeColor.setPredecessor(colorFilter);
        colorFilter.setPredecessor(beforeDepthSorting);

        //Create the projection transformation
        ProjectionTransformationFilter projectionTransformationFilter = new ProjectionTransformationFilter(pd.getProjTransform());
        Pipe beforeProjTransform = new Pipe();
        beforeProjTransform.setPredecessor(projectionTransformationFilter);

        // lighting can be switched on/off
        if (pd.isPerformLighting()) {
            // 4a. TODO perform lighting in VIEW SPACE
            LightingFilter lightingFilter = new LightingFilter(pd.getLightPos());
            Pipe beforeLighting = new Pipe();
            beforeLighting.setPredecessor(lightingFilter);
            lightingFilter.setPredecessor(beforeColor);
            
            // 5. TODO perform projection transformation on VIEW SPACE coordinates
            projectionTransformationFilter.setPredecessor(beforeLighting);
        } else {
            // 5. TODO perform projection transformation
            projectionTransformationFilter.setPredecessor(beforeColor);
        }

        // TODO 6. perform perspective division to screen coordinates
        PerspectiveDivisionFilter perspectiveDivisionFilter = new PerspectiveDivisionFilter(pd.getViewportTransform());
        Pipe beforePerspectiveDivision = new Pipe();
        beforePerspectiveDivision.setPredecessor(perspectiveDivisionFilter);
        perspectiveDivisionFilter.setPredecessor(beforeProjTransform);

        // TODO 7. feed into the sink (renderer)
        Sink sink = new Sink(pd.getGraphicsContext(), pd.getRenderingMode());
        Pipe beforeSink = new Pipe();
        beforeSink.setPredecessor(sink);
        sink.setPredecessor(beforePerspectiveDivision);

        // returning an animation renderer which handles clearing of the
        // viewport and computation of the praction
        return new AnimationRenderer(pd) {
            // TODO rotation variable goes in here
            float rotation = 0;

            /** This method is called for every frame from the JavaFX Animation
             * system (using an AnimationTimer, see AnimationRenderer). 
             * @param fraction the time which has passed since the last render call in a fraction of a second
             * @param model    the model to render 
             */
            @Override
            protected void render(float fraction, Model model) {
                // TODO compute rotation in radians
                rotation += fraction;
                float radians = (float) (rotation % (2 * Math.PI));

                // TODO create new model rotation matrix using pd.getModelRotAxis and Matrices.rotate
                Mat4 rotationMatrix = pd.getViewTransform().multiply(pd.getModelTranslation()).multiply(Matrices.rotate(radians, pd.getModelRotAxis()));

                // TODO compute updated model-view tranformation
                // Done in step above

                // TODO update model-view filter
                mvTransformationFilter.setTransformation(rotationMatrix);

                // TODO trigger rendering of the pipeline
                sink.read();
            }
        };
    }
}