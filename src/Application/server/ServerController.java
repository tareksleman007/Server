package Application.server;
// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

/**
 * This class overrides some of the methods in the abstract 
 * superclass in order to give more functionality to the Application.server.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 * @version July 2000
 */
public class ServerController extends AbstractServer {
  //Class variables *************************************************
  
  /**
   * The default port to listen on.
   */
  final public static int DEFAULT_PORT = 5555;
  private mysqlController sqlcontroller;
  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the echo Application.server.
   *
   * @param port The port number to connect on.
   */
  public ServerController(int port) {
    super(port);
  }

  
  //Instance methods ************************************************
  
  /**
   * This method is responsible for the creation of
   * the Application.server instance (there is no UI in this phase).
   *
   //* @param args[0] The port number to listen on.  Defaults to 5555
   *          if no argument is entered.
   */
  public static void main(String[] args) {
    int port = 0; //Port to listen on

    try {
        port = Integer.parseInt(args[0]); //Get port from command line
    }
    catch(Throwable t) {
        port = DEFAULT_PORT; //Set port to 5555
    }

    ServerController sv = new ServerController(port);

    try {
        sv.listen(); //Start listening for connections
    }
    catch (Exception ex) {
        System.out.println("ERROR - Could not listen for clients!");
    }
  }

  
  /**
   * This method overrides the one in the superclass.  Called
   * when the Application.server starts listening for connections.
   */
  protected void serverStarted() {
      System.out.println("Server listening for connections on port " + getPort());
      sqlcontroller = new mysqlController();
  }
  
  //Class methods ***************************************************
  
  /**
   * This method overrides the one in the superclass.  Called
   * when the Application.server stops listening for connections.
   */
  protected void serverStopped() {
    System.out.println("Server has stopped listening for connections.");
  }

    /**
     * This method handles any messages received from the client.
     *
     * @param msg The message received from the client.
     * @param client The connection from which the message originated.
     */
    // TODO: add check if arguments are null or the index doesnt exist
    // TODO: add client array or map so the server could communicate back with the correct client

    public void handleMessageFromClient(Object msg, ConnectionToClient client) {
        System.out.println("Message received: " + msg + " from " + client);

        String message = (String)msg;
        String[] queryArgs = message.split(" ");

        switch (queryArgs[0]){
            case "newUser":
                sqlcontroller.addUser(queryArgs[1], queryArgs[2], queryArgs[3],queryArgs[4],queryArgs[5],queryArgs[6],queryArgs[7]);
                break;

            case "checkExists":
                sqlcontroller.checkUserExists(queryArgs[3], queryArgs[4]);

            case "updateUser":
                sqlcontroller.checkUserExists(queryArgs[3], queryArgs[4]);
        }

        //this.sendToAllClients(msg);
    }

}
//End of EchoServer class
