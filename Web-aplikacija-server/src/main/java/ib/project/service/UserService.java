package ib.project.service;

import ib.project.model.Authority;
import ib.project.model.User;
import ib.project.repository.AuthorityRepository;
import ib.project.repository.UserRepository;
import ib.project.util.JksFileHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthorityRepository authorityRepository;

    public User addUser(User user) throws Exception {
        user.setActive(false);
        Authority authority = authorityRepository.getOne(1);
        user.setAuthority(authority);
        user = userRepository.save(user);
        user.setCertificate(JksFileHelper.createNewCertificate(user));
        userRepository.save(user);
        return user;
    }

    public void approveUser(Integer adminId ,Integer userId) throws Exception {
        Authority adminAuthority = authorityRepository.findById(2).get();

        User admin = userRepository.findById(adminId).get();//userRepository.getOne(adminId);
        User approvedUser = userRepository.findById(userId).get();//userRepository.getOne(userId);



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

    public byte[] getJks(Integer userId) throws IOException {
        User user = userRepository.findById(userId).get();
        FileInputStream fis = new FileInputStream(JksFileHelper.DATA_DIR_PATH+"\\"+user.getId()+".jks");
        byte[] jks = new byte[fis.available()];
        fis.read(jks);
        return jks;
    }

    public byte[] getCertificate(Integer userId) throws Exception {
        User user = userRepository.findById(userId).get();
        byte[] cert = JksFileHelper.getCertificate(user.getCertificate(), user);
        return cert;
    }

    public Optional<User> getUser(String email, String password) {
        Optional<User> optionalUser = userRepository.getByUsername(email);
        if(optionalUser.isPresent()){
            return optionalUser.get().getPassword().equals(password) && optionalUser.get().getActive() ? optionalUser : Optional.empty();
        }
        else  return optionalUser;
    }

    public List<User> getInNeedOfApproval() {
        return userRepository.getByActive();
    }
}
