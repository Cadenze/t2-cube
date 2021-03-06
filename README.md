# t2-cube

Java files for cube emulation, cube solving, time/effective turn metric calculation.

All documentation has been moved to the GitHub repository wiki page.

## Goals

We aim to be able to compare and contrast the common speedcubing methods not only based on turn numbers, but also consider holistically the smoothness of their algorithms, look times, regrips, and cube rotations. To achieve this, the project is split into 3 parts:

1. The design of either or both: 
    - a new metric, empirically determined to fit the realistic time of a solve, or
    - a theoretical deduction factor that alters the current metric numbers based on certain triggers;
2. The engineering of code, including:
    - the emulation of a Rubik's Cube,
    - the solvation of a Rubik's Cube using human techniques, and
    - the implementation of the aforementioned metric/factor; and lastly,
3. Using the program, generate a statistical distribution of solve metrics and analyze it.

## User's Manual

Run **Scrambler.java** to generate a number of scrambles. The result should be a text file **scrambles.txt** that contains a scramble per line.

Run **MultipleSolves.java** to solve all those generated scrambles. Type **scrambles.txt** in the first line, the name of a method (CFOP/Roux) in the second line, and the name of an output file of your choice (ideally in **.csv** format).

## v2 TODO list

* ~~Colour enumeration~~
* Universal search algorithm
* Graphical user interface
* More solve methods
    - ZZ (?)
    - Winter
* Statistics

I hope by the time I have finished this, summer isn't over and I will have finished learning the basics of linear algebra.

## v1 TODO list

* ~~Metric~~
    - ~~QTM, HTM, STM~~
    - ~~Empirical~~
    - ~~Deduction Factor~~
* ~~Emulation~~
* ~~Solve~~
    - ~~CFOP~~
    - ~~Roux~~
    - ~~ZZ(?)~~
* ~~Mass solve~~
* ~~Statistics~~

When this list is entirely crossed out, I will be basically done with my first year of my undergraduate degree.