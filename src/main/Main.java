package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JFileChooser;
import process.Procesos;


public class Main {
	private FileReader flujoLectura;
	private BufferedReader bRLectura;
	private String linea;
	private List<String> ids= new LinkedList<String>();
	private List<Procesos> procesosPrioridad1= new LinkedList<Procesos>();
	private List<Procesos> procesosPrioridad2= new LinkedList<Procesos>();
	private List<Procesos> procesosPrioridad3= new LinkedList<Procesos>();
	public Main() {
		importarInstrucciones();
		infoProcesos();
		
	}

	
	public static void main(String[] args) {
		new Main();
		
	}
	public void importarInstrucciones(){
		
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
				//System.out.println(linea);
				String partesInstruccion[]=linea.split(";");
				for (int i = 0; i < partesInstruccion.length; i++) {
					validarInstruccion(partesInstruccion[i]);
				}
				
			}
			
		} while (linea!=null);
   
          flujoLectura.close();
          
        }catch(Exception ex){

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
			validacion=false;
		} else {
			if(Integer.parseInt(partesInstruccion[4])>Integer.parseInt(partesInstruccion[3])) {
				//System.err.println("Instruccion invalida: " + instruccion + " error instruccion de bloqueo mayor a la cantidad de instrucciones");
				validacion=false;
			}else {
//				System.out.println("Instruccion valida: " + instruccion);
				validacion=true;
				ordenar(partesInstruccion);
			}
				
		}
		
		return validacion;
	}

	public void ordenar(String partes[]) {
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
						Integer.parseInt(partes[5])
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
						Integer.parseInt(partes[5])
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
						Integer.parseInt(partes[5])
				 )); 
			}
		}
		
		
		
		/*Collections.sort(procesoValidos, new Comparator<Procesos>() {

	        public int compare(Procesos p1, Procesos p2) {
	            return p1.getPrioridad() - p2.getPrioridad();
	        }
	    });*/
		
	}
	
	
	
	public void infoProcesos(){
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
	
}

	
