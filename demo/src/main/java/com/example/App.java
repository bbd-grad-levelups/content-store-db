package com.example;

import org.apache.jackrabbit.jcr2dav.Jcr2davRepositoryFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.jcr.*;

public class App {
    public static void main(String[] args) throws Exception {

        Session session = accessRemoteRepo();
        createCinemaData(session);
        createCheckersData(session);
        createHMData(session);
        createIncredibleConnectionData(session);
        createRestaurantData(session);

    }

    private static void createCinemaData(Session session) throws Exception {

        Node rootNode = session.getRootNode();

        // Create the Cinema node and its properties
        Node cinemaNode = rootNode.addNode("cinema");
        cinemaNode.setProperty("type", "Cinema");
        cinemaNode.setProperty("name", "Ster-Kinekor");
        cinemaNode.setProperty("location", "Shop 1A");
        cinemaNode.setProperty("openingHours", "09:00 - 23:00");

        // Add the various movies and their props, posters + trailers
        Node godzillaNode = cinemaNode.addNode("godzillaVsKong");
        godzillaNode.setProperty("name", "Godzilla vs Kong");
        godzillaNode.setProperty("genre", "Action");
        godzillaNode.setProperty("runTime", 120);
        godzillaNode.setProperty("price", 55.00);
        File godzillaTrailerFile = new File("data/godzilla.mp4");
        uploadFile(session, godzillaNode, "trailer", godzillaTrailerFile, "video/mp4", Collections.emptyMap());
        File godzillaPosterFile = new File("data/godzillaVsKong.jpg");
        uploadFile(session, godzillaNode, "poster", godzillaPosterFile, "image/jpeg", Collections.emptyMap());

        Node barbieNode = cinemaNode.addNode("barbie");
        barbieNode.setProperty("name", "Barbie Movie");
        barbieNode.setProperty("genre", "Comedy");
        barbieNode.setProperty("runTime", 105);
        File barbiePosterFile = new File("data/barbie.jpg");
        uploadFile(session, barbieNode, "poster", barbiePosterFile, "image/jpeg", Collections.emptyMap());

        Node hitchhikerNode = cinemaNode.addNode("hitchhikers");
        hitchhikerNode.setProperty("name", "The Hitchhikers Guide to the Galaxy");
        hitchhikerNode.setProperty("genre", "Comedy");
        hitchhikerNode.setProperty("runTime", 95);
        File hitchhikerPosterFile = new File("data/hitchhikers.jpg");
        uploadFile(session, barbieNode, "poster", hitchhikerPosterFile, "image/jpeg", Collections.emptyMap());

        Node megNode = cinemaNode.addNode("meg");
        megNode.setProperty("name", "The Meg");
        megNode.setProperty("genre", "Action");
        megNode.setProperty("runTime", 113);
        File megPosterFile = new File("data/meg.jpg");
        uploadFile(session, megNode, "poster", megPosterFile, "image/jpeg", Collections.emptyMap());

        Node deadpoolNode = cinemaNode.addNode("deadpool");
        deadpoolNode.setProperty("name", "Deadpool");
        deadpoolNode.setProperty("genre", "Comedy");
        deadpoolNode.setProperty("runTime", 108);
        File deadpoolPosterFile = new File("data/deadpool.jpg");
        uploadFile(session, deadpoolNode, "poster", deadpoolPosterFile, "image/jpeg", Collections.emptyMap());

        Node loveActuallyNode = cinemaNode.addNode("loveActually");
        loveActuallyNode.setProperty("name", "Love Actually");
        loveActuallyNode.setProperty("genre", "Romance");
        loveActuallyNode.setProperty("runTime", 135);
        File loveActuallyPosterFile = new File("data/loveActually.jpg");
        uploadFile(session, loveActuallyNode, "poster", loveActuallyPosterFile, "image/jpeg", Collections.emptyMap());

        Node titanicNode = cinemaNode.addNode("titanic");
        titanicNode.setProperty("name", "Titanic");
        titanicNode.setProperty("genre", "Romance");
        titanicNode.setProperty("runTime", 195);
        File titanicPosterFile = new File("data/titanic.jpg");
        uploadFile(session, titanicNode, "poster", titanicPosterFile, "image/jpeg", Collections.emptyMap());

        session.save();
    }

    private static void createCheckersData(Session session) throws Exception {
        Node rootNode = session.getRootNode();

        Node checkersNode = rootNode.addNode("checkers");
        checkersNode.setProperty("type", "Shop");
        checkersNode.setProperty("shopType", "Groceries");
        checkersNode.setProperty("name", "Checkers");
        checkersNode.setProperty("location", "Shop 1B");
        checkersNode.setProperty("openingHours", "08:00 - 19:00");

        File eggsFile = new File("data/eggs.jpg");
        Map<String, Object> eggsProperties = new HashMap<>();
        eggsProperties.put("type", "Item");
        eggsProperties.put("name", "Eggs");
        eggsProperties.put("price", 42.99);
        uploadFile(session, checkersNode, "eggs", eggsFile, "image/jpeg", eggsProperties);

        File milkFile = new File("data/milk.jpg");
        Map<String, Object> milkProperties = new HashMap<>();
        milkProperties.put("type", "Item");
        milkProperties.put("name", "Milk");
        milkProperties.put("price", 25.49);
        uploadFile(session, checkersNode, "milk", milkFile, "image/jpeg", milkProperties);

        File breadFile = new File("data/bread.jpg");
        Map<String, Object> breadProperties = new HashMap<>();
        breadProperties.put("type", "Item");
        breadProperties.put("name", "Bread");
        breadProperties.put("price", 15.75);
        uploadFile(session, checkersNode, "bread", breadFile, "image/jpeg", breadProperties);

        File applesFile = new File("data/apples.jpg");
        Map<String, Object> applesProperties = new HashMap<>();
        applesProperties.put("type", "Item");
        applesProperties.put("name", "Apples");
        applesProperties.put("price", 30.99);
        uploadFile(session, checkersNode, "apples", applesFile, "image/jpeg", applesProperties);

        File cheeseFile = new File("data/cheese.jpg");
        Map<String, Object> cheeseProperties = new HashMap<>();
        cheeseProperties.put("type", "Item");
        cheeseProperties.put("name", "Cheese");
        cheeseProperties.put("price", 55.25);
        uploadFile(session, checkersNode, "cheese", cheeseFile, "image/jpeg", cheeseProperties);

        File chickenBreastsFile = new File("data/chicken.jpg");
        Map<String, Object> chickenBreastsProperties = new HashMap<>();
        chickenBreastsProperties.put("type", "Item");
        chickenBreastsProperties.put("name", "Chicken Breasts");
        chickenBreastsProperties.put("price", 85.99);
        uploadFile(session, checkersNode, "chickenBreasts", chickenBreastsFile, "image/jpeg", chickenBreastsProperties);

        session.save();
    }

    private static void createIncredibleConnectionData(Session session) throws Exception {
        Node rootNode = session.getRootNode();

        Node incredibleConnectionNode = rootNode.addNode("incredibleConnection");
        incredibleConnectionNode.setProperty("type", "Shop");
        incredibleConnectionNode.setProperty("shopType", "Electronics");
        incredibleConnectionNode.setProperty("name", "Incredible Connection");
        incredibleConnectionNode.setProperty("location", "Shop 2A");
        incredibleConnectionNode.setProperty("openingHours", "09:00 - 20:00");

        File laptopFile = new File("data/laptop.jpg");
        Map<String, Object> laptopProperties = new HashMap<>();
        laptopProperties.put("type", "Item");
        laptopProperties.put("name", "Laptop");
        laptopProperties.put("price", 9999.99);
        uploadFile(session, incredibleConnectionNode, "laptop", laptopFile, "image/jpeg", laptopProperties);

        File smartphoneFile = new File("data/smartphone.jpg");
        Map<String, Object> smartphoneProperties = new HashMap<>();
        smartphoneProperties.put("type", "Item");
        smartphoneProperties.put("name", "Smartphone");
        smartphoneProperties.put("price", 799.99);
        uploadFile(session, incredibleConnectionNode, "smartphone", smartphoneFile, "image/jpeg", smartphoneProperties);

        File tabletFile = new File("data/tablet.jpg");
        Map<String, Object> tabletProperties = new HashMap<>();
        tabletProperties.put("type", "Item");
        tabletProperties.put("name", "Tablet");
        tabletProperties.put("price", 499.99);
        uploadFile(session, incredibleConnectionNode, "tablet", tabletFile, "image/jpeg", tabletProperties);

        File tvFile = new File("data/tv.jpg");
        Map<String, Object> tvProperties = new HashMap<>();
        tvProperties.put("type", "Item");
        tvProperties.put("name", "Television");
        tvProperties.put("price", 1199.99);
        uploadFile(session, incredibleConnectionNode, "tv", tvFile, "image/jpeg", tvProperties);

        File headphonesFile = new File("data/headphones.jpg");
        Map<String, Object> headphonesProperties = new HashMap<>();
        headphonesProperties.put("type", "Item");
        headphonesProperties.put("name", "Headphones");
        headphonesProperties.put("price", 199.99);
        uploadFile(session, incredibleConnectionNode, "headphones", headphonesFile, "image/jpeg", headphonesProperties);

        File cameraFile = new File("data/camera.jpg");
        Map<String, Object> cameraProperties = new HashMap<>();
        cameraProperties.put("type", "Item");
        cameraProperties.put("name", "Camera");
        cameraProperties.put("price", 599.99);
        uploadFile(session, incredibleConnectionNode, "camera", cameraFile, "image/jpeg", cameraProperties);

        session.save();
    }

    private static void createHMData(Session session) throws Exception {
        Node rootNode = session.getRootNode();

        Node hmNode = rootNode.addNode("HM");
        hmNode.setProperty("type", "Shop");
        hmNode.setProperty("shopType", "Clothing");
        hmNode.setProperty("name", "H&M");
        hmNode.setProperty("location", "Shop 3C");
        hmNode.setProperty("openingHours", "10:00 - 21:00");

        File tshirtFile = new File("data/tshirt.jpg");
        Map<String, Object> tshirtProperties = new HashMap<>();
        tshirtProperties.put("type", "Item");
        tshirtProperties.put("name", "T-shirt");
        tshirtProperties.put("price", 149.99);
        uploadFile(session, hmNode, "tshirt", tshirtFile, "image/jpeg", tshirtProperties);

        File jeansFile = new File("data/jeans.jpg");
        Map<String, Object> jeansProperties = new HashMap<>();
        jeansProperties.put("type", "Item");
        jeansProperties.put("name", "Jeans");
        jeansProperties.put("price", 399.99);
        uploadFile(session, hmNode, "jeans", jeansFile, "image/jpeg", jeansProperties);

        File jacketFile = new File("data/jacket.jpg");
        Map<String, Object> jacketProperties = new HashMap<>();
        jacketProperties.put("type", "Item");
        jacketProperties.put("name", "Jacket");
        jacketProperties.put("price", 799.99);
        uploadFile(session, hmNode, "jacket", jacketFile, "image/jpeg", jacketProperties);

        File dressFile = new File("data/dress.jpg");
        Map<String, Object> dressProperties = new HashMap<>();
        dressProperties.put("type", "Item");
        dressProperties.put("name", "Dress");
        dressProperties.put("price", 499.99);
        uploadFile(session, hmNode, "dress", dressFile, "image/jpeg", dressProperties);

        File sneakersFile = new File("data/sneakers.jpg");
        Map<String, Object> sneakersProperties = new HashMap<>();
        sneakersProperties.put("type", "Item");
        sneakersProperties.put("name", "Sneakers");
        sneakersProperties.put("price", 299.99);
        uploadFile(session, hmNode, "sneakers", sneakersFile, "image/jpeg", sneakersProperties);

        File hatFile = new File("data/hat.jpg");
        Map<String, Object> hatProperties = new HashMap<>();
        hatProperties.put("type", "Item");
        hatProperties.put("name", "Hat");
        hatProperties.put("price", 99.99);
        uploadFile(session, hmNode, "hat", hatFile, "image/jpeg", hatProperties);

        session.save();
    }

    public static void createRestaurantData(Session session) throws Exception {

        Node rootNode = session.getRootNode();

        Node spurNode = rootNode.addNode("spur");
        spurNode.setProperty("type", "Restaurant");
        spurNode.setProperty("restaurantType", "Family");
        spurNode.setProperty("name", "Spur");
        spurNode.setProperty("location", "Shop 1E");
        spurNode.setProperty("openingHours", "07:00 - 22:00");

        File spurMenuFile = new File("data/spur.pdf");
        uploadFile(session, spurNode, "menu", spurMenuFile, "application/pdf", Collections.emptyMap());

        Node kfcNode = rootNode.addNode("kfc");
        kfcNode.setProperty("type", "Restaurant");
        kfcNode.setProperty("restaurantType", "fastFood");
        kfcNode.setProperty("name", "KFC");
        kfcNode.setProperty("location", "Shop 2E");
        kfcNode.setProperty("openingHours", "08:30 - 23:00");

        File kfcMenuFile = new File("data/kfc.pdf");
        uploadFile(session, kfcNode, "menu", kfcMenuFile, "application/pdf", Collections.emptyMap());

        Node nobuNode = rootNode.addNode("nobu");
        nobuNode.setProperty("type", "Restaurant");
        nobuNode.setProperty("restaurantType", "fineCuisine");
        nobuNode.setProperty("name", "Nobu");
        nobuNode.setProperty("location", "Shop 4A");
        nobuNode.setProperty("openingHours", "17:30 - 23:30");

        File nobuMenuFile = new File("data/nobu.pdf");
        uploadFile(session, nobuNode, "menu", nobuMenuFile, "application/pdf", Collections.emptyMap());

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

    private static void uploadFile(Session session, Node parentNode, String nodeName, File file,
            String mimeType, Map<String, Object> properties) throws Exception {
        Node contentNode = parentNode.addNode(nodeName, "nt:unstructured");

        Node fileNode = contentNode.addNode("file", "nt:file");
        Node resourceNode = fileNode.addNode("jcr:content", "nt:resource");

        try (InputStream is = new FileInputStream(file)) {
            Binary binary = session.getValueFactory().createBinary(is);
            resourceNode.setProperty("jcr:data", binary);
        }

        resourceNode.setProperty("jcr:mimeType", mimeType);
        resourceNode.setProperty("jcr:lastModified", Calendar.getInstance());

        for (Map.Entry<String, Object> entry : properties.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (value != null) { // Skip if the value is null
                if (value instanceof String) {
                    contentNode.setProperty(key, (String) value);
                } else if (value instanceof Integer) {
                    contentNode.setProperty(key, (Integer) value);
                } else if (value instanceof Double) {
                    contentNode.setProperty(key, (Double) value);
                } else {
                    throw new IllegalArgumentException("Unsupported property type: " + value.getClass());
                }
            }
        }
    }

}
