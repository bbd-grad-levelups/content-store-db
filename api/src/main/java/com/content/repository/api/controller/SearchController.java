package com.content.repository.api.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.jcr.RepositoryException;

import com.content.repository.api.Service.contentService; 

@RestController
@RequestMapping("/api")
public class SearchController {
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<Map<String, String>> cinema(@RequestParam String node) {
        Map<String, String> response;
        try {
            response = contentService.findAll(node);
        } catch (RepositoryException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/cinema", method = RequestMethod.GET)
    public ResponseEntity<Map<String, String>> searchNode(@RequestParam String nodeToSearch ,@RequestBody Map<String, String> body) {
        Map<String, String> response;
        try {
            response = contentService.searchSecondLevelNode(nodeToSearch, body);
        } catch (RepositoryException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity<Map<String, String>> listSecondLevelNodes(){
        Map<String, String> response = new HashMap<>();
        try {
            contentService.listChildren(response);
        } catch (RepositoryException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/findNode", method = RequestMethod.GET)
    public ResponseEntity<Map<String, String>> findNode(@RequestBody Map<String, String> body){
        Map<String, String> response = new HashMap<>();
        try {
            response = contentService.findNodes(body);
        } catch (RepositoryException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
