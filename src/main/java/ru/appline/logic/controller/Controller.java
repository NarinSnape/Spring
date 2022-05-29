package ru.appline.logic.controller;

import org.springframework.web.bind.annotation.*;
import ru.appline.logic.Pet;
import ru.appline.logic.PetModel;

import java.io.PrintWriter;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class Controller {
    private static final PetModel petModel = PetModel.getInstance();
    private static final AtomicInteger newId = new AtomicInteger(1);
    @PostMapping(value = "/createPet", consumes = "application/json")
    public String createPet(@RequestBody Pet pet) {
        petModel.add(pet, newId.getAndIncrement());
        return "Поздравляем, вы создали своего " + petModel.getAll().size() + " питомца!";
    }

    @GetMapping(value = "/getAll", produces = "application/json")
    public Map<Integer, Pet> getAll() {
        return petModel.getAll();
    }

    @GetMapping(value = "/getPet", consumes = "application/json", produces = "application/json")
    public Pet getPet(@RequestBody Map<String, Integer> id) {
        return petModel.getFromList(id.get("id"));
    }

    @DeleteMapping(value = "/deletePet", consumes = "application/json", produces = "application/json")
    public String deletePet(@RequestBody Map<String, Integer> id) {
        petModel.delete(id.get("id"));
        return "Питомец " + id.get("id") + " удалён из списка.";
    }
    @PutMapping(value = "/updatePet", consumes = "application/json")
    public String updatePet(@PathVariable int id, @RequestBody Pet pet) {
        petModel.put(pet, id);
        return "Даные о питомце обновлены.";
    }
}
