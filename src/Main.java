import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

class Toy {
    private final int id;
    private final String name;
    private int quantity;
    private double weight;

    public Toy(int id, String name, int quantity, double weight) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.weight = weight;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}

class ToyShop {
    private final List<Toy> toys;
    private final Queue<Toy> prizeToys;
    private final String filename;

    public ToyShop(String filename) {
        this.toys = new ArrayList<>();
        this.prizeToys = new LinkedList<>();
        this.filename = filename;
    }

    public void addNewToy(int id, String name, int quantity, double weight) {
        Toy newToy = new Toy(id, name, quantity, weight);
        toys.add(newToy);
    }

    public void changeWeight(int id, double newWeight) {
        for (Toy toy : toys) {
            if (toy.getId() == id) {
                toy.setWeight(newWeight);
                return;
            }
        }
    }

    public void organizeDraw() {
        if (toys.isEmpty()) {
            System.out.println("Ќет игрушек дл€ рисовани€.");
            return;
        }

        double totalWeight = 0;
        for (Toy toy : toys) {
            totalWeight += toy.getWeight();
        }

        double randomValue = Math.random() * totalWeight;
        double cumulativeWeight = 0.0;

        for (Toy toy : toys) {
            cumulativeWeight += toy.getWeight();
            if (randomValue <= cumulativeWeight) {
                if (toy.getQuantity() > 0) {
                    prizeToys.offer(toy);
                    toy.setQuantity(toy.getQuantity() - 1);
                }
                return;
            }
        }
    }

    public void getPrizeToy() {
        if (!prizeToys.isEmpty()) {
            Toy toy = prizeToys.poll();
            writeToFile(toy);
        } else {
            System.out.println("ѕризовых игрушек нет.");
        }
    }

    private void writeToFile(Toy toy) {
        try (FileWriter fileWriter = new FileWriter(filename, true)) {
            fileWriter.write("ѕризова€ игрушка: " + toy.getName() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

public class Main {
    /*
    * —оздан класс Toy с четырьм€ свойствами: id игрушки, текстовым названием, количеством и весом (веро€тностью выпадени€ игрушки).
    * ¬ классе ToyShop реализованы методы дл€ добавлени€ новой игрушки (addNewToy) и изменени€ веса игрушки (changeWeight).
    * ћетод organizeDraw позвол€ет организовать розыгрыш игрушек, в результате которого призова€ игрушка добавл€етс€ в очередь prizeToys.
    * ћетод getPrizeToy отвечает за получение призовой игрушки. ѕри его вызове игрушка удал€етс€ из очереди prizeToys,
      записываетс€ в текстовый файл, и уменьшаетс€ количество этой игрушки в наличии.
    *  ласс ToyShop также использует работу с файлами дл€ записи информации о призовых игрушках.
    */
    public static void main(String[] args) {
        ToyShop shop = new ToyShop("prizes.txt");
        shop.addNewToy(1, "Teddy Bear", 10, 0.2);
        shop.addNewToy(2, "Train", 7, 0.1);
        shop.addNewToy(3, "Doll", 8, 0.3);
        shop.addNewToy(4, "Car", 9, 0.4);

        for (int i = 0; i < 5; i++) {
            shop.organizeDraw();
            shop.getPrizeToy();
        }
    }
}
