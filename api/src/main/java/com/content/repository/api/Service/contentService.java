package com.content.repository.api.Service;

import java.util.stream.*;
import java.util.*;
import javax.jcr.*;
import javax.jcr.Node;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;
import org.apache.jackrabbit.jcr2dav.Jcr2davRepositoryFactory;
import org.springframework.stereotype.Service;

@Service
public class contentService {
    static Session session;

    public contentService(){
        session = accessRemoteRepo();
    }

    private static Session accessRemoteRepo() {
        Map<String, String> parameters = new HashMap<>();
        Session session = null;
        // parameters.put("org.apache.jackrabbit.repository.uri",
        //         "http://63.35.177.92/server");

        parameters.put("org.apache.jackrabbit.repository.uri",
        "http://54.75.153.225/server");

        // parameters.put("org.apache.jackrabbit.repository.uri",
        // "http://localhost:8080/server");

        Jcr2davRepositoryFactory factory = new Jcr2davRepositoryFactory();
        try {
            Repository repository = factory.getRepository(parameters);
            session = repository.login(new SimpleCredentials("admin", "admin".toCharArray()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return session;
    }

    public static Map<String, String> findAll(String nodeToFind) throws RepositoryException {
        QueryManager queryManager = session.getWorkspace().getQueryManager();
        String queryString = "SELECT * FROM [nt:unstructured] AS nodes WHERE ISCHILDNODE('/"+ nodeToFind +"')";
        Query query = queryManager.createQuery(queryString, Query.JCR_SQL2);
        QueryResult result = query.execute();
        
        Map<String, String> response = new HashMap<>();
        NodeIterator nodes = result.getNodes();
        while (nodes.hasNext()) {
            Node node = nodes.nextNode();
            System.out.println("Node found: " + node.getPath());
            response.put(node.getPath() ,node.getName());
        }

        return response;
    }

    public static void listChildren(Map<String, String> response) throws RepositoryException {
        Node parentNode =session.getRootNode();
        NodeIterator children = parentNode.getNodes();
        while (children.hasNext()) {
            Node child = children.nextNode();
            response.put(child.getName(), child.getPath());
        }
    }

    public static Map<String, String> searchSecondLevelNode(String nodeToSearch, Map<String, String> body) throws RepositoryException {
        QueryManager queryManager = session.getWorkspace().getQueryManager();

        String whereClause = "";
        if (!body.isEmpty()) {
            whereClause = body.entrySet().stream()
                    .map(entry -> {
                        String key = entry.getKey();
                        Object value = entry.getValue();
                        if (value instanceof Number) {
                            return "[" + key + "] = " + value.toString();
                        } else {
                            String escapedValue = value.toString().replace("'", "''");
                            return "[" + key + "] LIKE '%" + escapedValue + "%'";
                        }
                    })
                    .collect(Collectors.joining(" AND ", " AND ", ""));
        }
    
        String queryString = "SELECT * FROM [nt:unstructured] WHERE ISCHILDNODE('/" + nodeToSearch + "')" + whereClause;

        Query query = queryManager.createQuery(queryString, Query.JCR_SQL2);
        QueryResult result = query.execute();
        NodeIterator nodes = result.getNodes();

        Map<String, String> response = new HashMap<>();
        while (nodes.hasNext()) {
            PropertyIterator properties = nodes.nextNode().getProperties();
            while (properties.hasNext()) {
                Property property = properties.nextProperty();
                response.put(property.getName(), property.getValue().getString());
            }
        }

        return response;
    }

    public static Map<String, String> findNodes(Map<String, String> body) throws RepositoryException {
        QueryManager queryManager = session.getWorkspace().getQueryManager();

        String whereClause = "";
        if (!body.isEmpty()) {
            whereClause = body.entrySet().stream()
                    .map(entry -> "[" + entry.getKey() + "] LIKE '%" + entry.getValue().replace("'", "''") + "%'")
                    .collect(Collectors.joining(" AND ", " AND ", ""));
        }
    
        String queryString = "SELECT * FROM [nt:unstructured] WHERE ISDESCENDANTNODE('/')" + whereClause;
    
        Query query = queryManager.createQuery(queryString, Query.JCR_SQL2);
        QueryResult result = query.execute();
    
        NodeIterator nodes = result.getNodes();
        Map<String, String> response = new HashMap<>();
        
        while (nodes.hasNext()) {
            Node node = nodes.nextNode();
            
            Node parentNode = node.getParent();
            String parentPath = parentNode.getPath();
            
            response.put(parentPath, node.getName());
        }
    
        return response;
    }
}
