package no.wangnilsen;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.wangnilsen.vo.Matrett;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * FoodResource
 */
@RestController
@RequestMapping("/foodresource")
public class FoodResource {

    private static final Logger LOGGER = Logger.getAnonymousLogger();

    /**
     * foodDataImpl
     */
    @Autowired
    FoodDaoImpl foodDaoImpl;


    /**
     * @param id - Matrett Id
     * @return - Matrett for gitt id
     */
    @RequestMapping(value = "matrett/{id}", method = RequestMethod.GET)
    public String getVare(@PathVariable("id") final String id) {
        final List<Matrett> matrettList = new ArrayList<>();
        try {
            matrettList.add(new Matrett(1, "Kjøttkaker", "http://www.matprat.no/kjottkaker"));
            matrettList.add(new Matrett(2, "Kylling med skinke", "http://www.matprat.no/kylling"));

            final ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(matrettList);
        } catch (final Exception e) {
            LOGGER.info("Feil under henting av matrett med id=" + id + ". Feilmelding: "
                    + e.getMessage());
        }
        return "";
    }

    /**
     * @param mat  - Matrett som skal legge til i databasen
     */
    @RequestMapping(value = "matrett", method = RequestMethod.POST)
    public void insertVare(@RequestBody final String mat) {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            final Matrett matrett = objectMapper.readValue(mat, Matrett.class);
            LOGGER.info(matrett.getNavn() + " -- " + matrett.getUrl());
            foodDaoImpl.insertMatrett(1, matrett.getNavn(), matrett.getUrl());
        } catch (final Exception e) {
            LOGGER.info("Feil under lagring av matrett. Feilmelding: " + e.getMessage());
        }
    }
}
