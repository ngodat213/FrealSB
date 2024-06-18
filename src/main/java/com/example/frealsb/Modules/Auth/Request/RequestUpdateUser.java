package com.example.frealsb.Modules.Auth.Request;

import com.example.frealsb.Modules.User.Model.User;
import lombok.*;

import java.util.Date;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestUpdateUser {
    private String password;
    private String email;
    private String phone;
    private String firstName;
    private String lastName;
    private String bio;
    private String avatarPublicId;

    public User toUpdate(User user){
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(password);
        user.setEmail(email);
        user.setAvatarPublicId(avatarPublicId);
        user.setPhone(phone);
        user.setBio(bio);
        return user;
    }
}
