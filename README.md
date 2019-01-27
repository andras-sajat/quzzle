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

## Running the application

* You will need to setup Java 1.8 JRE on the machine where you want to use the application.
* You will need to download the repository of the application from [Bitbucket](https://github.com/andras-sajat/quzzle/archive/master.zip)
* The after extracting it on your local machine, go the folder where you extracted it, and change into quzzle-master folder.
* From there run: 
    `` 
    java -cp quzzle.jar com.andris.Quzzle
    ``      