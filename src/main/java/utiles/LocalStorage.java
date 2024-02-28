package utiles;

import entities.User;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LocalStorage {
    private static final String STORAGE_PATH = "user_data.txt";

    public void writeToStorage(User user) throws IOException {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(STORAGE_PATH))) {
            outputStream.writeObject(user);
        }
    }

    public User readFromStorage() throws IOException, ClassNotFoundException {
        if (!Files.exists(Paths.get(STORAGE_PATH))) {
            return null;
        }

        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(STORAGE_PATH))) {
            return (User) inputStream.readObject();
        }
    }

    public void clearStorage() throws IOException {
        Path path = Paths.get(STORAGE_PATH);
        Files.deleteIfExists(path);
    }
}

