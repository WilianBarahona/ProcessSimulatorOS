package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import process.Procesos;
import process.ProcesosInvalido;


public class Main {
	private FileReader flujoLectura;
	private BufferedReader bRLectura;
	private String linea;
	private String cadenaInstrucciones;
	private int aceptarInstrucciones=10;//sirve para validar que solo hayan 10 procesos en el simulador
	
	//tiempo para relentizar el simulador en milisegundos
	private static final int TIEMPO_RETARDO_MS=250;
	
	
	//----lista de ids de los procesos que sirve para verificar ids unicos--
	private List<String> ids= new LinkedList<String>();
	
	//-------------TDA NUEVOS----------------
	private List<String> tdaNuevos=new LinkedList<String>();
	
	//-------------TDA LISTOS----------------
	private List<Procesos> tdaListosPrioridad1= new LinkedList<Procesos>();
	private List<Procesos> tdaListosPrioridad2= new LinkedList<Procesos>();
	private List<Procesos> tdaListosPrioridad3= new LinkedList<Procesos>();
	
	//----------TDA EJECUTANDO------------
	private List<Procesos> tdaEjecutandoPrioridad1=new LinkedList<Procesos>();
	private List<Procesos> tdaEjecutandoPrioridad2=new LinkedList<Procesos>();
	private List<Procesos> tdaEjecutandoPrioridad3=new LinkedList<Procesos>();
	
	//-----------TDA BLOQUEDO----------
	private List<Procesos> tdaBloqueadoPrioridad1=new LinkedList<Procesos>();
	private List<Procesos> tdaBloqueadoPrioridad2=new LinkedList<Procesos>();
	private List<Procesos> tdaBloqueadoPrioridad3=new LinkedList<Procesos>();
	
	//---------TDA TERMINANO-----------
	private List<Procesos> tdaTerminadoPrioridad1=new LinkedList<Procesos>();
	private List<Procesos> tdaTerminadoPrioridad2=new LinkedList<Procesos>();
	private List<Procesos> tdaTerminadoPrioridad3=new LinkedList<Procesos>();
	
	//-----------TDA PROCESOS INVALIDOS Y OTROS RECURSO NECESARIOS---------------------
	private List<ProcesosInvalido> procesosInvalidos= new LinkedList<ProcesosInvalido>();
	private List<Procesos> segmentos1= new LinkedList<Procesos>();
	private List<Procesos> segmentos2= new LinkedList<Procesos>();
	private List<Procesos> segmentos3= new LinkedList<Procesos>();

	public Main() throws InterruptedException {
		importarInstrucciones();
		infoNuevos();
		infoListos();
		ejecutar();
		infoListos();
		infoEjecutando();
		infoBloqueados();
		infoTerminados();
		
	}


	public static void main(String[] args) throws InterruptedException {
		new Main();

	}
	
	public void ejecutar() throws InterruptedException {
	    //System.out.println("Ejecutando el metodo ejecutar de la lista 1\n");
		 Scanner sc = new Scanner(System.in);
		 System.out.println("Cantidad de ciclos a ejecutar: ");
		 int ciclos = sc.nextInt();
	     int cantidad=0;
            while(cantidad<ciclos){
                int contador = 0;//remover cada elemento de la prioridad 1
                int b = 0;//actualizar cuantas veces se a entrado al ciclo
                moverTerminadoPrioridad1();
                moverTerminadoPrioridad2();
                moverTerminadoPrioridad3();
       	    	 if(!tdaListosPrioridad1.isEmpty()){
       	    		 Procesos a1=tdaListosPrioridad1.remove(contador);
       	    		if (tdaListosPrioridad1.size()==1) {
       	    			eliminarUltimoPrioridad1();
					 }
					moverTerminadoPrioridad1();
					
	                 	if(a1.getInfoAdicional()>=0 && a1.getInfoAdicional() < 3) {
	                        if(buscar(a1,segmentos1)==false) {
	                             //No ha entrado al ciclo FOR
							        int instrucciones = a1.getCantidadInstrucciones();
							        if (instrucciones==0) {
										a1.setEstadoProceso(4);
									}
							        a1.setEstadoProceso(2);
							        if(instrucciones > 0) {
							        	for(int i=0;i<5;i++) {
							        		Thread.sleep(TIEMPO_RETARDO_MS);
							        		if (i==4) {
							        			b= a1.getInfoAdicional()+1;
										        a1.setInfoAdicional(b);
											}
							        		a1.setCantidadInstrucciones(instrucciones);
							        		System.out.println("Prioridad1: "+a1.getIdProceso()+" Instruccion:"+a1.getCantidadInstrucciones());
							        		if (a1.getCantidadInstrucciones()==a1.getInstruccionBloqueo()) {
												a1.setEstadoProceso(3);
												if (tdaListosPrioridad1.size()==1) {
													eliminarUltimoPrioridad1();
												}else {
													moverEstadoBloqueadoPrioridad1();
												}
												
												i=5;//salir del ciclo
												instrucciones++;//dejar esta variable como estaba
											}
							        		
							        		
							        		ejecutarBloqueado();
							        		cantidad++;
							        		instrucciones--;
							        	}
							        	 
		                                 agregar(a1.getIdProceso(),
		                                		 a1.getEstadoProceso(), 
		                                		 a1.getPrioridad(), 
		                                		 a1.getCantidadInstrucciones(), 
		                                		 a1.getBloqueadoProceso(),
		                                		 a1.getEventoEspera(),
		                                		 a1.getInfoAdicional(),
		                                		 tdaListosPrioridad1);
		                                 segmentos1.add(a1);
								        }
	                         }
	                        for (int i = 0; i < tdaListosPrioridad1.size(); i++) {
		                            if(tdaListosPrioridad1.get(i).getInfoAdicional()>=3){
		                            	if(buscar(tdaListosPrioridad1.get(i),tdaListosPrioridad2)==false){
		                                    int c = tdaListosPrioridad1.get(i).getPrioridad()+1;
		                                    tdaListosPrioridad1.get(i).setPrioridad(c);
		                                    agregar(tdaListosPrioridad1.get(i).getIdProceso(), 
		                                    		tdaListosPrioridad1.get(i).getEstadoProceso(), 
		                                    		tdaListosPrioridad1.get(i).getPrioridad(), 
		                                    		tdaListosPrioridad1.get(i).getCantidadInstrucciones(),
		                                    		tdaListosPrioridad1.get(i).getBloqueadoProceso(),
		                                    		tdaListosPrioridad1.get(i).getEventoEspera(), 
		                                    		0, 
				                                    tdaListosPrioridad2);
		                                    tdaListosPrioridad1.remove(i);
	                             	}	
	                            }
						   }
	
                 }
	            //ejecutarBloqueadoPrioridad1();
                contador++;
    	    	 }else{
    	    		 if(!tdaListosPrioridad2.isEmpty()){
    	    				Procesos a2=tdaListosPrioridad2.remove(contador);
    	       	    		if (tdaListosPrioridad2.size()==1) {
    	       	    			eliminarUltimoPrioridad2();
    						 }
    						moverTerminadoPrioridad2();
    						
    		                 	if(a2.getInfoAdicional()>=0 && a2.getInfoAdicional() < 3) {
    		                        if(buscar(a2,segmentos2)==false) {
    		                             //No ha entrado al ciclo FOR
    								        int instrucciones = a2.getCantidadInstrucciones();
    								        if (instrucciones==0) {
												a2.setEstadoProceso(4);
											}
    								        a2.setEstadoProceso(2);
    								        if(instrucciones > 0) {
    								        	for(int i=0;i<5;i++) {
    								        		Thread.sleep(TIEMPO_RETARDO_MS);
    								        		if (i==4) {
    								        			b= a2.getInfoAdicional()+1;
    											        a2.setInfoAdicional(b);
    												}
    								        		a2.setCantidadInstrucciones(instrucciones);
    								        		System.out.println("Prioridad2: "+a2.getIdProceso()+" Instruccion:"+a2.getCantidadInstrucciones());
    								        		if (a2.getCantidadInstrucciones()==a2.getInstruccionBloqueo()) {
    													a2.setEstadoProceso(3);
    													if (tdaListosPrioridad2.size()==1) {
    														eliminarUltimoPrioridad2();
    													}else {
    														moverEstadoBloqueadoPrioridad2();
    													}
    													
    													i=5;//salir del ciclo
    													instrucciones++;//dejar esta variable como estaba
    												}
    								        		
    								        		
    								        		ejecutarBloqueado();
    								        		cantidad++;
    								        		instrucciones--;
    								        	}
    								        	 
    			                                 agregar(a2.getIdProceso(),
    			                                		 a2.getEstadoProceso(), 
    			                                		 a2.getPrioridad(), 
    			                                		 a2.getCantidadInstrucciones(), 
    			                                		 a2.getBloqueadoProceso(),
    			                                		 a2.getEventoEspera(),
    			                                		 a2.getInfoAdicional(),
    			                                		 tdaListosPrioridad2);
    			                                 segmentos2.add(a2);
    									        }
    		                         }
    		                        for (int i = 0; i < tdaListosPrioridad2.size(); i++) {
    			                            if(tdaListosPrioridad2.get(i).getInfoAdicional()>=3){
    			                            	if(buscar(tdaListosPrioridad2.get(i),tdaListosPrioridad3)==false){
    			                                    int c = tdaListosPrioridad2.get(i).getPrioridad()+1;
    			                                    tdaListosPrioridad2.get(i).setPrioridad(c);
    			                                    agregar(tdaListosPrioridad2.get(i).getIdProceso(), 
    			                                    		tdaListosPrioridad2.get(i).getEstadoProceso(), 
    			                                    		tdaListosPrioridad2.get(i).getPrioridad(), 
    			                                    		tdaListosPrioridad2.get(i).getCantidadInstrucciones(),
    			                                    		tdaListosPrioridad2.get(i).getBloqueadoProceso(),
    			                                    		tdaListosPrioridad2.get(i).getEventoEspera(), 
    			                                    		0, 
    					                                    tdaListosPrioridad3);
    			                                    tdaListosPrioridad2.remove(i);
    		                             	}	
    		                            }
    							   }
    		
    	                 }
    		             //ejecutarBloqueadoPrioridad2();
    	    			 contador++;
    	    		 }else{
    	    			 if (!tdaListosPrioridad3.isEmpty()) {
    	    				 Procesos a3=tdaListosPrioridad3.remove(contador);
    	        	    		if (tdaListosPrioridad3.size()==1) {
    	        	    			eliminarUltimoPrioridad3();
    	 					 }
    	 					moverTerminadoPrioridad3();
    	 	                        if(buscar(a3,segmentos3)==false) {
    	 	                             //No ha entrado al ciclo FOR
    	 							        int instrucciones = a3.getCantidadInstrucciones();
    	 							       if (instrucciones==0) {
												a3.setEstadoProceso(4);
											}
    	 							        a3.setEstadoProceso(2);
    	 							        if(instrucciones > 0) {
    	 							        	for(int i=0;i<5;i++) {
    	 							        		Thread.sleep(TIEMPO_RETARDO_MS);
    	 							        		if (i==4) {
    	 							        			b= a3.getInfoAdicional()+1;
    	 										        a3.setInfoAdicional(b);
    	 											}
    	 							        		a3.setCantidadInstrucciones(instrucciones);
    	 							        		System.out.println("Prioridad3: "+a3.getIdProceso()+" Instruccion:"+a3.getCantidadInstrucciones());
    	 							        		if (a3.getCantidadInstrucciones()==a3.getInstruccionBloqueo()) {
    	 												a3.setEstadoProceso(3);
    	 												if (tdaListosPrioridad3.size()==1) {
    	 													eliminarUltimoPrioridad3();
    	 												}else {
    	 													moverEstadoBloqueadoPrioridad3();
    	 												}
    	 												
    	 												i=5;//salir del ciclo
    	 												instrucciones++;//dejar esta variable como estaba
    	 											}
    	 							        		
    	 							        		
    	 							        		ejecutarBloqueado();
    	 							        		cantidad++;
    	 							        		instrucciones--;
    	 							        	}
    	 							        	 
    	 		                                 agregar(a3.getIdProceso(),
    	 		                                		 a3.getEstadoProceso(), 
    	 		                                		 a3.getPrioridad(), 
    	 		                                		 a3.getCantidadInstrucciones(), 
    	 		                                		 a3.getBloqueadoProceso(),
    	 		                                		 a3.getEventoEspera(),
    	 		                                		 a3.getInfoAdicional(),
    	 		                                		 tdaListosPrioridad3);
    	 		                                 segmentos3.add(a3);
    	 								        }
    	 	                         }
    	                  
    	 	             //ejecutarBloqueadoPrioridad3();
    	                 contador++;
    	    			 }
    	    		 }
    	    	 }
       	    	 
       	    	 if (tdaListosPrioridad1.isEmpty() && tdaListosPrioridad2.isEmpty() && tdaListosPrioridad3.isEmpty()) {
					cantidad=ciclos;//salir del ciclo while en caso de que las tres listas de prioridad este vacias
				}
                    
	         }

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
        
        //------------------ESTADO DEL SIMULADOR NUEVOS PROCESOS-------------------------
		for (int i = 0; i < partesInstruccion.length; i++) {
			tdaNuevos.add(partesInstruccion[i]);
		}
		
		//----VALIDAR PROCESOS SI EL PROCESO ES VALIDO PASARLO A ESTADO DEL SIMULADOR LISTOS------
		for (int i = 0; i < tdaNuevos.size(); i++) {
			validarInstruccion(tdaNuevos.get(i));
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
		if (aceptarInstrucciones>0) {
			//Validar id antes de meterlo a las listas de procesos
			
			//-----------VALIDACION DE ID UNICOS----------
			String id =partes[0];
			ids.add(id);
			int contadorId=0;//si contadorId>=2 entonces el proceso no se podra meter en ninguna lista
			for (int i = 0; i < ids.size(); i++) {
				if (id.equals(ids.get(i))) {
					contadorId++;
				}
			}

			//------------ESTADO DEL SIMULADOR PROCESOS LISTOS-------------------
			if(contadorId<=1){
				//Lista Prioridad 1
				if(partes[2].equals("1")) {
					tdaListosPrioridad1.add(new Procesos(
							Integer.parseInt(partes[0]),
							1,
							Integer.parseInt(partes[2]),
							Integer.parseInt(partes[3]),
							Integer.parseInt(partes[4]),
							Integer.parseInt(partes[5]),
							0
					 ));

				}

				//Lista Prioridad 2
				if(partes[2].equals("2")) {
					tdaListosPrioridad2.add(new Procesos(
							Integer.parseInt(partes[0]),
							1,
							Integer.parseInt(partes[2]),
							Integer.parseInt(partes[3]),
							Integer.parseInt(partes[4]),
							Integer.parseInt(partes[5]),
							0
					 ));
				}

				//Lista Prioridad 3
				if(partes[2].equals("3")) {
					tdaListosPrioridad3.add(new Procesos(
							Integer.parseInt(partes[0]),
							1,
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
		}
		
		aceptarInstrucciones--;



		/*Collections.sort(procesoValidos, new Comparator<Procesos>() {

	        public int compare(Procesos p1, Procesos p2) {
	            return p1.getPrioridad() - p2.getPrioridad();
	        }
	    });*/

	}

	public boolean buscar(Procesos a, List<Procesos> segmentos) {
		return segmentos.contains(a);
	}
	
	public void agregar(int id,int estado, int prioridad,int cantidad,int instrucciones,int evento, int partes, List<Procesos> procesosPrioridad) {
		procesosPrioridad.add(new Procesos(id,estado,prioridad,cantidad,instrucciones,evento,partes));
	}
	
	public void asignarProcesoInvalido(String instruccion,String infoAdicional){
		procesosInvalidos.add(new ProcesosInvalido(instruccion,infoAdicional));
	}
	
	
	
	//-------------INFORMACION DE LAS LISTAS --------------------
	public void infoNuevos(){
		System.out.println("-----INFO NUEVOS------------");
		for (int i = 0; i < tdaNuevos.size(); i++) {
			System.out.println((i+1)+". "+tdaNuevos.get(i)+"\n");
		}
	}
	
	public void infoListos(){
		System.out.println("-----INFO LISTOS-----------");
		System.out.println("\n"+"------Prioridad 1-------"+"\n");
		for (int i = 0; i < tdaListosPrioridad1.size(); i++) {
			System.out.println(tdaListosPrioridad1.get(i));
		}
		System.out.println("\n"+"------Prioridad 2-------"+"\n");
		for (int i = 0; i < tdaListosPrioridad2.size(); i++) {
			System.out.println(tdaListosPrioridad2.get(i));
		}
		System.out.println("\n"+"------Prioridad 3-------"+"\n");
		for (int i = 0; i < tdaListosPrioridad3.size(); i++) {
			System.out.println(tdaListosPrioridad3.get(i));
		}
	}
	
	public void infoEjecutando(){
		System.out.println("-----INFO EJECUTANDO-----------");
		for (int i = 0; i < tdaListosPrioridad1.size(); i++) {
			if(tdaListosPrioridad1.get(i).getEstadoProceso()==2) {
				tdaEjecutandoPrioridad1.add(tdaListosPrioridad1.get(i));
			}	
		}
		
		for (int i = 0; i < tdaListosPrioridad2.size(); i++) {
			if(tdaListosPrioridad2.get(i).getEstadoProceso()==2) {
				tdaEjecutandoPrioridad2.add(tdaListosPrioridad2.get(i));
			}	
		}
		
		for (int i = 0; i < tdaListosPrioridad3.size(); i++) {
			if(tdaListosPrioridad3.get(i).getEstadoProceso()==2) {
				tdaEjecutandoPrioridad3.add(tdaListosPrioridad3.get(i));
			}	
		}
		for (int i = 0; i < tdaEjecutandoPrioridad1.size(); i++) {
			System.out.println(tdaEjecutandoPrioridad1.get(i));
		}
		for (int i = 0; i < tdaEjecutandoPrioridad2.size(); i++) {
			System.out.println(tdaEjecutandoPrioridad2.get(i));
		}
		for (int i = 0; i < tdaEjecutandoPrioridad3.size(); i++) {
			System.out.println(tdaEjecutandoPrioridad3.get(i));
		}
	}
	
	public void infoBloqueados(){
		System.out.println("-----INFO BLOQUEADOS-----------");
		for (int i = 0; i < tdaBloqueadoPrioridad1.size(); i++) {
			System.out.println(tdaBloqueadoPrioridad1.get(i));
		}
		for (int i = 0; i < tdaBloqueadoPrioridad2.size(); i++) {
			System.out.println(tdaBloqueadoPrioridad2.get(i));
		}
		
		if (!tdaBloqueadoPrioridad3.isEmpty()) {
			for (int i = 0; i < tdaBloqueadoPrioridad3.size(); i++) {
				System.out.println(tdaBloqueadoPrioridad2.get(i));
			}
		}
		
	}
	
	public void infoTerminados(){
		System.out.println("-----INFO TERMINADOS----------");
		for (int i = 0; i < tdaTerminadoPrioridad1.size(); i++) {
			System.out.println(tdaTerminadoPrioridad1.get(i));
		}
		for (int i = 0; i < tdaTerminadoPrioridad2.size(); i++) {
			System.out.println(tdaTerminadoPrioridad2.get(i));
		}
		for (int i = 0; i < tdaTerminadoPrioridad3.size(); i++) {
			System.out.println(tdaTerminadoPrioridad3.get(i));
		}
	}
	
	
	
	public void moverEstadoBloqueadoPrioridad1(){
		for (int i = 0; i < tdaListosPrioridad1.size(); i++) {
            if(tdaListosPrioridad1.get(i).getEstadoProceso()==3){
            	if(buscar(tdaListosPrioridad1.get(i),tdaBloqueadoPrioridad1)==false){
                    agregar(tdaListosPrioridad1.get(i).getIdProceso(), 
                    		tdaListosPrioridad1.get(i).getEstadoProceso(), 
                    		tdaListosPrioridad1.get(i).getPrioridad(), 
                    		tdaListosPrioridad1.get(i).getCantidadInstrucciones(),
                    		0,
                    		tdaListosPrioridad1.get(i).getEventoEspera(), 
                    		tdaListosPrioridad1.get(i).getInfoAdicional(), 
                            tdaBloqueadoPrioridad1);
                    tdaListosPrioridad1.remove(i);
            	}	
            }
		}
	}
	
	public void ejecutarBloqueadoPrioridad1(){
		for (int i = 0; i <tdaBloqueadoPrioridad1.size(); i++) {
			if (tdaBloqueadoPrioridad1.get(i).getCantidadInstruccionesBloqueo()==0) {
				tdaBloqueadoPrioridad1.get(i).setInstruccionBloqueo(0);
				tdaBloqueadoPrioridad1.get(i).setEstadoProceso(2);
				Procesos p=tdaBloqueadoPrioridad1.remove(i);
				tdaListosPrioridad1.add(p);
				
			}else{
				tdaBloqueadoPrioridad1.get(i).setCantidadInstruccionesBloqueo((tdaBloqueadoPrioridad1.get(i).getCantidadInstruccionesBloqueo()-1));
			}
			
		}
	}
	
	public void eliminarUltimoPrioridad1(){
		if(tdaListosPrioridad1.get(0).getEstadoProceso()==3){
        	if(buscar(tdaListosPrioridad1.get(0),tdaBloqueadoPrioridad1)==false){
                agregar(tdaListosPrioridad1.get(0).getIdProceso(), 
                		tdaListosPrioridad1.get(0).getEstadoProceso(), 
                		tdaListosPrioridad1.get(0).getPrioridad(), 
                		tdaListosPrioridad1.get(0).getCantidadInstrucciones(),
                		tdaListosPrioridad1.get(0).getBloqueadoProceso(),
                		tdaListosPrioridad1.get(0).getEventoEspera(), 
                		tdaListosPrioridad1.get(0).getInfoAdicional(), 
                        tdaBloqueadoPrioridad1);
                tdaListosPrioridad1.remove(0);
     	}	
		
	   }
	}
	
	public void moverTerminadoPrioridad1(){
		for (int i = 0; i < tdaListosPrioridad1.size(); i++) {
			if (tdaListosPrioridad1.get(i).getCantidadInstrucciones()==0) {
				if(buscar(tdaListosPrioridad1.get(i),tdaTerminadoPrioridad1)==false){
	                agregar(tdaListosPrioridad1.get(i).getIdProceso(), 
	                		4, 
	                		tdaListosPrioridad1.get(i).getPrioridad(), 
	                		0,
	                		tdaListosPrioridad1.get(i).getBloqueadoProceso(),
	                		tdaListosPrioridad1.get(i).getEventoEspera(), 
	                		tdaListosPrioridad1.get(i).getInfoAdicional(), 
	                		tdaTerminadoPrioridad1);
	                tdaListosPrioridad1.remove(i);
	        	}	
			}
        	
		}
	}
	
	
	public void moverEstadoBloqueadoPrioridad2(){
		for (int i = 0; i < tdaListosPrioridad2.size(); i++) {
            if(tdaListosPrioridad2.get(i).getEstadoProceso()==3){
            	if(buscar(tdaListosPrioridad2.get(i),tdaBloqueadoPrioridad2)==false){
                    agregar(tdaListosPrioridad2.get(i).getIdProceso(), 
                    		tdaListosPrioridad2.get(i).getEstadoProceso(), 
                    		tdaListosPrioridad2.get(i).getPrioridad(), 
                    		tdaListosPrioridad2.get(i).getCantidadInstrucciones(),
                    		0,
                    		tdaListosPrioridad2.get(i).getEventoEspera(), 
                    		tdaListosPrioridad2.get(i).getInfoAdicional(), 
                            tdaBloqueadoPrioridad2);
                    tdaListosPrioridad2.remove(i);
            	}	
            }
		}
	}
	
	public void ejecutarBloqueadoPrioridad2(){
		for (int i = 0; i <tdaBloqueadoPrioridad2.size(); i++) {
			if (tdaBloqueadoPrioridad2.get(i).getCantidadInstruccionesBloqueo()==0) {
				tdaBloqueadoPrioridad2.get(i).setInstruccionBloqueo(0);
				tdaBloqueadoPrioridad2.get(i).setEstadoProceso(2);
				Procesos p=tdaBloqueadoPrioridad2.remove(i);
				tdaListosPrioridad2.add(p);
				
			}else{
				tdaBloqueadoPrioridad2.get(i).setCantidadInstruccionesBloqueo((tdaBloqueadoPrioridad2.get(i).getCantidadInstruccionesBloqueo()-1));
			}
			
		}
	}
	
	public void eliminarUltimoPrioridad2(){
		if(tdaListosPrioridad2.get(0).getEstadoProceso()==3){
        	if(buscar(tdaListosPrioridad2.get(0),tdaBloqueadoPrioridad2)==false){
                agregar(tdaListosPrioridad2.get(0).getIdProceso(), 
                		tdaListosPrioridad2.get(0).getEstadoProceso(), 
                		tdaListosPrioridad2.get(0).getPrioridad(), 
                		tdaListosPrioridad2.get(0).getCantidadInstrucciones(),
                		tdaListosPrioridad2.get(0).getBloqueadoProceso(),
                		tdaListosPrioridad2.get(0).getEventoEspera(), 
                		tdaListosPrioridad2.get(0).getInfoAdicional(), 
                        tdaBloqueadoPrioridad2);
                tdaListosPrioridad2.remove(0);
     	}	
		
	   }
	}
	
	public void moverTerminadoPrioridad2(){
		for (int i = 0; i < tdaListosPrioridad2.size(); i++) {
			if (tdaListosPrioridad2.get(i).getCantidadInstrucciones()==0) {
				if(buscar(tdaListosPrioridad2.get(i),tdaTerminadoPrioridad2)==false){
	                agregar(tdaListosPrioridad2.get(i).getIdProceso(), 
	                		4, 
	                		tdaListosPrioridad2.get(i).getPrioridad(), 
	                		0,
	                		tdaListosPrioridad2.get(i).getBloqueadoProceso(),
	                		tdaListosPrioridad2.get(i).getEventoEspera(), 
	                		tdaListosPrioridad2.get(i).getInfoAdicional(), 
	                		tdaTerminadoPrioridad2);
	                tdaListosPrioridad2.remove(i);
	        	}	
			}
        	
		}
	}
	
	
	public void moverEstadoBloqueadoPrioridad3(){
		for (int i = 0; i < tdaListosPrioridad3.size(); i++) {
            if(tdaListosPrioridad3.get(i).getEstadoProceso()==3){
            	if(buscar(tdaListosPrioridad3.get(i),tdaBloqueadoPrioridad3)==false){
                    agregar(tdaListosPrioridad3.get(i).getIdProceso(), 
                    		tdaListosPrioridad3.get(i).getEstadoProceso(), 
                    		tdaListosPrioridad3.get(i).getPrioridad(), 
                    		tdaListosPrioridad3.get(i).getCantidadInstrucciones(),
                    		tdaListosPrioridad3.get(i).getBloqueadoProceso(),
                    		tdaListosPrioridad3.get(i).getEventoEspera(), 
                    		tdaListosPrioridad3.get(i).getInfoAdicional(), 
                            tdaBloqueadoPrioridad3);
                    tdaListosPrioridad3.remove(i);
            	}	
            }
		}
	}
	
	public void ejecutarBloqueadoPrioridad3(){
		for (int i = 0; i <tdaBloqueadoPrioridad3.size(); i++) {
			if (tdaBloqueadoPrioridad3.get(i).getCantidadInstruccionesBloqueo()==0) {
				tdaBloqueadoPrioridad3.get(i).setInstruccionBloqueo(0);
				tdaBloqueadoPrioridad3.get(i).setEstadoProceso(2);
				Procesos p=tdaBloqueadoPrioridad3.remove(i);
				tdaListosPrioridad3.add(p);
				
			}else{
				tdaBloqueadoPrioridad3.get(i).setCantidadInstruccionesBloqueo((tdaBloqueadoPrioridad3.get(i).getCantidadInstruccionesBloqueo()-1));
			}
			
		}
	}
	
	public void ejecutarBloqueado(){
		for (int i = 0; i <tdaBloqueadoPrioridad1.size(); i++) {
			if (tdaBloqueadoPrioridad1.get(i).getCantidadInstruccionesBloqueo()==0) {
				tdaBloqueadoPrioridad1.get(i).setInstruccionBloqueo(0);
				tdaBloqueadoPrioridad1.get(i).setEstadoProceso(2);
				Procesos p=tdaBloqueadoPrioridad1.remove(i);
				tdaListosPrioridad1.add(p);
				
			}else{
				tdaBloqueadoPrioridad1.get(i).setCantidadInstruccionesBloqueo((tdaBloqueadoPrioridad1.get(i).getCantidadInstruccionesBloqueo()-1));
			}
			
		}
		for (int i = 0; i <tdaBloqueadoPrioridad2.size(); i++) {
			if (tdaBloqueadoPrioridad2.get(i).getCantidadInstruccionesBloqueo()==0) {
				tdaBloqueadoPrioridad2.get(i).setInstruccionBloqueo(0);
				tdaBloqueadoPrioridad2.get(i).setEstadoProceso(2);
				Procesos p=tdaBloqueadoPrioridad2.remove(i);
				tdaListosPrioridad2.add(p);
				
			}else{
				tdaBloqueadoPrioridad2.get(i).setCantidadInstruccionesBloqueo((tdaBloqueadoPrioridad2.get(i).getCantidadInstruccionesBloqueo()-1));
			}
			
		}
		
		for (int i = 0; i <tdaBloqueadoPrioridad3.size(); i++) {
			if (tdaBloqueadoPrioridad3.get(i).getCantidadInstruccionesBloqueo()==0) {
				tdaBloqueadoPrioridad3.get(i).setInstruccionBloqueo(0);
				tdaBloqueadoPrioridad3.get(i).setEstadoProceso(2);
				Procesos p=tdaBloqueadoPrioridad3.remove(i);
				tdaListosPrioridad3.add(p);
				
			}else{
				tdaBloqueadoPrioridad3.get(i).setCantidadInstruccionesBloqueo((tdaBloqueadoPrioridad3.get(i).getCantidadInstruccionesBloqueo()-1));
			}
			
		}
	}
	
	public void eliminarUltimoPrioridad3(){
		if(tdaListosPrioridad3.get(0).getEstadoProceso()==3){
        	if(buscar(tdaListosPrioridad3.get(0),tdaBloqueadoPrioridad3)==false){
                agregar(tdaListosPrioridad3.get(0).getIdProceso(), 
                		tdaListosPrioridad3.get(0).getEstadoProceso(), 
                		tdaListosPrioridad3.get(0).getPrioridad(), 
                		tdaListosPrioridad3.get(0).getCantidadInstrucciones(),
                		tdaListosPrioridad3.get(0).getBloqueadoProceso(),
                		tdaListosPrioridad3.get(0).getEventoEspera(), 
                		tdaListosPrioridad3.get(0).getInfoAdicional(), 
                        tdaBloqueadoPrioridad3);
                tdaListosPrioridad3.remove(0);
     	}	
		
	   }
	}
	
	public void moverTerminadoPrioridad3(){
		for (int i = 0; i < tdaListosPrioridad3.size(); i++) {
			if (tdaListosPrioridad3.get(i).getCantidadInstrucciones()==0) {
				if(buscar(tdaListosPrioridad3.get(i),tdaTerminadoPrioridad3)==false){
	                agregar(tdaListosPrioridad3.get(i).getIdProceso(), 
	                		4, 
	                		tdaListosPrioridad3.get(i).getPrioridad(), 
	                		0,
	                		tdaListosPrioridad3.get(i).getBloqueadoProceso(),
	                		tdaListosPrioridad3.get(i).getEventoEspera(), 
	                		tdaListosPrioridad3.get(i).getInfoAdicional(), 
	                		tdaTerminadoPrioridad3);
	                tdaListosPrioridad3.remove(i);
	        	}	
			}
        	
		}
	}
	
	
	public void infoProcesosInvalidos(){
		System.out.println("\n---------------Procesos Invalidos------------------\n");
		for (int i = 0; i < procesosInvalidos.size(); i++) {
			System.err.println(procesosInvalidos.get(i).toString());
		}

	}
}


