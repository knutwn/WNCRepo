package no.wangnilsen;

import no.wangnilsen.vo.Matrett;

public interface FoodDao {

    Matrett getMatrett(int id);
    void insertMatrett(int id, String navn, String url);
}
