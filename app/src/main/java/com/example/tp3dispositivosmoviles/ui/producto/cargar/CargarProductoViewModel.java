//ui/producto/cargar/CargarProductoViewModel
package com.example.tp3dispositivosmoviles.ui.producto.cargar;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.LiveData;

import com.example.tp3dispositivosmoviles.modelo.Producto;
import com.example.tp3dispositivosmoviles.repositorio.ProductoRepositorio;

public class CargarProductoViewModel extends AndroidViewModel {
  private final ProductoRepositorio repoProducto;
  private MutableLiveData <Evento> evento;
  private MutableLiveData<Void> limpiarCampos;
  public CargarProductoViewModel(@NonNull Application application) {
    super(application);
    repoProducto= ProductoRepositorio.getInstance();

  }

  public LiveData<Evento> getEvento(){
    if(evento==null){
      evento=new MutableLiveData<>();
    }
    return evento;
  }
  public LiveData<Void> getLimpiarCampos(){
    if(limpiarCampos==null){
      limpiarCampos= new MutableLiveData<>();
    }
    return limpiarCampos;
  }

  //Agregamos producto con validadcion
  public void agregarProducto (String codigo, String descripcion, String precioStr){
    double precio;

    try{
      precio= Double.parseDouble(precioStr.trim());
    }catch (NumberFormatException e){
      precio=0;
    }

    if(codigo.isEmpty() || descripcion.isEmpty() || precio<=0){
      evento.setValue(new Evento("Datos invalidos", false));
      return;
    }
    if (!codigo.matches("\\d+")) {
      evento.setValue(new Evento("El codigo debe contener solo numeros", false));
      return;
    }

    Producto nuevo= new Producto(codigo, descripcion, precio);
    boolean agregado= repoProducto.agregar(nuevo);


    //Si agrego actualiza el livedata con lista ordenada
    if (agregado) {
      evento.setValue(new Evento("Producto agregado correctamente", true));
      limpiarCampos.setValue(null); //dispara el evnto
    } else {
      evento.setValue(new Evento("Error: codigo duplicado", false));
    }
  }



  //Clase interna evento
  public static class Evento{
    private final String mensaje;
    private final boolean ok;

    public Evento(String mensaje, boolean ok) {
      this.mensaje = mensaje;
      this.ok = ok;
    }

    public String getMensaje() {return mensaje;}
    public boolean isOk(){return ok;}

  }

}
