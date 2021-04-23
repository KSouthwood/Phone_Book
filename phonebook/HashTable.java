package phonebook;

public class HashTable<T> {
    private static class TableEntry<T> {
        private final int key;
        private final T value;
        private boolean removed;

        public TableEntry(int key, T value) {
            this.key = key;
            this.value = value;
        }

        public int getKey() {
            return key;
        }

        public T getValue() {
            return value;
        }

        public void remove() {
            removed = true;
        }

        public boolean isRemoved() {
            return removed;
        }
    }

    //    private static class HashTable<T> {
    private int size;
    private TableEntry[] table;

    public HashTable(int size) {
        this.size = size;
        table = new TableEntry[size];
    }

    public boolean put(int key, T value) {
        int idx = findKey(key);

        if (idx == -1) {
            rehash();
            idx = findKey(key);
        }

        table[idx] = new TableEntry(key, value);
        return true;
    }

    public T get(int key) {
        int idx = findKey(key);

        if (idx == -1 || table[idx] == null) {
            return null;
        }

        return (T) table[idx].getValue();
    }

    public void remove(int key) {
        int idx = findKey(key);

        if (idx != -1 && table[idx] != null) {
            table[idx].remove();
        }
    }

    private int findKey(int key) {
        int hash = key % size;

        while (!(table[hash] == null || table[hash].getKey() == key)) {
            hash = (hash + 1) % size;

            if (hash == key % size) {
                return -1;
            }
        }

        return hash;
    }

    private void rehash() {
        TableEntry[] oldTable = table;
        table = new TableEntry[size * 2];
        size *= 2;

        for (var entry : oldTable) {
            this.put(entry.getKey(), (T) entry.getValue());
        }
    }

    @Override
    public String toString() {
        StringBuilder tableStringBuilder = new StringBuilder();

        for (int i = 0; i < table.length; i++) {
            if (table[i] == null) {
                tableStringBuilder.append(i + ": null");
            } else {
                tableStringBuilder.append(i + ": key=" + table[i].getKey()
                        + ", value=" + table[i].getValue()
                        + ", removed=" + table[i].isRemoved());
            }

            if (i < table.length - 1) {
                tableStringBuilder.append("\n");
            }
        }

        return tableStringBuilder.toString();
    }
}