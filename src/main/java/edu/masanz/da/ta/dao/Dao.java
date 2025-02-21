package edu.masanz.da.ta.dao;

import edu.masanz.da.ta.dto.*;
import edu.masanz.da.ta.utils.Security;

import java.util.*;

import static edu.masanz.da.ta.conf.Ctes.*;
import static edu.masanz.da.ta.conf.Ini.*;

/**
 * Clase que simula la capa de acceso a datos. Cuando veamos las interfaces crearemos una interfaz para esta clase.
 * También crearemos una clase que implemente esa interfaz y que se conecte a una base de datos relacional.
 * Y una clase servicio que podrá utilizar cualquiera de las dos implementaciones, la simulada, la real u otra.
 * Por ahora, simplemente es una clase con métodos estáticos que simulan la interacción con una base de datos.
 */
public class Dao {


    //region Colecciones que simulan la base de datos
    private static Map<String, Usuario> mapaUsuarios;

    private static Map<Long, Item> mapaItems;

    private static Map<Long, List<Puja>> mapaPujas;
    //endregion

    //region Inicialización de la base de datos simulada
    public static void ini() {
        iniMapaUsuarios();
        iniMapaItems();
        iniMapaPujas();
    }

    private static void iniMapaUsuarios() {
        // TODO 01 iniMapaUsuarios
        String splitter = SPLITTER;
        mapaUsuarios = new HashMap<>();
        for (String usuario : USUARIOS){
            String[] partes = usuario.split(splitter);
            String nombre = partes[0];
            String sal = partes[1];
            String hash = partes[2];
            String rol = partes[3];
            Usuario nuevoUsuario = new Usuario(nombre,sal,hash,rol);
            mapaUsuarios.put(nombre,nuevoUsuario);
        }
    }

    private static void iniMapaItems() {
        // TODO 02 iniMapaItems
        String splitter = SPLITTER;
        mapaItems = new HashMap<>();
        for (String item : ITEMS){
            String [] datos = item.split(splitter);
            long id = Long.parseLong(datos[0]);
            String nombre = datos[1];
            String desc = datos[2];
            int precio = Integer.parseInt(datos[3]);
            String foto = datos[4];
            String nombreUsuario = datos[5];
            int estado = Integer.parseInt(datos[6]);
            boolean historico = Boolean.parseBoolean(datos[7]);
            Item nuevoItem = new Item(id,nombre,desc,precio,foto,nombreUsuario,estado,historico);
            mapaItems.put(id,nuevoItem);
        }
    }

    private static void iniMapaPujas() {
        // TODO 03 iniMapaPujas
        String splitter = SPLITTER;
        mapaPujas = new HashMap<>();
        long contador = 1;
        List<Puja> pujas;
        for (String puja : PUJAS){
         String [] datos = puja.split(splitter);
         pujas = new ArrayList<>();
         long idItem = Long.parseLong(datos[0]);
         String nobmbreUsaurio = datos[1];
         int precio = Integer.parseInt(datos[2]);
         String fecha = datos[3];
         Puja puja1 = new Puja(idItem,nobmbreUsaurio,precio,fecha);
         pujas.add(puja1);
         mapaPujas.put(contador,pujas);
         contador++;
        }
    }
    //endregion

    //region Usuarios
    public static boolean autenticar(String nombreUsuario, String password) {
//        return password.equals("1234");
        // TODO 04 autenticar
        return false;
    }

    public static boolean esAdmin(String nombreUsuario) {
//        return nombreUsuario.equalsIgnoreCase("Admin");
        // TODO 05 esAdmin
        return false;
    }

    public static List<Usuario> obtenerUsuarios() {
        // TODO 06 obtenerUsuarios
        return null;
    }

    public static boolean crearUsuario(String nombre, String password, boolean esAdmin) {
        // TODO 07 crearUsuario
        return true;
    }

    public static boolean modificarPasswordUsuario(String nombre, String password) {
        // TODO 08 modificarPasswordUsuario
        return false;
    }

    public static boolean modificarRolUsuario(String nombre, String rol) {
        // TODO 09 modificarRolUsuario
        return false;
    }

    public static boolean eliminarUsuario(String nombre) {
        // TODO 10 eliminarUsuario
        return true;
    }

    //endregion

    //region Validación de artículos
    public static List<Item> obtenerArticulosPendientes() {
        // TODO 11 obtenerArticulosPendientes
        return null;
    }

    public static boolean validarArticulo(long id, boolean valido) {
        // TODO 12 validarArticulo
        return false;
    }

    public static boolean validarTodos() {
        // TODO 13 validarTodos
        return true;
    }
    //endregion

    //region Gestión de artículos y pujas de administrador
    public static List<ItemPujas> obtenerArticulosConPujas() {
        // TODO 14 obtenerArticulosConPujas
        return null;
    }

    public static boolean resetearSubasta() {
        // TODO 15 resetearSubasta
        return true;
    }

    public static List<PujaItem> obtenerHistoricoGanadores() {
        // TODO 16 obtenerHistoricoGanadores
        return null;
    }
    //endregion

    //region Acciones por parte de usuario normal (no admin)

    public static Item obtenerArticuloPujable(long idArt) {
        // TODO 17 obtenerArticuloPujable
        return null;
    }

    public static List<Item> obtenerArticulosPujables() {
        // TODO 18 obtenerArticulosPujables
        return null;
    }

    public static boolean pujarArticulo(long idArt, String nombre, int precio) {
        // TODO 19 pujarArticulo
        return false;
    }

    public static List<PujaItem> obtenerPujasVigentesUsuario(String nombreUsuario) {
        // TODO 20 obtenerPujasVigentesUsuario
        return null;
    }

    public static boolean ofrecerArticulo(Item item) {
        // TODO 21 ofrecerArticulo
        return true;
    }

    //endregion
}
