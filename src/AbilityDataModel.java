import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class AbilityDataModel {
    String Name;
    String AgainstWhatType;
    int Damage;
    AbilityDataModel (String name, String againstWhatType, int damage){
        this.Name = name;
        this.AgainstWhatType = againstWhatType;
        this.Damage = damage;
    }
}
