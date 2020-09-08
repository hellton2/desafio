package com.desafio.tech.configuration;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.desafio.tech.Constants;


@Configuration
public class WatchServiceConfig {
	
	
	@Bean
    public WatchService watchService() {
		
		
		System.out.println("MONITORING_FOLDER: " + Constants.IN);
        
        WatchService watchService = null;
        try {
            watchService = FileSystems.getDefault().newWatchService();
            Path path = Paths.get(Constants.IN);

            if (!Files.isDirectory(path)) {
                throw new RuntimeException("incorrect monitoring folder: " + path);
            }

            path.register(
                    watchService,
                    StandardWatchEventKinds.ENTRY_CREATE
            );
            
            
            
           
        } catch (IOException e) {
            // log.error("exception for watch service creation:", e);
        }
        return watchService;
    }

}
