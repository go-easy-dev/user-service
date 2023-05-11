package go.easy.userservice.controller;

import go.easy.userservice.dto.CreateUserRequest;
import go.easy.userservice.dto.UpdateEmailRequest;
import go.easy.userservice.dto.UserProfile;
import go.easy.userservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping("/create")
    ResponseEntity<UserProfile> createUser(@RequestBody @Valid CreateUserRequest request) {
        return ResponseEntity.ok(service.createUser(request));
    }

    @PostMapping("email/update")
    ResponseEntity<UserProfile> getUserByEmail(@RequestBody @Valid UpdateEmailRequest request) {
        return ResponseEntity.ok(service.updateUserEmail(request));
    }

    @GetMapping("email/{email}")
    ResponseEntity<UserProfile> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(service.getUserByEmail(email));
    }

    @GetMapping("{userId}")
    ResponseEntity<UserProfile> getUser(@PathVariable String userId) {
        return ResponseEntity.ok(service.getUserById(userId));
    }


}
