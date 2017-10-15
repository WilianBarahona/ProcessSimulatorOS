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
	private Procesos procesoEjecutando;
	
	//tiempo para relentizar el simulador en milisegundos
	private static final int TIEMPO_RETARDO_MS=1000;
	private int ciclos;
	
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
	private List<Procesos> segmentos= new LinkedList<Procesos>();

	public Main() {
		importarInstrucciones();
		//infoListos();
		//infoProcesosInvalidos();
		//infoListos();
		ejecutar1();
		infoListos();
		
	}


	public static void main(String[] args) {
		new Main();

	}
	
	public void ejecutar1() {
	    //System.out.println("Ejecutando el metodo ejecutar de la lista 1\n");
		 Scanner sc = new Scanner(System.in);
		 System.out.println("Cantidad de ciclos a ejecutar: ");
		 int ciclos = sc.nextInt();
	    int cantidad=0;
            while(cantidad<ciclos){
                int contador = 0;//remover cada elemento de la prioridad 1
                int b = 0;//actualizar cuantas veces se a entrado al ciclo
          
       	    	 if(!tdaListosPrioridad1.isEmpty()){
       	    		 Procesos a=tdaListosPrioridad1.remove(contador);
	                 	if(a.getInfoAdicional()>=0 && a.getInfoAdicional() < 3) {
	                        if(buscar(a,segmentos)==false) {
	                             //No ha entrado al ciclo FOR
							        b= a.getInfoAdicional()+1;
							        a.setInfoAdicional(b);
							        int instrucciones = a.getCantidadInstrucciones();
							        if(instrucciones > 0) {
							        	for(int i=0;i<5;i++) {
							        		cantidad++;
	                     				instrucciones--;
	                                 }
	                     			a.setCantidadInstrucciones(instrucciones);
	                                 agregar(a.getIdProceso(), a.getEstadoProceso(), a.getPrioridad(), a.getCantidadInstrucciones(), a.getBloqueadoProceso(),
						                a.getEventoEspera(), a.getInfoAdicional(), tdaListosPrioridad1);
	                                 segmentos.add(a);
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
		                                    		tdaListosPrioridad1.get(i).getInfoAdicional(), 
				                                    tdaListosPrioridad2);
		                                    tdaListosPrioridad1.remove(i);
	                             	}	
	                            }
						   }
	
                 }
                contador++;
    	    	 }else{
    	    		 if(!tdaListosPrioridad2.isEmpty()){
    	    			 System.out.println("ejecutando lista 2");
    	    			 
    	    			 contador++;
    	    		 }else{
    	    			 if (!tdaListosPrioridad3.isEmpty()) {
    	    				 System.out.println("ejecutando lista 3");
        	    			 contador++;
    	    			 }
    	    		 }
    	    	 }
                    
                    
                    
                    
                    
                    
                    
                    if(!tdaListosPrioridad1.isEmpty()){
//                        Procesos a=tdaListosPrioridad1.remove(contador);
//
//                        	if(a.getInfoAdicional()>=0 && a.getInfoAdicional() < 3) {
//	                           if(buscar(a,segmentos)==false) {
//	                                //No ha entrado al ciclo FOR
//							        b= a.getInfoAdicional()+1;
//							        a.setInfoAdicional(b);
//							        int instrucciones = a.getCantidadInstrucciones();
//							        if(instrucciones > 0) {
//							        	for(int i=0;i<5;i++) {
//							        		cantidad++;
//                            				instrucciones--;
//	                                    }
//                            			a.setCantidadInstrucciones(instrucciones);
//	                                    agregar(a.getIdProceso(), a.getEstadoProceso(), a.getPrioridad(), a.getCantidadInstrucciones(), a.getBloqueadoProceso(),
//						                a.getEventoEspera(), a.getInfoAdicional(), tdaListosPrioridad1);
//	                                    segmentos.add(a);
//							        }
//	                            }
//	                           for (int i = 0; i < tdaListosPrioridad1.size(); i++) {
//		                            if(tdaListosPrioridad1.get(i).getInfoAdicional()>=3){
//		                            	if(buscar(tdaListosPrioridad1.get(i),tdaListosPrioridad2)==false){
//		                                    int c = tdaListosPrioridad1.get(i).getPrioridad()+1;
//		                                    tdaListosPrioridad1.get(i).setPrioridad(c);
//		                                    agregar(tdaListosPrioridad1.get(i).getIdProceso(), 
//		                                    		tdaListosPrioridad1.get(i).getEstadoProceso(), 
//		                                    		tdaListosPrioridad1.get(i).getPrioridad(), 
//		                                    		tdaListosPrioridad1.get(i).getCantidadInstrucciones(),
//		                                    		tdaListosPrioridad1.get(i).getBloqueadoProceso(),
//		                                    		tdaListosPrioridad1.get(i).getEventoEspera(), 
//		                                    		tdaListosPrioridad1.get(i).getInfoAdicional(), 
//				                                    tdaListosPrioridad2);
//		                                    tdaListosPrioridad1.remove(i);
//	                                	}	
//			                        }
//							   }
//
//	                    }
//                       contador++;
	                }
               
//                cantidad++;

	            }

	     }

	public void ejecutar() {
		
		 @SuppressWarnings("resource")
		 Scanner sc = new Scanner(System.in);
		 System.out.println("Cantidad de ciclos a ejecutar: ");
		 ciclos = sc.nextInt();
	     int cantidad=0;//variable para controlar ciclo principal while
	     int segmentoCiclos=5; /*variable que controla que se cumplan 5 ciclos por 
	     					cada segmento de ejecucion*/
	     
	     //variables que permiten tener el control de los indices de las listas de prioridades
    	 int contador1=0;//remover cada elemento de la prioridad 1
    	 int contador2=0;//remover cada elemento de la prioridad 2
    	 int contador3=0;//remover cada elemento de la prioridad 3
	     
	     //-----------ESTADO DEL SIMULADOR PROCESOS EJECUNTANDOSE--------------
	     while(cantidad<ciclos){
	    	 if(!tdaListosPrioridad1.isEmpty()){
//	    		 System.out.println(contador1);
//	    		 procesoEjecutando=new Procesos();
//	    		 procesoEjecutando=tdaListosPrioridad1.get(contador1);//obtener el proceso de la lista de prioridad 1
//	    		 procesoEjecutando.setEstadoProceso(2);//estado del proceso en ejecucion(2)
//	    		 
//	    		 if (procesoEjecutando.getSegmentosEjecutados()==3){
//					tdaListosPrioridad1.remove(contador1);
//				 }else {
//					 if(procesoEjecutando.getSegmentosEjecutados()>=0 && procesoEjecutando.getSegmentosEjecutados()<3){
//		    			 if (segmentoCiclos==0) {
//		    				 /*Al cumplir con un segmento de cinco ciclos hacer:
//		    				  *cambiar el contador1 en pocas palabras cambiar al siguiente proceso 
//		    				  *aumentar la cantidad de segmentos a dicho proceso
//		    				 */
//							segmentoCiclos=5;
//							procesoEjecutando.setSegmentosEjecutados(procesoEjecutando.getSegmentosEjecutados()+1);
//							
//							/*Decision necesaria para evitar una excepcion de indice=-1 de la lista
//							a cuyo indice no se puede acceder*/
//							 if (contador1==(tdaListosPrioridad1.size()-1)) {
//									contador1=0;
//									procesoEjecutando=tdaListosPrioridad1.get(contador1);//obtener el proceso de la lista de prioridad 1
//						    		procesoEjecutando.setEstadoProceso(2);//estado del proceso en ejecucion(2)
//							}else{
//								contador1++;
//								 procesoEjecutando=tdaListosPrioridad1.get(contador1);//obtener el proceso de la lista de prioridad 1
//					    		 procesoEjecutando.setEstadoProceso(2);//estado del proceso en ejecucion(2)
//							}
//							System.out.println("segmento completo");
//							 
//						 }
//		    			 
//		    			 if(procesoEjecutando.getCantidadInstrucciones()==procesoEjecutando.getInstruccionBloqueo()){
//		    				 procesoEjecutando.setEstadoProceso(3);//proceso bloquedado
//		    				 System.out.println("proceso prioridad 1 bloqueado");
//		    			 }
//		    	
//	    				 if (procesoEjecutando.getCantidadInstrucciones()>=0) {
//	    					 procesoEjecutando.setCantidadInstrucciones(procesoEjecutando.getCantidadInstrucciones()-1);
//	    					 System.out.println(procesoEjecutando.getCantidadInstrucciones());
//	    					 segmentoCiclos--;
//	    				}
//		    			 			
//		    		 }
//					
//				}
//	    		 
	    	 }else{
	    		 if(!tdaListosPrioridad2.isEmpty()){
	    			 /*System.out.println("trabajando lista 2");
	    			 procesoEjecutando=new Procesos();
		    		 procesoEjecutando=tdaListosPrioridad2.get(contador2);//obtener el proceso de la lista de prioridad 1
		    		 procesoEjecutando.setEstadoProceso(2);//estado del proceso en ejecucion(2)
		    		 
		    		 if (contador2==(tdaListosPrioridad2.size()-1)) {
							contador2=0;
							tdaListosPrioridad2.remove(contador2);
							
					}else{
						contador2++;
						tdaListosPrioridad2.remove(contador2);
					}*/
	    		 }else{
	    			 if (!tdaListosPrioridad3.isEmpty()) {
	    				 /*System.out.println("trabajando lista 3");
		    			 procesoEjecutando=new Procesos();
			    		 procesoEjecutando=tdaListosPrioridad3.get(contador3);//obtener el proceso de la lista de prioridad 1
			    		 procesoEjecutando.setEstadoProceso(2);//estado del proceso en ejecucion(2)
			    		 if (contador3==(tdaListosPrioridad3.size()-1)) {
								contador3=0;
								tdaListosPrioridad3.remove(contador3);
								
						}else{
							contador3++;
							tdaListosPrioridad3.remove(contador3);
						
					    }*/
	    			 }
	    		 }
	    	 }
           
	    	 cantidad++; 
            
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
	
	public void infoNuevos(){
		for (int i = 0; i < tdaNuevos.size(); i++) {
			System.out.println(tdaNuevos.get(i)+"\n");
		}
	}
	
	public void infoListos(){
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
		System.out.println("\n"+"------Procesos en estado ejecutando-------"+"\n");
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
		for (int i = 0; i < tdaBloqueadoPrioridad1.size(); i++) {
			System.out.println(tdaBloqueadoPrioridad1.get(i));
		}
		for (int i = 0; i < tdaBloqueadoPrioridad2.size(); i++) {
			System.out.println(tdaBloqueadoPrioridad2.get(i));
		}
		for (int i = 0; i < tdaBloqueadoPrioridad3.size(); i++) {
			System.out.println(tdaBloqueadoPrioridad2.get(i));
		}
	}
	
	public void infoTerminados(){
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
	
	public void infoProcesosInvalidos(){
		System.out.println("\n---------------Procesos Invalidos------------------\n");
		for (int i = 0; i < procesosInvalidos.size(); i++) {
			System.err.println(procesosInvalidos.get(i).toString());
		}

	}
}


