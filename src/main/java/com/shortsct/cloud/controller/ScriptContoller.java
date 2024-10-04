package com.shortsct.cloud.controller;

import com.shortsct.cloud.exception.ResourceNotFoundException;
import com.shortsct.cloud.model.Script;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shortsct.cloud.repository.ScriptsRepository;

@CrossOrigin()
@RestController
@RequestMapping("/api/v1")
public class ScriptContoller {
    @Autowired
    private ScriptsRepository scriptsRepository;
    
    @GetMapping("/drafts")
    public List<Script> getAllScripts() {
        return scriptsRepository.findAll();
    }

    @PostMapping("/drafts")
    public Script createDraft(@RequestBody Script script) {
        return scriptsRepository.save(script);
    }

    @GetMapping("/drafts/{id}")
    public ResponseEntity<Script> getScriptById(@PathVariable Long id) {
        Script script =  scriptsRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Script not found with id: " + id));
        return ResponseEntity.ok(script);    
    }

    @PutMapping("/drafts/{id}")
    public ResponseEntity<Script> updateScript(@PathVariable Long id, @RequestBody Script scriptDetail) {
        Script script = scriptsRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Script not found with id: " + id));
        script.setTitle(scriptDetail.getTitle());
        script.setUrl(scriptDetail.getUrl());
        script.setEmail(scriptDetail.getEmail());
        script.setPassword(scriptDetail.getPassword());
        
        Script updatedScript = scriptsRepository.save(script);
        return ResponseEntity.ok(updatedScript);
    }   

    @DeleteMapping("/drafts/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteScript(@PathVariable Long id) {
        Script script = scriptsRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Script not found with id:" + id));
            scriptsRepository.delete(script);
            Map<String, Boolean> response = new HashMap<>();
            response.put("deleted", Boolean.TRUE);
            return ResponseEntity.ok(response);
    }

}
