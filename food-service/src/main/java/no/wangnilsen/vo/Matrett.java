package no.wangnilsen.vo;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Matrett {

    @Id
    private int id;
    private String navn;
    private String url;

    public Matrett() {}

    public Matrett(int id, String navn,String url) {
        this.id = id;
        this.navn = navn;
        this.url = url;
    }


    public int getId() { return id; }

    public void setId(int id) {
        this.id = id;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
