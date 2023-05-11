package com.studyroom.studyroomapp.models.service;

import java.util.List;

import com.studyroom.studyroomapp.models.entity.Rol;

public interface RolService {
    public List<Rol> findALl();
    public Rol findByRol(String rol);
    public Rol save(Rol r);
}
