import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class EcosystemSimulation extends JPanel implements ActionListener {
    private final List<Plant> plants = new ArrayList<>();
    private final List<Herbivore> herbivores = new ArrayList<>();
    private final List<Carnivore> carnivores = new ArrayList<>();
    private final Timer timer;

    public EcosystemSimulation() {
        this.setPreferredSize(new Dimension(800, 600));
        this.setBackground(Color.WHITE);
        timer = new Timer(100, this);
        timer.start();
    }

    public void addPlant(Plant plant) {
        plants.add(plant);
    }

    public void addHerbivore(Herbivore herbivore) {
        herbivores.add(herbivore);
    }

    public void addCarnivore(Carnivore carnivore) {
        carnivores.add(carnivore);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Plant plant : plants) {
            g.setColor(Color.GREEN);
            g.fillRect(plant.getX(), plant.getY(), 10, 10);
        }
        for (Herbivore herbivore : herbivores) {
            g.setColor(Color.BLUE);
            g.fillRect(herbivore.getX(), herbivore.getY(), 10, 10);
        }
        for (Carnivore carnivore : carnivores) {
            g.setColor(Color.RED);
            g.fillRect(carnivore.getX(), carnivore.getY(), 10, 10);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (Herbivore herbivore : herbivores) {
            herbivore.act(plants);
        }
        for (Carnivore carnivore : carnivores) {
            carnivore.act(herbivores);
        }
        for (Plant plant : plants) {
            plant.grow();
        }
        repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Ecosystem Simulation");
        EcosystemSimulation simulation = new EcosystemSimulation();
        
        simulation.addPlant(new Plant(100, 100));
        simulation.addPlant(new Plant(150, 150));
        simulation.addHerbivore(new Herbivore(200, 200));
        simulation.addCarnivore(new Carnivore(250, 250));

        frame.add(simulation);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

class Plant {
    private int x;
    private int y;

    public Plant(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void grow() {
        // Логика роста растения
    }
}

class Herbivore {
    private int x;
    private int y;
    private final Perceptron perceptron;
    private int energy;

    public Herbivore(int x, int y) {
        this.x = x;
        this.y = y;
        this.perceptron = new Perceptron(3); // Входы: направление движения и энергия
        this.energy = 100; // Начальная энергия
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void act(List<Plant> plants) {
        double[] inputs = {x / 800.0, y / 600.0, energy / 100.0};
        double action = perceptron.predict(inputs);

        // Простейшая логика движения
        if (action > 0.5) { // Двигаться
            move();
            energy -= 1; // Потеря энергии на движение
      import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class EcosystemSimulation extends JPanel implements ActionListener {
    private final List<Plant> plants = new ArrayList<>();
    private final List<Herbivore> herbivores = new ArrayList<>();
    private final List<Carnivore> carnivores = new ArrayList<>();
    private final Timer timer;

    public EcosystemSimulation() {
        this.setPreferredSize(new Dimension(800, 600));
        this.setBackground(Color.WHITE);
        timer = new Timer(100, this);
        timer.start();
    }

    public void addPlant(Plant plant) {
        plants.add(plant);
    }

    public void addHerbivore(Herbivore herbivore) {
        herbivores.add(herbivore);
    }

    public void addCarnivore(Carnivore carnivore) {
        carnivores.add(carnivore);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Plant plant : plants) {
            g.setColor(Color.GREEN);
            g.fillRect(plant.getX(), plant.getY(), 10, 10);
        }
        for (Herbivore herbivore : herbivores) {
            g.setColor(Color.BLUE);
            g.fillRect(herbivore.getX(), herbivore.getY(), 10, 10);
        }
        for (Carnivore carnivore : carnivores) {
            g.setColor(Color.RED);
            g.fillRect(carnivore.getX(), carnivore.getY(), 10, 10);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (Herbivore herbivore : herbivores) {
            herbivore.act(plants);
        }
        for (Carnivore carnivore : carnivores) {
            carnivore.act(herbivores);
        }
        for (Plant plant : plants) {
            plant.grow();
        }
        repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Ecosystem Simulation");
        EcosystemSimulation simulation = new EcosystemSimulation();
        
        simulation.addPlant(new Plant(100, 100));
        simulation.addPlant(new Plant(150, 150));
        simulation.addHerbivore(new Herbivore(200, 200));
        simulation.addCarnivore(new Carnivore(250, 250));

        frame.add(simulation);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

class Plant {
    private int x;
    private int y;

    public Plant(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void grow() {
        // Логика роста растения
    }
}

class Herbivore {
    private int x;
    private int y;
    private final Perceptron perceptron;
    private int energy;

    public Herbivore(int x, int y) {
        this.x = x;
        this.y = y;
        this.perceptron = new Perceptron(3); // Входы: направление движения и энергия
        this.energy = 100; // Начальная энергия
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void act(List<Plant> plants) {
        double[] inputs = {x / 800.0, y / 600.0, energy / 100.0};
        double action = perceptron.predict(inputs);

        // Простейшая логика движения
        if (action > 0.5) { // Двигаться
            move();
            energy -= 1; // Потеря энергии на движение
                  eat(plants); // Попытка поесть растения
        } else { // Оставаться на месте
            energy -= 0.5; // Небольшая потеря энергии
        }
        
        if (energy <= 0) {
            // Умирает
            System.out.println("Herbivore died.");
        }
    }

    private void move() {
        Random rand = new Random();
        x += rand.nextInt(3) - 1; // Движение по оси X
        y += rand.nextInt(3) - 1; // Движение по оси Y

        // Ограничение по границам экрана
        x = Math.max(0, Math.min(x, 790));
        y = Math.max(0, Math.min(y, 590));
    }

    private void eat(List<Plant> plants) {
        for (int i = 0; i < plants.size(); i++) {
            Plant plant = plants.get(i);
            if (Math.abs(x - plant.getX()) < 10 && Math.abs(y - plant.getY()) < 10) {
                energy += 20; // Получение энергии от растения
                plants.remove(i); // Удаление съеденного растения
                break;
            }
        }
    }
}

class Carnivore {
    private int x;
    private int y;
    private final Perceptron perceptron;
    private int energy;

    public Carnivore(int x, int y) {
        this.x = x;
        this.y = y;
        this.perceptron = new Perceptron(3); // Входы: направление движения и энергия
        this.energy = 100; // Начальная энергия
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void act(List<Herbivore> herbivores) {
        double[] inputs = {x / 800.0, y / 600.0, energy / 100.0};
        double action = perceptron.predict(inputs);

        // Простейшая логика движения
        if (action > 0.5) { // Двигаться
            move();
            energy -= 1; // Потеря энергии на движение
            eat(herbivores); // Попытка поесть травоядных
        } else { // Оставаться на месте
            energy -= 0.5; // Небольшая потеря энергии
        }
        
        if (energy <= 0) {
            // Умирает
            System.out.println("Carnivore died.");
        }
    }

    private void move() {
        Random rand = new Random();
        x += rand.nextInt(3) - 1; // Движение по оси X
        y += rand.nextInt(3) - 1; // Движение по оси Y

        // Ограничение по границам экрана
        x = Math.max(0, Math.min(x, 790));
        y = Math.max(0, Math.min(y, 590));
    }

    private void eat(List<Herbivore> herbivores) {
        for (int i = 0; i < herbivores.size(); i++) {
            Herbivore herbivore = herbivores.get(i);
            if (Math.abs(x - herbivore.getX()) < 10 && Math.abs(y - herbivore.getY()) < 10) {
                energy += 50; // Получение энергии от травоядного
                herbivores.remove(i); // Удаление съеденного травоядного
                break;
            }
        }
    }
}

class Perceptron {
    private double[] weights;

    public Perceptron(int inputSize) {
        weights = new double[inputSize + 1]; // +1 для смещения
        Random rand = new Random();
        
        for (int i = 0; i < weights.length; i++) {
            weights[i] = rand.nextDouble() * 2 - 1; // Инициализация весов в диапазоне [-1, 1]
        }
    }

    public double predict(double[] inputs) {
        double sum = weights[0]; // Смещение
       
       for (int i = 0; i < inputs.length; i++) {
            sum += weights[i + 1] * inputs[i];
       }
       return activationFunction(sum);
   }

   private double activationFunction(double sum) {
       return sum >= 0 ? 1 : 0; // Пороговая функция активации
   }
}
