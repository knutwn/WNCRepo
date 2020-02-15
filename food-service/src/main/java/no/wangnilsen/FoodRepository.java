package no.wangnilsen;

import no.wangnilsen.vo.Matrett;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepository extends JpaRepository<Matrett, Long> {
}
