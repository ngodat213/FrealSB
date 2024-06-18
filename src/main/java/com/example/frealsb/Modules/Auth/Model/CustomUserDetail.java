package com.example.frealsb.Modules.Auth.Model;

import com.example.frealsb.Modules.User.Model.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
public class CustomUserDetail implements UserDetails{
    private String link = "https://res.cloudinary.com/duhncgkpo/image/upload/v1717508494/";
    private String extensionImage = ".png";
    private User user;
    public CustomUserDetail(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(user.getRole());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    public String getDisplayName(){
        return user.getFirstName() + " " + user.getLastName();
    }

    public String getAvatar(){
        return link + user.getAvatarPublicId() + extensionImage;
    }
}
