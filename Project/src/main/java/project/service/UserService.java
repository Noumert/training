package project.service;

import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.domain.MyUserDetails;
import project.dto.UserDTO;
import project.entity.Roles;
import project.entity.User;
import project.repository.UserRepository;

import java.util.Optional;

@Slf4j
@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        return new MyUserDetails(user.orElseThrow(() -> new UsernameNotFoundException("User with email: " + email + "does not exist")));
    }

    public void saveNewUser (UserDTO userDto){
        //TODO inform the user about the replay email
        // TODO exception to endpoint
        try {
            userRepository.save(User
                    .builder()
                    .firstName(userDto.getFirstName())
                    .lastName(userDto.getLastName())
                    .email(userDto.getEmail())
                    .password(passwordEncoder.encode(userDto.getPassword()))
                    .roles(Roles.ROLE_USER.name())
                    .build());
        } catch (Exception ex){
            log.info("{Почтовый адрес уже существует}");
        }

    }

    public Optional<User> findByUserLogin (String email){
        //TODO check for user availability. password check
        return userRepository.findByEmail(email);
    }

}
