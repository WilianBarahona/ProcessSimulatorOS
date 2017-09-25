package process;

public class ProcesosInvalido {
	private String proceso;
	private String infoAdicional;
	
	public ProcesosInvalido(){}

	public ProcesosInvalido(String proceso, String infoAdicional) {
		super();
		this.proceso = proceso;
		this.infoAdicional = infoAdicional;
	}

	public String getProceso() {
		return proceso;
	}

	public void setProceso(String proceso) {
		this.proceso = proceso;
	}

	public String getInfoAdicional() {
		return infoAdicional;
	}

	public void setInfoAdicional(String infoAdicional) {
		this.infoAdicional = infoAdicional;
	}

	@Override
	public String toString() {
		return "ProcesoInvalido [proceso=" + proceso + ", infoAdicional=" + infoAdicional + "]";
	}
	
	
}
