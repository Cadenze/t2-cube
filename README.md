# t2-cube

Java files for cube emulation, cube solving, time/effective turn metric calculation.

## Cube.java

Emulates a Rubik's Cube. Scramble optional.

### Constructors

* `Cube()` creates a goddamned 3x3 cube. Solved.
* `Cube(String instructions)` creates a cube, scrambled by the instructions given.

### Methods

* *void* `move(String move)` moves a cube by a single Singmaster notated move.
* *void* `printCube()` Outputs to console the colours of a cube as a net.
* *String* `toString()` returns the colours of a cube in a single line.
* *String* `toString(int face)` returns the colours of a single face in a line.
* *String* `toString(int face, int row, int cell)` returns the colour of a specific cell.