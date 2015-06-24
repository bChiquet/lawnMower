import com.google.common.io.Resources;
import org.junit.Before;
import org.junit.Test;

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
    }
}