package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFileChooser;

public class Main {
	private FileReader flujoLectura;
	private BufferedReader bRLectura;
	private String linea;
	
	public Main() {
		importarInstrucciones();
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
			System.err.println("Instruccion invalida: " + instruccion);
			validacion=false;
		} else {
			if(Integer.parseInt(partesInstruccion[4])>Integer.parseInt(partesInstruccion[3])) {
				System.err.println("Instruccion invalida: " + instruccion);
				validacion=false;
			}else {
				System.out.println("Instruccion valida: " + instruccion);
				validacion=true;
			}
				
		}
		
		return validacion;
	}
}

	
