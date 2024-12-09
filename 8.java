import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Individual {
    double x;
    double y;
    double fitness;

    public Individual(double x, double y) {
        this.x = x;
        this.y = y;
        this.fitness = calculateFitness();
    }

    public double calculateFitness() {
        return 1 / (1 + x * x + y * y);
    }
}

public class GeneticAlgorithm {
    private static final int POPULATION_SIZE = 100;
    private static final int GENERATIONS = 1000;
    private static final double MUTATION_RATE = 0.1;
    private static final Random RANDOM = new Random();

    public static void main(String[] args) {
        List<Individual> population = initializePopulation();

        // Элитный отбор
        Individual bestElite = runGeneticAlgorithm(population, true);
        System.out.println("Элитный отбор: x = " + bestElite.x + ", y = " + bestElite.y + ", fitness = " + bestElite.fitness);

        // Сброс популяции
        population = initializePopulation();

        // Рулеточный отбор
        Individual bestRoulette = runGeneticAlgorithm(population, false);
        System.out.println("Рулеточный отбор: x = " + bestRoulette.x + ", y = " + bestRoulette.y + ", fitness = " + bestRoulette.fitness);
    }

    private static List<Individual> initializePopulation() {
        List<Individual> population = new ArrayList<>();
        for (int i = 0; i < POPULATION_SIZE; i++) {
            double x = RANDOM.nextDouble() * 10 - 5; // Генерируем x в диапазоне [-5, 5]
            double y = RANDOM.nextDouble() * 10 - 5; // Генерируем y в диапазоне [-5, 5]
            population.add(new Individual(x, y));
        }
        return population;
    }

    private static Individual runGeneticAlgorithm(List<Individual> population, boolean eliteSelection) {
        for (int generation = 0; generation < GENERATIONS; generation++) {
            List<Individual> newPopulation = new ArrayList<>();

            // Селекция
            if (eliteSelection) {
                newPopulation.add(selectBestIndividual(population)); // Добавляем лучшего в новую популяцию
            }

            while (newPopulation.size() < POPULATION_SIZE) {
                Individual parent1 = selectParent(population, eliteSelection);
                Individual parent2 = selectParent(population, eliteSelection);

                Individual offspring = crossover(parent1, parent2);
                mutate(offspring);
                newPopulation.add(offspring);
            }

            population = newPopulation;
        }

        return selectBestIndividual(population);
    }

    private static Individual selectBestIndividual(List<Individual> population) {
        return population.stream().max((a, b) -> Double.compare(a.fitness, b.fitness)).orElse(null);
    }

    private static Individual selectParent(List<Individual> population, boolean eliteSelection) {
        if (eliteSelection) {
            return selectBestIndividual(population); // Элитный отбор возвращает лучшего
        } else {
            return rouletteWheelSelection(population); // Рулеточный отбор
        }
    }

    private static Individual rouletteWheelSelection(List<Individual> population) {
        double totalFitness = population.stream().mapToDouble(ind -> ind.fitness).sum();
        double randomValue = RANDOM.nextDouble() * totalFitness;

        double cumulativeFitness = 0.0;
        for (Individual ind : population) {
            cumulativeFitness += ind.fitness;
          if (cumulativeFitness >= randomValue) {
                return ind;
            }
        }
        return population.get(population.size() - 1); // На случай, если что-то пойдет не так
    }

    private static Individual crossover(Individual parent1, Individual parent2) {
        double childX = (parent1.x + parent2.x) / 2;
        double childY = (parent1.y + parent2.y) / 2;
        return new Individual(childX, childY);
    }

    private static void mutate(Individual individual) {
        if (RANDOM.nextDouble() < MUTATION_RATE) {
            individual.x += RANDOM.nextGaussian(); // Нормальное распределение для мутации
            individual.y += RANDOM.nextGaussian(); // Нормальное распределение для мутации
            individual.fitness = individual.calculateFitness(); // Пересчитываем фитнес
        }
    }
}
