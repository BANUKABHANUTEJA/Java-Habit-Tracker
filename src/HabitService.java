import java.util.*;
class HabitService
{
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
    public boolean removeHabit(int id) {
        return habits.removeIf(h->h.getId()==id);
    }
}
