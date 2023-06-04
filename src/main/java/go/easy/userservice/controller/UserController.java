package go.easy.userservice.controller;

import go.easy.userservice.dto.UpdateEmailRequest;
import go.easy.userservice.dto.UserProfile;
import go.easy.userservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @Operation(summary = "обновления email пользователя")
    @PutMapping("/email/update")
    ResponseEntity<UserProfile> updateUserByEmail(@RequestBody @Valid UpdateEmailRequest request) {
        return ResponseEntity.ok(service.updateUserEmail(request));
    }

    @Operation(summary = "получение пользователя по email")
    @GetMapping("/email/{email}")
    ResponseEntity<UserProfile> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(service.getUserByEmail(email));
    }

    @Operation(summary = "получение пользователя по id")
    @GetMapping("/{userId}")
    ResponseEntity<UserProfile> getUser(@PathVariable String userId) {
        return ResponseEntity.ok(service.getUserById(userId));
    }


}
