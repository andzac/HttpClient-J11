package it.andzac.learn;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

/** Hello world! */
public class App {
  public static void main(String[] args) {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // create a new connection with a timeout of 20s
    HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(20)).build();

    try {
      // define a test endpoint
      String urlEndpoint = "https://postman-echo.com/get";
      URI uri = URI.create(urlEndpoint + "?foo1=bar1&foo2=bar2");

      // create a request for a given URI
      HttpRequest request = HttpRequest.newBuilder().uri(uri).build();

      // send the request to the server
      HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

      // process the response
      System.out.println("[Status code]: " + response.statusCode());
      System.out.println("[Headers]: " + response.headers().allValues("content-type"));
      System.out.println(
          "[Body]: " + gson.toJson(JsonParser.parseString(response.body().toString())));

    } catch (InterruptedException | IOException e) {
      e.printStackTrace();
    }
  }
}
