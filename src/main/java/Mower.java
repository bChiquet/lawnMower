/**
 * Mower object
 */
public class Mower {
    private int posX;
    private int posY;
    private String direction;
    private String orders;
    private int orderPos = 0;

    public Mower setPosition(int posX, int posY, String direction){
        this.posX = posX;
        this.posY = posY;
        this.direction = direction;
        return this;
    }
    public Mower setOrders(String orders){
        this.orders = orders;
        return this;
    }
}
