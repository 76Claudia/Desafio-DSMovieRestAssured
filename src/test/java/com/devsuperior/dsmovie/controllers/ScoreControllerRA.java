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
			
			
		
			putScoreInstance = new HashMap<>();
			putScoreInstance.put("movieId", 1);
			putScoreInstance.put("score", 4);
			
	 }
	
	@Test
	public void saveScoreShouldReturnNotFoundWhenMovieIdDoesNotExist() throws Exception {	

		   Map<String, Object> putScoreInstance = new HashMap<>();
		   putScoreInstance.put("movieId", nonExistingMovieId);
           JSONObject newScore = new JSONObject(putScoreInstance);
           nonExistingMovieId = 100L;
		
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
		 
		Map<String, Object> putScoreInstance = new HashMap<>();
		 putScoreInstance.put("movieId", null);
         JSONObject newScore = new JSONObject(putScoreInstance);
         nonExistingMovieId = 100L;
		
		given()
			.header("Content-type", "application/json")
			.header("Authorization", "Bearer " + clientToken)
			.body(newScore.toString())
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.put("/movies/{id}/score",nonExistingMovieId)
		.then()
			.statusCode(422);
			
		
			
		
	}
	
	
	@Test
	public void saveScoreShouldReturnUnprocessableEntityWhenScoreIsLessThanZero() throws Exception {		
		
		 
		Map<String, Object> invalidScore = new HashMap<>(putScoreInstance);
		 invalidScore.put("score", -1L);
         JSONObject newScore = new JSONObject(putScoreInstance);
         invalidMovieId = 0L;
		
		given()
			.header("Content-type", "application/json")
			.header("Authorization", "Bearer " + adminToken)
			.body(newScore.toString())
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.put("/movies/{id}/score", nonExistingMovieId)
		.then()
			.statusCode(422);
		
	}
	
}
