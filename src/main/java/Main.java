import actor.OrderManagerActor;
import akka.actor.typed.ActorSystem;
import message.OrderManagerMessage;
import message.impl.CreateOrderMessage;

public class Main {
    public static void main(String[] args) {
        ActorSystem<OrderManagerMessage> orderManager = ActorSystem.create(OrderManagerActor.createBehavior(), "OrderManager");
        orderManager.tell(new CreateOrderMessage("0"));
        orderManager.tell(new CreateOrderMessage("0"));
        orderManager.tell(new CreateOrderMessage("0"));
        orderManager.tell(new CreateOrderMessage("0"));
    }
}
