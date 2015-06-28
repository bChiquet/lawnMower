import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;

/**
 * Mower object
 */
public class Mower {
    //Mower coordinates & direction faced
    Integer posX;
    Integer posY;
    Direction direction;
    //Orders to be executed by the mower
    private List<Order> orders = new ArrayList<>();

    /**
     * Setting the mower's position
     * @param posX the x-position of the mower
     * @param posY the y-position of the mower
     * @param direction the direction the mower is facing
     * @return self - harmcrest style.
     */
    public Mower setPosition(Integer posX, Integer posY, Direction direction){
        this.posX = posX;
        this.posY = posY;
        this.direction = direction;
        return this;
    }

    /**
     * Setting the mower's pile.
     * @param orders the list of orders
     * @return self - harmcrest style.
     */
    public Mower setOrders(String orders) {

        orders.chars()
                .mapToObj(c -> (char)c)
                .map(Object::toString)
                .map(Order::fromString)
                .forEachOrdered(this.orders::add);
        return this;
    }

    /**
     * Processes the mower's order list
     * @param isMovementLegal A method to check if a movement is legal.
     */
    public void processAllOrders(BiFunction<Integer, Integer, Boolean> isMovementLegal) {
        orders.stream()
                .map(this::ordersToMovements)
                .filter(this::doesMove) //Filtering out the stationary movements.
                .forEachOrdered(movement -> {
                    if (isMovementLegal.apply(
                            posX + movement.get(Directions.MAP_X),
                            posY + movement.get(Directions.MAP_Y))) {
                        setPosition(posX + movement.get(Directions.MAP_X),
                                    posY + movement.get(Directions.MAP_Y),
                                    direction);
                    }
                });
    }

    //Returns true if a movement vector is not NoMove.
    private boolean doesMove(List<Integer> movement) {
        return ! movement.stream()
                .reduce((a, b) -> a+b)
                .get().equals(0);
    }

    //Translates the movements from the order list into a list of coordinate changes.
    private List<Integer> ordersToMovements(Order order) {
        //If the mower moves forward, return move vector.
        switch (order) {
            case MOVE:
                switch (direction) {
                    case NORTH:
                        return Directions.moveNorth;
                    case SOUTH:
                        return Directions.moveSouth;
                    case EAST:
                        return Directions.moveEast;
                    case WEST:
                        return Directions.moveWest;
                }
                break;
            //If pivot, we pivot direction and return no move.
            case TURN_RIGHT:
                pivotRight();
                break;
            case TURN_LEFT:
                pivotLeft();
                break;
        }
        return Directions.noMove;
    }

    //steering methods
    private Direction pivotLeft(){
        switch(direction){
            case NORTH:
                direction = Direction.WEST;
                break;
            case SOUTH:
                direction = Direction.EAST;
                break;
            case EAST:
                direction = Direction.NORTH;
                break;
            case WEST:
                direction = Direction.SOUTH;
        }
        return null;
    }

    private Direction pivotRight() {
        switch(direction){
            case NORTH:
                direction = Direction.WEST;
                break;
            case SOUTH:
                direction = Direction.EAST;
                break;
            case EAST:
                direction = Direction.SOUTH;
                break;
            case WEST:
                direction = Direction.NORTH;
        }
        return null;
    }
}