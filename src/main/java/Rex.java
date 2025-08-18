import java.util.Scanner;

public class Rex {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String chatbotName = "Rex";
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
            } else {
                System.out.println("____________________________________________________________");
                System.out.println(input);
                System.out.println("____________________________________________________________");
            }
        }
        sc.close();
    }
}
