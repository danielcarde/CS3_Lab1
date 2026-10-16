import java.util.Random;

public class HashTableRegression {
    private static void require(boolean ok, String message) {
        if (!ok) throw new AssertionError(message);
    }
    public static void main(String[] args) {
        for (int capacity : new int[]{0, -1}) {
            try { new HashTable(capacity); throw new AssertionError("Invalid capacity accepted"); }
            catch (IllegalArgumentException expected) { }
        }
        HashTable table = new HashTable(3);
        ImageFeature first = new ImageFeature("ab", new float[]{1});
        ImageFeature second = new ImageFeature("ba", new float[]{2});
        table.insert(first); table.insert(second); table.remove(first);
        require(table.returnFeature("ba") == second, "Collision chain lost after deletion");
        require(table.search("missing") == -1, "Absent lookup must terminate");
        table.insert(first);
        ImageFeature replacement = new ImageFeature("ba", new float[]{3});
        table.insert(replacement);
        require(table.returnFeature("ba") == replacement, "Duplicate must replace by name");
        table.insert(new ImageFeature("c", new float[]{4}));
        try { table.insert(new ImageFeature("d", new float[]{5})); throw new AssertionError("Full insert accepted"); }
        catch (IllegalStateException expected) { }
        table.remove(second); table.insert(new ImageFeature("d", new float[]{5}));
        require(table.search("d") >= 0, "Deleted slot was not reused");
        table.remove(new ImageFeature("unknown", new float[0]));

        HashTable compared = new HashTable(257);
        BTree tree = new BTree(3);
        Random random = new Random(17);
        String[] names = new String[200];
        for (int i = 0; i < names.length; i++) names[i] = "image-" + i;
        for (int i = names.length - 1; i > 0; i--) {
            int other = random.nextInt(i + 1);
            String tmp = names[i]; names[i] = names[other]; names[other] = tmp;
        }
        for (String name : names) {
            ImageFeature feature = new ImageFeature(name, new float[]{1, 2});
            compared.insert(feature); tree.insert(feature);
        }
        for (String name : names)
            require(compared.returnFeature(name) == tree.returnFeature(name), "B-tree/hash mismatch");
        require(compared.search("absent") == -1 && tree.returnFeature("absent") == null, "Absent mismatch");
        require(compared.hash("z".repeat(100000)) >= 0, "Long-name hash overflow");
        System.out.println("Hash-table regression checks passed");
    }
}
