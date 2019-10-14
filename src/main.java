import java.util.*;
import java.io.*;

public class main {

    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws IOException, Cliente.NoEsLetraexcepcion {

        System.out.println("Introduzca la ruta del fichero con los clientes: ");
        String clientes_path = sc.nextLine();
        System.out.println("Introduzca la ruta del fichero con los vehículos: ");
        String vehiculos_path = sc.nextLine();

        Cliente[] clientes = cargar_matriz_clientes(contar(clientes_path), clientes_path);
        Vehiculo[] vehiculos = cargar_matriz_vehiculos(contar(vehiculos_path), vehiculos_path);

        Concesionario c = new Concesionario(clientes, vehiculos);

        menu(c);
    }//main

    //Contará las líneas que posee el fichero, con el fin de conocer el número de Clientes/Vehículos que hay.
    private static int contar(String path) throws IOException {

        Scanner doc = new Scanner(new File(path));
        int num_items = 0;
        while (doc.hasNextLine()) {
            num_items++;
            doc.nextLine();
        }

        doc.close();
        return num_items;
    }

    //Cargará todos los datos de los clientes desde el fichero dado a una matriz.
    private static Cliente[] cargar_matriz_clientes(int num_clientes, String path) throws FileNotFoundException, Cliente.NoEsLetraexcepcion {
        Cliente[] clientes = new Cliente[num_clientes];
        Scanner doc_Clientes = new Scanner(new File(path));

        for (int i = 0; i < num_clientes; i++) {
            String nombre = doc_Clientes.next();
            String dni = doc_Clientes.next();
            long tarjeta = doc_Clientes.nextLong();
            int tiempo_carnet = doc_Clientes.nextInt();

            // Crea un ClienteVip en el caso que lea 'true'
            if (doc_Clientes.nextBoolean())
                clientes[i] = new ClienteVip(nombre, dni, tarjeta, tiempo_carnet, doc_Clientes.nextInt());
                // Si no, crea simplemente un Cliente
            else
                clientes[i] = new Cliente(nombre, dni, tarjeta, tiempo_carnet);

            if (i < (num_clientes - 1))
                doc_Clientes.nextLine();
        }

        doc_Clientes.close();
        return clientes;
    }

    // Cargará todos los datos de los vehículos desde el fichero dado a una matriz.
    private static Vehiculo[] cargar_matriz_vehiculos(int num_coches, String path) throws FileNotFoundException {
        Vehiculo[] vehiculos = new Vehiculo[num_coches];
        Scanner doc_Vehiculos = new Scanner(new File(path));
        int i = 0;
        while (doc_Vehiculos.hasNextLine()) {
            char type = doc_Vehiculos.next().charAt(0);
            if (type == 'h') {

                String matricula = doc_Vehiculos.next();
                int km = doc_Vehiculos.nextInt();
                int ppd = doc_Vehiculos.nextInt();
                String modelo = doc_Vehiculos.next();
                int autonomia = doc_Vehiculos.nextInt();
                boolean cambio_auto = doc_Vehiculos.nextBoolean();

                vehiculos[i] = new Hibrido(matricula, km, ppd, modelo, autonomia, cambio_auto);
            } else {
                if (type == 'c' || type == 'd' || type == 'm') {
                    String matricula = doc_Vehiculos.next();
                    int km = doc_Vehiculos.nextInt();
                    int ppd = doc_Vehiculos.nextInt();
                    String modelo = doc_Vehiculos.next();
                    boolean AC = doc_Vehiculos.nextBoolean();

                    vehiculos[i] = new NoHibridos(matricula, km, ppd, modelo, AC);
                } else {
                    System.out.println("No es un tipo válido de vehículo");
                }
            }

            i++;
        } //while

        doc_Vehiculos.close();
        return vehiculos;
    } //cargar_matriz_vehiculos

    //Creará el menú que mostrará todas las opciones al usuario.
    private static void menu(Concesionario c)  {
        int opcion = 1;
        Scanner sc = new Scanner(System.in);
        while (opcion != 0) {
            System.out.println("¡Bienvenid@ al menú principal de su concesionario. Por favor, elija una opción: ");
            System.out.println("[1] Consultar todos los vehiculos de la base de datos.");
            System.out.println("[2] Consultar todos los contratos de un cliente.");
            System.out.println("[3] Alquilar un vehiculo.");
            System.out.println("[4] Consultar vehiculos que tiene alquilado un cliente");
            System.out.println("[5] Consultar si cierto cliente es VIP.");
            System.out.println("[6] Consultar los descuentos de cierto cliente.\n");
            System.out.println("[0] Salir.\n");

            opcion = 1;
            try {
                opcion = sc.nextInt();

                switch (opcion) {
                    case 1:
                        System.out.println(c.ver_vehiculos());
                        break;
                    case 2:
                        System.out.println("Introduzca el DNI del cliente del que desea consultar los contratos: ");
                        String dni = sc.next();
                        try {
                            comprobarDNI(dni);
                        } catch (Cliente.NoEsLetraexcepcion noEsLetraexcepcion) {
                            noEsLetraexcepcion.printStackTrace();
                        }
                        if (c.esCliente(dni)) {
                            System.out.println(c.consultar_cliente(dni).get_contratos());
                        } else
                            System.out.println("Este DNI no se encuentra registrado en nuestra base de datos");
                        break;
                    case 3:
                        try {
                            c.alquilar_vehiculo(c);
                        } catch (Cliente.NoEsLetraexcepcion noEsLetraexcepcion) {
                            noEsLetraexcepcion.printStackTrace();
                        }
                        break;
                    case 4:
                        System.out.println("Introduzca el DNI del cliente del que desea consultar los vehículos: ");
                        String dni_checking = sc.next();
                        try {
                            comprobarDNI(dni_checking);
                        } catch (Cliente.NoEsLetraexcepcion noEsLetraexcepcion) {
                            noEsLetraexcepcion.printStackTrace();
                        }
                        if (c.esCliente(dni_checking))
                            System.out.println(c.consultar_cliente(dni_checking).get_vehiculos());
                        else
                            System.out.println("Este DNI no se encuentra registrado en nuestra base de datos");
                        break;
                    case 5:
                        System.out.println("Introduzca el DNI del cliente que desea consultar su estado: ");
                        String check_dni = sc.next();
                        try {
                            comprobarDNI(check_dni);
                        } catch (Cliente.NoEsLetraexcepcion noEsLetraexcepcion) {
                            noEsLetraexcepcion.printStackTrace();
                        }
                        if (c.esCliente(check_dni)) {

                            if (c.consultar_vip(check_dni))
                                System.out.println("El cliente con DNI " + check_dni + " es VIP.");
                            else
                                System.out.println("El cliente con DNI " + check_dni + " no es VIP.");
                        } else
                            System.out.println("Este cliente no está registrado en la base de datos.");
                        break;
                    case 6:
                        System.out.println("Introduzca el DNI del cliente del que desea consultar los descuentos: ");
                        String dni_check = sc.next();
                        try {
                            comprobarDNI(dni_check);
                        } catch (Cliente.NoEsLetraexcepcion noEsLetraexcepcion) {
                            noEsLetraexcepcion.printStackTrace();
                        }
                        if (c.esCliente(dni_check)) {
                            System.out.println("Dicho cliente tiene un descuento del " + c.consultar_cliente(dni_check).get_descuentos() + "%");
                        } else
                            System.out.println("Este cliente no se encuentra en la base de datos");
                        break;
                    default:
                        while (opcion < 0 || opcion > 6) {
                            System.out.println("No es una opcion del menú. Por favor, inténtelo de nuevo:");
                            opcion = sc.nextInt();
                        }
                        break;
                }//switch
            }catch(InputMismatchException e){
                System.out.println("Debe de tener un valor numérico.");
                sc.next();
            }
        }//while
    }//menu

    //Nos comprueba que el dni cumple el formato
    public static void comprobarDNI(String nif) throws Cliente.NoEsLetraexcepcion{
        if(nif.length()!=9) {
            throw new Cliente.NoEsLetraexcepcion("Longitud incorrecta");

        }
        for (int i=0;i<nif.length();i++) {
            if (i<=nif.length()-2) {
                if(!(nif.charAt(i)>='0'&&nif.charAt(i)<='9')){
                    throw new Cliente.NoEsLetraexcepcion("DNI incorrecto por los primeros numeros");
                }
            }else {
                if(!Character.isLetter(nif.charAt(8))){
                    throw new Cliente.NoEsLetraexcepcion("DNI incorrecto por la ultima letra");
                }
            }
        }
    }

}//class main
