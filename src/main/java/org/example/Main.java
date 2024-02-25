package org.example;

import org.example.Service.ListecommandeService;
import org.example.Service.RestaurantService;
import org.example.entities.Listecommande;
import org.example.entities.ModePayment;
import org.example.entities.Restaurant;

import java.util.List;

public class Main {
    public static void main(String[] args) {



        //Restaurant P1=new Restaurant(2,"hafood","africain","54512887","whe are the best","tunis","8/10");
       Listecommande commandeModifier=new Listecommande(1,"WARDAbey","Pizza","warda","WARDA@com","ariana", ModePayment.CASH);



        //RestaurantService restaurantService = new RestaurantService();

        ListecommandeService liste =new ListecommandeService();
        liste.ajouter(commandeModifier);
        //restaurantService.add(commandeModifier);
        // restaurantService.supprimer(9);
        // Création d'un objet Restaurant à ajouter
        //restaurantService.update(commandeModifier);
        //String placeToSearch ="ariana";
        //List<Restaurant> restaurantsInPlace = restaurantService.searchByPlace(placeToSearch);

        // Utilisation des résultats
       //for (Restaurant restaurant : restaurantsInPlace) {
         //  System.out.println(restaurant);
      //}

        //int id_categorie =2;
        //List<Restaurant> restaurantsByCategory = restaurantService.getRestaurantsByCategory(id_categorie);

        // Utilisation des résultats
        //for (Restaurant restaurant : restaurantsByCategory) {
            //System.out.println(restaurant);
        }
    }



