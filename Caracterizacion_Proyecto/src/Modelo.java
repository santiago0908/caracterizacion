import ilog.concert.*;
import ilog.cplex.*;

public class Modelo {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		crearModelo();
		
	}
	
	public static void crearModelo() {
	
		int n = 3; // i edificios (330) 
		int m = 3; // j usos
		int c = 2; // k comunas (19)
		double[][]B = new double[n][m];
		
		B[0][0] = 10;
		B[0][1] = 0;
		B[0][2] = 0;
		B[1][0] = 0;
		B[1][1] = 4;
		B[1][2] = 8;
		B[2][0] = 0;
		B[2][1] = 5;
		B[2][2] = 10;
		
	try {
		@SuppressWarnings("resource")
		IloCplex modelo1 = new IloCplex();
		
		// Variables
		
//		IloNumVar[][][] x = new IloNumVar[c][n][m];
//	     for (int i = 0; i < c; i++){
//	        for (int j = 0; j < n; j++){
//	        	for (int k = 0; k < m; k++) {
//				x[i][j][k]=modelo1.intVar(0, Integer.MAX_VALUE);
//				}
//	        }     
//	     }
	     
	     IloNumVar[][] x = new IloNumVar[n][m];
	     for (int i = 0; i < n; i++){
	        for (int j = 0; j < m; j++){
				x[i][j]=modelo1.intVar(0, Integer.MAX_VALUE);
	        }     
	     }
	     
	     
//	     IloNumVar[][] y = new IloNumVar[n][m];
//	     for (int i = 0; i < n; i++) {
//	    	 for (int j = 0; j < m; j++) {
//	    	 y[i] = modelo1.boolVarArray(m);
//		}
//	    }
	     
	     
		
		// Función Objetivo
//		IloLinearNumExpr objective = modelo1.linearNumExpr();
//		for (int i = 0; i < c; i++){
//	        for (int j = 0; j < n; j++){
//	        	for (int k = 0; k < m; k++) {
//	        		objective.addTerm(1, x[i][j][k]);
//				}
//	        }     
//	     }
		
		IloLinearNumExpr objective = modelo1.linearNumExpr();
		for (int i = 0; i < n; i++){
	        for (int j = 0; j < m; j++){
	        		objective.addTerm(1, x[i][j]);
				
	        }     
	     }
		
		// Definición de objetivo
		
		modelo1.addMaximize(objective);
		
		
		// Restricciones
		
		IloLinearNumExpr[] capacidad = new IloLinearNumExpr[n];
		for (int i = 0; i < m; i++) {
			capacidad[i]=modelo1.linearNumExpr();
			for (int j = 0; j < n; j++) {
				capacidad[i].addTerm(1.0, x[i][j]);
			}
		}
		
		for (int i = 0; i < n; i++) {
		for (int j = 0; j < m; j++) {
			//modelo1.addLe(capacidad[i], modelo1.prod(B[i][j], y[i][j]));
			modelo1.addLe(capacidad[i], B[i][j]);
			
			}
		}
		
		
		// Resolver
		//modelo1.setParam(IloCplex.IntParam.SimDisplay,0);
		
		if (modelo1.solve()) {
			System.out.println("obj "+modelo1.getObjValue());
			
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < m; j++) {
					
					System.out.println("x[" + i + ","+j + "] ="+
				modelo1.getValue(x[i][j]));
				}
				}
		} 
		
		
	} catch (IloException exc) {
		// TODO: handle exception
		exc.printStackTrace();
	}
	}

}
