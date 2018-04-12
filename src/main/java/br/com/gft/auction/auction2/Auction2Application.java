package br.com.gft.auction.auction2;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class Auction2Application extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(Auction2Application.class);
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
		 SpringApplication.run(Auction2Application.class, args);
	}
    
  
		
}
