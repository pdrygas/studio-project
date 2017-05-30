# sp1-project
How to load chrome extension?
https://developer.chrome.com/extensions/getstarted#unpacked

**How to run the server?**
1. Execute database script src/test/resources/schema.sql in *psql* (\i schema.sql)
2. Run *main* function from src/main/java/pl/edu/agh/Application.java
3. Send POST request to http://localhost:8080/login (with parameters: *username* and *password*) in order to get auth token.
4. Get token from login response header (*X-AUTH-TOKEN*) and use it in every request. 
