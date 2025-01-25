package com.hms.Controller;

import com.hms.Entity.Property;
import com.hms.Service.PropertyService;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Property")
public class PropertyController {

    private PropertyService ps;

    public PropertyController(PropertyService ps) {
        this.ps = ps;
    }

    @PostMapping("/addPro")
    public ResponseEntity<?> addProperty(@RequestBody Property p,
                                         @RequestParam long cityId,
                                         @RequestParam long countryId){
        ps.addProperty(p, cityId, countryId);
        return ResponseEntity.ok("Property added successfully");
    }
    @DeleteMapping("/deletePro")
    public String delete(){
        return "Goodbye from HMS";
    }

    @GetMapping("/{search}")
    public List<Property> getProperties(
            @PathVariable String search
    ) {
        List<Property> property = ps.getProperty(search);
        return property;
    }
}
