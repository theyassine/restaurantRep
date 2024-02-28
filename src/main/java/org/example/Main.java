package org.example;

import Entities.Evennement;
import Service.EvennementService;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        EvennementService evennementService = new EvennementService();

        // Add an event
        // Corrected eventToAdd instantiation

        Evennement eventToAdd = new Evennement(1, "Event_Name", "Event_Description", "2024-01-01", "2024-01-10", "Event_Location", 12, "14:30:00", "14:30:00", "Resto_Name","1234567");
       evennementService.add(eventToAdd);

        // Display all events

       System.out.println("All Events:");
       evennementService.readAll().forEach(System.out::println);

        // Update an event

      List<Evennement> eventsToUpdate = evennementService.readAll();
        if (!eventsToUpdate.isEmpty()) {
            Evennement eventToUpdate = eventsToUpdate.get(0);
           eventToUpdate.setNom_event("Updated_Event_Name");
           evennementService.update(eventToUpdate);

             //Display all events after update

           System.out.println("All Events after update:");
           evennementService.readAll().forEach(System.out::println);
        } else {
          System.out.println("No events found to update.");
       }

        // Delete an event

        List<Evennement> eventsToDelete = evennementService.readAll();
        if (!eventsToDelete.isEmpty()) {
            Evennement eventToDelete = eventsToDelete.get(0);
           evennementService.delete(eventToDelete);

            // Display all events after delete

           System.out.println("All Events after delete:");
         evennementService.readAll().forEach(System.out::println);
        } else {
            System.out.println("No events found to delete.");
       }

        // Read event by ID

        int eventIdToRead = 1;
       Evennement readEventById = evennementService.readById(eventIdToRead);
       if (readEventById != null) {
            System.out.println("Event found by ID:");
            System.out.println(readEventById);
       } else {
           System.out.println("Event not found for ID: " + eventIdToRead);
         }
    }
}
