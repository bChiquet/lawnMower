/**
 * Direction enum
 */
public enum Direction {
    NORTH("N"),
    SOUTH("S"),
    EAST("E"),
    WEST("W");


    private final String cardinal;

    Direction(String cardinal) {
        this.cardinal = cardinal;
    }

    public static Direction fromString (String string) {
        for (Direction d : Direction.values()){
            if (d.cardinal.equals(string)) return d;
        }
        return null;
    }
}