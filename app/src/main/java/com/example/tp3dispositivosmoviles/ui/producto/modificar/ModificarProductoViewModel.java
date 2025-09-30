//ui/producto/modificar/ModificarProductoViewModel
package com.example.tp3dispositivosmoviles.ui.producto.modificar;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.example.tp3dispositivosmoviles.modelo.Producto;
import com.example.tp3dispositivosmoviles.repositorio.ProductoRepositorio;


public class ModificarProductoViewModel extends AndroidViewModel {
  private final ProductoRepositorio repoProducto;
  private MutableLiveData<Producto> productoSeleccionado;
  private MutableLiveData<Evento> evento;

  public ModificarProductoViewModel(@NonNull Application application) {
    super(application);
    repoProducto = ProductoRepositorio.getInstance();
  }

  public LiveData<Producto> getProductoSeleccionado() {
    if (productoSeleccionado == null) {
      productoSeleccionado = new MutableLiveData<>();
    }
    return productoSeleccionado;
  }

  public void setProductoSeleccionado(Producto producto) {
    if (productoSeleccionado == null) {
      productoSeleccionado = new MutableLiveData<>();
    }
    productoSeleccionado.setValue(producto);
  }

  public LiveData<Evento> getEvento() {
    if (evento == null) {
      evento = new MutableLiveData<>();
    }
    return evento;
  }



  public void guardarCambios(String codigo, String descripcion, String precioStr) {
    Producto producto = productoSeleccionado != null ? productoSeleccionado.getValue() : null;
    if (producto == null) {
      evento.setValue(new Evento("No hay producto cargado", false));
      return;
    }

    // Validaciones
    if (codigo == null || codigo.trim().isEmpty()) {
      evento.setValue(new Evento("Ingrese un codigo", false));
      return;
    }
    if (!codigo.matches("\\d+")) {
      evento.setValue(new Evento("El codigo debe contener solo numeros", false));
      return;
    }
    if (descripcion == null || descripcion.trim().isEmpty()) {
      evento.setValue(new Evento("Ingrese una descripcion", false));
      return;
    }

    double precio;
    try {
      precio = Double.parseDouble(precioStr.trim());
      if (precio <= 0) {
        evento.setValue(new Evento("El precio debe ser mayor a 0", false));
        return;
      }
    } catch (NumberFormatException e) {
      evento.setValue(new Evento("Ingrese un precio valido", false));
      return;
    }

    // Actualizo
    producto.setCodigo(codigo);
    producto.setDescripcion(descripcion);
    producto.setPrecio(precio);

    repoProducto.actualizarProducto(producto);
    evento.setValue(new Evento("Producto actualizado correctamente", true));
  }

  // Clase Evento
  public static class Evento {
    private final String mensaje;
    private final boolean ok;

    public Evento(String mensaje, boolean ok) {
      this.mensaje = mensaje;
      this.ok = ok;
    }

    public String getMensaje() { return mensaje; }
    public boolean isOk() { return ok; }
  }
  public void clearEvento() {
    if (evento != null) {
      evento.setValue(null);
    }
  }



}