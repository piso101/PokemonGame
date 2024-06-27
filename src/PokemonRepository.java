import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class PokemonRepository {
    private List<PokemonBaseDataModel> pokemons;

    public PokemonRepository() {
        pokemons = new ArrayList<>();

        // Adding Pokémon with example data
        pokemons.add(new PokemonBaseDataModel("Dandelon", 100, 100, 5, "Water", 10f, 5f, "/Pokemons/Dandelon.png", new AbilityDataModel("Kropelki", "Fire", 5), new AbilityDataModel("Kałuza", "Fire", 10), new AbilityDataModel("Powódź", "Fire", 15)));
        pokemons.add(new PokemonBaseDataModel("Dandelion", 120, 120, 5, "Water", 12f, 6f, "/Pokemons/Dandelion.png", new AbilityDataModel("Kropelki", "Fire", 5), new AbilityDataModel("Kałuza", "Fire", 10), new AbilityDataModel("Powódź", "Fire", 15)));
        pokemons.add(new PokemonBaseDataModel("MoSkal", 110, 110, 5, "Rock", 11f, 5.5f, "/Pokemons/MoSkal.png", new AbilityDataModel("Skamielina", "Psychic", 5), new AbilityDataModel("Kamień", "Psychic", 10), new AbilityDataModel("Skała", "Psychic", 20)));
        pokemons.add(new PokemonBaseDataModel("Skal", 130, 130, 5, "Rock", 13f, 6.5f, "/Pokemons/Skal.png", new AbilityDataModel("Skamielina", "Psychic", 7), new AbilityDataModel("Kamień", "Psychic", 13), new AbilityDataModel("Skała", "Psychic", 21)));
        pokemons.add(new PokemonBaseDataModel("Mao", 90, 90, 5, "Fire", 9f, 4.5f, "/Pokemons/Mao.png", new AbilityDataModel("Iskra", "Water", 10), new AbilityDataModel("Płomyk", "Water", 13), new AbilityDataModel("Ogień", "Water", 15)));
        pokemons.add(new PokemonBaseDataModel("Maoti", 140, 140, 5, "Fire", 14f, 7f, "/Pokemons/Maoti.png", new AbilityDataModel("Iskra", "Water", 13), new AbilityDataModel("Płomyk", "Water", 14), new AbilityDataModel("Ogień", "Water", 15)));
        pokemons.add(new PokemonBaseDataModel("Marn", 95, 95, 5, "Psychic", 9.5f, 4.7f, "/Pokemons/Marn.png", new AbilityDataModel("Zauroczenie", "Rock", 12), new AbilityDataModel("Miganie", "Rock", 10), new AbilityDataModel("Taniec", "Rock", 18)));
        pokemons.add(new PokemonBaseDataModel("Marnito", 125, 125, 5, "Psychic", 12.5f, 6.2f, "/Pokemons/Marnito.png", new AbilityDataModel("Zauroczenie", "Rock", 7), new AbilityDataModel("Miganie", "Rock", 10), new AbilityDataModel("Taniec", "Rock", 20)));
    }

    public List<PokemonBaseDataModel> getPokemons() {
        return pokemons;
    }
}
