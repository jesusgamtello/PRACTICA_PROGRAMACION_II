abstract class Vehiculo{

    protected String matricula;
    protected int km;
    protected int preciodia;
    protected String modelo;
    protected boolean disponible = true;


    public Vehiculo(String matricula, int km, int preciodia, String modelo) {
        this.matricula = matricula;
        this.km = km;
        this.preciodia = preciodia;
        this.modelo = modelo;
    }

    //Getters
    public String get_matricula(){ return matricula; }
    public String get_modelo(){ return modelo; }
    public int get_km(){ return km; }
    public int get_preciodia(){ return preciodia; }
    public boolean get_disponible() { return disponible; }

    //Polimorfismo
    public int get_autonomia() { return 0; }
    public boolean get_cambio_auto() { return false; }
    public boolean get_AC() {return false;}

    public void set_disponible(boolean disponible) {
        this.disponible = disponible;
    }
}
