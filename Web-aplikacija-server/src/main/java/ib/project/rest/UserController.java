package ib.project.rest;

import ib.project.model.User;
import ib.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/users")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/")
    public ResponseEntity<User> register(@RequestBody User user){
        User newUser = userService.addUser(user);
        return ResponseEntity.ok(newUser);
    }

    @PutMapping("/admins/{adminId}/approve/{userId}")
    public ResponseEntity<Void> approveUser(@PathVariable("adminId") Integer adminId, @PathVariable("userId") Integer userId) throws Exception {
        userService.approveUser(adminId, userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/")
    public ResponseEntity<List<User>> getUsers(@RequestParam(required = false) String email){
        List<User> list = new ArrayList<User>();
        if(email != null)  list = userService.getUsers(email);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}/get-certificate")
    public ResponseEntity<byte[]> getCertificate(@PathVariable("id") Integer userId) throws IOException {
        byte[] cert = userService.getCertificate(userId);
        return ResponseEntity.ok(cert);
    }


}
