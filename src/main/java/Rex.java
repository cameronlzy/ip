import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Rex {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String chatbotName = "Rex";
        List<Task> tasks = new ArrayList<>();

        System.out.println("____________________________________________________________");
        System.out.println("Hello! I'm " + chatbotName);
        System.out.println("What can I do for you? \n");
        System.out.println("____________________________________________________________");

        while(true) {
            String input = sc.nextLine();

            if(input.equalsIgnoreCase("bye")) {
                System.out.println("____________________________________________________________");
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println("____________________________________________________________");
                break;
            } else if(input.equalsIgnoreCase("list")) {
                System.out.println("____________________________________________________________");
                System.out.println("Here are the tasks in your list:");
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.println((i + 1) + ". " + tasks.get(i));
                }
                System.out.println("____________________________________________________________");
            } else if (input.startsWith("mark ")) {
                try {
                    int index = Integer.parseInt(input.split(" ")[1]) - 1;
                    Task t = tasks.get(index);
                    t.markDone();
                    System.out.println("____________________________________________________________");
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println("  " + t);
                    System.out.println("____________________________________________________________");
                } catch (Exception e) {
                    System.out.println("Invalid task number!");
                }
            } else if (input.startsWith("unmark ")) {
                try {
                    int index = Integer.parseInt(input.split(" ")[1]) - 1;
                    Task t = tasks.get(index);
                    t.markUndone();
                    System.out.println("____________________________________________________________");
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println("  " + t);
                    System.out.println("____________________________________________________________");
                } catch (Exception e) {
                    System.out.println("Invalid task number!");
                }
            } else {
                tasks.add(new Task(input));
                System.out.println("____________________________________________________________");
                System.out.println("added: " + input);
                System.out.println("____________________________________________________________");
            }
        }
        sc.close();
    }
}
