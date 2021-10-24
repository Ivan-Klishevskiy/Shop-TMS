package by.teachmeskills.service;

import by.teachmeskills.model.Shop;

public interface IOService {
    void objectToJson(Shop shop, String filename);
    Shop jsonToObject(String filename);
    <T> T readObject(String way);
    <T> void saveObject(String way, T object);
    void writeInFile(String way, String txt);
}
