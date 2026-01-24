package com.devsuperior.dsmovie.controllers;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.devsuperior.dsmovie.tests.TokenUtil;

import io.restassured.http.ContentType;

public class ScoreControllerRA {
	
	private String clientUsername, clientPassword, adminUsername, adminPassword;
	private String clientToken, adminToken, invalidToken;
	private Long existingMovieId, nonExistingMovieId, invalidMovieId;
	

	private Map<String, Object> putScoreInstance;
	
	@BeforeEach
	public void setUp () throws JSONException {
		 baseURI = "http://localhost:8080";
		 
		    clientUsername = "alex@gmail.com";
			clientPassword = "123456";
			adminUsername = "maria@gmail.com";
			adminPassword = "123456";
			
			existingMovieId = 1L;
			nonExistingMovieId = 100L;
			invalidMovieId = 0L;
			
			
			
			clientToken = TokenUtil.obtainAccessToken(clientUsername, clientPassword);
			adminToken = TokenUtil.obtainAccessToken(adminUsername, adminPassword);
			invalidToken = invalidToken = adminToken + "xpto"; // Invalid token
		

	 }
	
	@Test
	public void saveScoreShouldReturnNotFoundWhenMovieIdDoesNotExist() throws Exception {	

		   Map<String, Object> putScoreInstance = new HashMap<>();
		   putScoreInstance.put("movieId", 100L);
		   putScoreInstance.put("score", 4.0);
           JSONObject newScore = new JSONObject(putScoreInstance);
           
		
		given()
			.header("Content-type", "application/json")
			.header("Authorization", "Bearer " + adminToken)
			.body(newScore.toString())
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.put("/movies/{id}/score", nonExistingMovieId)
		.then()
			.statusCode(404);
			
		
	}
	
	
	@Test
	public void saveScoreShouldReturnUnprocessableEntityWhenMissingMovieId() throws Exception {
		Map<String, Object> scoreInstance = new HashMap<>();
		scoreInstance.put("movieId", null);
		scoreInstance.put("score", 4.0);
        JSONObject newScore = new JSONObject(scoreInstance);
     	
		given()
			.header("Content-type", "application/json")
			.header("Authorization", "Bearer " + clientToken)
			.body(newScore.toString())
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		
		.when()
			.put("/scores")
		.then()
			.statusCode(422);
			
	}
	
	
	@Test
	public void saveScoreShouldReturnUnprocessableEntityWhenScoreIsLessThanZero() throws Exception {		
		Map<String, Object> scoreInstance = new HashMap<>();
		 scoreInstance.put("movieId", 1);
		 scoreInstance.put("score", -1.0);
         JSONObject newScore = new JSONObject(scoreInstance);
         invalidMovieId = 0L;
       
		
		given()
			.header("Content-type", "application/json")
			.header("Authorization", "Bearer " + adminToken)
			.body(newScore.toString())
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
		 	.put("/scores")
		.then()
			.statusCode(422);
		
	}
	
}
