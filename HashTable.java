import java.util.Objects;

public class HashTable {
    private final ImageFeature[] ifTable;
    private final boolean[] deleted;
    private final int buckets;

    public HashTable(int buckets) {
        if (buckets <= 0) throw new IllegalArgumentException("Capacity must be positive");
        this.buckets = buckets;
        this.ifTable = new ImageFeature[buckets];
        this.deleted = new boolean[buckets];
    }

    public int hash(String name) {
        Objects.requireNonNull(name, "Feature name");
        long value = 0;
        for (int i = 0; i < name.length(); i++) value += name.charAt(i);
        return (int) (value % buckets);
    }

    /** Insert or replace by name; insertion into a full table fails promptly. */
    public void insert(ImageFeature image) {
        Objects.requireNonNull(image, "Feature");
        int key = hash(image.name);
        int available = -1;
        for (int probes = 0; probes < buckets; probes++) {
            if (ifTable[key] != null) {
                if (ifTable[key].name.equals(image.name)) {
                    ifTable[key] = image;
                    return;
                }
            } else {
                if (available < 0) available = key;
                if (!deleted[key]) break;
            }
            key = (key + 1) % buckets;
        }
        if (available < 0) throw new IllegalStateException("Hash table is full");
        ifTable[available] = image;
        deleted[available] = false;
    }

    public int search(String name) {
        int key = hash(name);
        for (int probes = 0; probes < buckets; probes++) {
            if (ifTable[key] != null) {
                if (ifTable[key].name.equals(name)) return key;
            } else if (!deleted[key]) return -1;
            key = (key + 1) % buckets;
        }
        return -1;
    }

    public void remove(ImageFeature image) {
        Objects.requireNonNull(image, "Feature");
        int key = search(image.name);
        if (key < 0) return;
        ifTable[key] = null;
        deleted[key] = true;
    }

    public ImageFeature returnFeature(String name) {
        int key = search(name);
        return key < 0 ? null : ifTable[key];
    }

    public void printTable() {
        for (int i = 0; i < buckets; i++)
            System.out.println(i + ": " + (ifTable[i] == null ? "x" : ifTable[i].name));
    }
}
