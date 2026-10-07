public class HabitNotFoundException extends RuntimeException {
    public HabitNotFoundException(int id) {
        super("No habit with ID " + id);
    }
}