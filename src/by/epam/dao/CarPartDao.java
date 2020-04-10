package by.epam.dao;

import by.epam.entities.CarPart;
import by.epam.Utility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class CarPartDao {
    //private ObjectOutputStream outputStream;
    Logger log = LogManager.getLogger();

    public void create(CarPart carPart) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Utility.carPartData, true))) {
            writer.write(carPart.toStringFile());
            writer.newLine();
            log.info("CarPart created");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public ArrayList<CarPart> readAll() {
        try (BufferedReader reader = new BufferedReader(new FileReader(Utility.carPartData))) {
            ArrayList<CarPart> carParts = new ArrayList<>();
            String buffer;
            while ((buffer = reader.readLine()) != null) {
                carParts.add(carPartTokenizer(buffer));
            }
            return carParts;
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    public void writeAll(ArrayList<CarPart> carParts) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(Utility.carPartData)))) {
            for (CarPart carPart : carParts) {
                writer.write(carPart.toStringFile());
                writer.write("\n");
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public int getMaxId() {
        ArrayList<CarPart> carParts = readAll();
        int result = 0;
        for (CarPart carPart : carParts) {
            if (carPart.getId() > result) {
                result = carPart.getId();
            }
        }
        return result;
    }

    public void update(int id, CarPart newCarPart) {
        ArrayList<CarPart> carParts = readAll();
        for (CarPart carPart : carParts) {
            if (id == carPart.getId()) {
                carPart.setName(newCarPart.getName());
                carPart.setDescription(newCarPart.getDescription());
                carPart.setCarId(newCarPart.getCarId());
                break;
            }
        }
        writeAll(carParts);
        log.info("CarPart updated");
    }

    public CarPart readCarPart(int id) {
        ArrayList<CarPart> carParts = readAll();
        for (CarPart carPart : carParts) {
            if (id == carPart.getId()) {
                return carPart;
            }
        }
        log.error("Wrong id");
        throw new IllegalArgumentException(String.format("Id %d не найден", id));
    }

    public void delete(int id) {
        ArrayList<CarPart> carParts = readAll();
        for (CarPart carPart : carParts) {
            if (id == carPart.getId()) {
                carParts.remove(carPart);
                writeAll(carParts);
                log.info("CarPart deleted");
                return;
            }
        }
        throw new IllegalArgumentException(String.format("Id %d не найден", id));
    }

    public CarPart carPartTokenizer(String buffer){
        StringTokenizer st = new StringTokenizer(buffer, Utility.valueSeparator);
        int id = Integer.parseInt(st.nextToken());
        String name = st.nextToken();
        String description = st.nextToken();
        String CarId = st.nextToken();
        return new CarPart(id, name, description, CarId);
    }
}
