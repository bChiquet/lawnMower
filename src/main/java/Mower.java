/**
 * Mower object
 */
public class Mower {
    private int posX;
    private int posY;
    private String direction;
    private String orders;

    /**
     * Setting the mower's position
     * @param posX the x-position of the mower
     * @param posY the y-position of the mower
     * @param direction the direction the mower is facing
     * @return self - harmcrest style.
     */
    public Mower setPosition(int posX, int posY, String direction){
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
    public Mower setOrders(String orders){
        this.orders = orders;
        return this;
    }
}