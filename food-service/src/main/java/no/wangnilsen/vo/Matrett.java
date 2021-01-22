package no.wangnilsen.vo;


import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Matrett
 */
@Entity
public class Matrett {

    @Id
    private int id;
    private String navn;
    private String url;

    /**
     * Matrett contructor
     */
    public Matrett() {}

    /**
     * @param id - Id til matrett
     * @param navn - Navnet på matrett
     * @param url - url til nettsted
     */
    public Matrett(final int id, final String navn, final String url) {
        this.id = id;
        this.navn = navn;
        this.url = url;
    }


    public int getId() { return id; }

    public void setId(final int id) {
        this.id = id;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(final String navn) {
        this.navn = navn;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }
}
