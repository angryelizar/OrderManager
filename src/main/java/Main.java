import actor.OrderManagerActor;
import akka.actor.typed.ActorSystem;
import message.Message;
import message.impl.CreateOrderMessage;

public class Main {
    public static void main(String[] args) {
        ActorSystem<Message> orderManager = ActorSystem.create(OrderManagerActor.createBehavior(), "OrderManager");
    }
}
