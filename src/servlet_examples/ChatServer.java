package servlet_examples;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatServer extends Remote {
  public void addClient(ChatClient client) throws RemoteException;
  public void broadcastMessage(String message) throws RemoteException;
  public void deleteClient(ChatClient client) throws RemoteException;
  public String getNextMessage() throws RemoteException;
}
