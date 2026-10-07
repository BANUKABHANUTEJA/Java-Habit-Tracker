import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    HabitService service = new HabitService();

    while (true) {
      System.out.println();
      System.out.println("1. Add habit");
      System.out.println("2. List habits");
      System.out.println("3. Remove habit");
      System.out.println("4. Mark habit done today");
      System.out.println("5. Show today's progress");
      System.out.println("6. Mark habit done on a past date");
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
          String name = scanner.nextLine();
          try {
            Habit habit = service.addHabit(name);
            System.out.println("Added: " + habit);
          } catch (InvalidHabitException e) {
            System.out.println(e.getMessage());
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
            service.removeHabit(id);
            System.out.println("Removed habit " + id);
          } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
          } catch (HabitNotFoundException e) {
            System.out.println(e.getMessage());
          }
          break;
        }
        case 4: {
          System.out.print("Enter habit ID to mark done: ");
          try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            if (service.markDone(id, LocalDate.now())) {
              System.out.println("Marked habit " + id + " as done today.");
            } else {
              System.out.println("Habit " + id + " is already marked today.");
            }
          } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
          } catch (HabitNotFoundException | InvalidHabitException e) {
            System.out.println(e.getMessage());
          }
          break;
        }
        case 5: {
          List<Habit> habits = service.listHabits();
          if (habits.isEmpty()) {
            System.out.println("No habits yet.");
          } else {
            System.out.println("Today's progress:");
            for (Habit habit : habits) {
              String box = service.isDoneToday(habit.getId()) ? "[x]" : "[ ]";
              System.out.println(box + " " + habit.getName());
            }
          }
          break;
        }
        case 6: {
          System.out.print("Enter habit ID: ");
          try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Enter date (yyyy-MM-dd): ");
            LocalDate date = LocalDate.parse(scanner.nextLine().trim());
            if (service.markDone(id, date)) {
              System.out.println("Marked habit " + id + " as done on " + date);
            } else {
              System.out.println("Habit " + id + " is already marked on " + date);
            }
          } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
          } catch (DateTimeParseException e) {
            System.out.println("Invalid date. Use the format yyyy-MM-dd, e.g. 2026-10-03.");
          } catch (HabitNotFoundException | InvalidHabitException e) {
            System.out.println(e.getMessage());
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