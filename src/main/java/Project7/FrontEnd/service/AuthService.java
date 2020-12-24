package Project7.FrontEnd.service;

import Project7.FrontEnd.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class AuthService {

    Logger logger = (Logger) LoggerFactory.getLogger(AuthService.class);

    public String memoireToken;
    public Boolean authentification;
    public UserDTO userConnecte;


}
