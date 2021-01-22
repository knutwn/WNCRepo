package no.wangnilsen;

import no.wangnilsen.vo.Matrett;

/**
 * FoodDao
 */
public interface FoodDao {

    /**
     * @param id - Id til matrett
     * @return - Matrett for gitt id.
     */
    Matrett getMatrett(final int id);

    /**
     * @param id  - Matrett id
     * @param navn - navn på matrett
     * @param url - url til nettsted for oppskrift
     */
    void insertMatrett(final int id, final String navn, final String url);
}
