package com.hms.Controller;

import com.hms.Entity.Booking;
import com.hms.Entity.Property;
import com.hms.Entity.RoomAvailability;
import com.hms.Repository.BookingRepository;
import com.hms.Repository.PropertyRepository;
import com.hms.Repository.RoomAvailabilityRepository;
import com.hms.Service.PDFGenerator;
import com.hms.Service.SmsService;
import com.hms.Service.WhatsAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Room")
public class RoomController {

    private RoomAvailabilityRepository rar;
    private PropertyRepository pr;
    private BookingRepository br;
    private PDFGenerator pg;
    private SmsService ss;
    private WhatsAppService ws;

    public RoomController(RoomAvailabilityRepository rar, PropertyRepository pr, BookingRepository br,
                          PDFGenerator pg, SmsService ss, WhatsAppService ws) {
        this.rar = rar;
        this.pr = pr;
        this.br = br;
        this.pg = pg;
        this.ss = ss;
        this.ws = ws;
    }

    @PostMapping("/addRoom")
    public ResponseEntity<?> addRoom(@RequestBody RoomAvailability ra,
                                     @RequestParam long propertyId
                                     ){
        Optional<Property> byId = pr.findById(propertyId);
        if(byId.isPresent()){
            Property pro = byId.get();
            ra.setProperty_Id(pro);
            RoomAvailability save = rar.save(ra);
            return ResponseEntity.ok().body(save);
        }
    return new ResponseEntity<>("PropertyId is invalid", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("update")
    public String update(@RequestParam long BookingId

                         ){
        Booking bk = br.findById(BookingId).orElseThrow(() -> new RuntimeException());
        bk.setGuest_Name("Siti");
        bk.setVersion(1L);
//        System.out.println(bk.getVersion());
        br.save(bk);
        return "Booking Updated Successfully";
    }

    @GetMapping("/getRoom")
    public ResponseEntity<?> getAllRooms(@RequestParam long propertyId,
                                        @RequestParam LocalDate from,
                                         @RequestParam LocalDate to,
                                         @RequestParam String type,
//                                         @RequestParam String toWhatsAppNumber,
                                         @RequestBody Booking bk
                                         ) {

        List<RoomAvailability> rooms = rar.findByDate(from, to, type, propertyId);

        Property property = pr.findById(propertyId).get();

        for (RoomAvailability r : rooms) {
            if (r.getTotal_Rooms() == 0) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("All rooms are booked.");
            }
            r.setTotal_Rooms(r.getTotal_Rooms() - 1);
            bk.setProperty(property);
            Booking saveBook = br.save(bk);
            String pdfFilePath = pg.generatePDF("sample.pdf", saveBook, type, saveBook);

            //WhatsApp Message Sending Code
//        String s = ws.sendWhatsAppMessageWithPdf(toWhatsAppNumber, "you Booking Successfully Added to " + property.getName() + " Hotel And "+ saveBook +"\n Thats all ");
//        System.out.println("\n"+s);

            // SMS sending code here
//        System.out.println("Sending SMS...");
//        String s = ss.sendSms(String.valueOf(bk.getPhone()), "you Booking Successfully Added to " + property.getName() + " Hotel And "+ saveBook +"\n Thats all ");
//        System.out.println("\n"+s);
            return ResponseEntity.ok().body(rooms);
        }
        return ResponseEntity.internalServerError().body("No Room Found");
    }

}
