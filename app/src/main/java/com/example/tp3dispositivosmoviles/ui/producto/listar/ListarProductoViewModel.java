//ui/producto/listar/ListarProductoViewModel
package com.example.tp3dispositivosmoviles.ui.producto.listar;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;


import com.example.tp3dispositivosmoviles.modelo.Producto;
import com.example.tp3dispositivosmoviles.repositorio.ProductoRepositorio;

import java.util.List;

public class ListarProductoViewModel extends AndroidViewModel {
  private final ProductoRepositorio repoProducto;
  //private MutableLiveData<List<Producto>> mutableProducto;
  private MediatorLiveData<EstadoLista> estadoLista; /* Es una clase especial que extiende LiveData.

Se usa cuando quieres que tu LiveData dependa de multiples fuentes al mismo tiempo.
cuando quieras fusionar, derivar o reaccionar a multiples LiveData.*/


  public ListarProductoViewModel(@NonNull Application application) {
    super(application);
    repoProducto= ProductoRepositorio.getInstance();
  }

  /*public LiveData<List<Producto>> getMutableProducto() {
    if (mutableProducto == null) {
      mutableProducto = new MutableLiveData<>();
      mutableProducto.setValue(repoProducto.getProductosOrdenadosPorDescripcion());
      actualizarEstadoLista();
    }
    return mutableProducto;
  }*/

  // Observa directamente el LiveData del repo
  public LiveData<List<Producto>> getMutableProducto() {
    return repoProducto.getProductosLiveData();
  }

  public LiveData<EstadoLista> getEstadoLista() {
    if (estadoLista == null) {
        estadoLista = new MediatorLiveData<>();
      estadoLista.addSource(repoProducto.getProductosLiveData(), lista -> {
        if (lista == null || lista.isEmpty()) {
          estadoLista.setValue(EstadoLista.VACIA);
        } else {
          estadoLista.setValue(EstadoLista.CON_DATOS);
        }
      });
    }
    return estadoLista;
  }

  public Producto buscarProductoPorCodigo(String codigo) {
    return repoProducto.buscarPorCodigo(codigo);
  }

  public void actualizarProducto(Producto producto) {
    repoProducto.actualizarProducto(producto);
  }



  public enum EstadoLista {
    VACIA, CON_DATOS
  }

}
