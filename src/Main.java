import java.util.*;

public class Main {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    HabitService service = new HabitService();

    while (true) {
      System.out.println();
      System.out.println("1. Add habit");
      System.out.println("2. List habits");
      System.out.println("3. Remove habit");
      System.out.println("0. Exit");
      System.out.print("Choose: ");

      int choice;
      try {
        choice = Integer.parseInt(scanner.nextLine().trim());
      } catch (NumberFormatException e) {
        System.out.println("Please enter a number.");
        continue;
      }

      switch (choice) {
        case 1: {
          System.out.print("Habit name: ");
          String name = scanner.nextLine().trim();
          if (name.isEmpty()) {
            System.out.println("Habit name cannot be empty.");
          } else {
            Habit habit = service.addHabit(name);
            System.out.println("Added: " + habit);
          }
          break;
        }
        case 2: {
          List<Habit> habits = service.listHabits();
          if (habits.isEmpty()) {
            System.out.println("No habits yet.");
          } else {
            for (Habit habit : habits) {
              System.out.println(habit);
            }
          }
          break;
        }
        case 3: {
          System.out.print("Enter habit ID to remove: ");
          try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            if (service.removeHabit(id)) {
              System.out.println("Removed habit " + id);
            } else {
              System.out.println("No habit with ID " + id);
            }
          } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
          }
          break;
        }
        case 0:
          System.out.println("Bye!");
          scanner.close();
          return;
        default:
          System.out.println("Invalid choice.");
      }
    }
  }
}