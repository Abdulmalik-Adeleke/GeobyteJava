package com.example.demo.Controller;

import java.sql.SQLException;

import com.example.demo.Dto.DestinationRouteDto;
import com.example.demo.Dto.HubDto;
import com.example.demo.Service.AdminService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class Admin {

    private final AdminService adminService;

    @Autowired
    public Admin(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping(value = "/admin/hubs")
    public ResponseEntity<?> getHubs() {
        var paylaod = adminService.getHubs();
        return ResponseEntity.ok(paylaod);
    }

    @PostMapping(value = "/admin/destination/add")
    public ResponseEntity<?> addDestination(@RequestBody DestinationRouteDto destinationroute) {
        try {
            Boolean success = adminService.insertDestination(destinationroute.getDestinationdto(), destinationroute.getRoutes());
            System.out.println(success);
        } catch (SQLException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(value = "/admin/hub/add")
    public ResponseEntity<?> addHub(@RequestBody HubDto hubdto) {
        boolean result = adminService.insertHub(hubdto);
        if (result) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
