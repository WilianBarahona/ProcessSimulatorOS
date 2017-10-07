package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import process.Procesos;
import process.ProcesosInvalido;


public class Main {
	private FileReader flujoLectura;
	private BufferedReader bRLectura;
	private String linea;
	private String cadenaInstrucciones;
	private List<String> ids= new LinkedList<String>();
	private List<Procesos> procesosPrioridad1= new LinkedList<Procesos>();
	private List<Procesos> procesosPrioridad2= new LinkedList<Procesos>();
	private List<Procesos> procesosPrioridad3= new LinkedList<Procesos>();
	private List<ProcesosInvalido> procesosInvalidos= new LinkedList<ProcesosInvalido>();
	private List<Procesos> segmentos= new LinkedList<Procesos>();
	
	public Main() {
		importarInstrucciones();
		//infoProcesosValidos();
	//	infoProcesosInvalidos();
		ejecutar();
	}

	
	public static void main(String[] args) {
		new Main();
		
	}
	public void importarInstrucciones(){
		
		cadenaInstrucciones="";
		/*-----------Para usar el buscador de archivo descomentar las siguientes lineas------*/
        /*JFileChooser buscador= new JFileChooser();
        buscador.setApproveButtonText("Seleccionar");
        buscador.showOpenDialog(null);*/


        try{
           /*Para usar el buscador de archivos descomentar la siguiente linea y borrar la linea
           flujoLectura= new FileReader("resource/procesos.txt");*/
        	
           //flujoLectura= new FileReader(buscador.getSelectedFile());
           flujoLectura= new FileReader("resource/procesos.txt");
           bRLectura= new BufferedReader(flujoLectura);
           
           do {
        	   linea = bRLectura.readLine();
			if (linea!=null) {
				cadenaInstrucciones+=linea;	
			}
			
		} while (linea!=null);
   
          flujoLectura.close();
          
        }catch(Exception ex){

        }
        
        /*Enviar todas las intrucciones en una sola linea para poder eliminar posibles saltos de lineas
        entre instrucciones*/
        String partesInstruccion[]=cadenaInstrucciones.split(";");
		for (int i = 0; i < partesInstruccion.length; i++) {
			validarInstruccion(partesInstruccion[i]);
		}
		
	}
	
	public boolean validarInstruccion(String instrucciones){
		//System.out.println("\n"+instrucciones);
		//Eliminar todos los espacios
		
		boolean validacion;
		String instruccion=instrucciones.replace(" ", "");
		/*partesInstruccion[0]=id del proceso
		 *partesInstruccion[1]=estado del proceso
		 *partesInstruccion[2]=prioridad
		 *partesInstruccion[3]=cantidad de instrucciones
		 *partesInstruccion[4]=instruccion de bloqueo
		 *partesInstruccion[5]=evento que genera el bloqueo
		*/
		String partesInstruccion[]=instruccion.split("/");
	
		
		//Pattern pat = Pattern.compile("[0-9]{4}/[0-9]{1}/[0-9]{1}/[0-9]{3}/[0-9]{3}/[0-9]{1}");
		Pattern pat = Pattern.compile("[0-9]{4}/[0-4]{1}/[1-3]{1}/[0-9]{3}/[0-9]{3}/[3,5]{1}");
		Matcher mat = pat.matcher(instruccion);
		if (!mat.matches()) {
			//System.err.println("Instruccion invalida: " + instruccion +" error patron invalido");
			asignarProcesoInvalido(instruccion,"Error Patron invalido");
			validacion=false;
			
		} else {
			if(Integer.parseInt(partesInstruccion[4])>Integer.parseInt(partesInstruccion[3])) {
				//System.err.println("Instruccion invalida: " + instruccion + " error instruccion de bloqueo mayor a la cantidad de instrucciones");
				asignarProcesoInvalido(instruccion,"IntruccionDeBloque>CantidadDeInstrucciones");
				validacion=false;
			}else {
     			//System.out.println("Instruccion valida: " + instruccion);
				validacion=true;
				ordenar(partesInstruccion,instruccion);
			}
				
		}
		
		return validacion;
	}

	public void ordenar(String partes[],String instruccion) {
		//Validar id antes de meterlo a las listas de procesos
		String id =partes[0];
		ids.add(id);
		int contadorId=0;//si contadorId>=2 entonces el proceso no se podra meter en ninguna lista
		for (int i = 0; i < ids.size(); i++) {
			if (id.equals(ids.get(i))) {
				contadorId++;
			}
		}
		
		if(contadorId<=1){
			//Lista Prioridad 1 
			if(partes[2].equals("1")) {
				procesosPrioridad1.add(new Procesos(
						Integer.parseInt(partes[0]),
						Integer.parseInt(partes[1]),
						Integer.parseInt(partes[2]),
						Integer.parseInt(partes[3]),
						Integer.parseInt(partes[4]),
						Integer.parseInt(partes[5]),
						0
				 )); 
				
			}
			
			//Lista Prioridad 2
			if(partes[2].equals("2")) {
				procesosPrioridad2.add(new Procesos(
						Integer.parseInt(partes[0]),
						Integer.parseInt(partes[1]),
						Integer.parseInt(partes[2]),
						Integer.parseInt(partes[3]),
						Integer.parseInt(partes[4]),
						Integer.parseInt(partes[5]),
						0
				 )); 
			}
			
			//Lista Prioridad 3
			if(partes[2].equals("3")) {
				procesosPrioridad3.add(new Procesos(
						Integer.parseInt(partes[0]),
						Integer.parseInt(partes[1]),
						Integer.parseInt(partes[2]),
						Integer.parseInt(partes[3]),
						Integer.parseInt(partes[4]),
						Integer.parseInt(partes[5]),
						0
				 )); 
			}
		}else{
			asignarProcesoInvalido(instruccion,"id repetido");
		}
		
		
		
		/*Collections.sort(procesoValidos, new Comparator<Procesos>() {

	        public int compare(Procesos p1, Procesos p2) {
	            return p1.getPrioridad() - p2.getPrioridad();
	        }
	    });*/
		
	}
	
	
	
	public void infoProcesosValidos(){
		System.out.println("\n"+"------Prioridad 1-------"+"\n");
		for (int i = 0; i < procesosPrioridad1.size(); i++) {
			System.out.println(procesosPrioridad1.get(i));
		}
		System.out.println("\n"+"------Prioridad 2-------"+"\n");
		for (int i = 0; i < procesosPrioridad2.size(); i++) {
			System.out.println(procesosPrioridad2.get(i));
		}
		System.out.println("\n"+"------Prioridad 3-------"+"\n");
		for (int i = 0; i < procesosPrioridad3.size(); i++) {
			System.out.println(procesosPrioridad3.get(i));
		}
	}
	
	public void asignarProcesoInvalido(String instruccion,String infoAdicional){
		procesosInvalidos.add(new ProcesosInvalido(instruccion,infoAdicional)); 
	}
	
	public void infoProcesosInvalidos(){
		System.out.println("\n---------------Procesos Invalidos------------------\n");
		for (int i = 0; i < procesosInvalidos.size(); i++) {
			System.err.println(procesosInvalidos.get(i).toString());
		}
		
	}
	public void ejecutar() {
		System.out.println("Ejecutando el metodo ejecutar de la lista 1\n");
		int contador = 0;
		int b = 0;
		while(!procesosPrioridad1.isEmpty()) {
			Procesos a=procesosPrioridad1.remove(contador);
			if(a.getInfoAdicional()>=0 && a.getInfoAdicional()<=4) {
				//Busqueda en segmentos (arreglo que contiene los que han entrado al FOR osea disminucion de instrucciones)
				if(buscar(a,segmentos)==false) {
					//No ha entrado al ciclo FOR
					b= a.getInfoAdicional()+1;
					a.setInfoAdicional(b);
					int instrucciones = a.getCantidadInstrucciones();
					if(instrucciones > 0) {
						for(int i=0;i<5;i++) {
							instrucciones--;
						}
						a.setCantidadInstrucciones(instrucciones);
						System.out.println(a);
						agregar(a.getIdProceso(), a.getEstadoProceso(), a.getPrioridad(), a.getCantidadInstrucciones(), a.getBloqueadoProceso(),
								a.getEventoEspera(), a.getInfoAdicional(), procesosPrioridad1);
						segmentos.add(a);
					}
				}
			}
			else {
				//Bajar la prioridad
				int c = a.getPrioridad()+1;
				a.setPrioridad(c);
				agregar(a.getIdProceso(), a.getEstadoProceso(), a.getPrioridad(), a.getCantidadInstrucciones(), a.getBloqueadoProceso(),
						a.getEventoEspera(), a.getInfoAdicional(), procesosPrioridad2);
			}
			
		}
		contador++;
		
	}
	
	
	
	public boolean buscar(Procesos a, List<Procesos> segmentos) {
		return segmentos.contains(a);
		}	
	public void agregar(int id,int estado, int prioridad,int cantidad,int instrucciones,int evento, int partes, List<Procesos> procesosPrioridad) {
		procesosPrioridad.add(new Procesos(id,estado,prioridad,cantidad,instrucciones,evento,partes));
	}
}

	
