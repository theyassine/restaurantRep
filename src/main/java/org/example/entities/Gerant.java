package org.example.entities;

public class Gerant {

    private String num_telephone;

    public Gerant(String num_telephone) {
        this.num_telephone = num_telephone;
    }

    public String getNum_telephone() {
        return num_telephone;
    }

    public void setNum_telephone(String num_telephone) {
        this.num_telephone = num_telephone;
    }

    @Override
    public String toString() {
        return "Gerant{" +
                "num_telephone='" + num_telephone + '\'' +
                '}';
    }
}
