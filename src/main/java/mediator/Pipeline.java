package mediator;

public interface Pipeline {

    <R, C extends Command<R>> R send(C command);

    <N extends Notification> void send(N notification);


}
