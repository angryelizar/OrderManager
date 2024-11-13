package console;

import actor.OrderManagerActor;
import akka.actor.typed.ActorSystem;
import message.OrderManagerMessage;
import message.impl.CreateOrderMessage;
import message.impl.DeleteOrderMessage;
import message.impl.GetListOfOrdersMessage;
import message.impl.UpdateOrderStatusMessage;
import state.OrderStatus;

import java.util.Map;
import java.util.Scanner;

public class CLIApplication {
    private final ActorSystem<OrderManagerMessage> actorSystem;

    Map<String, String> options = CLIUtils.options;
    Map<String, OrderStatus> statesMap = CLIUtils.statesMap;

    public CLIApplication() throws InterruptedException {
        this.actorSystem = ActorSystem.create(OrderManagerActor.createBehavior(), "OrderManager");
        start();
    }

    private void start() throws InterruptedException {
        CLIUtils.printInfo("OrderManager App CLI started!\n");
        CLIUtils.printWarning("Enter the \"help\" for get more information about commands");
        Scanner scanner = new Scanner(System.in);

        Thread.sleep(1000);

        while (true) {
            System.out.print("% ");
            try {
                doValidCommand(scanner.nextLine().trim());
            } catch (ArrayIndexOutOfBoundsException e) {
                CLIUtils.printWarning("Parameter not found!");
            }
        }
    }

    private void doValidCommand(String input) throws InterruptedException {
        String[] parts = input.split(" ");
        switch (parts[0].toLowerCase()) {
            case "help":
                getHelp();
                break;
            case "exit":
                actorSystem.terminate();
                System.exit(0);
                break;
            case "list":
                listCommand();
                break;
            case "create":
                createCommand(parts[1]);
                break;
            case "delete":
                deleteCommand(parts[1]);
                break;
            case "update":
                updateCommand(parts[1], parts[2]);
                break;
            default:
                CLIUtils.printWarning("Command not found!");
                break;
        }
    }

    private void updateCommand(String id, String status) throws InterruptedException {
        if (Boolean.FALSE.equals(isNumber(id))) {
            CLIUtils.printError("ID need to be a number!");
            return;
        }
        actorSystem.tell(new UpdateOrderStatusMessage(id, statesMap.get(status)));
        Thread.sleep(1000);
    }

    private void deleteCommand(String id) throws InterruptedException {
        if (Boolean.FALSE.equals(isNumber(id))) {
            CLIUtils.printError("ID need to be a number!");
            return;
        }
        actorSystem.tell(new DeleteOrderMessage(id));
        Thread.sleep(1000);
    }

    private void createCommand(String id) throws InterruptedException {
        if (Boolean.FALSE.equals(isNumber(id))) {
            CLIUtils.printError("ID need to be a number!");
            return;
        }
        actorSystem.tell(new CreateOrderMessage(id));
        Thread.sleep(1000);
    }

    private void listCommand() throws InterruptedException {
        actorSystem.tell(new GetListOfOrdersMessage());
        Thread.sleep(1000);
    }

    private void getHelp() {
        for (String command : options.keySet()) {
            String value = options.get(command);
            CLIUtils.printWarning(String.format("%-20s - %-20s", command, value));
        }
    }

    public Boolean isNumber(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}