import com.google.common.base.Throwables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class LawnMowing {
    //File mapping
    static int MAP_X = 1;
    static int MAP_Y = 2;
    static int MAP_DIRECTION = 3;

    //Lawn dimensions
    Integer lawnSizeX;
    Integer lawnSizeY;

    //List of mowers on the lawn
    List<Mower> mowers = new ArrayList<>();

    //As mower instructions are on multiple lines, we build the mower incrementally.
    //The mower being processed is stored here.
    private Mower halfBuiltMower;

    //A logger
    Logger logger = LoggerFactory.getLogger(LawnMowing.class);

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
        mowers.stream().forEachOrdered(m -> m.processAllOrders(this::isPositionLegal));
    }

    /**
     * function to check if a mower can be at coordinates
     * @param testedPos the position considered
     * @return 0 if the position is legal, a positive number otherwise
     */
    public boolean isPositionLegal(Position testedPos) {
        //Check if the final pos is in the field
        if ( testedPos.x < 0 || testedPos.x > lawnSizeX ||
                testedPos.y < 0 || testedPos.y > lawnSizeY) {
            return false;
        }
        //Check if the mower collides with another mower
        return mowers.stream()
                .map(mower -> !(mower.pos.equals(testedPos)))
                .reduce(true, (m1, m2) -> m1 & m2);
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
        Matcher lawnSize = Pattern.compile("^(\\d) (\\d)$")
                .matcher(lawnSizeLine);
        if (lawnSize.matches()){
            lawnSizeX = Integer.parseInt(lawnSize.group(MAP_X));
            lawnSizeY = Integer.parseInt(lawnSize.group(MAP_Y));
        }
        else {
            logger.error("Lawn size failure");
            //throw lawnNotOkException ?
        }
    }

    //Processes mower initial location file line
    private void beginMowerAssembly(String mowerLocation) {
        Matcher location = Pattern.compile("^(\\d) (\\d) ([NSEW])$")
                .matcher(mowerLocation);
        if (location.matches()) {
            halfBuiltMower = new Mower().setPosition(
                    Integer.parseInt(location.group(MAP_X)),
                    Integer.parseInt(location.group(MAP_Y)),
                    Direction.fromString(location.group(MAP_DIRECTION))
            );
        }
        else {
            logger.error("Mower location failure");
            //Throw cantFindMowerException ?
        }
    }

    //Processes mower orders file line
    private void completeMowerAssembly(String mowerOrders) {
        if (mowerOrders.matches("^[ADG]+$")){
            if (isPositionLegal(halfBuiltMower.pos)) {
                mowers.add(halfBuiltMower.setOrdersFromString(mowerOrders));
            }
            else {
                //TODO Figure what to do with the failing mower.
            }
            halfBuiltMower = null;
        }
        else {
            logger.error("Mower orders failure");
            //Throw ICantOrdersException ?
        }
    }
}
