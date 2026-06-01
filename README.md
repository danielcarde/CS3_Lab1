# Lab1: Image Feature Search

A Java lab that converts images into feature vectors and compares hash table versus B-tree lookup for image feature search.

## Highlights
Converts image files into numeric feature vectors.
Stores feature records in a custom hash table.
Stores feature records in a custom B-tree.
Uses image pair data to compare lookups.
Includes sample images for local testing.

## Overview
This lab explores searchable image feature data. The program can generate feature files from images, generate image pairs, load the data, and compare lookup behavior through a hash table or B-tree.
It combines image processing, file generation, custom data structures, and timing-style comparison in one Java project.

## Why This Project
This project is useful as a portfolio piece because it connects data structures to a more applied problem: searching image feature vectors. It shows how hash tables and trees can be compared in a concrete workflow.

## Usage
Compile: Compile all Java files with javac *.java.
Generate features: Run java FeatureFileGenerator if image_features.txt needs to be recreated.
Generate pairs: Run java PairFileGenerator if image_pairs.txt needs to be recreated.
Run: Execute the main comparison program with java lab1Main.

## Installation
Ensure you have the Java Development Kit (JDK) installed on your system.
Open a terminal in the lab folder before compiling so the program can find any required local text files.

## Architecture
File Structure: The project contains lab1Main.java, HashTable.java, BTree.java, BTreeNode.java, ImageFeature.java, ImageToVector.java, FeatureFileGenerator.java, PairFileGenerator.java, image.java, sample images, and image_pairs.txt.
Components: ImageToVector extracts image data, ImageFeature stores feature records, HashTable and BTree provide searchable structures, and lab1Main drives the comparison.

## Future Improvements
Regenerate large feature files instead of storing them in Git.
Add clearer timing and comparison output.
Support larger image datasets and more distance metrics.
