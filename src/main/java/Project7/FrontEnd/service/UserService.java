package Project7.FrontEnd.service;

import Project7.FrontEnd.dto.BibliothequeDTO;
import Project7.FrontEnd.dto.LivreDTO;
import Project7.FrontEnd.dto.UserDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Service
public class UserService {

    Logger logger = (Logger) LoggerFactory.getLogger(UserService.class);

    /*Methode pour obtenir un user par Id de l'API rest*/
    public UserDTO getUserById(int id) throws IOException {
        ObjectMapper mapper =new ObjectMapper();
        UserDTO userTrouve = mapper.readValue(new URL("http://localhost:9090/user/"+id),new TypeReference<UserDTO>(){});
        if(userTrouve!= null) {
            logger.info(" retour du user car il existe "+userTrouve);
            return userTrouve;
        } else {
            logger.info(" retour de null car il n'y a pas de bibliotheque avec cet id");
            return null;
        }
    }

    /*Methode pour obtenir un user par mail de l'API rest*/
    public UserDTO getUserByMail(String mail) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper
                .writeValueAsString(mail);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9090/user/me"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        logger.info(" reponse du body "+response.body());
        System.out.println(response.body());

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response.body(), new TypeReference<UserDTO>(){});
    }



}
