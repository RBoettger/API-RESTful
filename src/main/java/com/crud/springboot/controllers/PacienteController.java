package com.crud.springboot.controllers;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.crud.springboot.dtos.PacienteRecordDto;
import com.crud.springboot.model.PacienteModel;
import com.crud.springboot.repositories.PacienteRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RestController
public class PacienteController {

    @Autowired
    PacienteRepository pacienteRepository;

    @PostMapping("/pacientes")
    public ResponseEntity<PacienteModel> addPaciente(@RequestBody @Valid PacienteRecordDto pacienteRecordDto){
        var pacienteModel = new PacienteModel();
        BeanUtils.copyProperties(pacienteRecordDto, pacienteModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(pacienteRepository.save(pacienteModel));
    }

    @GetMapping("/pacientes")
    public ResponseEntity<List<PacienteModel>> getAllPaciente(){
        return ResponseEntity.status(HttpStatus.OK).body(pacienteRepository.findAll());
    }

    @GetMapping("/pacientes/{id}")
    public ResponseEntity<Object> getByIdPaciente(@PathVariable(value = "id")UUID id){
        Optional<PacienteModel> paciente0  = pacienteRepository.findById(id);
        if (paciente0.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paciente não foi encontrado.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(paciente0.get());
    }

    @PutMapping("/pacientes/{id}")
    public ResponseEntity<Object> updatePaciente(@PathVariable(value = "id")UUID id,
                                                 @RequestBody @Valid PacienteRecordDto pacienteRecordDto){
        Optional<PacienteModel> paciente0 = pacienteRepository.findById(id);
        if (paciente0.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paciente não foi encontrado.");
        }
        var pacienteModel = paciente0.get();
        BeanUtils.copyProperties(pacienteRecordDto, pacienteModel);
        return ResponseEntity.status(HttpStatus.OK).body(pacienteRepository.save(pacienteModel));
    }

    @DeleteMapping("/pacientes/{id}")
    public ResponseEntity<Object> deletePaciente(@PathVariable(value = "id")UUID id){
        Optional<PacienteModel> paciente0 = pacienteRepository.findById(id);
        if (paciente0.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paciente não foi encontrado.");
        }
        pacienteRepository.delete(paciente0.get());
        return ResponseEntity.status(HttpStatus.OK).body("Paciente excluído com sucesso!");
    }
}
