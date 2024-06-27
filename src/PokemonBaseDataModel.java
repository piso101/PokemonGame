import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class PokemonBaseDataModel {
    public String Name;
    public float HealthPoints;
    public float MaxHealthPoints;
    public int Level;
    public String Type;
    public Float Damage;
    public Float Defense;
    public Image PokemonImage;
    AbilityDataModel FirstAbility;
    AbilityDataModel SecondAbility;
    AbilityDataModel ThirdAbility;
    public PokemonBaseDataModel(String name, float healthPoints,float maxHealthPoints, int level, String type, Float damage, Float defense, String imagePath,AbilityDataModel firstAbility,AbilityDataModel secondAbility,AbilityDataModel thirdAbility) {
        this.Name = name;
        this.HealthPoints = healthPoints;
        this.MaxHealthPoints = maxHealthPoints;
        this.Level = level;
        this.Type = type;
        this.Damage = damage;
        this.Defense = defense;
        try {
            this.PokemonImage = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
            this.PokemonImage = null;
        }
        this.FirstAbility = firstAbility;
        this.SecondAbility = secondAbility;
        this.ThirdAbility = thirdAbility;
    }

}
