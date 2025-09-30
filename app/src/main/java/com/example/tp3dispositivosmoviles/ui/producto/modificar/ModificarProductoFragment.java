//ui/producto/modificar/ModificarProductoFragment
package com.example.tp3dispositivosmoviles.ui.producto.modificar;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.tp3dispositivosmoviles.R;
import com.example.tp3dispositivosmoviles.databinding.FragmentModificarProductoBinding;
import com.example.tp3dispositivosmoviles.modelo.Producto;
import com.example.tp3dispositivosmoviles.ui.producto.listar.ListarProductoViewModel;

import java.io.Serializable;

public class ModificarProductoFragment extends Fragment {

  private FragmentModificarProductoBinding binding;
  private ListarProductoViewModel vmListar;

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    binding = FragmentModificarProductoBinding.inflate(inflater, container, false);
    vmListar = new ViewModelProvider(requireActivity()).get(ListarProductoViewModel.class);

    binding.btnBuscar.setOnClickListener(v -> {
      String codigo = binding.etCodigo.getText().toString().trim();
      if (codigo.isEmpty()) {
        Toast.makeText(getContext(), "Ingrese un codigo", Toast.LENGTH_SHORT).show();
        return;
      }

      Producto producto = vmListar.buscarProductoPorCodigo(codigo);
      if (producto != null) {
        Bundle args = new Bundle();
        args.putSerializable("producto", producto);
        Navigation.findNavController(binding.getRoot())
                .navigate(R.id.action_nav_modificar_to_detalleFragment, args);
      } else {
        Toast.makeText(getContext(), "Producto no encontrado", Toast.LENGTH_SHORT).show();
      }
    });

    return binding.getRoot();
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binding = null;
  }
}