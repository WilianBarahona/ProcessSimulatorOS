package process;


public class Procesos {
	private int idProceso;
	private int estadoProceso;
	private int prioridad;
	private int cantidadInstrucciones;
	private int instruccionBloqueo;
	private int eventoBloqueo;
	private int infoAdicional;
	
	public Procesos() {
		super();
		}

	public Procesos(int idProceso,
			int estadoProceso, 
			int prioridad,
			int cantidadInstrucciones,
			int bloqueadoProceso,
			int eventoEspera,
			int infoAdicional) {
		super();
		this.idProceso = idProceso;
		this.estadoProceso = estadoProceso;
		this.prioridad = prioridad;
		this.cantidadInstrucciones = cantidadInstrucciones;
		this.instruccionBloqueo = bloqueadoProceso;
		this.eventoBloqueo = eventoEspera;
		this.infoAdicional=infoAdicional;
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
		return instruccionBloqueo;
	}
	public void setBloqueadoProceso(int bloqueadoProceso) {
		this.instruccionBloqueo = bloqueadoProceso;
	}
	public int getEventoEspera() {
		return eventoBloqueo;
	}
	public void setEventoEspera(int eventoEspera) {
		this.eventoBloqueo = eventoEspera;
	}
	
	public int getEventoBloqueo() {
		return eventoBloqueo;
	}

	public void setEventoBloqueo(int eventoBloqueo) {
		this.eventoBloqueo = eventoBloqueo;
	}


	public int getInfoAdicional() {
		return infoAdicional;
	}

	public void setInfoAdicional(int infoAdicional) {
		this.infoAdicional = infoAdicional;
	}

	@Override
	public String toString() {
		return "Proceso [idProceso=" + idProceso + ", estadoProceso=" + estadoProceso + ", prioridad=" + prioridad
				+ ", cantidadInstrucciones=" + cantidadInstrucciones + ", bloqueadoProceso=" + instruccionBloqueo
				+ ", eventoEspera=" + eventoBloqueo + ", infoAdicional=" + infoAdicional+"]" + "\n";
	}
	


}
