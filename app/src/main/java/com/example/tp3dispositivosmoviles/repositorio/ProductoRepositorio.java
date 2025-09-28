//repositorio/ProductoRepositorio
package com.example.tp3dispositivosmoviles.repositorio;

import com.example.tp3dispositivosmoviles.modelo.Producto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ProductoRepositorio {
  /*Manejamos la lista en memoria, mientras la app este activa*/

  private static final ArrayList<Producto> productos= new ArrayList<>();
  /*revisar si esta bien que seaprivado, en realidad esta bien si hya q instanciar*/

  //Ordenamos por descripcion
  public List<Producto> getProductosOrdenadosPorDescripcion(){
    List<Producto> copia= new ArrayList<>(productos);
    Collections.sort(copia, new Comparator<Producto>(){
      @Override
        public int compare(Producto p1, Producto p2 ){
        return p1.getDescripcion().toLowerCase().compareTo(p2.getDescripcion().toLowerCase());
      }
    });
    return copia;
  }

  //verificamos si existe el codigo
  public boolean existeCodigo(String codigo){
    for(Producto p: productos){
      if(p.getCodigo().equalsIgnoreCase(codigo)) return true;
    }
    return false;
  }

  //Intenta agregar, si agrega es true
  public boolean agregar(Producto producto){
    if(existeCodigo(producto.getCodigo())){
      return false;
    }
    productos.add(producto);
    return true;
  }


}
