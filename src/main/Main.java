package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

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
		
        JFileChooser buscador= new JFileChooser();
        buscador.setApproveButtonText("Seleccionar");
        buscador.showOpenDialog(null);


        try{
           flujoLectura= new FileReader(buscador.getSelectedFile());
           bRLectura= new BufferedReader(flujoLectura);
           
           do {
        	   linea = bRLectura.readLine();
			if (linea!=null) 
				System.out.println(linea);
			
		} while (linea!=null);
   
          flujoLectura.close();
          
        }catch(Exception ex){

        }
	}
}

	
