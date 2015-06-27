import com.google.common.base.Throwables;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class LawnMowing {
    //Lawn dimensions
    Integer lawnSizeX;
    Integer lawnSizeY;

    //List of mowers on the lawn
    List<Mower> mowers = new ArrayList<>();

    private Mower halfBuiltMower;

    /**
     * reads mowing file and initializes LawnMowing
     * @param mowingFile the instruction file
     * @return self - harmcrest style.
     */
    public LawnMowing on(String mowingFile) {
        try{
            Stream<String> mowingInstructions = Files.lines(Paths.get(mowingFile));
            mowingInstructions.map(String::trim)
                    .filter(s -> !s.equals(""))
                    .forEachOrdered(this::consumeLine);

        } catch (IOException e) {
            Throwables.propagate(e);
        }
        return this;
    }

    /**
     * Starts all the mowers on the lawn mower list.
     */
    public void processMowersOrders() {
        mowers.stream().forEachOrdered(m -> m.processAllOrders(this::checkIfPositionIsLegal));
    }

    /**
     * function to check if a mower can be at coordinates
     * @param finalPosX the potential x-coordinate of the mower
     * @param finalPosY the potential y-coordinate of the mower
     * @return 0 if the position is legal, a positive number otherwise
     */
    public Integer checkIfPositionIsLegal(Integer finalPosX, Integer finalPosY) {
        //Check if the final pos is in the field
        if ( finalPosX < 0 || finalPosX > lawnSizeX ||
                finalPosY < 0 || finalPosY > lawnSizeY) {
            return 1;
        }
        //Check if the mower collides with another mower
        return mowers.stream()
                .map(mower -> {
                           if (mower.posX.equals(finalPosX) && mower.posY.equals(finalPosY)) {
                               return 1;
                           }
                            return 0;
                        })
                .reduce((m1, m2) -> m1 + m2)
                .get();
    }

    //Processing of file lines
    private void consumeLine(String s) {
        //If no lawn size exists, we look for the lawn size
        if (lawnSizeX == null) {
            setLawnSize(s);
        }
        //If a mower is being built, assign orders to it
        else if (halfBuiltMower == null) {
            beginMowerAssembly(s);
        }
        //Otherwise build new mower.
        else {
            completeMowerAssembly(s);
        }
    }

    //Processes lawn size file line
    private void setLawnSize(String lawnSizeLine) {
        String[] lawnDimensions;
        if (lawnSizeLine.matches("^\\d \\d$")){
            lawnDimensions = lawnSizeLine.split("\\s+");
            lawnSizeX = Integer.valueOf(lawnDimensions[Directions.MAP_X]);
            lawnSizeY = Integer.valueOf(lawnDimensions[Directions.MAP_Y]);
        }
        else {
            System.out.println("Lawn size failure");
            //throw lawnNotOkException
        }
    }

    //Processes mower initial location file line
    private void beginMowerAssembly(String mowerLocation) {
        if (mowerLocation.matches("^\\d \\d [NSEW]$")) {
            String[] coords = mowerLocation.split("\\s+");
            halfBuiltMower = new Mower().setPosition(
                    Integer.valueOf(coords[Directions.MAP_X]),
                    Integer.valueOf(coords[Directions.MAP_Y]),
                    coords[Directions.MAP_DIRECTION]);
        }
        else {
            System.out.println("Mower location failure");
            //Throw cantFindMowerException
        }
    }

    //Processes mower orders file line
    private void completeMowerAssembly(String mowerOrders) {
        if (mowerOrders.matches("^[ADG]+$")){
            mowers.add(halfBuiltMower.setOrders(mowerOrders));
            halfBuiltMower = null;
        }
        else {
            System.out.println("Mower orders failure");
            //Throw ICantOrdersException
        }
    }
}
