import actor.OrderManagerActor;
import akka.actor.typed.ActorSystem;
import message.OrderManagerMessage;
import message.impl.CreateOrderMessage;
import message.impl.GetListOfOrdersMessage;


public class Main {
    public static void main(String[] args) {
        ActorSystem<OrderManagerMessage> orderManager = ActorSystem.create(OrderManagerActor.createBehavior(), "OrderManager");
        orderManager.tell(new CreateOrderMessage("0"));
        orderManager.tell(new CreateOrderMessage("1"));
        orderManager.tell(new CreateOrderMessage("2"));
        orderManager.tell(new CreateOrderMessage("100"));
        orderManager.tell(new GetListOfOrdersMessage());
    }
}
