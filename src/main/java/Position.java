/**
 * Position class
 */
public class Position {
    Integer x;
    Integer y;

    public Position(Integer x, Integer y){
        this.x = x;
        this.y = y;
    }

    public Position(Position pos){
        this.x = pos.x;
        this.y = pos.y;
    }

    //Movement functions
    public Position moveNorth(){
        y++;
        return this;
    }
    public Position moveSouth(){
        y--;
        return this;
    }
    public Position moveEast(){
        x++;
        return this;
    }
    public Position moveWest(){
        x--;
        return this;
    }

    @Override
    public boolean equals(Object pos){
        if (pos == null || getClass() != pos.getClass()) return false;
        Position position = (Position) pos;
        return this.x.equals(position.x) && this.y.equals(position.y);
    }
}
