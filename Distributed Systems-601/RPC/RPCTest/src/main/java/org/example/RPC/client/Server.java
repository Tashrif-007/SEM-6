package org.example.RPC.client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args) {
        try {
            Calculator stub = new CalculatorImpl();
            Registry registry = LocateRegistry.createRegistry(1099); // default port
            registry.rebind("CalculatorService", stub);
            System.out.println("Server ready...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
