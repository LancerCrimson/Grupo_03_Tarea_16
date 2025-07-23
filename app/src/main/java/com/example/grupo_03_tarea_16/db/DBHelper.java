package com.example.grupo_03_tarea_16.db;

import android.content.Context;

import com.example.grupo_03_tarea_16_ejercicio_01.modelos.Accidente;
import com.example.grupo_03_tarea_16_ejercicio_01.modelos.Acta;
import com.example.grupo_03_tarea_16_ejercicio_01.modelos.Agente;
import com.example.grupo_03_tarea_16_ejercicio_01.modelos.Audiencia;
import com.example.grupo_03_tarea_16_ejercicio_01.modelos.Infraccion;
import com.example.grupo_03_tarea_16_ejercicio_01.modelos.NormasDeT;
import com.example.grupo_03_tarea_16_ejercicio_01.modelos.OficinaGob;
import com.example.grupo_03_tarea_16_ejercicio_01.modelos.Propietario;
import com.example.grupo_03_tarea_16_ejercicio_01.modelos.PuesDeControl;
import com.example.grupo_03_tarea_16_ejercicio_01.modelos.Usuario;
import com.example.grupo_03_tarea_16_ejercicio_01.modelos.Vehiculo;
import com.example.grupo_03_tarea_16_ejercicio_01.modelos.Zona;

import java.util.ArrayList;

public class DBHelper {
    private DBAdapter dbAdapter;
    public DBHelper(Context context) {
        dbAdapter = new DBAdapter(context);
    }

    // ========== MÉTODOS PARA USUARIO ==========

    public long InsertarUsuario(Usuario usuario) {
        dbAdapter.open();
        long id = dbAdapter.InsertarUsuario(usuario);
        dbAdapter.close();
        return id;
    }

    public void EditarUsuario(Usuario usuario) {
        dbAdapter.open();
        dbAdapter.EditarUsuario(usuario);
        dbAdapter.close();
    }

    public void EliminarUsuario(Usuario usuario) {
        dbAdapter.open();
        dbAdapter.EliminarUsuario(usuario);
        dbAdapter.close();
    }

    public Usuario get_Usuario(int id) {
        dbAdapter.open();
        Usuario usuario = dbAdapter.get_Usuario(id);
        dbAdapter.close();
        return usuario;
    }

    public ArrayList<Usuario> get_all_Usuario() {
        dbAdapter.open();
        ArrayList<Usuario> lista = dbAdapter.get_all_Usuario();
        dbAdapter.close();
        return lista;
    }

    // ========== MÉTODOS PARA PROPIETARIO ==========

    public long InsertarPropietario(Propietario propietario) {
        dbAdapter.open();
        long id = dbAdapter.InsertarPropietario(propietario);
        dbAdapter.close();
        return id;
    }

    public void EditarPropietario(Propietario propietario) {
        dbAdapter.open();
        dbAdapter.EditarPropietario(propietario);
        dbAdapter.close();
    }

    public void EliminarPropietario(Propietario propietario) {
        dbAdapter.open();
        dbAdapter.EliminarPropietario(propietario);
        dbAdapter.close();
    }

    public Propietario get_Propietario(String cedulaP) {
        dbAdapter.open();
        Propietario propietario = dbAdapter.get_Propietario(cedulaP);
        dbAdapter.close();
        return propietario;
    }

    public ArrayList<Propietario> get_all_Propietario() {
        dbAdapter.open();
        ArrayList<Propietario> lista = dbAdapter.get_all_Propietario();
        dbAdapter.close();
        return lista;
    }

    // ========== MÉTODOS PARA ZONA ==========

    public long InsertarZona(Zona zona) {
        dbAdapter.open();
        long id = dbAdapter.InsertarZona(zona);
        dbAdapter.close();
        return id;
    }

    public void EditarZona(Zona zona) {
        dbAdapter.open();
        dbAdapter.EditarZona(zona);
        dbAdapter.close();
    }

    public void EliminarZona(Zona zona) {
        dbAdapter.open();
        dbAdapter.EliminarZona(zona);
        dbAdapter.close();
    }

    public Zona get_Zona(int id) {
        dbAdapter.open();
        Zona zona = dbAdapter.get_Zona(id);
        dbAdapter.close();
        return zona;
    }

    public ArrayList<Zona> get_all_Zona() {
        dbAdapter.open();
        ArrayList<Zona> zonas = dbAdapter.get_all_Zona();
        dbAdapter.close();
        return zonas;
    }

    // ========== MÉTODOS PARA AUDIENCIA ==========

    public long InsertarAudiencia(Audiencia audiencia) {
        dbAdapter.open();
        long id = dbAdapter.InsertarAudiencia(audiencia);
        dbAdapter.close();
        return id;
    }

    public void EditarAudiencia(Audiencia audiencia) {
        dbAdapter.open();
        dbAdapter.EditarAudiencia(audiencia);
        dbAdapter.close();
    }

    public void EliminarAudiencia(Audiencia audiencia) {
        dbAdapter.open();
        dbAdapter.EliminarAudiencia(audiencia);
        dbAdapter.close();
    }

    public Audiencia get_Audiencia(int id) {
        dbAdapter.open();
        Audiencia audiencia = dbAdapter.get_Audiencia(id);
        dbAdapter.close();
        return audiencia;
    }

    public ArrayList<Audiencia> get_all_Audiencia() {
        dbAdapter.open();
        ArrayList<Audiencia> audiencias = dbAdapter.get_all_Audiencia();
        dbAdapter.close();
        return audiencias;
    }

    // ========== MÉTODOS PARA NORMASDET ==========

    public long InsertarNorma(NormasDeT norma) {
        dbAdapter.open();
        long id = dbAdapter.InsertarNorma(norma);
        dbAdapter.close();
        return id;
    }

    public void EditarNorma(NormasDeT norma) {
        dbAdapter.open();
        dbAdapter.EditarNorma(norma);
        dbAdapter.close();
    }

    public void EliminarNorma(NormasDeT norma) {
        dbAdapter.open();
        dbAdapter.EliminarNorma(norma);
        dbAdapter.close();
    }

    public NormasDeT get_Norma(int id) {
        dbAdapter.open();
        NormasDeT norma = dbAdapter.get_Norma(id);
        dbAdapter.close();
        return norma;
    }

    public ArrayList<NormasDeT> get_all_Norma() {
        dbAdapter.open();
        ArrayList<NormasDeT> normas = dbAdapter.get_all_Norma();
        dbAdapter.close();
        return normas;
    }

    // ========== MÉTODOS PARA VEHICULO ==========

    public long InsertarVehiculo(Vehiculo vehiculo) {
        dbAdapter.open();
        long id = dbAdapter.InsertarVehiculo(vehiculo);
        dbAdapter.close();
        return id;
    }

    public void EditarVehiculo(Vehiculo vehiculo) {
        dbAdapter.open();
        dbAdapter.EditarVehiculo(vehiculo);
        dbAdapter.close();
    }

    public void EliminarVehiculo(Vehiculo vehiculo) {
        dbAdapter.open();
        dbAdapter.EliminarVehiculo(vehiculo);
        dbAdapter.close();
    }

    public Vehiculo get_Vehiculo(String numPlaca) {
        dbAdapter.open();
        Vehiculo vehiculo = dbAdapter.get_Vehiculo(numPlaca);
        dbAdapter.close();
        return vehiculo;
    }

    public ArrayList<Vehiculo> get_all_Vehiculo() {
        dbAdapter.open();
        ArrayList<Vehiculo> vehiculos = dbAdapter.get_all_Vehiculo();
        dbAdapter.close();
        return vehiculos;
    }

    // ========== MÉTODOS PARA PUESDECONTROL ==========

    public long InsertarPuestoDeControl(PuesDeControl pc) {
        dbAdapter.open();
        long id = dbAdapter.InsertarPuestoDeControl(pc);
        dbAdapter.close();
        return id;
    }

    public void EditarPuestoDeControl(PuesDeControl pc) {
        dbAdapter.open();
        dbAdapter.EditarPuesDeControl(pc);
        dbAdapter.close();
    }

    public void EliminarPuestoDeControl(PuesDeControl pc) {
        dbAdapter.open();
        dbAdapter.EliminarPuesDeControl(pc);
        dbAdapter.close();
    }

    public PuesDeControl get_PuestoDeControl(int id) {
        dbAdapter.open();
        PuesDeControl pc = dbAdapter.get_PuesDeControl(id);
        dbAdapter.close();
        return pc;
    }

    public ArrayList<PuesDeControl> get_all_PuestoDeControl() {
        dbAdapter.open();
        ArrayList<PuesDeControl> lista = dbAdapter.get_all_PuesDeControl();
        dbAdapter.close();
        return lista;
    }

    // ========== MÉTODOS PARA AGENTE ==========

    public long InsertarAgente(Agente agente) {
        dbAdapter.open();
        long id = dbAdapter.InsertarAgente(agente);
        dbAdapter.close();
        return id;
    }

    public void EditarAgente(Agente agente) {
        dbAdapter.open();
        dbAdapter.EditarAgente(agente);
        dbAdapter.close();
    }

    public void EliminarAgente(Agente agente) {
        dbAdapter.open();
        dbAdapter.EliminarAgente(agente);
        dbAdapter.close();
    }

    public Agente get_Agente(int id) {
        dbAdapter.open();
        Agente agente = dbAdapter.get_Agente(id);
        dbAdapter.close();
        return agente;
    }

    public ArrayList<Agente> get_all_Agente() {
        dbAdapter.open();
        ArrayList<Agente> lista = dbAdapter.get_all_Agente();
        dbAdapter.close();
        return lista;
    }

    // ========== MÉTODOS PARA INFRACCION ==========

    public long InsertarInfraccion(Infraccion infraccion) {
        dbAdapter.open();
        long id = dbAdapter.InsertarInfraccion(infraccion);
        dbAdapter.close();
        return id;
    }

    public void EditarInfraccion(Infraccion infraccion) {
        dbAdapter.open();
        dbAdapter.EditarInfraccion(infraccion);
        dbAdapter.close();
    }

    public void EliminarInfraccion(Infraccion infraccion) {
        dbAdapter.open();
        dbAdapter.EliminarInfraccion(infraccion);
        dbAdapter.close();
    }

    public Infraccion get_Infraccion(int id) {
        dbAdapter.open();
        Infraccion infraccion = dbAdapter.get_Infraccion(id);
        dbAdapter.close();
        return infraccion;
    }

    public ArrayList<Infraccion> get_all_Infraccion() {
        dbAdapter.open();
        ArrayList<Infraccion> lista = dbAdapter.get_all_Infraccion();
        dbAdapter.close();
        return lista;
    }

    // ========== MÉTODOS PARA OFICINAGOB ==========

    public long InsertarOficinaGob(OficinaGob oficina) {
        dbAdapter.open();
        long id = dbAdapter.InsertarOficinaGob(oficina);
        dbAdapter.close();
        return id;
    }

    public void EditarOficinaGob(OficinaGob oficina) {
        dbAdapter.open();
        dbAdapter.EditarOficinaGob(oficina);
        dbAdapter.close();
    }

    public void EliminarOficinaGob(OficinaGob oficina) {
        dbAdapter.open();
        dbAdapter.EliminarOficinaGob(oficina);
        dbAdapter.close();
    }

    public OficinaGob get_OficinaGob(int id) {
        dbAdapter.open();
        OficinaGob oficina = dbAdapter.get_OficinaGob(id);
        dbAdapter.close();
        return oficina;
    }

    public ArrayList<OficinaGob> get_all_OficinaGob() {
        dbAdapter.open();
        ArrayList<OficinaGob> lista = dbAdapter.get_all_OficinaGob();
        dbAdapter.close();
        return lista;
    }

    // ========== MÉTODOS PARA ACCIDENTE ==========

    public long InsertarAccidente(Accidente accidente) {
        dbAdapter.open();
        long id = dbAdapter.InsertarAccidente(accidente);
        dbAdapter.close();
        return id;
    }

    public void EditarAccidente(Accidente accidente) {
        dbAdapter.open();
        dbAdapter.EditarAccidente(accidente);
        dbAdapter.close();
    }

    public void EliminarAccidente(Accidente accidente) {
        dbAdapter.open();
        dbAdapter.EliminarAccidente(accidente);
        dbAdapter.close();
    }

    public Accidente get_Accidente(int id) {
        dbAdapter.open();
        Accidente accidente = dbAdapter.get_Accidente(id);
        dbAdapter.close();
        return accidente;
    }

    public ArrayList<Accidente> get_all_Accidente() {
        dbAdapter.open();
        ArrayList<Accidente> accidentes = dbAdapter.get_all_Accidente();
        dbAdapter.close();
        return accidentes;
    }

    // ========== MÉTODOS PARA ACTA ==========

    public long InsertarActa(Acta acta) {
        dbAdapter.open();
        long id = dbAdapter.InsertarActa(acta);
        dbAdapter.close();
        return id;
    }

    public void EditarActa(Acta acta) {
        dbAdapter.open();
        dbAdapter.EditarActa(acta);
        dbAdapter.close();
    }

    public void EliminarActa(Acta acta) {
        dbAdapter.open();
        dbAdapter.EliminarActa(acta);
        dbAdapter.close();
    }

    public Acta get_Acta(int id) {
        dbAdapter.open();
        Acta acta = dbAdapter.get_Acta(id);
        dbAdapter.close();
        return acta;
    }

    public ArrayList<Acta> get_all_Acta() {
        dbAdapter.open();
        ArrayList<Acta> actas = dbAdapter.get_all_Acta();
        dbAdapter.close();
        return actas;
    }

}
