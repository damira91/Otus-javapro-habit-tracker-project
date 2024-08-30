package ru.otus.kudaiberdieva.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.otus.kudaiberdieva.dtos.UserLoginRequestDto;
import ru.otus.kudaiberdieva.entities.Profile;
import ru.otus.kudaiberdieva.entities.User;
import ru.otus.kudaiberdieva.exceptions.InformationExistException;
import ru.otus.kudaiberdieva.exceptions.ResourceNotFoundException;
import ru.otus.kudaiberdieva.repositories.UserRepository;
import ru.otus.kudaiberdieva.security.JWTUtils;
import ru.otus.kudaiberdieva.security.MyUserDetails;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder,
                       JWTUtils jwtUtils,
                       @Lazy AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }


    public User createUser(User user){
        Optional<User> userOptional = Optional.ofNullable(userRepository.findUserByEmailAddress(user.getEmail())); //checks if email address already exists in database
        if (userOptional.isEmpty()){ // email not registered yet
            user.setPassword(passwordEncoder.encode(user.getPassword())); //encode password given
            user.setProfile(new Profile());
            return userRepository.save(user);
        } else {
            throw new InformationExistException("user with email address " + user.getEmail() + " already exist.");
        }
    }

    public Optional<String> loginUser(UserLoginRequestDto userLoginDto){
        UsernamePasswordAuthenticationToken authenticationToken = new
                UsernamePasswordAuthenticationToken(userLoginDto.getEmail(), userLoginDto.getPassword());
        try{
            Authentication authentication = authenticationManager.authenticate((authenticationToken)); //authenticate the user
            SecurityContextHolder.getContext().setAuthentication(authentication); //set security context
            MyUserDetails myUserDetails = ( MyUserDetails ) authentication.getPrincipal(); //get user details from authenticated object
            return Optional.of(jwtUtils.generateJwtToken(myUserDetails)); // generate a token for the authenticated user
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public User save(User user) {
        return null;
    }
    @Override
    public User findUserByEmail(String emailAddress){
        return userRepository.findUserByEmailAddress(emailAddress);
    }
    @Override
    public User updateUserProfile(User user){
        Optional<User> userOptional = Optional.ofNullable(findUserByEmail(user.getEmail()));
        if(userOptional.isPresent()){
            if (userOptional.get().getProfile() == user.getProfile()){
                throw new InformationExistException("Profile details are the same. No update needed.");
            }

            if(user.getProfile().getFirstName() != null &&
                    !String.valueOf(userOptional.get().getProfile().getFirstName()).equals(user.getProfile().getFirstName())){
                userOptional.get().getProfile().setFirstName(user.getProfile().getFirstName());
            }

            if (user.getProfile().getLastName() != null &&
                    !String.valueOf(userOptional.get().getProfile().getLastName()).equals(user.getProfile().getLastName())){
                userOptional.get().getProfile().setLastName(user.getProfile().getLastName());
            }

            if (user.getProfile().getBio() != null &&
                    !String.valueOf(userOptional.get().getProfile().getBio()).equals(user.getProfile().getBio())){
                userOptional.get().getProfile().setBio(user.getProfile().getBio());
            }
            return userRepository.save(userOptional.get());
        } else {
            throw new ResourceNotFoundException("user with email address " + user.getEmail() + " not found.");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
