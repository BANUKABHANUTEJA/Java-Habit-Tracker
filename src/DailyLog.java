import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DailyLog {
    private final Map<LocalDate, Set<Integer>> logs = new HashMap<>();

    public boolean markDone(int habitId, LocalDate date) {
        Set<Integer> done = logs.computeIfAbsent(date, d -> new HashSet<>());
        return done.add(habitId);
    }

    public boolean isDone(int habitId, LocalDate date) {
        return logs.getOrDefault(date, Collections.emptySet()).contains(habitId);
    }
    public void removeHabit(int habitId) {
        for (Set<Integer> done : logs.values()) {
            done.remove(habitId);
        }
    }
}