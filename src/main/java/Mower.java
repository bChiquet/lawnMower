import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Mower object
 */
public class Mower {
    //Mower coordinates & direction faced
    Position pos;
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
        this.pos = new Position(posX, posY);
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
                .mapToObj(c -> (char) c)
                .map(Object::toString)
                .map(Order::fromString)
                .forEachOrdered(this.orders::add);
        return this;
    }

    /**
     * Processes the mower's order list
     * @param isMovementLegal A method to check if a movement is legal.
     * @return self - harmcrest style.
     */
    public Mower processAllOrders(Predicate<Position> isMovementLegal) {
        orders.stream()
                .forEachOrdered(order -> executeOrder(order, isMovementLegal));
        return this;
    }

    //Executes an order given if it is legal.
    private void executeOrder(Order order, Predicate <Position> isMovementLegal) {
        //If the mower moves forward, return move vector.
        switch (order) {
            case MOVE:
                moveIfApplicable(order, isMovementLegal);
                break;
            //If pivot, we pivot direction and return no move.
            case TURN_RIGHT:
                pivotRight();
                break;
            case TURN_LEFT:
                pivotLeft();
        }
    }

    //applies an order move if it is not illegal.
    private void moveIfApplicable(Order order, Predicate<Position> isMovementLegal) {
        Position move = pos;
        switch (direction) {
            case NORTH:
                move = new Position(pos).moveNorth();
                break;
            case SOUTH:
                move = new Position(pos).moveSouth();
                break;
            case EAST:
                move = new Position(pos).moveEast();
                break;
            case WEST:
                move = new Position(pos).moveWest();
        }
        if (isMovementLegal.test(move)) pos = move;
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