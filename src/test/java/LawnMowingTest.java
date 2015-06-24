import com.google.common.io.Resources;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
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
}