package org.example.RPC.client;

import org.example.RPC.client.Calculator;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    public static void main(String[] args) {
        try {
            String serverIP="10.100.200.147";
            Registry registry= LocateRegistry.getRegistry(serverIP,1099);
            Calculator stub= (Calculator) registry.lookup("CalculatorService");

            int result= stub.add(3,107);
            System.out.println("The result is-"+result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
//IP Details For: 103.221.253.161