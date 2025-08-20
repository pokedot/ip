import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FlyingNugget {
    public static void main(String[] args) {
        String intro =  """
            Hello from
             _____ _       _             _   _                        _
            |  ___| |_   _(_)_ __   __ _| \\ | |_   _  __ _  __ _  ___| |_
            | |_  | | | | | | '_ \\ / _` |  \\| | | | |/ _` |/ _` |/ _ \\ __|
            |  _| | | |_| | | | | | (_| | |\\  | |_| | (_| | (_| |  __/ |_
            |_|   |_|\\__, |_|_| |_|\\__, |_| \\_|\\__,_|\\__, |\\__, |\\___|\\__|
                     |___/         |___/             |___/ |___/
            """;

        String name = "FlyingNugget";
        String line = "\t____________________________________________________________";
        Scanner scanner = new Scanner(System.in);
        List<String> tasks = new ArrayList<>();

        System.out.println(intro);
        System.out.println(line);
        System.out.println("\tHello! I'm " + name);
        System.out.println("\tWhat can I do for you?");
        System.out.println(line);

        while (true) {
            String input = scanner.nextLine();
            if (input.equals("bye")) {
                break;
            } else if (input.equals("list")) {
                System.out.println(line);
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.println("\t" + (i + 1) + ". " + tasks.get(i));
                }
                System.out.println(line);
            } else {
                System.out.println(line);
                tasks.add(input);
                System.out.println("\t added: " + input);
                System.out.println(line);
            }
        }

        System.out.println(line);
        System.out.println("\tBye. Hope to see you again soon!");
        System.out.println(line);
        scanner.close();
    }
}
