//ui/producto/CargarFragment
package com.example.tp3dispositivosmoviles.ui.producto;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tp3dispositivosmoviles.databinding.FragmentCargarBinding;


public class CargarFragment extends Fragment {

  private FragmentCargarBinding binding;
  private ProductoViewModel vm;

  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    //com.example.tp3dispositivosmoviles.ui.cargar.CargarViewModel cargarViewModel = new ViewModelProvider(this).get(com.example.tp3dispositivosmoviles.ui.cargar.CargarViewModel.class);

    binding = FragmentCargarBinding.inflate(inflater, container, false);
    View root = binding.getRoot();

    //final TextView textView = binding.textGallery;
    //cargarViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
    vm= new ViewModelProvider(requireActivity()).get(ProductoViewModel.class);

    //Observar mensajes desde el vm
    vm.getEvento().observe(getViewLifecycleOwner(),evento->{
      Toast.makeText(getContext(), evento.getMensaje(), Toast.LENGTH_SHORT).show();
      if (evento.isLimpiarCampos()) {
        binding.etCodigo.setText("");
        binding.etDescripcion.setText("");
        binding.etPrecio.setText("");
      }
    });

    //cargar
    binding.btnCargarproducto.setOnClickListener(v -> {
      //SETEAR LOS CAMPOS PARA DESPUESD DE LLENAR LOS  CAMPOS, EVITAR USAR CONDICIONES
      String codigo= binding.etCodigo.getText().toString().trim();
      String descripcion= binding.etDescripcion.getText().toString().trim();


      double precio = 0;
      try {
        precio = Double.parseDouble(binding.etPrecio.getText().toString().trim());
      } catch (NumberFormatException e) {
        // Si no es numrico, el ViewModel se encargara de marcarlo invalido
      }

      vm.agregarProducto(codigo, descripcion, precio);

    });

    return root;
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binding = null;
  }
}