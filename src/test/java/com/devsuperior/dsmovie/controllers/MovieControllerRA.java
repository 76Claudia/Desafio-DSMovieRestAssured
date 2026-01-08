package com.devsuperior.dsmovie.controllers;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



public class MovieControllerRA {
	
	private String clientUsername, clientPassword, adminUsername, adminPassword;
	private String clientToken, adminToken, invalidToken;
	private Long existingMovieId, nonExistingMovieId;
	private String movieTitle;
	
	
	@BeforeEach
	public void setUp() {
		baseURI = "http://localhost:8080";
		
		clientUsername = "maria@gmail.com";
		clientPassword = "123456";
		adminUsername = "alex@gmail.com";
		adminPassword = "123456";
		
		
		movieTitle = "Vingadores: Ultimato";
		
		
	}
	
	@Test
	public void findAllShouldReturnOkWhenMovieNoArgumentsGiven() {
		
		given()
			.get("/movies")
			
		.then()
			.statusCode(200);			
	}
	
	@Test
	public void findAllShouldReturnPagedMoviesWhenMovieTitleParamIsNotEmpty() {		
		given()
		  	.get("/movies?title={movieTitle}", movieTitle)
		.then()
			.statusCode(200)
			.body("content.id[0]", is(13))
			.body("content.title[0]", equalTo("Vingadores: Ultimato"))
			.body("content.score[0]", is(0.0F))	
			.body("content.count[0]", is(0))
			.body("content.image[0]", equalTo("https://www.themoviedb.org/t/p/w533_and_h300_bestv2/7RyHsO4yDXtBv1zUU3mTpHeQ0d5.jpg"));
			
	}
	
	@Test
	public void findByIdShouldReturnMovieWhenIdExists() {	
		existingMovieId = 13L;
		
		given()
			.get("/movies/{id}", existingMovieId)
		.then()
			.statusCode(200)
			.body("id", is(13))
			.body("title", equalTo("Vingadores: Ultimato"))
			.body("score", is(0.0F))
			.body("count", is(0))
			.body("image", equalTo("https://www.themoviedb.org/t/p/w533_and_h300_bestv2/7RyHsO4yDXtBv1zUU3mTpHeQ0d5.jpg"));
			
	}
	
	@Test
	public void findByIdShouldReturnNotFoundWhenIdDoesNotExist() {	
		nonExistingMovieId = 100L;
		
		given()
		.get("/movies/{id}", nonExistingMovieId)
	.then()
		.statusCode(404);
		
		
		
}

		
	
	
	@Test
	public void insertShouldReturnUnprocessableEntityWhenAdminLoggedAndBlankTitle() throws JSONException {		
	}
	
	@Test
	public void insertShouldReturnForbiddenWhenClientLogged() throws Exception {
	}
	
	@Test
	public void insertShouldReturnUnauthorizedWhenInvalidToken() throws Exception {
	}
}
