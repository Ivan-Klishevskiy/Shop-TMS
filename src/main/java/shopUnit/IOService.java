package shopUnit;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.Scanner;
import java.util.regex.Pattern;

public class IOService {
    private Scanner sc;

    public void objectToJson(Shop shop, String filename) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File(filename), shop);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Shop jsonToObject(String filename) {
        ObjectMapper mapper = new ObjectMapper();
        try (FileReader reader = new FileReader(filename)) {
            return  (Shop) mapper.readValue(new File(filename), Shop.class);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    public <T> T readObject(String way) {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(way))) {
            return (T) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> void saveObject(String way, T object) {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(way))) {
            objectOutputStream.writeObject(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void writeInFile(String way, String txt) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(way))) {
            writer.write(txt);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }


    public int getInt(int min, int max) {
        sc = new Scanner(System.in);
        while (true) {
            while (!sc.hasNextInt()) {
                System.out.println("Ошибка ввода. Введите число:");
                sc.next();
            }
            int number = sc.nextInt();
            if (number >= min && number <= max) {
                return number;
            } else {
                System.out.println("Ошибка ввода. Число меньше чем  " + min + " или больше чем " + max);
            }
        }
    }

    public String getString() {
        sc = new Scanner(System.in);
        while (true) {
            while (!sc.hasNextLine()) {
                sc.next();
            }
            String str = sc.nextLine();

            if (Pattern.matches("(^[A-ZА-Я]\\s?([a-zа-я]\\s?)*(\\d*\\s?)*)", str)) {
                return str;
            } else {
                System.out.println("Ошибка ввода.");
            }
        }
    }
}
