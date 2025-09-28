//ui/producto/ProductoViewModel
package com.example.tp3dispositivosmoviles.ui.producto;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tp3dispositivosmoviles.modelo.Producto;
import com.example.tp3dispositivosmoviles.repositorio.ProductoRepositorio;


import java.util.List;

public class ProductoViewModel extends AndroidViewModel {

  private ProductoRepositorio repoProducto;
  private MutableLiveData<List<Producto>> mutableProducto;
  private MutableLiveData<Evento> evento;
  private MutableLiveData<Boolean> listaVacia;

  public ProductoViewModel(@NonNull Application application) {
    super(application);
    repoProducto= new ProductoRepositorio();
  }

  public LiveData<Evento> getEvento(){
    if(evento==null){
      evento= new MutableLiveData<>();
    }
    return evento;
  }
  public LiveData<List<Producto>> getMutableProducto(){
    if(mutableProducto==null){
      mutableProducto= new MutableLiveData<>();
      //inicializo con lista vacia
      mutableProducto.setValue(repoProducto.getProductosOrdenadosPorDescripcion());
      actualizarListaVacia();
    }
    return mutableProducto;
  }

  public LiveData<Boolean> getListaVacia() { //expone si esta vacía
    if (listaVacia == null) {
      listaVacia = new MutableLiveData<>(true);
    }
    return listaVacia;
  }


//Agregamos producto con validadcion
  public void agregarProducto (String codigo, String descripcion, double precio){
    if(codigo.isEmpty() || descripcion.isEmpty() || precio<=0){
      evento.setValue(new Evento("Datos invalidos", false));
      return;
    }

    Producto nuevo= new Producto(codigo, descripcion, precio);
    boolean agregado= repoProducto.agregar(nuevo);


    //Si agrego actualiza el livedata con lista ordenada
    if (agregado){
      mutableProducto.setValue(repoProducto.getProductosOrdenadosPorDescripcion());
      evento.setValue(new Evento("Producto agregado correctamente", true));
    }else {
      evento.setValue(new Evento("Error codigo duplicado", false));
    }
    actualizarListaVacia();
  }
/*aca llamamos los metodos del repositorio, y se la pasamos atravez del mutable a la vista*/
  /*la lista es estatica, y no puede ser privada sino en cada instacia debemos crear un new*/




  private void actualizarListaVacia(){
    if(listaVacia==null){
      listaVacia= new MutableLiveData<>();
    }
    List<Producto> productos= mutableProducto.getValue();
    listaVacia.setValue(productos==null || productos.isEmpty());
  }

  //Clase interna evento
  public static class Evento{
    private final String mensaje;
    private final boolean limpiarCampos;

    public Evento(String mensaje, boolean limpiarCampos) {
      this.mensaje = mensaje;
      this.limpiarCampos = limpiarCampos;
    }

    public String getMensaje() {
      return mensaje;
    }

    public boolean isLimpiarCampos() {
      return limpiarCampos;
    }

  }


}