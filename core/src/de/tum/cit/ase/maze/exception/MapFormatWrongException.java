package de.tum.cit.ase.maze.exception;

/**
 * Custom Exception, which is thrown if a map file is not correct
 */
public class MapFormatWrongException extends Exception {
    public MapFormatWrongException() {
        System.err.println("The map format is wrong. Please check that all relevant entry/exit points and keys exist");
    }
}
