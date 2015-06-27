import com.google.common.io.Resources;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class LawnMowingTest {

    LawnMowing lawnMowing;

    @Before
    public void setUp(){
        lawnMowing = new LawnMowing();
    }

    @Test
    public void should_process_file(){
        lawnMowing.on(Resources.getResource("basicMowing").getPath());
        assertEquals(new Integer(5), lawnMowing.lawnSizeX);
        assertEquals(new Integer(5), lawnMowing.lawnSizeY);
        assertThat(lawnMowing.mowers, hasSize(2));
    }

    @Test
    public void should_find_out_what_to_do_with_incorrect_file(){
        //Todo implement policy to handle bad files
    }

    @Test
    public void should_not_create_stacked_mowers() {
        //TODO add control to avoid two stacked mowers at file creation
    }


    @Test
    public void should_get_proper_end_positions(){
        lawnMowing.on(Resources.getResource("basicMowing").getPath())
                .processMowersOrders();

        assertEquals(new Integer(1), lawnMowing.mowers.get(0).posX);
        assertEquals(new Integer(3), lawnMowing.mowers.get(0).posY);
        assertEquals("N", lawnMowing.mowers.get(0).direction);
        assertEquals(new Integer(5), lawnMowing.mowers.get(1).posX);
        assertEquals(new Integer(1), lawnMowing.mowers.get(1).posY);
        assertEquals("E", lawnMowing.mowers.get(1).direction);
    }

    @Test
    public void mower_should_not_get_out_of_lawn() {
        lawnMowing.on(Resources.getResource("borderCheck").getPath())
                .processMowersOrders();

        assertEquals(new Integer(0), lawnMowing.mowers.get(0).posX);
        assertEquals(new Integer(0), lawnMowing.mowers.get(0).posY);
    }

    @Test
    public void mowers_should_not_collide() {
        lawnMowing.on(Resources.getResource("collisionCheck").getPath())
                .processMowersOrders();

        assertEquals(new Integer(1), lawnMowing.mowers.get(1).posX);
        assertEquals(new Integer(2), lawnMowing.mowers.get(1).posY);
    }
}