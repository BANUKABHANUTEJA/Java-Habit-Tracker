import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HabitService {
    private final DailyLog dailyLog = new DailyLog();
    private final List<Habit> habits = new ArrayList<>();
    private int nextId = 1;

    public Habit addHabit(String name) {
        String cleaned = (name == null) ? "" : name.trim();
        if (cleaned.isEmpty()) {
            throw new InvalidHabitException("Habit name cannot be empty.");
        }
        boolean duplicate = habits.stream()
                .anyMatch(h -> h.getName().equalsIgnoreCase(cleaned));
        if (duplicate) {
            throw new InvalidHabitException("Habit already exists: " + cleaned);
        }
        Habit habit = new Habit(nextId++, cleaned);
        habits.add(habit);
        return habit;
    }

    public List<Habit> listHabits() {
        return new ArrayList<>(habits);
    }

    public boolean habitExists(int id) {
        return habits.stream().anyMatch(h -> h.getId() == id);
    }


    public boolean markDone(int habitId, LocalDate date) {
        if (!habitExists(habitId)) {
            throw new HabitNotFoundException(habitId);
        }
        if (date.isAfter(LocalDate.now())) {
            throw new InvalidHabitException("Cannot mark a future date: " + date);
        }
        return dailyLog.markDone(habitId, date);
    }

    public boolean isDoneToday(int habitId) {
        return dailyLog.isDone(habitId, LocalDate.now());
    }

    public void removeHabit(int id) {
        boolean removed = habits.removeIf(h -> h.getId() == id);
        if (!removed) {
            throw new HabitNotFoundException(id);
        }
        dailyLog.removeHabit(id);
    }
}