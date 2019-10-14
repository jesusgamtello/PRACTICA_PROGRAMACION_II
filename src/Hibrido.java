public class Hibrido extends Vehiculo {
    private int autonomia;
    private boolean cambio_auto;

    public Hibrido(String matricula, int km, int preciodia, String modelo, int autonomia, boolean cambio_auto) {
        super(matricula, km, preciodia, modelo);
        this.autonomia = autonomia;
        this.cambio_auto = cambio_auto;
    }

    //Getters
    public int get_autonomia() { return autonomia; }
    public boolean get_cambio_auto() { return cambio_auto; }
}
