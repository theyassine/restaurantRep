package utiles;

import entities.User;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;


public class LocalStorage {
    private static final String STORAGE_PATH = "user_data.txt";

    public LocalStorage() throws IOException {
        this.initLocalStorage();
    }

    public File initLocalStorage() throws IOException {
        File myObj = new File(STORAGE_PATH);
        if (myObj.createNewFile()) {
            System.out.println("Storage created: " + myObj.getName());
        } else {
            System.out.println("Storage initialized.");
        }
        return myObj;
    }

    public void writeToStorage(User user) throws IOException {
        FileWriter myWriter = new FileWriter(STORAGE_PATH);
        JSONObject obj = new JSONObject();

        obj.put("id", user.getId());
        obj.put("nom", user.getNom());
        obj.put("prenom", user.getPrenom());
        obj.put("email", user.getEmail());
        obj.put("pwd", user.getPwd()); // Utilisez getPwd() pour obtenir le mot de passe
        obj.put("role", user.getRole().toString()); // Utilisez getRole() et toString() pour obtenir le rôle

        System.out.println(obj.toJSONString());

        myWriter.write(obj.toJSONString());
        myWriter.close();
        System.out.println("Successfully stored user.");
    }

    public User getStoredUser() throws FileNotFoundException {
        User user = new User();

        File myObj = new File(STORAGE_PATH);
        Scanner myReader = new Scanner(myObj);
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            Object obj = JSONValue.parse(data);
            JSONObject json = (JSONObject) obj;
            user = getUserFromJSON(json);
        }
        myReader.close();
        return user;
    }

    public User getUserFromJSON(JSONObject json) {
        User user = new User();

        int id = ((Long) json.get("id")).intValue(); // Castez en int
        String nom = (String) json.get("nom");
        String prenom = (String) json.get("prenom");
        String email = (String) json.get("email");
        String pwd = (String) json.get("pwd");
        entities.role role = entities.role.valueOf((String) json.get("role")); // Castez en role

        user.setId(id);
        user.setNom(nom);
        user.setPrenom(prenom);
        user.setEmail(email);
        user.setPwd(pwd);
        user.setRole(role);

        return user;
    }

    public void deleteStorage() {
        File myObj = new File(STORAGE_PATH);
        if (myObj.delete()) {
            System.out.println("Deleted storage: " + myObj.getName());
        } else {
            System.out.println("Failed to delete the file.");
        }
    }
}

