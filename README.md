# Quzzle solver

## Design

The task is to find a solution for getting from the initial state of the board to the target state abiding to the rules of the Quzzle game in maximum 90 moves.

There are two many possible states of the board to explore all of them so my solution is to start from the initial state and explore all possible moves reachable in one move from there. Then do this inductive from the next steps too until a maximum of 90 steps. If the solution is not found until then, then return to an earlier state and try the other possibilities there.

This solution would explore only those states of the board which are reachable in maximum 90 moves, which is much less..

To make it more efficient, when i try the possible moves from a state i start with those which are closer to the target.

## Implementation

I used 4 classes.

* Quzzle - the main class, responsible for finding the solution and to interact with the user.
* Table - to represent a state of the board
* Shape - to represent a shape on the board
* Move - to represent a move on the table

The initial and target setup of the table is stored in two text files in the root folderÉ
* 'original_starting_state.txt' contains the setup of the starting state
* 'original_target.txt' contains the setup of the target state

The format of these files is the following:
Each line contains a shape to put on the table. A shape has the following attributes separated by a space:
- the vertical size of the shape (1-2)
- the horizontal size of the shape (1-2)
- the type of the shape represented by an integer (1: 2*2 shape, 2: 2*1 shape, 3: 1*2 shape, 4: 1*1 shape)
- the unique identifier of the shape (1-9)
- the horizontal position of the shape on the table (0 to 3)
- the vertical position of the shape on the table (0 to 4)


## Running the application

* You will need to setup Java 1.8 JRE on the machine where you want to use the application.
* You will need to download the repository of the application from [Bitbucket](https://github.com/andras-sajat/quzzle/archive/master.zip)
* After extracting it on your local machine, go the folder where you extracted it, and change into quzzle-master folder.
* From there run: 
    `` 
    java -cp quzzle.jar com.andris.Quzzle
    ``
* The solution will appear on the standard output showing the moves needed. The move will tell you which piece (identified by the unique identifier from the setup) needs to move in which direction.

Also in the repository there is the output of a successful run in the 'console-output.txt'.
