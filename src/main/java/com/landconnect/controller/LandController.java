package com.landconnect.controller;

import com.landconnect.dto.request.LandRequest;
import com.landconnect.dto.request.LandSearchRequest;
import com.landconnect.dto.response.ApiResponse;
import com.landconnect.service.LandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/lands")
public class LandController {
    private final LandService landService;
    @PostMapping
    @PreAuthorize("hasAnyAuthority('SELLER','ADMIN')")
    public ResponseEntity<ApiResponse> createLand(@Valid @RequestBody LandRequest request){

        return ResponseEntity.ok(landService.createLand(request));
    }
    @GetMapping
    public ResponseEntity<ApiResponse> getAllLands(){
        return ResponseEntity.ok(landService.getAllLands());
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getLandById(@PathVariable Long id){
        return ResponseEntity.ok(landService.getLandById(id));
    }
    @PreAuthorize("hasAnyAuthority('SELLER','ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateLand(@PathVariable Long id, @Valid @RequestBody LandRequest request){
        return ResponseEntity.ok(landService.updateLand(id,request));
    }
    @PreAuthorize("hasAnyAuthority('SELLER','ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteLand( @PathVariable Long id){
        return ResponseEntity.ok(landService.deleteLand(id));

    }
    @GetMapping("/page")
    public ResponseEntity<ApiResponse> getAllLandsWithPagination(
            @RequestParam(defaultValue="0") int page,
            @RequestParam(defaultValue="5") int size,
            @RequestParam(defaultValue="id") String sortBy,
            @RequestParam(defaultValue="asc") String sortDir){
        return ResponseEntity.ok(landService.getAllLand(page,size,sortBy,sortDir));

    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse> searchLands(
            @ModelAttribute LandSearchRequest request,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
            ){
      return ResponseEntity.ok( landService.searchLands(request,page,size,sortBy,sortDir));
    }
}
