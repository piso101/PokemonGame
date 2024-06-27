import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.List;
import java.util.Random;
import java.awt.geom.AffineTransform;

public class ViewController extends JFrame {
    static public PokemonBaseDataModel playerPokemon;
    static public PokemonBaseDataModel enemyPokemon;
    private Image background;
    private BackgroundPanel backgroundPanel;
    private boolean isPlayerTurn = true;
    private boolean isRoundOver = false;
    JPanel buttonPanel = new JPanel();
    JPanel eventInfoPanel = new JPanel();
    JLabel EventDesc = new JLabel();
    JPanel ResetButtonPanel = new JPanel();



    public ViewController() {
        // Ustawienie tytułu okna
        setTitle("Gra Pokemon");

        // Ustawienie domyślnego rozmiaru okna
        setSize(800, 450); // 16:9 ratio

        // Ustawienie operacji zamknięcia
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Wybór losowych Pokémonów
        selectRandomPokemons();

        // Pobranie obrazu tła z klasy BackGroundGenerator
        this.background = BackGroundGeneraotr.Background;

        // Utworzenie i dodanie panelu z tłem
        this.backgroundPanel = new BackgroundPanel();
        add(backgroundPanel, BorderLayout.CENTER);

        // Utworzenie i dodanie panelu z przyciskami i eventami
        initializeButtons();
        initializeEventsDesc();
    }
    public void displayTextInDialog(String text) {//Wyświetlanie tekstu w oknie dialogowym
        JOptionPane.showMessageDialog(this, text);
    }
    private void selectRandomPokemons() {
        // Pobranie wszystkich Pokémonów
        PokemonRepository repository = new PokemonRepository();
        List<PokemonBaseDataModel> allPokemons = repository.getPokemons();

        // Utworzenie obiektu do losowania
        Random rand = new Random();

        // Losowe wybranie Pokémonów
        playerPokemon = allPokemons.get(rand.nextInt(allPokemons.size()));
        enemyPokemon = allPokemons.get(rand.nextInt(allPokemons.size()));

        // Upewnienie się, że nie wybrano tego samego Pokémona
        while (playerPokemon == enemyPokemon) {
            enemyPokemon = allPokemons.get(rand.nextInt(allPokemons.size()));
        }
    }

    class BackgroundPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (background != null) {
                g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), this);
            }

            int size = 20;
            Graphics2D g2d = (Graphics2D) g.create();

            if (playerPokemon != null && playerPokemon.PokemonImage != null) {
                int newWidth = playerPokemon.PokemonImage.getWidth(this) / size;
                int newHeight = playerPokemon.PokemonImage.getHeight(this) / size;
                AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
                tx.translate(-newWidth, 0);
                g2d.setTransform(tx);
                g2d.drawImage(playerPokemon.PokemonImage, 200 - newWidth, 100, newWidth, newHeight, this);
            }

            if (enemyPokemon != null && enemyPokemon.PokemonImage != null) {
                int newWidth = enemyPokemon.PokemonImage.getWidth(this) / size;
                int newHeight = enemyPokemon.PokemonImage.getHeight(this) / size;
                g.drawImage(enemyPokemon.PokemonImage, 550, 100, newWidth, newHeight, this);
            }

            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 18));

            drawPokemonInfo(g, playerPokemon, 10, true);
            drawPokemonInfo(g, enemyPokemon, getWidth() - 200, false);
        }

        private void drawPokemonInfo(Graphics g, PokemonBaseDataModel pokemon, int x, boolean isPlayer) {
            if (pokemon != null) {
                String info = "Name: " + pokemon.Name;
                g.drawString(info, x, 20);

                float healthPercentage = pokemon.HealthPoints / pokemon.MaxHealthPoints;
                int barWidth = 170;
                int barHeight = 10;

                g.setColor(isPlayer ? Color.BLUE : Color.RED);
                g.fillRect(x, 30, (int) (barWidth * healthPercentage), barHeight);

                g.setColor(Color.BLACK);
                g.drawRect(x, 30, barWidth, barHeight);

                String hpInfo = "HP: " + pokemon.HealthPoints + "/" + pokemon.MaxHealthPoints;
                g.drawString(hpInfo, x, 60);
            }
        }
    }
    private void initializeButtons() {

        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 6, 10));

        // Tworzenie przycisków i dodawanie ActionListenerów
        JButton firstAbilityButton = new JButton(playerPokemon.FirstAbility.Name);
        firstAbilityButton.addActionListener(e -> useAbility(playerPokemon.FirstAbility));
        buttonPanel.add(firstAbilityButton);

        JButton secondAbilityButton = new JButton(playerPokemon.SecondAbility.Name);
        secondAbilityButton.addActionListener(e -> useAbility(playerPokemon.SecondAbility));
        buttonPanel.add(secondAbilityButton);

        JButton thirdAbilityButton = new JButton(playerPokemon.ThirdAbility.Name);
        thirdAbilityButton.addActionListener(e -> useAbility(playerPokemon.ThirdAbility));
        buttonPanel.add(thirdAbilityButton);

        // Dodawanie panelu z przyciskami do okna
        buttonPanel.setBackground(Color.LIGHT_GRAY);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    private void useAbility(AbilityDataModel ability) {
        if (isRoundOver) return;// Sprawdzenie, czy runda się nie skończyła
        // Obliczanie obrażeń
        float damage = playerPokemon.Damage;
        if (enemyPokemon.Type.equals(ability.AgainstWhatType)) {
            damage += ability.Damage;
        }

        // Zadawanie obrażeń przeciwnikowi
        enemyPokemon.HealthPoints -= damage;
        changeEventDesc("Uzywasz: " + ability.Name + " zadając: "+damage+" Obrażeń");
        isPlayerTurn = false;

        // Sprawdzenie, czy przeciwnik został pokonany
        if (enemyPokemon.HealthPoints < 0) {
            enemyPokemon.HealthPoints = 0;
            changeEventDesc("Przeciwnik " + enemyPokemon.Name + " został pokonany!");
            displayTextInDialog("Przeciwnik " + enemyPokemon.Name + " został pokonany!");
            isRoundOver = true;
        }

        // Aktualizacja UI lub stanu gry
        updateGameState();

        if(!isPlayerTurn)enemyAttack(); // Tura przeciwnika

    }
    private void initializeEventsDesc() {
        eventInfoPanel.setLayout(new BorderLayout());

        EventDesc.setFont(new Font("Arial", Font.BOLD, 14));
        eventInfoPanel.add(EventDesc, BorderLayout.CENTER);
        EventDesc.setText("---Użyj pierwszego ataku by zacząć walkę!---");

        JButton Reset = new JButton("Nowa Walka!");
        Reset.addActionListener(e -> resetGame());
        Reset.setOpaque(false);
        Reset.setBackground(new Color(0, 0, 0, 0));
        eventInfoPanel.add(Reset, BorderLayout.NORTH);  // Ustawienie przycisku w prawym górnym rogu

        eventInfoPanel.setBackground(Color.WHITE);

        add(eventInfoPanel, BorderLayout.PAGE_START);
    }
    private void resetGame()
    {
        this.dispose();
        SwingUtilities.invokeLater(()->{ViewController frame = new ViewController(); frame.setVisible(true);});
    }
    private void changeEventDesc(String desc)
    {
        EventDesc.setText(desc);

    }
    private void enemyAttack() {
        // Blokowanie przycisków
        setButtonsEnabled(false);

        // Opóźnienie (5 sekund)
        new Timer(2000, e -> {
            // Losowanie i wykonanie ataku przeciwnika
            performEnemyAttack();

            // Odblokowanie przycisków
            setButtonsEnabled(true);
        }).start();
    }
    private void setButtonsEnabled(boolean enabled) {
        for (Component component : buttonPanel.getComponents()) {
            component.setEnabled(enabled);
        }

    }
    private void performEnemyAttack() {
        if (isRoundOver) return;// Sprawdzenie, czy runda się nie skończyła

        Random rand = new Random();// Utworzenie obiektu do losowania
        List<AbilityDataModel> enemyAbilities = List.of(enemyPokemon.FirstAbility, enemyPokemon.SecondAbility, enemyPokemon.ThirdAbility);// Pobranie wszystkich umiejętności przeciwnika
        AbilityDataModel chosenAbility = enemyAbilities.get(rand.nextInt(enemyAbilities.size()));// Losowe wybranie umiejętności

        // Obliczanie obrażeń
        float damage = enemyPokemon.Damage;
        if (playerPokemon.Type.equals(chosenAbility.AgainstWhatType)) {
            damage += chosenAbility.Damage;
        }

        // Zadawanie obrażeń pokemonowi gracza
        if (!isPlayerTurn) {
            playerPokemon.HealthPoints -= damage;
            try {
                Thread.sleep(1250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            changeEventDesc("Przeciwnik używa: " + chosenAbility.Name + " zadając ci: " + damage + " Obrażeń");
        }

        // Sprawdzenie, czy pokemon gracza został pokonany
        if (playerPokemon.HealthPoints < 0) {
            playerPokemon.HealthPoints = 0;
            isRoundOver = true;
            changeEventDesc("Twój pokemon " + playerPokemon.Name + " został pokonany!");
            displayTextInDialog("Twój pokemon " + playerPokemon.Name + " został pokonany!");
        }

        // Logowanie i aktualizacja UI lub stanu gry
        isPlayerTurn = true;
        updateGameState();
    }
    public void updateGameState() {
        // Wywołanie metody repaint na panelu, który rysuje paski zdrowia
        backgroundPanel.repaint();
    }

    public static void main(String[] args) {
        BackGroundGeneraotr fightProcessor = new BackGroundGeneraotr();
        SwingUtilities.invokeLater(() -> {
            ViewController frame = new ViewController();
            frame.setVisible(true);
        });


    }
}


