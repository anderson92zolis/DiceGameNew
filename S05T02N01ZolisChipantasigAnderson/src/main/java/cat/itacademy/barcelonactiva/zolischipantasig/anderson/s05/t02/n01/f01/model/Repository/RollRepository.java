package cat.itacademy.barcelonactiva.zolischipantasig.anderson.s05.t02.n01.f01.model.Repository;

import cat.itacademy.barcelonactiva.zolischipantasig.anderson.s05.t02.n01.f01.model.Entity.Roll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RollRepository extends JpaRepository<Roll,Integer> {
}
