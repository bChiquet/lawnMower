import com.google.common.base.Throwables;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class LawnMowing {
    //File mapping
    final int MAP_X = 0;
    final int MAP_Y = 1;
    final int MAP_DIRECTION = 2;

    //Lawn dimensions
    public Integer lawnSizeX;
    public Integer lawnSizeY;

    //List of mowers on the lawn
    public List<Mower> mowers = new ArrayList<>();

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
            lawnSizeX = Integer.valueOf(lawnDimensions[MAP_X]);
            lawnSizeY = Integer.valueOf(lawnDimensions[MAP_Y]);
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
                    Integer.valueOf(coords[MAP_X]),
                    Integer.valueOf(coords[MAP_Y]),
                    coords[MAP_DIRECTION]);
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
