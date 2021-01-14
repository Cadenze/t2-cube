# t2-cube

Java files for cube emulation, cube solving, time/effective turn metric calculation.

## RubiksCube.java

Emulates a Rubik's Cube as 6 separate faces. Scramble optional.

### Constructors

* `RubiksCube()` creates a goddamned 3x3 cube. Solved.
* `RubiksCube(String instructions)` creates a cube, scrambled by the instructions given.

### Methods

* *String*[][] `compileCube()` generates 6 arrays representing the colours of the cube.
* *String*[] `compileFace()` generates an array of colours.
* *void* `move(String move)` moves a cube by a single Singmaster notated move.
* *void* `printCube()` Outputs to console the colours of a cube as a net.
* *String* `toString()` returns the colours of a cube in a single line.
* *String* `toString(int face)` returns the colours of a single face in a line.
* *String* `toString(int face, int cell)` returns the colour of a specific cell.

## Cube.java (deprecated)

Emulates a Rubik's Cube as 6 separate faces. Scramble optional.

### Constructors

* `Cube()` creates a goddamned 3x3 cube. Solved.
* `Cube(String instructions)` creates a cube, scrambled by the instructions given.

### Methods

* *void* `move(String move)` moves a cube by a single Singmaster notated move.
* *void* `printCube()` Outputs to console the colours of a cube as a net.
* *String* `toString()` returns the colours of a cube in a single line.
* *String* `toString(int face)` returns the colours of a single face in a line.
* *String* `toString(int face, int row, int cell)` returns the colour of a specific cell.

-----------

## Corner.java

Enum type for corners of a cube.

### Methods

* *String* `colour(int number)` returns the colour of a specific side of a corner piece.
* *String* `colour(int spin, int number)` returns the colour of a side of a corner, after being spun a specific amount of times.

## Edge.java

Enum type for edges of a cube.

### Methods

* *String* `colour(int number)` returns the colour of one side of an edge piece.
* *String* `colour(boolean parity, int number)` returns the colour of the side of an edge piece, taking into consideration parity.
