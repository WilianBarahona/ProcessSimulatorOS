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
				System.out.println(linea);
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
	
	public boolean validarInstruccion(String instruccion){
		System.out.println("\n"+instruccion);
		Pattern pat = Pattern.compile("[0-9]{4}/[0-9]{1}/[0-9]{1}/[0-9]{3}/[0-9]{3}/[0-9]{1}");
		Matcher mat = pat.matcher(instruccion);
		if (!mat.matches()) {
			System.err.println("Instruccion invalida: " + instruccion);
			return false;
		} else {
			System.out.println("Instruccion Valida: " + instruccion);
			return true;
		}
	}
}

	
