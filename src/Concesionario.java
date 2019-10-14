import java.util.*;

public class Concesionario {

    private Cliente[] clientes;
    private Vehiculo[] vehiculos;

    public Concesionario(Cliente[] clientes, Vehiculo[] vehiculos){
        this.clientes = clientes;
        this.vehiculos = vehiculos;
    }

    public Cliente[] get_clientes() { return clientes; }

//Nos muestra todos los vehiculos disponibles en la base de datos
    public String ver_vehiculos(){
        String c = "";
        for (int i=0; i<vehiculos.length; i++) {
            if (vehiculos[i].get_disponible()) {
                c += ("Modelo: " + vehiculos[i].get_modelo()
                        + "\nMatrícula: " + vehiculos[i].get_matricula()
                        + "\nKm: " + vehiculos[i].get_km()
                        + "\nPrecio por día: " + vehiculos[i].get_preciodia());

                if (vehiculos[i] instanceof Hibrido) {
                    c += ("\nAutonomía: " + vehiculos[i].get_autonomia()
                            + "\nCambio automático: ");
                    if (vehiculos[i].get_cambio_auto())
                        c += ("sí\n\n");
                    else
                        c += ("no\n\n");
                } else {
                    c += ("\nAC: ");
                    if (vehiculos[i].get_AC())
                        c += ("sí\n\n");
                    else
                        c += ("no\n\n");
                }
            }
        }
        return c;
    } //consultar_vehiculos

//Nos devuelve el cliente al cual pertence el dni
    public Cliente consultar_cliente(String dni){
        int i = 0;
        while (!dni.equalsIgnoreCase(clientes[i].get_dni())){
            i++;
        }
        return clientes[i];
    }//consultar_cliente

//Nos comprueba el dni y nos devuelve true si pertenece a la base de datos
    public boolean esCliente(String dni){
        for (int i=0; i<clientes.length; i++){
            if (dni.equalsIgnoreCase(clientes[i].get_dni())){
                return true;
            }
        }
        return false;
    }//esCliente

//Nos devuelve el vehiculo a cuya matricula este asociado
    public Vehiculo consultar_vehiculo(String matricula){
        if (esVehiculo(matricula)){
            int i = 0;
            while (!matricula.equalsIgnoreCase(vehiculos[i].get_matricula())){
                i++;
            }
            return vehiculos[i];
        }
        System.out.println("Este vehículo no se encuentra en la base de datos\n");
        return null;
    }//consultar_vehiculo

//Comprueba la matricula y nos devuelve true si pertenece a la base de datos
    public boolean esVehiculo(String matricula){
        for (int i=0; i<vehiculos.length; i++){
            if (matricula.equalsIgnoreCase(vehiculos[i].get_matricula())){
                return true;
            }
        }
        return false;
    }//esVehiculo


    public boolean consultar_vip(String dni){ return (consultar_cliente(dni) instanceof ClienteVip); }

    public void añadir_cliente(Cliente[] clientes, Cliente cliente){
        Cliente[] aux = new Cliente[(clientes.length) + 1];

        for (int i=0; i<clientes.length; i++){
            aux[i] = clientes[i];
        }

        aux[clientes.length] = cliente;
        this.clientes = aux;
    }

    public void alquilar_vehiculo(Concesionario c) throws Cliente.NoEsLetraexcepcion{
        Scanner sc = new Scanner(System.in);

        System.out.println("Introduzca el DNI del cliente que desea alquilar un vehículo: ");
        String dni = sc.next();
        Cliente cliente = null;

        if (c.esCliente(dni)) {
            cliente = c.consultar_cliente(dni);
        } else {
            System.out.println("No se ha encontrado un cliente con ese DNI. Por favor, cree uno nuevo.");
            System.out.println("Inserte su nombre: ");
            String nombre = sc.next();
            System.out.println("Inserte su número de tarjeta: ");
            long num_tarjeta = sc.nextLong();
            System.out.println("Introduzca el número de años que lleva con el carnet: ");
            int tiempo_carnet = sc.nextInt();
            cliente = new Cliente(nombre, dni, num_tarjeta, tiempo_carnet);

            System.out.println("Ha creado con éxito su cliente\n");
        }

        System.out.println("Introduzca la matrícula del vehículo que desea alquilar: ");
        String matricula = sc.next();

        if (!c.esVehiculo(matricula)) {
            while (!c.esVehiculo(matricula)) {
                System.out.println("El vehículo con esa matrícula no se encuentra en la base de datos. Por favor, pruebe otra vez: ");
                matricula = sc.next();
            }
        }

        if (!c.consultar_vehiculo(matricula).get_disponible()) {
            while (!c.consultar_vehiculo(matricula).get_disponible()) {
                System.out.println("El vehículo con esa matrícula ya está alquilado a otro cliente. Por favor, pruebe otra vez: ");
                matricula = sc.next();
            }
        }

        Vehiculo vehiculo = c.consultar_vehiculo(matricula);

        System.out.println("Especifique cuantos días desea alquilar el vehículo: ");
        int num_dias = sc.nextInt();

        cliente.set_contratos(new Contrato(cliente, vehiculo, num_dias));
        c.añadir_cliente(c.get_clientes(), cliente);
        c.consultar_vehiculo(matricula).set_disponible(false);

        System.out.println("¡El contrato ha sido creado con éxito!\n");
    }

}
