import java.util.List;
import java.util.function.BinaryOperator;

/**
 * Mower object
 */
public class Mower {
    //Mower coordinates & direction faced
    Integer posX;
    Integer posY;
    String direction;
    //Orders to be executed by the mower
    private String orders;

    /**
     * Setting the mower's position
     * @param posX the x-position of the mower
     * @param posY the y-position of the mower
     * @param direction the direction the mower is facing
     * @return self - harmcrest style.
     */
    public Mower setPosition(Integer posX, Integer posY, String direction){
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
        this.orders = orders;
        return this;
    }

    /**
     * Processes the mower's order list
     * @param checkIfMovementIsLegalMethod A method to check if a movement is legal.
     */
    public void processAllOrders(BinaryOperator<Integer> checkIfMovementIsLegalMethod) {
        orders.chars()
                .mapToObj(c -> (char) c)
                .map(Object::toString)
                .map(this::ordersToMovements)
                .filter(this::doesMove) //Filtering out the stationary movements.
                .forEachOrdered(movement -> {
                    if (checkIfMovementIsLegalMethod.apply(
                            posX + movement.get(Directions.MAP_X),
                            posY + movement.get(Directions.MAP_Y)) != 0) {
                        //Do nothing because the mower would get illegal position
                    }
                    else {
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
    private List<Integer> ordersToMovements(String order) {
        //If the mower moves forward, return move vector.
        if (order.equals("A")) {
            switch (direction) {
                case "N":
                    return Directions.moveNorth;
                case "S":
                    return Directions.moveSouth;
                case "E":
                    return Directions.moveEast;
                case "W":
                    return Directions.moveWest;
            }
        }
        //If pivot, we pivot direction and return no move.
        else if(order.equals("D")){
            direction = Directions.pivotRight(direction);
        }
        else if(order.equals("G")){
            direction = Directions.pivotLeft(direction);
        }
        return Directions.noMove;
    }
}