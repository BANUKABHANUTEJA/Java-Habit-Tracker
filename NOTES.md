# Java Habit Tracker: Learning Notes

Winter Arc project (Oct 3 – Dec 31, 2026). A console habit tracker in Java that grows into a Spring Boot + React app.

---

## Day 1: Habit class and input loop

**What I built:** A `Habit` class (id, name) and a loop in `Main` that reads habit names until I type `exit`, then prints them all.

**Why it works:**
- Fields are `private`, with public getters, so outside code can't change them directly (encapsulation).
- The `id` is `final` because an ID should never change after creation.
- `idCounter++` uses the current value and then increases it, so every habit gets a unique ID.
- `trim()` and `isEmpty()` stop blank names from being added.

**What confused me:** Why `String toString()` didn't compile.
- Every class extends `Object`, and `Object.toString()` is `public`.
- An overriding method can't have weaker access than the one it overrides, so mine must be `public`.
- `@Override` makes the compiler catch typos like `tostring()`.

**Other things I learned:**
- A setter normally returns `void`. I removed the return value.
- Only one class per file can be `public`, and the file name must match it. I moved `Habit` into its own file.
- Git: the project had a repo inside a repo, which showed up as a blue arrow folder on GitHub. I fixed it by keeping one repo at the top level and ignoring `.idea/` and `*.iml`.

---

## Day 2: HabitService and menu

**What I built:** A `HabitService` class with `addHabit`, `listHabits` and `removeHabit`, and a menu in `Main` (1 Add, 2 List, 3 Remove, 0 Exit).

**Why it works:**
- `Main` only reads input and prints output. `HabitService` holds the list and the logic. This is the same split as controller and service in Spring Boot.
- `Integer.parseInt(scanner.nextLine().trim())` inside `try/catch` stops the program crashing on text like "abc". I avoided `nextInt()` because it leaves the Enter key in the buffer.
- `removeIf(h -> h.getId() == id)` removes matching habits and returns `true` if anything was removed.
- `listHabits()` returns a copy so outside code can't change my data without using the service.

**What confused me:** Lambdas and how `javac` finds files.
- `h -> h.getId() == id` means "given a habit `h`, return whether its ID equals `id`".
- To compile from the terminal I need `javac -d out src/*.java`. Running `javac Main.java` from the project root fails because the files are inside `src`.

**Understanding check:**
1. *Why is `nextId` private in `HabitService`?* So only the service can create IDs. If `Main` could change it, IDs could clash or repeat.
2. *What happens if `continue` is removed from the `catch` block?* After a bad number, the code would carry on to the `switch` with `choice` unset, which won't even compile. `continue` sends the loop back to the menu.
3. *Why does `removeHabit` return a boolean?* So the caller can tell whether anything was removed and show the right message.

---

## Day 3: DailyLog and mark done today

**What I built:** A `DailyLog` class that records which habits were done on which date, plus menu options to mark a habit done today and see today's progress (`[x]` / `[ ]`).

**Why it works:**
- Data is stored as `Map<LocalDate, Set<Integer>>`: each date maps to the set of habit IDs done that day.
- A `Set` can't hold duplicates, so a habit can't be marked twice on one day. `set.add(x)` returns `false` if it was already there, which I use to detect "already marked".
- `computeIfAbsent(date, d -> new HashSet<>())` gets the set for a date, or creates one if there isn't one yet.
- `DailyLog` only knows dates and IDs, not `Habit` objects, which keeps it simple.

**What confused me:** Imports.
- `import java.util.*;` doesn't cover `LocalDate`, which lives in `java.time`. A wildcard only covers one package.
- The ternary `condition ? a : b` is a one-line `if/else` that gives a value.

**Understanding check:**
1. *Why `Set<Integer>` instead of `List<Integer>`?* A habit can only be done once a day, and a `Set` enforces uniqueness and checks "is it in there?" quickly.
2. *What does `computeIfAbsent` do?* If the key exists, it returns the stored value. If not, it creates a value, stores it and returns it, all in one call. This is shorter and safer than checking `containsKey` by hand.
3. *Why does `removeHabit` also call `dailyLog.removeHabit`?* Otherwise old logs would keep IDs for habits that no longer exist.

---

## Day 4: Custom exceptions and validation

**What I built:** `HabitNotFoundException` and `InvalidHabitException`. The service now rejects empty names, duplicate names (ignoring case), unknown IDs and future dates. I added a menu option to mark a habit done on a past date (`yyyy-MM-dd`).

**Why it works:**
- `HabitService` validates and **throws**. `Main` **catches** and prints `e.getMessage()`.
- Exceptions travel up the call stack until something catches them, so `Main` can handle errors thrown several calls below.
- `LocalDate.parse()` throws `DateTimeParseException` for bad text, so it always goes inside `try/catch` when the text comes from a user.

**What confused me:** Checked vs unchecked exceptions, and `throw` vs `throws`.
- **Checked** (extends `Exception`): the compiler forces me to handle it. Used for things outside my control, like a missing file.
- **Unchecked** (extends `RuntimeException`): no forced handling. Used for bad input and programmer mistakes.
- `throw` creates and throws an exception. `throws` in a method signature warns callers that it might.
- Catch the more specific exception first. `NumberFormatException` is a child of `IllegalArgumentException`, so reversing them is a compile error.

**Understanding check:**
1. *Why is `HabitNotFoundException` unchecked?* A missing ID is a caller mistake, not an unavoidable external failure, so it's unchecked. Checked exceptions are for things like file or network errors. Unchecked also keeps the service code clean and matches how Spring Boot works.
2. *Why validate in `HabitService` and not in `Main`?* The service owns the business rules, so they apply no matter who calls it (console, web, tests). `Main` only handles input and output.
3. *What does `catch (A | B e)` do?* It's a multi-catch: one block handles either exception type the same way, which avoids duplicated catch blocks. The two types can't be parent and child.

---

## Good habits so far
- Commit and push at the end of every session.
- Write the notes the same day, in my own words.
- Test after every small change, not at the end.
- Break the program on purpose: bad input, wrong IDs, empty values.

## Coming up
- **Day 5:** Streams, and stats (completion %, perfect days, per-habit totals).
- **Day 6:** Streak logic and JUnit tests.
- **Day 7:** Saving and loading data with files.
