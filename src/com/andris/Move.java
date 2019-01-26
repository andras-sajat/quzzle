package com.andris;

import lombok.Data;

@Data
public class Move {

    int piece;

    DIRECTIONS direction;

    int distance;

    Move(int piece, DIRECTIONS dir) {
        this.piece = piece;
        this.direction = dir;
    }

    public String toString() {
        return "\nShape "+(piece+1) + " "+direction;
    }

    public enum DIRECTIONS {
        LEFT,
        RIGHT,
        UP,
        DOWN,
    }
}
