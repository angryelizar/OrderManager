import actor.OrderManagerActor;
import akka.actor.typed.ActorSystem;
import message.OrderManagerMessage;
import message.impl.CreateOrderMessage;
import message.impl.DeleteOrderMessage;
import message.impl.UpdateOrderStatusMessage;
import state.Completed;
import state.Created;
import state.InProgress;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        ActorSystem<OrderManagerMessage> orderManager = ActorSystem.create(OrderManagerActor.createBehavior(), "OrderManager");
//        orderManager.tell(new CreateOrderMessage("0"));
//        orderManager.tell(new CreateOrderMessage("1"));
//        orderManager.tell(new CreateOrderMessage("2"));
        orderManager.tell(new CreateOrderMessage("100"));
        orderManager.tell(new UpdateOrderStatusMessage("100", new Created()));

        orderManager.tell(new UpdateOrderStatusMessage("100", new InProgress()));

        orderManager.tell(new DeleteOrderMessage("100"));
        orderManager.tell(new UpdateOrderStatusMessage("100", new InProgress()));
    }
}
