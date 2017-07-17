package process;


public class Process {
	private int idProceso;
	private int estadoProceso;
	private int prioridad;
	private int cantidadInstrucciones;
	private int bloqueadoProceso;
	private int eventoEspera;
	private int lectura;
	private int eventolectura;
	
	public Process(int idProceso, int estadoProceso, int prioridad, int cantidadInstrucciones, int bloqueadoProceso,
			int eventoEspera,int lectura,int eventolectura) {
		this.idProceso = idProceso;
		this.estadoProceso = estadoProceso;
		this.prioridad = prioridad;
		this.cantidadInstrucciones = cantidadInstrucciones;
		this.bloqueadoProceso = bloqueadoProceso;
		this.eventoEspera = eventoEspera;
		this.lectura = lectura;
		this.eventolectura = eventolectura;
	}
	
	public Process() {
	}

	public int getIdProceso() {
		return idProceso;
	}
	public void setIdProceso(int idProceso) {
		this.idProceso = idProceso;
	}
	public int getEstadoProceso() {
		return estadoProceso;
	}
	public void setEstadoProceso(int estadoProceso) {
		this.estadoProceso = estadoProceso;
	}
	public int getPrioridad() {
		return prioridad;
	}
	public void setPrioridad(int prioridad) {
		this.prioridad = prioridad;
	}
	public int getCantidadInstrucciones() {
		return cantidadInstrucciones;
	}
	public void setCantidadInstrucciones(int cantidadInstrucciones) {
		this.cantidadInstrucciones = cantidadInstrucciones;
	}
	public int getBloqueadoProceso() {
		return bloqueadoProceso;
	}
	public void setBloqueadoProceso(int bloqueadoProceso) {
		this.bloqueadoProceso = bloqueadoProceso;
	}
	public int getEventoEspera() {
		return eventoEspera;
	}
	public void setEventoEspera(int eventoEspera) {
		this.eventoEspera = eventoEspera;
	}
	public int getLectura() {
		return lectura;
	}
	public void setLectura(int lectura) {
		this.lectura = lectura;
	}
	public int getEventoLectura() {
		return eventolectura;
	}
	public void setEventoLectura(int eventolectura) {
		this.eventolectura = eventolectura;
	}
	
	
	@Override
	public String toString() {
		return  "Proceso: "+idProceso + " Estado: " + estadoProceso + " Prioridad: " + prioridad
				+ " C.Intrucciones: " + cantidadInstrucciones + " I.Bloqueo :" + bloqueadoProceso
				+ " E.Espera: " + eventoEspera +" U.Intruccion: "+lectura+ " C.I.Restante: "+eventolectura;
	}
	
	//---------ORDENA LOS PROCESOS POR PRIORIDAD---------------------/
	public int compareTo(Process o) {
        if (prioridad > o.getPrioridad()) {
            return 1;
        } else if (prioridad < o.getPrioridad()) {
            return -1;
        } else {
            return 0;
        }
    }
	
}
