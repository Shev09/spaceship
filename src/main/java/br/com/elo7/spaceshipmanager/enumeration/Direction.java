package br.com.elo7.spaceshipmanager.enumeration;

import lombok.Getter;


@Getter
public enum Direction {

    N(1), E(2), S(3), W(4);

    int value;

    Direction(int value) {
        this.value = value;
    }
    
    public Direction turnLeft() {
        return turn(-1);
    }

    public Direction turnRight() {
        return turn(1);
    }

    private Direction turn(int steps) {
        int newIndex = (this.ordinal() + steps + Direction.values().length) % Direction.values().length;
        return Direction.values()[newIndex];
    }

}
