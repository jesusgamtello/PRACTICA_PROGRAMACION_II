public class Contrato {

    private Cliente c;
    private Vehiculo v;
    private int num_dias;

    public Contrato(Cliente c, Vehiculo v, int num_dias){
        this.c = c;
        this.v = v;
        this.num_dias = num_dias;
    }

    //Getters
    public Cliente get_cliente(){ return c; }
    public Vehiculo get_vehiculo(){ return v; }
    public int get_num_dias(){ return num_dias; }

}