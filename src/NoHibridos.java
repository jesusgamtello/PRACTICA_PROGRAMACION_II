
public class NoHibridos extends Vehiculo {
	private boolean AC;

	public NoHibridos(String matricula, int km, int preciodia, String modelo, boolean AC) {
		super(matricula, km, preciodia, modelo);
		this.AC = AC;
	}

	public boolean get_AC() {return AC;}
}
