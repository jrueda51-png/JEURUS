package util;

public class ItemCombo {

    private int id;
    private String nombre;

    public ItemCombo(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String toString() {
        return nombre;
    }
}
