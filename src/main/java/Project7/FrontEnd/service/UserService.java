package Project7.FrontEnd.service;

import Project7.FrontEnd.dto.BibliothequeDTO;
import Project7.FrontEnd.dto.LivreDTO;
import Project7.FrontEnd.dto.UserDTO;
import Project7.FrontEnd.form.LivreForm;
import Project7.FrontEnd.form.LoginForm;
import Project7.FrontEnd.form.UserForm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
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

/*
        var values = new HashMap<String, String>() {{
            put("username", "admin@gmail");
        }};*/
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


    /*Methode pour envoyer le login et récupérer le token d'autorisation*/
    public String getTokenByMailAndMotDePasse(LoginForm utilisateur) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        var objectMapper = new ObjectMapper();
        String requestUtilisateur = objectMapper
                .writeValueAsString(utilisateur);
        logger.info(" valeur du string envoyé "+utilisateur);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9090/login"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestUtilisateur))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        logger.info(" retour du body: "+response.body());
        return response.body();
    }

    /*Methode pour envoyer le user et creer le compte dans la base de données*/
    public UserDTO createUserDansBaseDeDonnees(UserDTO utilisateur) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        var objectMapper = new ObjectMapper();
        String requestUtilisateur = objectMapper
                .writeValueAsString(utilisateur);
        logger.info(" valeur du string envoyé "+utilisateur);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9090/user/addUser"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestUtilisateur))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        logger.info(" retour du body: "+response.body());
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response.body(), new TypeReference<UserDTO>() {
        });


    }

    /*Methode pour transformer un UserFormen en UserDTO*/
    public UserDTO transformerUserFormEnUserDTO(UserForm userForm) {
        UserDTO userDTO = new UserDTO();
        userDTO.setIdUser(userForm.getIdUser());
        userDTO.setNomUser(userForm.getNomUser());
        userDTO.setPrenomUser(userForm.getPrenomUser());
        userDTO.setMailUser(userForm.getMailUser());
        userDTO.setMotDePasse(userForm.getMotDePasse());
        return userDTO;
    }

}
