package javaapplication3;
import java.util.Arrays;
import java.util.Scanner;
import javax.swing.JOptionPane;


public class JavaApplication3 {

  
    public static void main(String[] args) {
         Scanner sc = new Scanner(System.in);

        // Pedir al usuario que ingrese el número de procesos
        int n =  Integer.parseInt(JOptionPane.showInputDialog(null,"Ingresa el número de procesos: ")); 
        // Declarar arrays para almacenar los tiempos de ráfaga, espera y retorno
        int[] burstTime = new int[n];
        int[] waitingTime = new int[n];
        int[] turnaroundTime = new int[n]; 
        // Pedir al usuario que ingrese el tiempo de ráfaga de cada proceso
        System.out.println("Ingresa el tiempo de ráfaga de cada proceso: ");
        for(int i = 0; i < n; i++) {
            System.out.print("Proceso " + (i+1) + ": ");
            burstTime[i] = Integer.parseInt(JOptionPane.showInputDialog(null,"Ingresa el tiempo de ráfaga de cada proceso:"));
        }    
        // Pedir al usuario que ingrese el tamaño del quantum

        System.out.print("Ingresa el tamaño del quantum: ");
        int quantum = Integer.parseInt(JOptionPane.showInputDialog(null,"Ingresa el tamaño del quantum:"));
        // Verificar que el número de procesos y el tamaño del quantum sean mayores que cero
        if (n <= 0 || quantum <= 0) {
           JOptionPane.showMessageDialog(null,"El quantum no puede ser menor a 0 " );
            sc.close();
            return;
        }
//Se declara un arreglo adicional llamado remainingTime (tiempo restante) 
//y se inicializa con los valores del arreglo burstTime. Este arreglo se utiliza para realizar un seguimiento del tiempo restante de cada proceso a medida que se ejecuta el algoritmo.

        int[] remainingTime = new int[n];
         for(int i = 0; i < n; i++) {
            remainingTime[i] = burstTime[i];
        }      
//Se inicializa la variable time con cero. Esta variable se utiliza para realizar un seguimiento 
//del tiempo transcurrido durante la ejecución del algoritmo.

        int time = 0;
        while(true) {//Se inicia un bucle while que se ejecuta continuamente hasta que todos los procesos hayan finalizado.
            boolean done = true;//Dentro del bucle, se establece la variable booleana done en true. Esto se utiliza para verificar si todos los procesos han finalizado en cada ronda.
            for(int i = 0; i < n; i++) {
                if(remainingTime[i] > 0) {
                    done = false;
                    if(remainingTime[i] > quantum) {
                        time += quantum; // i el tiempo restante es mayor que el quantum, se incrementa time por el valor del quantum y se reduce el tiempo restante del proceso en el quantum.
                        remainingTime[i] -= quantum;// Si el tiempo restante es menor o igual que el quantum, se incrementa time por el valor del tiempo restante del proceso y se establece el tiempo de espera del proceso restando su tiempo de ráfaga al tiempo actual.
                    }
                    else {
                        time += remainingTime[i]; //Se incrementa el valor de time sumándole el tiempo restante del proceso , esto representa el momento en el que el proceso termina su ejecución
                        waitingTime[i] = time - burstTime[i];//Se calcula el tiempo de espera del proceso i restando su tiempo de ráfaga original (burstTime) al valor actual de time. El tiempo de espera es el tiempo total que el proceso ha esperado en la cola antes de poder ejecutarse.
                        remainingTime[i] = 0;//Se establece el tiempo restante del proceso i en cero para indicar que ha finalizado.
                    }
                }
            }
            //Después del bucle for, se verifica si done es true. Si es así, significa que todos los procesos han finalizado y se sale del bucle while.
            if(done == true) {
                break;
            }
        }
//Se calcula el tiempo de retorno para cada proceso sumando su tiempo de ráfaga y tiempo de espera.

        for(int i = 0; i < n; i++) {
            turnaroundTime[i] = burstTime[i] + waitingTime[i];
        } 
           
        for(int i = 0; i < n; i++) {
            JOptionPane.showMessageDialog(null,"Proceso: " + (i+1)+"\n" + "Tiempo de rafaga: " + burstTime[i] +"\n"+ "Tiempo de espera: " + waitingTime[i] +"\n"+ "Tiempo de retorno: " + turnaroundTime[i]);
        }
  // Después de imprimir la tabla con los tiempos de ráfaga, espera y retorno para cada proceso


// Generar el diagrama de Gantt
        StringBuilder ganttBuilder = new StringBuilder();
        ganttBuilder.append("Diagrama de Gantt:\n");
        
//Se declara una variable currentTime inicializada en 0 la variable se utiliza para llevar un seguimiento del tiempo actual 
        int currentTime = 0;
        
//Se inicia un bucle while que se ejecuta mientras haya algún proceso cuyo tiempo de ráfaga (burstTime) sea mayor que 0.
        while (Arrays.stream(burstTime).anyMatch(bt -> bt > 0)) {
            for (int i = 0; i < n; i++) {
                if (burstTime[i] > 0) {
                   
                    int executionTime = Math.min(quantum, burstTime[i]);//(El tiempo de ejecución es el mínimo )//garantiza que cada proceso se ejecute durante un tiempo máximo igual al tamaño del quantum o hasta que su tiempo de ráfaga se agote
                    currentTime += executionTime;//Se actualiza el currentTime sumando el tiempo de ejecución del proceso actual. Esto representa el momento en el que el proceso termina su ejecución.
                    burstTime[i] -= executionTime;//Se reduce el tiempo de ráfaga (burstTime) del proceso actual en el tiempo de ejecución.
                    
                    //se muestra el número de proceso y el tiempo actual en el que finaliza su ejecución.
                    ganttBuilder.append(" | P").append(i + 1).append(" ").append(currentTime);
                }
            }
        }
        
        JOptionPane.showMessageDialog(null, ganttBuilder.toString());

System.out.println();

        // Calcular y mostrar el tiempo promedio de espera y retorno
        float totalWaitingTime = 0; //Esta variable se utiliza para almacenar la suma total de los tiempos de espera de todos los procesos.
        float totalTurnaroundTime = 0; //Esta variable se utiliza para almacenar la suma total de los tiempos de retorno de todos los procesos.

        for(int i = 0; i < n; i++) { //Dentro del bucle, se acumula el tiempo de espera del proceso i en la variable 
            totalWaitingTime += waitingTime[i]; //se suman los valores acumulados
            totalTurnaroundTime += turnaroundTime[i];//se suman los valores acumulados
        } 
        // se imprime el tiempo promedio de retorno dividiendo (totalTurnaroundTime ,totalWaitingTime) entre n 
         JOptionPane.showMessageDialog(null,"Tiempo promedio de espera: " + (totalWaitingTime/n));
          JOptionPane.showMessageDialog(null,"Tiempo promedio de retorno: " + (totalTurnaroundTime/n));
        sc.close();
    }
}