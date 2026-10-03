import java.util.*;
public class Main {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    List<Habit> habits = new ArrayList<>();
    int idCounter = 1;
    while (true) {
      System.out.println("Enter a habit name (or type 'exit' to quit):");
      String input = scanner.nextLine().trim();
      if (input.equalsIgnoreCase("exit")) {
        break;
      }
      if (input.isEmpty()) {
        System.out.println("Habit name cannot be empty. Please try again.");
        continue;
      }
      Habit habit = new Habit(idCounter++, input);
      habits.add(habit);
      System.out.println("Added: " + habit);
    }
    System.out.println("Your habits:");
    for (Habit habit : habits) {
      System.out.println(habit);
    }
    scanner.close();
  }
}