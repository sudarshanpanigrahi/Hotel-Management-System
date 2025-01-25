package com.hms.Controller;

import com.hms.Entity.AppUser;
import com.hms.Entity.Property;
import com.hms.Entity.Review;
import com.hms.Repository.PropertyRepository;
import com.hms.Repository.ReviewRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Review")
public class ReviewController {

    private PropertyRepository pr;
    private ReviewRepository rr;
    public ReviewController(PropertyRepository pr, ReviewRepository rr) {
        this.pr = pr;
        this.rr = rr;
    }
    @PostMapping("/add")
    public ResponseEntity<?> addReview(@RequestBody Review r,
                                       @RequestParam long property,
                                       @AuthenticationPrincipal AppUser user){

        Property pro = pr.findById(property).get();
        Optional<Review> status = rr.findByAppUserAndProperty(user, pro);
        if(status.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Already Review Given");
        }
        r.setAppUser(user);
        r.setProperty(pro);
        Review save = rr.save(r);
        return new ResponseEntity<>(save,HttpStatus.CREATED);
    }

    @GetMapping("/get")
    public List<Review> getReviews (@AuthenticationPrincipal AppUser user){
        List<Review> userR = rr.findByAppUser(user);
        return userR;
    }
}
