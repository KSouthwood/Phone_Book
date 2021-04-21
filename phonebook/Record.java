package phonebook;

public class Record {
    private final long number;
    private final String name;

    Record(String entry) {
        String[] values = entry.split("\\s", 2);
        this.number = Long.parseLong(values[0]);
        this.name = values[1];
    }

    public long getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Record{" +
                "number=" + number +
                ", name='" + name + '\'' +
                '}';
    }
}
