package cat.itacademy.barcelonactiva.zolischipantasig.anderson.s05.t02.n01.f01.model.Service;

import cat.itacademy.barcelonactiva.zolischipantasig.anderson.s05.t02.n01.f01.model.Dto.PlayerDto;
import cat.itacademy.barcelonactiva.zolischipantasig.anderson.s05.t02.n01.f01.model.Exceptions.ResourceNotFoundException;

public interface RollInterface {
    void deleteRollsofAPlayer(int id) throws ResourceNotFoundException;
    PlayerDto createRoll(int id) throws ResourceNotFoundException;
}
