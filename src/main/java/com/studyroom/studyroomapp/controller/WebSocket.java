package com.studyroom.studyroomapp.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.studyroom.studyroomapp.controller.errors.exceptions.Genericas.NotFoundException;
import com.studyroom.studyroomapp.models.entity.Comunicado;
import com.studyroom.studyroomapp.models.entity.Usuario;
import com.studyroom.studyroomapp.models.service.ComunicadoService;
import com.studyroom.studyroomapp.models.service.UsuarioService;

@Controller
public class WebSocket {

    @Autowired
    private ComunicadoService comunicadoService;

    @Autowired
    private UsuarioService usuarioService;

    @MessageMapping("/send-comunicado")
    @SendTo("/topic/comunicados")
    public Comunicado sendComunicado(Comunicado comunicado){
        Usuario usuario = usuarioService.findByUsername(comunicado.getUsuario().getUsername());
        if(usuario == null){
            throw new NotFoundException("El usuario ".concat(String.valueOf(comunicado.getUsuario().getId())));
        }
        comunicado.setUsuario(usuario);
        comunicado.setFecha(new Date());
        System.out.println(comunicado);
        return comunicadoService.save(comunicado);
    }

    @MessageMapping("/delete-comunicado/{id}")
    @SendTo("/topic/comunicados-delete")
    public Comunicado deleteComunicado(@DestinationVariable Long id){
        Comunicado comunicado = comunicadoService.findById(id);
        if(comunicado == null){
            throw new NotFoundException("El comunicado ".concat(String.valueOf(id)));
        }
        comunicadoService.deleteById(id);
        return comunicado;
    }
}
