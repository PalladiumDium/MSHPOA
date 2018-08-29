package model;

public class Mesure {

    public Mesure(String unite, String value) {
        this.unite = unite;

        this.value = Double.parseDouble(value.replace(',', '.'));
        switch (unite.toUpperCase()){
            case "BAR":
            case "HPA":
            case "PA":
                this.type = "pression";
                break;
            case "C":
                this.type = "temperature";
                break;
            case "%":
                this.type = "humidite";
        }
    }


    public String getUnite() {
        return unite;
    }

    public void setUnite(String unite) {
        this.unite = unite;
    }

    public Double getValue() {

        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String unite;
    private double value;
    private String type;

}
