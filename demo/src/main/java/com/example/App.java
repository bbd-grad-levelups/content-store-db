package com.example;

import org.apache.jackrabbit.commons.JcrUtils;
import javax.jcr.*;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;

public class App {
    public static void main(String[] args) throws Exception {

        Repository repository = JcrUtils.getRepository();
        Session session = repository.login(new SimpleCredentials("admin", "admin".toCharArray()));
        try {
            Node root = session.getRootNode();

            // Store content
            Node hello = root.addNode("hello");
            Node world = hello.addNode("world");
            world.setProperty("message", "Hello, World!");
            session.save();

            // Retrieve content
            Node node = root.getNode("hello/world");
            System.out.println("HELLO" + node.getPath());
            System.out.println("Message" + node.getProperty("message").getString());

            // Search content
            searchContent(session, "Hello, World!");

            // Remove content
            // root.getNode("hello").remove();
            // session.save();
        } finally {
            session.logout();
        }
    }

    private static void searchContent(Session session, String message) throws RepositoryException {
        QueryManager queryManager = session.getWorkspace().getQueryManager();
        String queryString = "SELECT * FROM [nt:base] WHERE [message] = '" + message + "'";

        Query query = queryManager.createQuery(queryString, Query.JCR_SQL2);
        QueryResult result = query.execute();

        javax.jcr.NodeIterator nodes = result.getNodes();
        while (nodes.hasNext()) {
            Node node = nodes.nextNode();
            System.out.println("Found node: " + node.getPath());
            System.out.println("Message: " + node.getProperty("message").getString());
        }
    }
}
