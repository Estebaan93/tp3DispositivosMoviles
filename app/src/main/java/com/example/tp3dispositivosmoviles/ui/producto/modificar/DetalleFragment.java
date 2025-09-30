//ui/producto/modificar/DetalleFragment
package com.example.tp3dispositivosmoviles.ui.producto.modificar;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.tp3dispositivosmoviles.R;
import com.example.tp3dispositivosmoviles.databinding.FragmentDetalleBinding;
import com.example.tp3dispositivosmoviles.modelo.Producto;
import com.example.tp3dispositivosmoviles.ui.producto.listar.ListarProductoViewModel;


public class DetalleFragment extends Fragment {
  private FragmentDetalleBinding binding;
  private ModificarProductoViewModel vm;
  private Producto producto;

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater,
                           ViewGroup container,
                           Bundle savedInstanceState) {

    binding = FragmentDetalleBinding.inflate(inflater, container, false);
    vm = new ViewModelProvider(requireActivity()).get(ModificarProductoViewModel.class);


    if (getArguments() != null) {
      Producto producto = (Producto) getArguments().getSerializable("producto");
      vm.setProductoSeleccionado(producto);
    }


    vm.getProductoSeleccionado().observe(getViewLifecycleOwner(), producto -> {
      if (producto != null) {
        binding.etCodigo.setText(producto.getCodigo());
        binding.etDescripcion.setText(producto.getDescripcion());
        binding.etPrecio.setText(String.valueOf(producto.getPrecio()));
      }
    });


    vm.getEvento().observe(getViewLifecycleOwner(), evento -> {
      if (evento != null) {
        Toast.makeText(getContext(), evento.getMensaje(), Toast.LENGTH_SHORT).show();
        if (evento.isOk()) {
          Navigation.findNavController(binding.getRoot())
                  .navigate(R.id.action_detalleFragment_to_nav_listar);
        }
        vm.clearEvento();
      }
    });



    binding.btnGuardar.setOnClickListener(v -> {
      vm.guardarCambios(
              binding.etCodigo.getText().toString().trim(),
              binding.etDescripcion.getText().toString().trim(),
              binding.etPrecio.getText().toString().trim()
      );
    });

    return binding.getRoot();
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binding = null;
  }

}