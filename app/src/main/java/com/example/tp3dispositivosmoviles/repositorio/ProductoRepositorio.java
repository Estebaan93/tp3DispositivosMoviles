//repositorio/ProductoRepositorio
package com.example.tp3dispositivosmoviles.repositorio;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.tp3dispositivosmoviles.modelo.Producto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ProductoRepositorio {
  /*Manejamos la lista en memoria, mientras la app este activa*/
  private static ProductoRepositorio instancia; //asegura que toda la app use la misma lista de productos en memoria.
  private static final ArrayList<Producto> productos= new ArrayList<>();
  /**/
  private MutableLiveData<List<Producto>> productosLiveData;
  private ProductoRepositorio() {
    // constructor privado: evita que se cree con "new"
  }

  public static synchronized ProductoRepositorio getInstance() {
    if (instancia == null) {
      instancia = new ProductoRepositorio();
    }
    return instancia;
  }

  public LiveData<List<Producto>> getProductosLiveData() {
    if (productosLiveData == null) {
      productosLiveData = new MutableLiveData<>();
      productosLiveData.setValue(new ArrayList<>()); // arranca vacio
    }
    return productosLiveData;
  }


  //Ordenamos por descripcion
  /*public List<Producto> getProductosOrdenadosPorDescripcion() {
    List<Producto> copia = new ArrayList<>(productos);
    Collections.sort(copia, (p1, p2) -> p1.getDescripcion().compareToIgnoreCase(p2.getDescripcion()));
    return copia;
  }*/

  //verificamos si existe el codigo
  /*public boolean existeCodigo(String codigo){
    for(Producto p: productos){
      if(p.getCodigo().equalsIgnoreCase(codigo)) return true;
    }
    return false;
  }*/

  //Intenta agregar, si agrega es true
  public boolean agregar(Producto producto) {
    for (Producto p : productos) {
      if (p.getCodigo().equalsIgnoreCase(producto.getCodigo())) {
        return false;
      }
    }
    productos.add(producto);
    actualizarLista();
    return true;
  }

  private void actualizarLista() {
    if (productosLiveData == null) {
      productosLiveData = new MutableLiveData<>();
    }
    List<Producto> copia = new ArrayList<>(productos);
    Collections.sort(copia, (p1, p2) -> p1.getDescripcion().compareToIgnoreCase(p2.getDescripcion()));
    productosLiveData.setValue(copia);
  }

}
