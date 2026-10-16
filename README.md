# Image Feature Search

Academic Java project by Daniel Cardenas comparing a custom linear-probing hash table and B-tree over image feature vectors. It extracts vectors from sample images and calculates cosine similarity for image pairs.

## Run

Requires a JDK (CI uses Java 21). Run from the repository root so relative image/data paths resolve.

```sh
mkdir -p build
javac -d build *.java tests/HashTableRegression.java
java -cp build FeatureFileGenerator
java -cp build PairFileGenerator
java -cp build lab1Main
```

`FeatureFileGenerator` creates `image_features.txt` from `dog.jpg`, `cat.jpg`, and `wolf.jpg`. The generated vector file is intentionally not committed. `PairFileGenerator` writes `image_pairs.txt`; it may replace that sample file. The console offers hash-table lookup, B-tree lookup, or exit.

## Data structures

`HashTable` validates capacity, bounds probing, preserves collision chains with deletion tombstones, and replaces a record with the same image name. Inserting a new name into a full table throws `IllegalStateException`. The console currently allocates 100 hash slots, so larger datasets need an explicit capacity change.

`BTree` and `BTreeNode` implement the tree comparison. `ImageToVector` extracts vectors; `ImageFeature` stores names and vectors. Console timings are exploratory measurements, not a controlled benchmark or a claim that one structure is faster.

## Verification

```sh
java -cp build HashTableRegression
```

Checks cover colliding keys after deletion, slot reuse, duplicate names, invalid capacity, full-table termination, absent records, and long names. A deterministic 200-record shuffled insertion set is looked up in both structures. Compilation and these checks passed locally on JDK 25; CI repeats on JDK 21. This does not exhaustively validate B-tree deletion or arbitrary image inputs.

Future work includes configurable capacity, stronger input validation, and reproducible performance experiments.
