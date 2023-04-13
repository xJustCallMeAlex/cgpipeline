package at.fhv.sysarch.lab1.pipeline;

import at.fhv.sysarch.lab1.animation.AnimationRenderer;
import at.fhv.sysarch.lab1.filters.*;
import at.fhv.sysarch.lab1.obj.Model;
import at.fhv.sysarch.lab1.pipe.Pipe;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Matrices;
import javafx.animation.AnimationTimer;

public class PushPipelineFactory {
    public static AnimationTimer createPipeline(PipelineData pd) {

        // TODO: push from the source (model)
        // Create the source that pushes each face through the pipeline
        Source source = new Source();
        Pipe afterSource = new Pipe();
        source.setSuccessor(afterSource);

        // TODO 1. perform model-view transformation from model to VIEW SPACE coordinates
        // Create the first filter that performs the Model View Transformation
        MVTransformationFilter mVTransformationFilter = new MVTransformationFilter();
        afterSource.setSuccessor(mVTransformationFilter);
        Pipe afterMVTransformation = new Pipe();
        mVTransformationFilter.setSuccessor(afterMVTransformation);

        // TODO 2. perform backface culling in VIEW SPACE
        // Create the second filter that culls all unnecessary faces
        BFCullingFilter bFCullingFilter = new BFCullingFilter();
        afterMVTransformation.setSuccessor(bFCullingFilter);
        Pipe afterBFCulling = new Pipe();
        bFCullingFilter.setSuccessor(afterBFCulling);

        // TODO 3. perform depth sorting in VIEW SPACE
        // Only implemented in the Pull Pipeline

        // TODO 4. add coloring (space unimportant)
        // Create the color filter that adds the correct color to each face
        ColorFilter colorFilter = new ColorFilter(pd.getModelColor());
        afterBFCulling.setSuccessor(colorFilter);
        Pipe afterColor = new Pipe();
        colorFilter.setSuccessor(afterColor);

        // Create the projection transformation
        ProjectionTransformationFilter projectionTransformationFilter = new ProjectionTransformationFilter(pd.getProjTransform());

        // lighting can be switched on/off
        if (pd.isPerformLighting()) {
            // 4a. TODO perform lighting in VIEW SPACE
            // Create the lighting filter that changes the color of the faces, depending on their angle to the light-source
            LightingFilter lightingFilter = new LightingFilter(pd.getLightPos());
            afterColor.setSuccessor(lightingFilter);
            Pipe afterLighting = new Pipe();
            lightingFilter.setSuccessor(afterLighting);
            
            // 5. TODO perform projection transformation on VIEW SPACE coordinates
            afterLighting.setSuccessor(projectionTransformationFilter);


        } else {
            // 5. TODO perform projection transformation
            afterColor.setSuccessor(projectionTransformationFilter);
        }
        Pipe afterProjTransform = new Pipe();
        projectionTransformationFilter.setSuccessor(afterProjTransform);

        // TODO 6. perform perspective division to screen coordinates
        // Create the viewport transformation
        PerspectiveDivisionFilter perspectiveDivisionFilter = new PerspectiveDivisionFilter(pd.getViewportTransform());
        afterProjTransform.setSuccessor(perspectiveDivisionFilter);
        Pipe afterPerspectiveDivision = new Pipe();
        perspectiveDivisionFilter.setSuccessor(afterPerspectiveDivision);

        // TODO 7. feed into the sink (renderer)
        // Create the sink that renders the object on the screen
        Sink sink = new Sink(pd.getGraphicsContext(), pd.getRenderingMode());
        afterPerspectiveDivision.setSuccessor(sink);

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

                // TODO create new model rotation matrix using pd.modelRotAxis
                Mat4 rotationMatrix = pd.getViewTransform().multiply(pd.getModelTranslation()).multiply(Matrices.rotate(radians, pd.getModelRotAxis()));

                // TODO compute updated model-view tranformation
                // Done in step above

                // TODO update model-view filter
                mVTransformationFilter.setTransformation(rotationMatrix);

                // TODO trigger rendering of the pipeline
                source.write(model);

            }
        };
    }
}