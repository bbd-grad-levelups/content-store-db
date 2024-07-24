package com.example;

import org.apache.jackrabbit.jcr2dav.Jcr2davRepositoryFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import javax.jcr.*;
import javax.jcr.nodetype.NodeType;

public class App {
    public static void main(String[] args) throws Exception {

        Session session = accessRemoteRepo();
        createInitialData(session);

    }

    private static void createInitialData(Session session) throws Exception {

        Node rootNode = session.getRootNode();

        // Create the Cinema node and its properties
        Node cinemaNode = rootNode.addNode("cinema");
        cinemaNode.setProperty("name", "Ster-Kinekor");
        cinemaNode.setProperty("location", "Shop 1A");
        cinemaNode.setProperty("openingHours", "09:00 - 23:00");

        // Create the Godzilla node representing the movie and its properties
        Node godzillaNode = cinemaNode.addNode("godzilla");
        godzillaNode.setProperty("name", "Godzilla vs Kong");
        godzillaNode.setProperty("genre", "Action");
        godzillaNode.setProperty("runTime", 120);
        File godzillaTrailerFile = new File("data/test.mp4"); // TODO: Actual trailer
        uploadFile(session, godzillaNode, "trailer", godzillaTrailerFile, "video/mp4");
        File godzillaPosterFile = new File("data/test.jpg"); // TODO: Actual poster
        uploadFile(session, godzillaNode, "poster", godzillaPosterFile, "image/jpeg");

        // Create the Barbie node representing the movie and its properties
        Node barbieNode = cinemaNode.addNode("barbie");
        barbieNode.setProperty("name", "Barbie Movie");
        barbieNode.setProperty("genre", "Comedy");
        barbieNode.setProperty("runTime", 105);
        File barbieTailerFile = new File("data/test.mp4");// TODO: Actual trailer
        uploadFile(session, barbieNode, "trailer", barbieTailerFile, "video/mp4");
        File barbiePosterFile = new File("data/test.jpg");// TODO: Actual trailer
        uploadFile(session, barbieNode, "poster", barbiePosterFile, "image/jpeg");

        /*
         * TODO:
         * Add 2 restaurants [would have props on node: Name, Description,
         * restaurantType, opening hours etc]:
         * One with just a pdf of the menu as a node.
         * Another with a node for a MENU and then the menu has child nodes for each of
         * the dishes
         * Each dish would be its own node (some would be an image, others text all with
         * properties for Name, Description and Price etc)
         * 
         * Add 2 shops (different types: One Grocery another Electronics [tag this props
         * for name, location, opening hours, shopType etc])
         * Each shop has child nodes for the items it sells.
         * Some items would be an image others text node (each node would have a Name,
         * Price, Item Type and Description etc)
         * 
         * In General we need good props so we can do good searches!!!!
         */

        session.save();

    }

    private static Session accessRemoteRepo() {
        Map<String, String> parameters = new HashMap<>();
        Session session = null;
        parameters.put("org.apache.jackrabbit.repository.uri",
                "http://localhost:8080/server");

        Jcr2davRepositoryFactory factory = new Jcr2davRepositoryFactory();
        try {
            Repository repository = factory.getRepository(parameters);
            session = repository.login(new SimpleCredentials("admin", "admin".toCharArray()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return session;
    }

    private static void uploadFile(Session session, Node parentNode, String nodeName, File file, String mimeType)
            throws Exception {

        Node fileNode = parentNode.addNode(nodeName, NodeType.NT_FILE);
        Node contentNode = fileNode.addNode("jcr:content", NodeType.NT_RESOURCE);

        try (InputStream is = new FileInputStream(file)) {
            Binary binary = session.getValueFactory().createBinary(is);
            contentNode.setProperty("jcr:data", binary);
        }

        contentNode.setProperty("jcr:mimeType", mimeType);
        contentNode.setProperty("jcr:lastModified", Calendar.getInstance());
    }

}
