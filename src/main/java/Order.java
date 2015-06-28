/**
 * Order type enum
 */
public enum Order {
    MOVE("A"),
    TURN_LEFT("G"),
    TURN_RIGHT("D");

    private final String order;

    Order(String orderType) {
        this.order = orderType;
    }

    public static Order fromString (String string) {
        for (Order o : Order.values()){
            if(o.order.equals(string)) return o;
        }
        return null;
    }
}
