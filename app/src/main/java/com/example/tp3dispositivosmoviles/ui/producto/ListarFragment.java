//ui/producto/ListarFragment
package com.example.tp3dispositivosmoviles.ui.producto;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.databinding.DataBindingUtil;


import com.example.tp3dispositivosmoviles.R;
import com.example.tp3dispositivosmoviles.databinding.FragmentListarBinding;

public class ListarFragment extends Fragment {

  private FragmentListarBinding binding;
  private ListarProductoViewModel vm;

  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_listar, container, false);
    vm = new ViewModelProvider(requireActivity()).get(ListarProductoViewModel.class);
    View root = binding.getRoot();

    binding.setVm(vm);
    binding.setLifecycleOwner(getViewLifecycleOwner());

    // Configurar RecyclerView
    binding.idLista.setLayoutManager(new LinearLayoutManager(getContext()));

    // Observar lista de productos
    vm.getMutableProducto().observe(getViewLifecycleOwner(), productos -> {
      ProductoAdapter adapter = new ProductoAdapter(productos, getLayoutInflater());
      binding.idLista.setAdapter(adapter);
    });

    return root;
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binding = null;
  }
}