package mediator;

import java.util.stream.Stream;

@FunctionalInterface
public interface PipelineSteps {

  Stream<PipelineStep> supply();

}
