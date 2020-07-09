package ib.project.rest;

import ib.project.model.User;
import ib.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "api/users")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<User> register(@RequestBody User user) throws Exception {
        User newUser = userService.addUser(user);
        return ResponseEntity.ok(newUser);
    }

    @PutMapping("/admins/{adminId}/approve/{userId}")
    public ResponseEntity<Void> approveUser(@PathVariable("adminId") Integer adminId, @PathVariable("userId") Integer userId) throws Exception {
        userService.approveUser(adminId, userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers(@RequestParam(required = false) String email){
        List<User> list = new ArrayList<User>();
        if(email != null)  list = userService.getUsers(email);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}/get-certificate")
    public ResponseEntity<InputStreamResource> getCertificate(@PathVariable("id") Integer userId) throws Exception {
        byte[] cert = userService.getCertificate(userId);
        InputStreamResource inputStreamResource = new InputStreamResource(new ByteArrayInputStream(cert));
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + userId+".cer")
                .contentLength(cert.length).contentType(MediaType.APPLICATION_OCTET_STREAM).body(inputStreamResource);
    }

    @GetMapping("/{id}/get-jks")
    public ResponseEntity<InputStreamResource> getJks(@PathVariable("id") Integer userId) throws IOException {
        byte[] jks = userService.getJks(userId);
        InputStreamResource inputStreamResource = new InputStreamResource(new ByteArrayInputStream(jks));
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + userId+".jks")
                .contentLength(jks.length).contentType(MediaType.APPLICATION_OCTET_STREAM).body(inputStreamResource);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<User> login(@RequestBody User user){
        Optional<User> optionalUser = userService.getUser(user.getEmail(), user.    getPassword());
        if(optionalUser.isPresent()){
            return ResponseEntity.ok(optionalUser.get());
        }
        else return ResponseEntity.badRequest().build();
    }

    @GetMapping("/not-approved")
    public ResponseEntity<List<User>> getInNeedOfApproval() {
        return ResponseEntity.ok(userService.getInNeedOfApproval());
    }

}
