package process;


public class Procesos {
	private int idProceso;
	private int estadoProceso;
	private int prioridad;
	private int cantidadInstrucciones;
	private int bloqueadoProceso;
	private int eventoEspera;
	
	public Procesos() {
		super();
		}

	public Procesos(int idProceso, int estadoProceso, int prioridad, int cantidadInstrucciones, int bloqueadoProceso,
			int eventoEspera) {
		super();
		this.idProceso = idProceso;
		this.estadoProceso = estadoProceso;
		this.prioridad = prioridad;
		this.cantidadInstrucciones = cantidadInstrucciones;
		this.bloqueadoProceso = bloqueadoProceso;
		this.eventoEspera = eventoEspera;
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
	@Override
	public String toString() {
		return "Proceso [idProceso=" + idProceso + ", estadoProceso=" + estadoProceso + ", prioridad=" + prioridad
				+ ", cantidadInstrucciones=" + cantidadInstrucciones + ", bloqueadoProceso=" + bloqueadoProceso
				+ ", eventoEspera=" + eventoEspera + "]" + "\n";
	}
	
//	//---------ORDENA LOS PROCESOS POR PRIORIDAD---------------------/
//	public int compareTo(Process o) {
//        if (prioridad > o.getPrioridad()) {
//            return 1;
//        } else if (prioridad < o.getPrioridad()) {
//            return -1;
//        } else {
//            return 0;
//        }
//    }
	

}
