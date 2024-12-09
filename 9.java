import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Perceptron {
    private double[] weights;
    private int inputSize;

    public Perceptron(int inputSize) {
        this.inputSize = inputSize;
        this.weights = new double[inputSize + 1]; // +1 для смещения
        initializeWeights();
    }

    private void initializeWeights() {
        Random rand = new Random();
        for (int i = 0; i < weights.length; i++) {
            weights[i] = rand.nextDouble() * 2 - 1; // Инициализация весов в диапазоне [-1, 1]
        }
    }

    public double predict(double[] inputs) {
        double sum = weights[0]; // Смещение
        for (int i = 0; i < inputSize; i++) {
            sum += weights[i + 1] * inputs[i];
        }
        return activationFunction(sum);
    }

    private double activationFunction(double sum) {
        return sum >= 0 ? 1 : 0; // Пороговая функция активации
    }

    public void setWeights(double[] newWeights) {
        this.weights = newWeights;
    }

    public double[] getWeights() {
        return weights;
    }
}

class GeneticAlgorithm {
    private static final int POPULATION_SIZE = 100;
    private static final int GENERATIONS = 1000;
    private static final double MUTATION_RATE = 0.1;
    private static final Random RANDOM = new Random();

    public static void main(String[] args) {
        // Пример данных для обучения (AND)
        double[][] inputs = { {0, 0}, {0, 1}, {1, 0}, {1, 1} };
        double[] outputs = {0, 0, 0, 1}; // Ожидаемые результаты для AND

        // Обучение однослойного перцептрона
        Perceptron singleLayerPerceptron = new Perceptron(2);
        double[] bestWeightsSingleLayer = runGeneticAlgorithm(singleLayerPerceptron, inputs, outputs);
        System.out.println("Лучшие веса однослойного перцептрона: " + arrayToString(bestWeightsSingleLayer));

        // Обучение многослойного перцептрона
        MultiLayerPerceptron multiLayerPerceptron = new MultiLayerPerceptron(2, 2, 1);
        double[] bestWeightsMultiLayer = runGeneticAlgorithm(multiLayerPerceptron, inputs, outputs);
        System.out.println("Лучшие веса многослойного перцептрона: " + arrayToString(bestWeightsMultiLayer));
    }

    private static double[] runGeneticAlgorithm(Perceptron perceptron, double[][] inputs, double[] outputs) {
        List<double[]> population = initializePopulation(perceptron.getWeights().length);

        for (int generation = 0; generation < GENERATIONS; generation++) {
            List<double[]> newPopulation = new ArrayList<>();

            for (double[] individual : population) {
                perceptron.setWeights(individual);
                double fitness = calculateFitness(perceptron, inputs, outputs);
                if (newPopulation.size() < POPULATION_SIZE) {
                    newPopulation.add(individual);
                }
            }

            while (newPopulation.size() < POPULATION_SIZE) {
                double[] parent1 = selectParent(population, perceptron, inputs, outputs);
                double[] parent2 = selectParent(population, perceptron, inputs, outputs);
                double[] offspring = crossover(parent1, parent2);
                mutate(offspring);
                newPopulation.add(offspring);
            }

            population = newPopulation;
        }

        return selectBestIndividual(population, perceptron, inputs, outputs);
    }

    private static List<double[]> initializePopulation(int size) {
        List<double[]> population = new ArrayList<>();
for (int i = 0; i < POPULATION_SIZE; i++) {
            double[] individual = new double[size];
            for (int j = 0; j < size; j++) {
                individual[j] = RANDOM.nextDouble() * 2 - 1; // Инициализация весов в диапазоне [-1, 1]
            }
            population.add(individual);
        }
        return population;
    }

    private static double calculateFitness(Perceptron perceptron, double[][] inputs, double[] outputs) {
        int correctPredictions = 0;
        for (int i = 0; i < inputs.length; i++) {
            if (perceptron.predict(inputs[i]) == outputs[i]) {
                correctPredictions++;
            }
        }
        return (double) correctPredictions / inputs.length; // Доля правильных предсказаний
    }

    private static double[] selectParent(List<double[]> population, Perceptron perceptron, double[][] inputs, double[] outputs) {
        return rouletteWheelSelection(population, perceptron, inputs, outputs);
    }

    private static double[] rouletteWheelSelection(List<double[]> population, Perceptron perceptron, double[][] inputs, double[] outputs) {
        double totalFitness = population.stream()
                                         .mapToDouble(ind -> calculateFitness(perceptron.setWeights(ind), inputs, outputs))
                                         .sum();
        double randomValue = RANDOM.nextDouble() * totalFitness;

        double cumulativeFitness = 0.0;
        for (double[] ind : population) {
            cumulativeFitness += calculateFitness(perceptron.setWeights(ind), inputs, outputs);
            if (cumulativeFitness >= randomValue) {
                return ind;
            }
        }
        return population.get(population.size() - 1); // На случай ошибки
    }

    private static double[] crossover(double[] parent1, double[] parent2) {
        double[] child = new double[parent1.length];
        for (int i = 0; i < child.length; i++) {
            child[i] = RANDOM.nextBoolean() ? parent1[i] : parent2[i];
        }
        return child;
    }

    private static void mutate(double[] individual) {
        for (int i = 0; i < individual.length; i++) {
            if (RANDOM.nextDouble() < MUTATION_RATE) {
                individual[i] += RANDOM.nextGaussian() * 0.5; // Нормальное распределение для мутации
            }
        }
    }

    private static double[] selectBestIndividual(List<double[]> population, Perceptron perceptron, double[][] inputs, double[] outputs) {
        return population.stream()
                        .max((a, b) -> Double.compare(calculateFitness(perceptron.setWeights(a), inputs, outputs),
                                                       calculateFitness(perceptron.setWeights(b), inputs, outputs)))
                        .orElse(null);
    }

    private static String arrayToString(double[] array) {
        StringBuilder sb = new StringBuilder();
        for (double v : array) {
            sb.append(v).append(" ");
        }
        return sb.toString().trim();
    }
}

class MultiLayerPerceptron extends Perceptron {
    private Perceptron[] hiddenLayer;
    private int hiddenSize;
    
    public MultiLayerPerceptron(int inputSize, int hiddenSize, int outputSize) {
        super(inputSize); // Вызов конструктора базового класса
        this.hiddenSize = hiddenSize;
        
        hiddenLayer = new Perceptron[hiddenSize];
        
        for (int i = 0; i < hiddenSize; i++) {
            hiddenLayer[i] = new Perceptron(inputSize); // Инициализация скрытых перцептронов
        }
        
        // Выходной перцептрон
        this.outputPerceptron = new Perceptron(hiddenSize);
    }
    
    private Perceptron outputPerceptron;

    @Override
    public double predict(double[] inputs) {
        double[] hiddenOutputs = new double[hiddenSize];
        
        for (int i = 0; i < hiddenSize; i++) {
            hiddenOutputs[i] = hiddenLayer[i].predict(inputs);
        }
        
        return outputPerceptron.predict(hiddenOutputs); // Выходной слой принимает выходы скрытого слоя
    }
@Override
    public void setWeights(double[] newWeights) {
        int index = 0;
        
        for (Perceptron p : hiddenLayer) {
            double[] weights = newWeights[index++];
            p.setWeights(weights);
        }
        
        outputPerceptron.setWeights(newWeights[index]); // Установка весов выходного перцептрона
    }

    @Override
    public double[] getWeights() {
        List<Double> allWeights = new ArrayList<>();
        
        for (Perceptron p : hiddenLayer) {
            for (double weight : p.getWeights()) {
                allWeights.add(weight);
            }
        }
        
        for (double weight : outputPerceptron.getWeights()) {
            allWeights.add(weight);
        }
        
        return allWeights.stream().mapToDouble(Double::doubleValue).toArray();
    }
}
