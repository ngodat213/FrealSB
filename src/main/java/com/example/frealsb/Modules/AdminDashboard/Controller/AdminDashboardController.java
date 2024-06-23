package com.example.frealsb.Modules.AdminDashboard.Controller;

import com.example.frealsb.Const.ErrorConstants;
import com.example.frealsb.Const.ToastConstants;
import com.example.frealsb.Modules.Blog.Service.IBlogService;
import com.example.frealsb.Modules.Event.Service.IEventService;
import com.example.frealsb.Modules.Location.Model.Location;
import com.example.frealsb.Modules.Location.Service.ILocationService;
import com.example.frealsb.Modules.User.Model.User;
import com.example.frealsb.Modules.User.Service.IUserService;
import com.example.frealsb.Util.Model.PaginationDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/dashboard")
public class AdminDashboardController {

    @Autowired
    private IUserService userService;
    @Autowired
    private ILocationService locationService;
    @Autowired
    private IBlogService blogService;
    @Autowired
    private IEventService eventService;

    @GetMapping("")
    public String dashboard (){
        return "Layouts/Dashboard/index";
    }
    @GetMapping("/mail_box")
    public String mailBox (){
        return "Layouts/Dashboard/mail_box";
    }

    @GetMapping("/chat")
    public String chat (){
        return "Layouts/Dashboard/chat";
    }

    @GetMapping("/invoice")
    public String invoice (){
        return "Layouts/Dashboard/invoice";
    }

    @GetMapping("/user")
    public String userTable ( @RequestParam(defaultValue = "1") int page,
                              @RequestParam(defaultValue = "10") int limit,
                              @RequestParam(defaultValue = "desc", name = "sort") String sortDirection,
                              @RequestParam(defaultValue = "createdAt") String sortBy,
                              Model model){
        model.addAttribute("user", new User());
        PaginationDTO paginationDTO = new PaginationDTO(page, limit, sortDirection, sortBy);
        model.addAttribute("users", userService.getAll(paginationDTO));
        return "Layouts/Dashboard/user_table";
    }

    @GetMapping("/blog")
    public String blogTable (){
        return "Layouts/Dashboard/invoice";
    }

    @GetMapping("/event")
    public String eventTable (){
        return "Layouts/Dashboard/invoice";
    }

    @GetMapping("/culture")
    public String cultureTable (){
        return "Layouts/Dashboard/invoice";
    }

    @GetMapping("/culture_category")
    public String cultureCategoryTable (){
        return "Layouts/Dashboard/invoice";
    }

    @GetMapping("/food")
    public String foodTable (){
        return "Layouts/Dashboard/invoice";
    }

    @GetMapping("/food_category")
    public String foodCategoryTable (){
        return "Layouts/Dashboard/invoice";
    }

    @GetMapping("/food_feature")
    public String foodFeatureTable (){
        return "Layouts/Dashboard/invoice";
    }

    @GetMapping("/location")
    public String locationTable (@RequestParam(defaultValue = "1") int page,
                                 @RequestParam(defaultValue = "10") int limit,
                                 @RequestParam(defaultValue = "desc", name = "sort") String sortDirection,
                                 @RequestParam(defaultValue = "createdAt") String sortBy,
                                 Model model){
        model.addAttribute("location", new Location());
        PaginationDTO paginationDTO = new PaginationDTO(page, limit, sortDirection, sortBy);
        model.addAttribute("locations", locationService.getAll(paginationDTO));
        return "Layouts/Dashboard/location_table";
    }


//  API
    @GetMapping("/edit_location/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getLocation(@PathVariable String id) {
        Location existingLocation = locationService.getLocationById(id);
        if (existingLocation != null) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("location", existingLocation); // Include location details in the response
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(404).body(Map.of("status", "error", "message", "Location not found"));
        }
    }
    @PutMapping("/edit_location/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> editLocation(@PathVariable String id, @Validated @ModelAttribute Location location) {
        Location existingLocation = locationService.getLocationById(id);
        if (existingLocation != null) {
            existingLocation.setCity(location.getCity());
            existingLocation.setProvince(location.getProvince());
            existingLocation.setFeatures(location.getFeatures());
            locationService.saveLocation(existingLocation);
            return ResponseEntity.ok(Map.of("status", "success", "message", "Location updated successfully"));
        } else {
            return ResponseEntity.status(404).body(Map.of("status", "error", "message", "Location not found"));
        }
    }

    @DeleteMapping("/delete_location/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> deleteLocation(@PathVariable String id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Location isDeleted = locationService.deleteLocation(id);
            if (isDeleted != null) {
                response.put("status", "success");
                response.put("message", "Location deleted successfully");
                return ResponseEntity.ok(response);
            } else {
                response.put("status", "error");
                response.put("message", "Location not found");
                return ResponseEntity.status(404).body(response);
            }
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    @PostMapping("/create_location")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> createLocation(@Valid @ModelAttribute Location location) {
        Map<String, Object> response = new HashMap<>();
        if (locationService.existsByCityAndProvinceAndFeatures(location)) {
            response.put(ErrorConstants.status, ErrorConstants.statusError);
            response.put(ErrorConstants.message, ErrorConstants.locationExits);
            return ResponseEntity.badRequest().body(response);
        }
        locationService.saveLocation(location);
        response.put(ErrorConstants.status,ErrorConstants.statusSuccess);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/user_lock/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> userLock(@PathVariable("id") String userId) {
        Map<String, Object> response = new HashMap<>();
        try {
            boolean isAdmin = userService.handleLockUser(userId);
            if (!isAdmin) {
                response.put(ErrorConstants.status, ErrorConstants.statusError);
                response.put(ErrorConstants.message, ToastConstants.cantLockUser);
                return ResponseEntity.badRequest().body(response);
            }
            response.put(ErrorConstants.status,ErrorConstants.statusSuccess);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put(ErrorConstants.status, ErrorConstants.statusError);
            response.put(ErrorConstants.message, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/create_user")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> createUser(@Valid @ModelAttribute User user) {
        Map<String, Object> response = new HashMap<>();
        if (userService.existsByEmail(user.getEmail())) {
            response.put(ErrorConstants.status, ErrorConstants.statusError);
            response.put(ErrorConstants.message, ErrorConstants.emailExits);
            return ResponseEntity.badRequest().body(response);
        }
        userService.createUser(user);
        response.put(ErrorConstants.status,ErrorConstants.statusSuccess);
        return ResponseEntity.ok(response);
    }
}
