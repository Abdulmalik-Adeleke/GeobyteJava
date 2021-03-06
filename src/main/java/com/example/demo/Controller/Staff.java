package com.example.demo.Controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;
// import java.util.concurrent.CompletableFuture;

import javax.servlet.http.HttpServletRequest;

import com.example.demo.Auth.Token.TokenGenerator;
import com.example.demo.Dto.ChosenRouteLog;
import com.example.demo.Dto.DeleteMarked;
import com.example.demo.Dto.ResponseStatus;
import com.example.demo.Models.LoggedRoutes;
import com.example.demo.Repository.LoggedRoutesRepository;
import com.example.demo.ResponseModel.OrderObjectDto;
import com.example.demo.ResponseModel.ResultObjectDto;
import com.example.demo.Service.StaffService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class Staff {

    private static final String Authorization = "Authorization";
    private final StaffService staffService;
    private final LoggedRoutesRepository log;

    @Autowired
    public Staff(StaffService staffService, LoggedRoutesRepository log) {
        this.staffService = staffService;
        this.log = log;
    }

    @GetMapping(value = "/staff/hubs")
    public ResponseEntity<?> getHubs() {
        try {
            var paylaod = staffService.getHubs();
            return ResponseEntity.ok(paylaod);
        } catch (Exception e) {
            return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping(value = "/staff/originlocations")
    public ResponseEntity<?> getOrigins() {
        try {
            var paylaod = staffService.getOrigins();
            return ResponseEntity.ok(paylaod);
        } catch (Exception e) {
            return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/staff/endlocations")
    public ResponseEntity<?> getEndLocations(@RequestParam String origin) {
        try {
            var paylaod = staffService.getDestinations(origin);
            return ResponseEntity.ok(paylaod);
        } catch (Exception e) {
            return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/staff/orders")
    public ResponseEntity<?> getOrders() {
        try {
            var paylaod = staffService.getOrders();
            return ResponseEntity.ok(paylaod);
        } catch (Exception e) {
            return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping(value = "/staff/deleteorders")
    public ResponseEntity<?> deleteOrders(@RequestBody DeleteMarked deletemarked) {
            var ids = deletemarked.getIds();
            System.out.println("ids are "+ids);
            var success = staffService.setdeletemarked(deletemarked.getIds());
            if (success) {
                ResponseStatus status = new ResponseStatus("SUCCESS");
                return ResponseEntity.ok(status);
            }
            return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // return CompletableFuture ?
    @GetMapping(value = "/staff/getroutes")
    public List<ResultObjectDto> getRoutes(@RequestParam String origin, @RequestParam String destination,
            @RequestParam double radiusInKm) {
        List<ResultObjectDto> result = new ArrayList<ResultObjectDto>();

        var destinationInfo = staffService.getDestinations(origin, destination);

        int len = destinationInfo.size();

        if (len == 1) {
            // CompletableFuture.supplyAsync(() ->
            // result.add(staffService.getResult(destinationInfo.get(0), radiusInKm)));
            result.add(staffService.getResult(destinationInfo.get(0), radiusInKm));
        } else if (len == 2) {
            // CompletableFuture.supplyAsync(() ->
            // staffService.getResult(destinationInfo.get(0),radiusInKm)
            // ).
            // thenCombine(
            // staffService.getResult(destinationInfo.get(1),radiusInKm),
            // (res1,res2) ->
            // System.out.println(res1)
            // System.out.println(res2)
            // );
            result.add(staffService.getResult(destinationInfo.get(0), radiusInKm));
            result.add(staffService.getResult(destinationInfo.get(1), radiusInKm));
        }
        return result;
    }

    @GetMapping(value = "/staff/extralocations")
    public LinkedHashMap<String,List<OrderObjectDto>> extraLocations(@RequestParam String id, @RequestParam List<Integer> idsToIgnore,
            @RequestParam double radiusInKm) {
        UUID guid = UUID.fromString(id);
        return staffService.getMoreDeliveryLocations(guid, idsToIgnore, radiusInKm);
    }

    @PostMapping(value = "/staff/logs")
    public ResponseEntity<?> saveRouteLogs(HttpServletRequest request, @RequestBody ChosenRouteLog chosenroutelog) {
    
        TokenGenerator tokenGenerator = new TokenGenerator();
        String authheader = request.getHeader(Authorization);
        String jwttoken = authheader.substring(7);
        if (jwttoken != null && tokenGenerator.validateToken(jwttoken)) {
            String userEmail = tokenGenerator.getSubjectFromToken(jwttoken);
            Date date = new Date();
            // TODO: stream request body
            LoggedRoutes routelog = new LoggedRoutes(userEmail, chosenroutelog.log, date);
            routelog.toString();
            log.save(routelog);
            return (ResponseEntity<?>) ResponseEntity.ok("log appended");
        }
        return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.UNAUTHORIZED);
    }

}
