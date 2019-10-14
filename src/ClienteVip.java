public class ClienteVip extends Cliente {

    private int id_vip;

    public ClienteVip(String nombre, String dni, long num_tarjeta, int tiempo_carnet, int id_vip) throws Cliente.NoEsLetraexcepcion{
        super(nombre, dni, num_tarjeta, tiempo_carnet);
        this.id_vip = id_vip;

        if (tiempo_carnet > 10)
            descuentos = 25;
        else
            descuentos = 20;
    }


    public int get_tarjeta_vip() { return id_vip; }
}