import java.util.*;
import java.time.LocalDate;
class HabitService
{
    private final DailyLog dailyLog = new DailyLog();
    private final List<Habit> habits = new ArrayList<>();
    private int nextId=1;
    public Habit addHabit(String name)
    {
        Habit habit=new Habit(nextId++,name);
        habits.add(habit);
        return habit;
    }
    public List<Habit> listHabits()
    {
        return new ArrayList<>(habits);
    }
    public boolean habitExists(int id) {
        return habits.stream().anyMatch(h -> h.getId() == id);
    }

    // true = newly marked, false = already marked today
    public boolean markDoneToday(int habitId) {
        return dailyLog.markDone(habitId, LocalDate.now());
    }

    public boolean isDoneToday(int habitId) {
        return dailyLog.isDone(habitId, LocalDate.now());
    }

    public boolean removeHabit(int id) {
        boolean removed = habits.removeIf(h -> h.getId() == id);
        if (removed) {
            dailyLog.removeHabit(id);
        }
        return removed;
    }
}
