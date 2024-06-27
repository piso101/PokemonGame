import java.awt.*;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Random;

public class BackGroundGeneraotr  {
    public static final Image Background; // Static background image

    static {
        String[] backgroundPaths = {
                "/Backgroundimg/file-AinVuI7jyL1TBb56p9qFjVzx.png",
                "/Backgroundimg/file-pzcnHiBBlo0OJJOUeP7fGZdt.png"
        };

        Random rand = new Random();
        int randomIndex = rand.nextInt(backgroundPaths.length); // Random index for the array of paths

        Image tempImage = null;
        try {
            tempImage = ImageIO.read(BackGroundGeneraotr.class.getResource(backgroundPaths[randomIndex])); // Load image from the random index
        } catch (IOException e) {
            e.printStackTrace();
        }
        Background = tempImage;
    }
}