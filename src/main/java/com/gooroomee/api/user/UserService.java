package com.gooroomee.api.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Boolean signup(User user) {
        if (userRepository.findByEmail(user.getEmail()).isEmpty()) {
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public boolean signin(SignInDto signinDto) {
        Optional<SignInDto> findUser = userRepository.findByEmail(signinDto.getEmail());
        if(!findUser.isEmpty()) {
            SignInDto existinguser = findUser.get();
            if(existinguser.getPassword().equals(signinDto.getPassword()))
                return true;
        }
        return false;
    }
}
