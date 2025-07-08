package study.TestSecuriry.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import study.TestSecuriry.dto.JoinDto;
import study.TestSecuriry.entity.UserEntity;
import study.TestSecuriry.repository.UserRepository;

@Service
public class JoinService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void joinProcess(JoinDto joinDto) {

        // db에 동일한 username 확인
        boolean isUser = userRepository.existByUsername(joinDto.getUsername());
        if (isUser) {
            return;
        }
        
        UserEntity data = new UserEntity();
        data.setUsername(joinDto.getUsername());
        data.setPassword(bCryptPasswordEncoder.encode(joinDto.getPassword()));
        data.setRole("ROLE_ADMIN");

        userRepository.save(data);

    }
}
