
# Order Management System (Akka Actors)

This is a mini-system for managing orders, implemented using Akka Actor Typed (Java 11, Maven). The system allows for creating, updating, and deleting orders, with each order being represented by an individual actor. The system also includes a simple command-line interface (CLI) to interact with the orders.

## Features

- **Order Management**: 
  - Create, update, and delete orders via `OrderManager` actor.
  - Orders are managed in individual actors (`OrderActor`), each handling its own state.
  - States of an order: Created, InProgress, Completed, and Cancelled.
  - Logs events for every action taken on orders (creation, updating, deletion).

- **State Design Pattern**: 
  - `OrderActor` uses the State design pattern to manage its status transitions.

- **Emulated Database**: 
  - A stub repository using `Map<Integer, ActorRef<OrderMessage>>` is used to emulate a database, temporarily storing orders in memory.

- **Command-Line Interface**:
  - A simple CLI is implemented to interact with the system:
    - `create <id>` – creates a new order with the specified ID (IDs are numeric).
    - `update <id> <status>` – updates the status of the order (Created, InProgress, Completed, Cancelled).
    - `delete <id>` – deletes an order.
    - `list` – lists all orders with their current statuses.
    - `help` – shows the list of available commands.
    - `exit` – terminates the `OrderManager` actor and exits the application.

## Technologies Used

- **Akka Actor Typed** for managing actor-based concurrency.
- **Java 11** for the development environment.
- **Maven** for project management and dependencies.

## How to Run

1. Clone the repository.
2. Build the project with Maven:

   ```bash
   mvn clean install
   ```

3. Run the application:

   ```bash
   mvn exec:java
   ```

4. Use the CLI to manage orders.

## Example Usage

```bash
OrderManager App CLI started!

Enter the "help" for get more information about commands
% help
exit                 - Exit the CLI.       
help                 - Get all commands    
create <id>          - Creates a new order with id
list                 - Show the all orders 
delete <id>          - Deletes the order with identifier id.
update <id> <status> - Updates the status of an order with id to the specified status (Created, InProgress, Completed, Canceled)
% create 1
03:38:23.326 [OrderManager-akka.actor.default-dispatcher-5] INFO actor.OrderManagerActor -- Created a new order with ID 1
% update 1 InProgress
03:38:39.308 [OrderManager-akka.actor.default-dispatcher-4] INFO state.Created -- Order has changed state to in-progress
% update 1 Completed
03:38:50.177 [OrderManager-akka.actor.default-dispatcher-5] INFO state.Created -- Order completed!
% delete 1
03:38:55.950 [OrderManager-akka.actor.default-dispatcher-5] INFO actor.OrderManagerActor -- Deleting order 1
% list
Command not found!
% list
% create 1
03:39:12.033 [OrderManager-akka.actor.default-dispatcher-5] INFO actor.OrderManagerActor -- Created a new order with ID 1
% list
03:39:15.813 [OrderManager-akka.actor.default-dispatcher-4] INFO actor.OrderActor -- |  ID 1     | Created              |
```

## Design Overview

- **OrderManager Actor**: Handles the list of orders and provides APIs to create, delete, and update orders.
- **OrderActor**: Represents individual orders with their states and can transition between them using the State design pattern.
- **Message Types**:
  - `CreateOrderMessage`: Create a new order.
  - `DeleteOrderMessage`: Delete an existing order.
  - `GetListOfOrdersMessage`: Get the current list of all orders.
  - `UpdateOrderStatusMessage`: Update the status of an existing order.

## Logging

- Basic logging is implemented to capture all actions performed on the system, such as creating, updating, and deleting orders.

## Non-Functional Requirements

- Use of Akka Actor Typed and state management.
- Simple but functional CLI for interacting with the system.
- Minimum logging for operations.

## Conclusion

This project demonstrates the use of Akka Actors for building a simple, scalable order management system. The use of the State design pattern ensures that each order can transition through different states, while the actor model provides concurrency and isolation between orders.
