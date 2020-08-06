package mediator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Run each notification handler after one another.
 *
 * Returns when all handlers are finished.
 *
 * In case of any exception(s), they will be captured in an AggregateException.
 */
public class ContinueOnException implements NotificationHandlingStrategy {

  @Override
  public void handle(List<Runnable> runnableNotifications) {
    Collection<Throwable> exceptions = new ArrayList<>();
    runnableNotifications.forEach(it -> {
        try {
          it.run();
        } catch (Throwable e) {
          exceptions.add(e);
        }
    });
    throw new AggregateException(exceptions);
  }
}
