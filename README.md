# Java 9 and HttpClient
- The ancient solution to communicate with an endpoint was introduced
at the beginning with ```HttpUrlConnection```
- As result of more advanced feature as the modern era require, 
developers used popular implementations like:
    - Apache HttpComponents
    - OKHttp
- Since Java version 11 a new library is introduced to fill the gap
produced with old implementation: ```HttpClient```.
- The new library support sync and async call.
- Briefly with the ```HttpClient``` is necessary:
    1. create an instance of HttpClient
    2. create an instance of HttpRequest
    3. Perform the request
    4. Retrieve an instance of HttpResponse.
    
- Simple demo (sync call):

```java
public class App {
  public static void main(String[] args) {
    //Use GSON to prettify the body result
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // create a new connection with a timeout of 20s
    HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(20)).build();

    try {
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
```
- Simple demo (async call):

```java
public class App {
  public static void main(String[] args) {
    //Use GSON to prettify the body result
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // create a new connection with a timeout of 20s
    HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(20)).build();

    try {
        // send the request to the server
        System.out.println("## Async call...");
        client
           .sendAsync(request, HttpResponse.BodyHandlers.ofString())
           .thenApply(HttpResponse::body)
           .thenApply(JsonParser::parseString)
           .thenApply(gson::toJson)
           .thenAccept(System.out::println)
           .join();
 
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
  }          
```   