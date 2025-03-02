package edu.masanz.da.ta.dao;

import edu.masanz.da.ta.dto.*;
import edu.masanz.da.ta.utils.Security;

import java.util.*;

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
        for (String puja : PUJAS){
            String[] datos = puja.split(splitter);
            long idItem = Long.parseLong(datos[0]);
            String nombreUsuario = datos[1];
            int precio = Integer.parseInt(datos[2]);
            String instante = datos[3];
            Puja nuevaPuja = new Puja(idItem,nombreUsuario,precio,instante);
            if (!mapaPujas.containsKey(idItem)){
                mapaPujas.put(idItem,new ArrayList<>());
            }
            mapaPujas.get(idItem).add(nuevaPuja);
        }
    }
    //endregion

    //region Usuarios
    public static boolean autenticar(String nombreUsuario, String password) {
//        return password.equals("1234");
        // TODO 04 autenticar
        if (!mapaUsuarios.containsKey(nombreUsuario)){
            return false;
        }
        Usuario usuario = mapaUsuarios.get(nombreUsuario);
        return Security.validateHashedPasswordSalt(password,usuario.getSal(),usuario.getHashPwSal());
    }

    public static boolean esAdmin(String nombreUsuario) {
//        return nombreUsuario.equalsIgnoreCase("Admin");
        // TODO 05 esAdmin
        if (!mapaUsuarios.containsKey(nombreUsuario)){
            return false;
        }
        Usuario usuario = mapaUsuarios.get(nombreUsuario);
        return usuario.getRol().equalsIgnoreCase("Admin");
    }

    public static List<Usuario> obtenerUsuarios() {
        // TODO 06 obtenerUsuarios
        List<Usuario> usuarios = new ArrayList<>();
        if (mapaUsuarios.isEmpty()) {
            return null;
        }
        usuarios.addAll(mapaUsuarios.values());
        return usuarios;
    }

    public static boolean crearUsuario(String nombre, String password, boolean esAdmin) {
        // TODO 07 crearUsuario
        if (mapaUsuarios.containsKey(nombre)){
            return false;
        }
        String salt = Security.generateSalt();
        String hashPwSal = Security.hash(password);
        String rol = esAdmin ? "Admin" : "User";
        Usuario nuevoUsuario = new Usuario(nombre,salt,hashPwSal,rol);
        mapaUsuarios.put(nombre,nuevoUsuario);
        return true;
    }

    public static boolean modificarPasswordUsuario(String nombre, String password) {
        // TODO 08 modificarPasswordUsuario
        if(!mapaUsuarios.containsKey(nombre)){
            return false;
        }
        Usuario usuario = mapaUsuarios.get(nombre);
        String nuevoSalt = Security.generateSalt();
        String nuevoHashSalt = Security.hash(password);

        Usuario usuarioModificado = new Usuario(usuario.getNombre(),nuevoSalt,nuevoHashSalt,usuario.getRol());
        mapaUsuarios.put(usuario.getNombre(), usuarioModificado);

        return true;

    }

    public static boolean modificarRolUsuario(String nombre, String rol) {
        // TODO 09 modificarRolUsuario
        if (!mapaUsuarios.containsKey(nombre)){
            return false;
        }
        Usuario usuario = mapaUsuarios.get(nombre);
        Usuario usuarioNuevoRol = new Usuario(usuario.getNombre(), usuario.getSal(), usuario.getHashPwSal(), rol);
        mapaUsuarios.put(usuario.getNombre(),usuarioNuevoRol);
        return true;
    }

    public static boolean eliminarUsuario(String nombre) {
        // TODO 10 eliminarUsuario
        if (!mapaUsuarios.containsKey(nombre)){
            return false;
        }
        mapaUsuarios.remove(nombre);
        return true;
    }

    //endregion

    //region Validación de artículos
    public static List<Item> obtenerArticulosPendientes() {
        // TODO 11 obtenerArticulosPendientes
        List<Item> items = new ArrayList<>();
        if (mapaItems.isEmpty()){
            return null;
        }
        for (Item item : mapaItems.values()){
            if (item.getEstado() == 0){
                items.add(item);
            }
        }
        return items;
    }

    public static boolean validarArticulo(long id, boolean valido) {
        // TODO 12 validarArticulo
        if (!mapaItems.containsKey(id)){
            return false;
        }
        Item item = mapaItems.get(id);
        if (valido){
            item.setEstado(1);
        } else {
            item.setEstado(-1);
        }
        return true;

    }

    public static boolean validarTodos() {
        // TODO 13 validarTodos
        if (mapaItems.isEmpty()){
            return false;
        }
        for (Item item : mapaItems.values()){
            if (item.getEstado() == 0){
                item.setEstado(1);
            }
        }
        return true;
    }
    //endregion

    //region Gestión de artículos y pujas de administrador
    public static List<ItemPujas> obtenerArticulosConPujas() {
        // TODO 14 obtenerArticulosConPujas
        List<ItemPujas> itemsPujas = new ArrayList<>();
        if (mapaItems.isEmpty()){
            return null;
        }
        for (Item item : mapaItems.values()){
            if (mapaPujas.containsKey(item.getId())){
                List<Puja> pujas = mapaPujas.get(item.getId());
                ItemPujas itemPuj = new ItemPujas(item,pujas);
                itemsPujas.add(itemPuj);
            }
        }
        return itemsPujas;
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
