import java.lang.*;
public class Cliente {
    public static class NoEsLetraexcepcion extends Exception {
        public NoEsLetraexcepcion(String s) {
        }
    }
    protected String dni;
    protected String nombre;
    protected int descuentos = 0;
    protected int tiempo_carnet;
    protected long num_tarjeta;
    protected short num_contratos = 0;
    protected Contrato[] contratos = new Contrato[10];

    public Cliente(String nombre, String dni, long num_tarjeta, int tiempo_carnet)throws NoEsLetraexcepcion{


        this.nombre = nombre;
        this.dni = dni;
        this.num_tarjeta = num_tarjeta;
        this.tiempo_carnet = tiempo_carnet;

        if (tiempo_carnet>10)
            descuentos = 5;
    }

    //Getters
    public String get_dni() { return dni;  }
    public String get_nombre() {
        return nombre;
    }
    public int get_descuentos() {
        return descuentos;
    }
    public int get_tiempo_carnet() {
        return tiempo_carnet;
    }
    public long get_num_tarjeta() {
        return num_tarjeta;
    }
    public String get_contratos(){
        String c = "";
        if (num_contratos != 0){
            for (int i=0; i<num_contratos; i++){
                c += ("Contrato nº"+(i+1));
                c += ("\n------------");
                c += ("\nNombre del cliente: " + contratos[i].get_cliente().get_nombre());
                c += ("\nDNI: " + contratos[i].get_cliente().get_dni());
                c += ("\nDescuento: " + contratos[i].get_cliente().get_descuentos()+"%");
                c += ("\nMatrícula del vehículo alquilado: " + contratos[i].get_vehiculo().get_matricula());
                c += ("\nModelo del vehículo: " + contratos[i].get_vehiculo().get_modelo());
                c += ("\nNúmero de días alquilado: " + contratos[i].get_num_dias());
                c += ("\nPrecio total (con descuento): " + ((contratos[i].get_num_dias()*contratos[i].get_vehiculo().get_preciodia()) * ((double) 1-(descuentos/100)))+"€");
                c += ("\n");
            }
        }
        else
            c += ("Este cliente no tiene ningún contrato activo.");

        return c;
    }

    public String get_vehiculos(){
        String c = "";

        if (num_contratos != 0){
            for (int i=0; i<num_contratos; i++){
                c += ("\nMatrícula: " + contratos[i].get_vehiculo().get_matricula());
                c += ("\nModelo: " + contratos[i].get_vehiculo().get_modelo() + "\n");
            }
        }
        else
            c = "Este cliente no tiene ningún vehículo registrado.\n\n";

        return c;
    }

    //Setters
    public void set_contratos(Contrato contrato){
        contratos[num_contratos] = contrato;
        num_contratos++;
    }

    //Polimorfismo
    public int get_tarjeta_vip() { return 0; }

}
