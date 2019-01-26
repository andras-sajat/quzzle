package com.andris;

import lombok.Data;

import java.util.ArrayList;

import static com.andris.Move.DIRECTIONS.*;

@Data
public class Table {
    ArrayList<Integer> layout;
    private ArrayList<Shape> shapesOnTheTable = new ArrayList<>();

    Table() {
        layout = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            layout.add(0);
        }
    }

    static int distance(Table table1, String target) {
        String rep1 = table1.stringCodeOfTable();
        int dist = 0;
        for (int i=0; i<rep1.length(); i++) {
            if(!rep1.substring(i,i+1).equals(target.substring(i,i+1))) {
                dist++;
            }
        }
        return dist;
    }

    Table copyOf() {
        Table newTable = new Table();
        ArrayList<Shape> shapesOnNewTable = new ArrayList<>();
        for(int i=0; i<9; i++) {
            shapesOnNewTable.add(this.getShapesOnTheTable().get(i).copyOf());
        }
        newTable.setShapesOnTheTable(shapesOnNewTable);
        newTable.putShapesOnTable();

        return newTable;
    }

    static String stringOfTargetTable(ArrayList<Shape> shapes) {
        Table table = new Table();
        ArrayList<Shape> shapesOnTheTargetTable = new ArrayList<>();
        shapesOnTheTargetTable.addAll(shapes);
        table.setShapesOnTheTable(shapesOnTheTargetTable);
        table.putShapesOnTable();
        System.out.println("Target table: \n"+table.toString());
        return table.stringCodeOfTable();
    }

    private void put(Shape shape) {
        for(int i=0; i< shape.getHSize(); i++) {
            for(int j=0; j< shape.getVSize(); j++) {
                layout.set((shape.getVPos()+j)*4+shape.getHPos()+i, shape.getId());
            }
        }
    }

    private void putShapesOnTable() {
        for(int i=0; i<9; i++) {
            put(shapesOnTheTable.get(i));
        }
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        for(int i=0; i<5; i++) {
            for(int j=0; j<4; j++) {
                str.append(this.layout.get(i*4+j));
            }
            str.append("\n");
        }
        return str.toString();
    }

    void setupTable(ArrayList<Shape> shapes) {
        shapesOnTheTable.addAll(shapes);
        setShapesOnTheTable(shapesOnTheTable);
        putShapesOnTable();
    }

    String stringCodeOfTable() {
        StringBuilder repr = new StringBuilder("");
        for(int i=0; i < 20; i++) {
            int indexOfElement = getLayout().get(i)-1;
            repr.append(indexOfElement<0 ? "0" : getShapesOnTheTable().get(indexOfElement).getSymbol());
        }
        return repr.toString();
    }

    ArrayList<Move> possibleMoves(Quzzle quzzle) {
        ArrayList<Move> moves = new ArrayList<>();
        for(int id=0; id < 9; id++) {
            if(canItMove(getShapesOnTheTable().get(id), DIRECTION_LEFT)){
                moves.add(new Move(id, DIRECTION_LEFT));
            }
            if(canItMove(getShapesOnTheTable().get(id), DIRECTION_RIGHT)){
                moves.add(new Move(id, DIRECTION_RIGHT));
            }
            if(canItMove(getShapesOnTheTable().get(id), DIRECTION_UP)){
                moves.add(new Move(id, DIRECTION_UP));
            }
            if(canItMove(getShapesOnTheTable().get(id), DIRECTION_DOWN)){
                moves.add(new Move(id, DIRECTION_DOWN));
            }
        }
        return moves;
    }

    private boolean canItMove(Shape s, Move.DIRECTIONS dir) {
        switch(dir) {
            case DIRECTION_LEFT:
                if (s.getHPos() > 0 && layout.get(s.getVPos() * 4 + s.getHPos() - 1) == 0
                        && (s.getVSize() == 1 || layout.get(s.getVPos() * 4 + s.getHPos() + 3) == 0)) {
                    return true;
                }
                break;
            case DIRECTION_RIGHT:
                if(s.getHPos()<(4-s.getHSize())
                        && s.getVPos()*4+s.getHPos()+s.getHSize() < 20
                        && layout.get(s.getVPos()*4+s.getHPos()+s.getHSize()) == 0
                        && (s.getVSize()==1
                            || layout.get(s.getVPos()*4+s.getHPos()+s.getHSize()+4) == 0 )) {
                    return true;
                }
                break;
            case DIRECTION_UP:
                if(s.getVPos()>0 && layout.get((s.getVPos()-1)*4+s.getHPos()) == 0
                        && (s.getHSize()==1 || layout.get((s.getVPos()-1)*4+s.getHPos()+1) == 0 )) {
                    return true;
                }
                break;
            case DIRECTION_DOWN:
                if((s.getVPos()< 5 - s.getVSize()) && layout.get((s.getVPos()+s.getVSize())*4+s.getHPos()) == 0
                        && (s.getHSize()==1 || layout.get((s.getVPos()+s.getVSize())*4+s.getHPos()) == 0 )) {
                    return true;
                }
                break;
            default: return false;
        }
        return false;
    }
}
