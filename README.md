# t2-cube

Java files for cube emulation, cube solving, time/effective turn metric calculation.

## RubiksCube.java

Emulates a Rubik's Cube as 6 separate faces. Scramble optional.

### Constructors

* `RubiksCube()` creates a goddamned 3x3 cube. Solved.
* `RubiksCube(String instructions)` creates a cube, scrambled by the instructions given.

### Methods

* *boolean* `compareCells(int face, int cell1, int cell2)` compares the colours of two cells on the same face.
* *boolean* `compareCells(int face1, int cell1, int face2, int cell2)` compares the colours of two cells.
* *String* `compileCell(int face, int cell)` generates the colour of a cell.
* *String*[][] `compileCube()` generates 6 arrays representing the colours of the cube.
* *String*[] `compileFace(int face)` generates an array of colours.
* *int* `findCentre(String colour)` finds the face of a centre.
* *int* `findCorner(Corner cubelet)` finds the position of a corner.
* *int* `findCorner(Corner cubelet, int start, int stop)` fancy findCorner with search parameters.
* *int* `findEdge(Edge cubelet)` finds the position of an edge.
* *int* `findEdge(Edge cubelet, int start, int stop)` fancy findEdge with search parameters.
* *String* `getCentre(int face)` get centre from face.
* *Corner* `getCorner(int position)` get corner from position.
* *Edge* `getEdge(int position)` get edge from position.
* *boolean* `getParity(int position)` get parity from position.
* *int* `getSpin(int position)` get spin from position.
* *void* `move(String move)` moves a cube by a single Singmaster notated move.
* *void* `moves(String moves)` multiple "move"s.
* *void* `printCube()` outputs to console the colours of a cube as a net.
* *String* `toString()` returns the colours of a cube in a single line.
* *String* `toString(int face)` returns the colours of a single face in a line.
* *String* `toString(int face, int cell)` returns the colour of a specific cell.

## Solve.java

Solves a Rubik's Cube with the Beginner's Layer-by-layer (4LLL) method.

### Constructors

* `Solve()` does effin' nothing.
* `Solve(String instructions)` scrambles and solves.
* `Solve(String instructions, boolean solve)` to solve, or not to solve?

### Methods

* *void* `solve()` solves a cube using the beginner's method, with 4-look last layer.
    * *void* `cross()` creates a white cross.
    * *void* `corners()` fills the first layer.
    * *void* `secondLayer()` inserts the second layer.
    * *void* `oll1()` creates a yellow cross.
    * *void* `oll2()` orients the yellow face.
    * *void* `pll1()` permutates the corners.
    * *void* `pll2()` permutates the edges.
    * *void* `alignment()` rotates the top layer to complete the cube.
* *int* `countHTM(String moves)` returns the half turn metric of the given moves.
* *int* `countQTM(String moves)` returns the quarter turn metric of the given moves.
* *int* `countSTM(String moves)` returns the slice turn metric of the given moves.
* *int* `getHTM()` retrieves the current half turn metric.
* *int* `getQTM()` retrieves the current quarter turn metric.
* *int* `getSTM()` retrieves the current slice turn metric.
* *void* `updateMetrics(String moves)` adds to the metrics given a set of moves.

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
