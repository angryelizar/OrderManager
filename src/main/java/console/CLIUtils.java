package console;

import com.diogonunes.jcolor.AnsiFormat;
import state.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.diogonunes.jcolor.Ansi.colorize;
import static com.diogonunes.jcolor.Attribute.*;
import static com.diogonunes.jcolor.Attribute.NONE;

public class CLIUtils {
    public static final Map<String, String> options = new HashMap<>(Map.of(
            "help", "Get all commands",
            "list", "Show the all orders",
            "create <id>", "Creates a new order with id",
            "update <id> <status>", "Updates the status of an order with id to the specified status " +
                    "(Created, InProgress, Completed, Canceled)",
            "delete <id>", "Deletes the order with identifier id.",
            "exit", "Exit the CLI."
    ));

    public static final Map<String, OrderStatus> statesMap = new HashMap<>(Map.of(
            enums.OrderStatus.CREATED.getValue(), new Created(),
            enums.OrderStatus.IN_PROGRESS.getValue(), new InProgress(),
            enums.OrderStatus.COMPLETED.getValue(), new Completed(),
            enums.OrderStatus.CANCELLED.getValue(), new Cancelled()
    ));

    static AnsiFormat fError = new AnsiFormat(RED_TEXT(), NONE());
    static AnsiFormat fInfo = new AnsiFormat(CYAN_TEXT());
    static AnsiFormat warningText = new AnsiFormat(CYAN_TEXT());
    AnsiFormat fWarning = new AnsiFormat(GREEN_TEXT(), NONE(), NONE());

    public static void printError(String message) {
        System.out.println(colorize(message, fError));
    }

    public static void printInfo(String message) {
        System.out.println(colorize(message, fInfo));
    }

    public static void printWarning(String message) {
        System.out.println(colorize(message, warningText));
    }



}
