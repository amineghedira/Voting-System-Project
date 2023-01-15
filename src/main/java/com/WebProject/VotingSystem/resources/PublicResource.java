package com.WebProject.VotingSystem.resources;

import com.WebProject.VotingSystem.Constants;
import com.WebProject.VotingSystem.domain.Admin;
import com.WebProject.VotingSystem.domain.User;
import com.WebProject.VotingSystem.services.AdminService;
import com.WebProject.VotingSystem.services.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/")
public class PublicResource {
    @Autowired
    AdminService adminService;
    @Autowired
    UserService userService;
@PostMapping("/login/admin/")
    public ResponseEntity<Map<String, String>> loginAdmin(@RequestBody Map<String, Object> adminMap){
     String adminName = (String) adminMap.get("adminName");
     String password = (String) adminMap.get("password");
     Admin admin = adminService.validateAdmin(adminName, password);
     return new ResponseEntity<>(generateAdminToken(admin),HttpStatus.OK);
 }
@PostMapping("/register/admin/")
    public ResponseEntity<Map<String,String>> registerAdmin(@RequestBody Map<String, Object> adminMap){
     String adminName= (String) adminMap.get("adminName");
     String password= (String) adminMap.get("password");
     Admin admin = adminService.registerAdmin(adminName, password);
     return new ResponseEntity<>(generateAdminToken(admin), HttpStatus.OK);
  }

 @PostMapping("/register/user/")
    public ResponseEntity<Map<String,String>> registerUser(@RequestBody Map<String, Object> userMap){
     Integer CIN = Integer.valueOf((String) userMap.get("CIN"));
     String email= (String) userMap.get("email");
     String password= (String) userMap.get("password");
     User user = userService.registerUser(CIN, email, password);
     return new ResponseEntity<>(generateUserToken(user), HttpStatus.OK);}

    @PostMapping("/login/user/")
     public ResponseEntity<Map<String, String>> loginUser(@RequestBody Map<String, Object> userMap){
        String email = (String) userMap.get("email");
        String password = (String) userMap.get("password");
        User user = userService.validateUser(email, password);
        return new ResponseEntity<>(generateUserToken(user),HttpStatus.OK);
       }

  private Map<String, String> generateAdminToken(Admin admin){
    long timestamp = System.currentTimeMillis();
    String token = Jwts.builder().signWith(SignatureAlgorithm.HS256, Constants.API_ADMIN_KEY)
            .setIssuedAt(new Date(timestamp))
            .setExpiration(new Date(timestamp+ Constants.TOKEN_VALIDITY))
            .claim("adminID", admin.getAdminID())
            .claim("adminName", admin.getAdminName())
            .compact();
    Map<String, String> map = new HashMap<>();
    map.put("token",token);
    return map;

  }
    private Map<String, String> generateUserToken(User user){
        long timestamp = System.currentTimeMillis();
        String token = Jwts.builder().signWith(SignatureAlgorithm.HS256, Constants.API_USER_KEY)
                .setIssuedAt(new Date(timestamp))
                .setExpiration(new Date(timestamp+ Constants.TOKEN_VALIDITY))
                .claim("userID", user.getUserID())
                .claim("CIN", user.getCIN())
                .claim("email", user.getEmail())
                .compact();
        Map<String, String> map = new HashMap<>();
        map.put("token",token);
        return map;

    }
}
