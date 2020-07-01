package ib.project.service;

import ib.project.model.Authority;
import ib.project.model.User;
import ib.project.repository.AuthorityRepository;
import ib.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthorityRepository authorityRepository;

    public User addUser(User user){
        user.setActive(false);
        Authority authority = authorityRepository.getOne(1);
        user.setAuthority(authority);
        user = userRepository.save(user);
        return user;
    }

    public void approveUser(Integer adminId ,Integer userId) throws Exception {
        Authority adminAuthority = authorityRepository.getOne(2);

        User admin = userRepository.getOne(adminId);
        User approvedUser = userRepository.getOne(userId);

        if (admin.getAuthority().getId() == adminAuthority.getId()){
            approvedUser.setActive(true);
            userRepository.save(approvedUser);
            return;
        }
        else throw new Exception("Admin not admin");
    }

    public List<User> getUsers(String email){
        return userRepository.getUsers(email);
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public byte[] getCertificate(Integer userId) throws IOException {
        User user = userRepository.getOne(userId);
        FileInputStream fis = new FileInputStream(user.getCertificate());
        byte[] cert = new byte[fis.available()];
        fis.read(cert);
        return cert;
    }
}
