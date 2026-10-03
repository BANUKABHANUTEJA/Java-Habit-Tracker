class Habit {
    private final int id;
    private String name;
    public Habit(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public int getId() {
        return id;
    }
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return "Habit ID: " + id + ", Name: " + name;
    }

}
