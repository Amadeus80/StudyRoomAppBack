package com.studyroom.studyroomapp.controller;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ReservaControllerTest {

    private static String horaActual(LocalDateTime ahora){
        ZoneId zonaHorariaEspaña = ZoneId.of("Europe/Madrid");
        ZonedDateTime zonedDateTime = ahora.atZone(ZoneOffset.systemDefault());
        ZonedDateTime horaEspaña = zonedDateTime.withZoneSameInstant(zonaHorariaEspaña);
        int hora = horaEspaña.getHour();
        int minutos = horaEspaña.getMinute();
        String horaString = "";
        if(minutos > 30){
            hora++;
        }
        if(hora < 10){
            horaString = "0"+String.valueOf(hora);
        }
        else{
            horaString = String.valueOf(hora);
        }
        
        
        return horaString + ":00";
    }

    public static Date fechaSinHoras(Date date){
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
    
    @DisplayName("Test para la funcion de obtener hora actual")
    @Test
    public void horaActualTest(){
        String hora = horaActual(LocalDateTime.of(2023, Month.JUNE, 17, 12, 19));
        String hora2 = horaActual(LocalDateTime.of(2023, Month.JUNE, 17, 12, 30));
        String hora3 = horaActual(LocalDateTime.of(2023, Month.JUNE, 17, 12, 31));
        String hora4 = horaActual(LocalDateTime.of(2023, Month.JUNE, 17, 12, 59));
        assertEquals("12:00", hora);
        assertEquals("12:00", hora2);
        assertEquals("13:00", hora3);
        assertEquals("13:00", hora4);
    }

    @DisplayName("Test para la funcion que quita las horas de una fecha")
    @Test
    public void fechaSinHorasTest(){
        Date fecha = fechaSinHoras(new Date());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        int hora = calendar.get(Calendar.HOUR_OF_DAY);
        int minutos = calendar.get(Calendar.MINUTE);
        int segundos = calendar.get(Calendar.SECOND);
        int milisegundos = calendar.get(Calendar.MILLISECOND);
        assertEquals(0, hora);
        assertEquals(0, minutos);
        assertEquals(0, segundos);
        assertEquals(0, milisegundos);
    }
}
