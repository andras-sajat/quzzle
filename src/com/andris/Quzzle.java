package com.andris;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public class Quzzle {

    private static final int MAX_MOVES = 93;

    private String representationOfTarget;

    private ArrayList<Move> solution;

    private int moveId = 0;

    private ArrayList<String> visitedSets;

    public static void main(String[] args) {
        Quzzle quzzle = new Quzzle();
        System.out.println("Starting at "+ new Date());
        quzzle.findSolution();
        System.out.println("Stopping at "+ new Date());
    }

    private void findSolution() {
        Table initialTable = new Table();
        try {
            initialTable.setupTable(loadFromFile("original_starting_state.txt"));
            representationOfTarget = Table.stringOfTargetTable(loadFromFile("original_target.txt"));
        } catch (IOException e) {
            System.out.println("File not found.");
            return;
        }
        System.out.println("Initial table: \n"+initialTable.toString());
        visitedSets = new ArrayList<>();
        visitedSets.add(initialTable.stringCodeOfTable());
        ArrayList<Move> listOfPossibleMoves = initialTable.possibleMoves(this);
        boolean solutionFound = false;
        for(Move actualMove: listOfPossibleMoves) {
            solutionFound = executeNextStep(initialTable, actualMove, 1, new ArrayList<Move>());
            if(solutionFound) {
                break;
            }
        }
        System.out.println(solutionFound?
                    "Solution found in "+(moveId+1)+" moves. There are "+solution.size()+" moves needed: \n"+solution :
                    "No solution could be found. Explored "+(moveId+1)+" moves.");
    }

    private Table makeMove(Table oldTable, Move move) {
        moveId++;
        Table table = oldTable.copyOf();
        Shape s = table.getShapesOnTheTable().get(move.piece);
        switch(move.direction) {
            case DIRECTION_LEFT:
                s.setHPos(s.getHPos()-1);
                table.layout.set(s.getVPos()*4+s.getHPos()+s.getHSize(),0);
                table.layout.set(s.getVPos()*4+s.getHPos(),s.getId());
                if(s.getVSize()>1) {
                    table.layout.set((s.getVPos()+1)*4+s.getHPos()+s.getHSize(),0);
                    table.layout.set((s.getVPos()+1)*4+s.getHPos(),s.getId());
                }
                break;
            case DIRECTION_RIGHT:
                s.setHPos(s.getHPos()+1);
                table.layout.set(s.getVPos()*4+s.getHPos()-1,0);
                table.layout.set(s.getVPos()*4+s.getHPos()+s.getHSize()-1,s.getId());
                if(s.getVSize()>1) {
                    table.layout.set(s.getVPos()*4+s.getHPos()+3,0);
                    table.layout.set(s.getVPos()*4+s.getHPos()+s.getHSize()+3,s.getId());
                }
                break;
            case DIRECTION_UP:
                s.setVPos(s.getVPos()-1);
                table.layout.set(s.getVPos()*4+s.getHPos(), s.getId());
                table.layout.set((s.getVPos()+s.getVSize())*4+s.getHPos(),0);
                if(s.getHSize()>1) {
                    table.layout.set(s.getVPos()*4+s.getHPos()+1,s.getId());
                    table.layout.set((s.getVPos()+s.getVSize())*4+s.getHPos()+1,0);
                }
                break;
            case DIRECTION_DOWN:
                s.setVPos(s.getVPos() + 1);
                table.layout.set((s.getVPos() - 1) * 4 + s.getHPos(), 0);
                table.layout.set((s.getVPos() + s.getVSize() - 1) * 4 + s.getHPos(), s.getId());
                if (s.getHSize() > 1) {
                    table.layout.set((s.getVPos() - 1) * 4 + s.getHPos() + 1, 0);
                    table.layout.set((s.getVPos() + s.getVSize() - 1) * 4 + s.getHPos() + 1, s.getId());
                }
                break;
            default:
        }
        table.getShapesOnTheTable().set(move.piece, s);
        return table;
    }


    private boolean executeNextStep(Table actualTable, Move move, int depth, ArrayList<Move> listOfMoves) {
        if(weAreThere(actualTable)) {
            store(listOfMoves);
            return true;
        }
        Table newTable = makeMove(actualTable, move);
        if(weAlreadyVisited(newTable)) {
            return false;
        }
        visitedSets.add(newTable.stringCodeOfTable());
        ArrayList<Move> newListOfMoves = new ArrayList<>(listOfMoves);
        newListOfMoves.add(move);
        ArrayList<Move> listOfPossibleMoves = newTable.possibleMoves(this);
        depth++;
        boolean solutionFound = false;
        ArrayList<Integer> dists = new ArrayList<>();
        if(depth<MAX_MOVES){
            for(Move actualMove: listOfPossibleMoves) {
                actualMove.setDistance(Table.distance(makeMove(newTable, actualMove), representationOfTarget));
            }
            List<Move> li = listOfPossibleMoves
                    .stream()
                    .sorted(Comparator.comparingInt(Move::getDistance))
                    .collect(Collectors.toList());
            listOfPossibleMoves = new ArrayList<>(li);
            for(Move actualMove: listOfPossibleMoves) {
                solutionFound = executeNextStep(newTable, actualMove, depth, newListOfMoves);
                if(solutionFound) {
                    break;
                }
            }
        }
        return solutionFound;
    }

    private boolean weAlreadyVisited(Table actualTable) {
        return visitedSets.contains(actualTable.stringCodeOfTable());
    }

    private void store(ArrayList<Move> listOfMoves) {
        solution = listOfMoves;
    }

    private boolean weAreThere(Table table) {
        return table.stringCodeOfTable().equals(representationOfTarget);
    }

    private ArrayList<Shape> loadFromFile(String filename) throws IOException {
        ArrayList<Shape> result = new ArrayList<>();
        try(BufferedReader in = new BufferedReader(new FileReader(new File(filename)))) {
            while(in.ready()) {
                String line = in.readLine();
                StringTokenizer str = new StringTokenizer(line, ",");
                if(str.countTokens() > 5) {
                    result.add(new Shape(Integer.parseInt(str.nextToken()),
                            Integer.parseInt(str.nextToken()),
                            Integer.parseInt(str.nextToken()),
                            Integer.parseInt(str.nextToken()),
                            Integer.parseInt(str.nextToken()),
                            Integer.parseInt(str.nextToken())));
                }
            }
        }
        return result;
    }

}
