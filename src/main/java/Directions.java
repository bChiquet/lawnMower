import java.util.Arrays;
import java.util.List;

/**
 * Class specifying movements, directions, etc.
 */
public final class Directions {
    static int MAP_X = 0;
    static int MAP_Y = 1;
    static int MAP_DIRECTION = 2;

    public static List<Integer> moveNorth = Arrays.asList(0, 1);
    public static List<Integer> moveSouth = Arrays.asList(0, -1);
    public static List<Integer> moveEast = Arrays.asList(1, 0);
    public static List<Integer> moveWest = Arrays.asList(-1, 0);
    public static List<Integer> noMove = Arrays.asList(0, 0);

}
