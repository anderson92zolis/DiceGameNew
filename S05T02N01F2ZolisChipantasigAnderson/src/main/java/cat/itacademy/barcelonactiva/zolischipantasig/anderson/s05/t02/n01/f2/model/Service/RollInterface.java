package cat.itacademy.barcelonactiva.zolischipantasig.anderson.s05.t02.n01.f2.model.Service;


import cat.itacademy.barcelonactiva.zolischipantasig.anderson.s05.t02.n01.f2.model.Dto.PlayerDto;
import cat.itacademy.barcelonactiva.zolischipantasig.anderson.s05.t02.n01.f2.model.Exceptions.ResourceNotFoundException;
import org.bson.types.ObjectId;

public interface RollInterface {
    void deleteRollsofAPlayer(ObjectId id) throws ResourceNotFoundException;
    PlayerDto createRoll(ObjectId id) throws ResourceNotFoundException;
}
