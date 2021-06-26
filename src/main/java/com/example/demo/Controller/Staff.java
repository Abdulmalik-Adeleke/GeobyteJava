package com.example.demo.Controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import javax.servlet.http.HttpServletRequest;

import com.example.demo.Auth.Token.TokenGenerator;
import com.example.demo.Dto.ChosenRouteLog;
import com.example.demo.Models.LoggedRoutes;
import com.example.demo.Repository.LoggedRoutesRepository;
import com.example.demo.ResponseModel.OrderObjectDto;
import com.example.demo.ResponseModel.ResultObjectDto;
import com.example.demo.Service.StaffService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
        var paylaod = staffService.getHubs();
        return ResponseEntity.ok(paylaod);
    }

    // return completableFuture.thenCombine() ?
    @GetMapping(value = "/staff/getroutes")
    public List<ResultObjectDto> getRoutes(@RequestParam String origin, @RequestParam String destination,
            @RequestParam double radiusInKm) {
        List<ResultObjectDto> result = new ArrayList<ResultObjectDto>();
        var destinationInfo = staffService.getDestinations(origin, destination);

        int len = destinationInfo.size();

        // TODO: use CompletableFutures
        if (len == 1) {
            CompletableFuture.supplyAsync(() -> result.add(staffService.getResult(destinationInfo.get(0), radiusInKm)));
        } else if (len == 2) {

            // CompletableFuture.supplyAsync(() ->
            // staffService.getResult(destinationInfo.get(0),radiusInKm)
            // )
            // thenCombine(
            // staffService.getResult(destinationInfo.get(1),radiusInKm),
            // (res1,res2) ->
            // System.out.println(res1)
            // ) ;
            result.add(staffService.getResult(destinationInfo.get(0), radiusInKm));
            result.add(staffService.getResult(destinationInfo.get(1), radiusInKm));
        }
        return result;
    }

    @GetMapping(value = "/staff/extralocations")
    public List<OrderObjectDto> extraLocations(@RequestParam UUID id, @RequestParam List<Integer> idsToIgnore,
            @RequestParam double radiusInKm) {
        return staffService.getMoreDeliveryLocations(id, idsToIgnore, radiusInKm);
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
            try {
                log.save(routelog);
                return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.CREATED);
            } catch (Exception e) {
                return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.UNAUTHORIZED);
    }

}
