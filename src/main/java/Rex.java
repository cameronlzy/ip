import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Rex {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String chatbotName = "Rex";
        List<String> list = new ArrayList<>();
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
                for(int i = 0; i < list.size(); i++) {
                    System.out.println(i + 1 + ". " + list.get(i));
                }
                System.out.println("____________________________________________________________");
            } else {
                list.add(input);
                System.out.println("____________________________________________________________");
                System.out.println("added: " + input);
                System.out.println("____________________________________________________________");
            }
        }
        sc.close();
    }
}
