package no.wangnilsen;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.wangnilsen.vo.Matrett;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/foodresource")
public class FoodResource {

    Logger log = Logger.getAnonymousLogger();
    
    @Autowired
    FoodDaoImpl foodDaoImpl;


    @RequestMapping(value="matrett/{id}", method = RequestMethod.GET)
    @Produces(MediaType.APPLICATION_JSON)
    public String getVare(@PathVariable("id")String id) {
        List<Matrett> matrettList = new ArrayList<>();
        try {
            matrettList.add(new Matrett(1,"Kjøttkaker", "http://www.matprat.no/kjottkaker"));
            matrettList.add(new Matrett(2,"Kylling med skinke", "http://www.matprat.no/kylling"));

            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(matrettList);
        }catch(Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @RequestMapping(value = "matrett", method = RequestMethod.POST)
    public void insertVare(@RequestBody String mat) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Matrett matrett = objectMapper.readValue(mat, Matrett.class);
            log.info(matrett.getNavn() + " -- " + matrett.getUrl());
            foodDaoImpl.insertMatrett(1, matrett.getNavn(), matrett.getUrl());
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
