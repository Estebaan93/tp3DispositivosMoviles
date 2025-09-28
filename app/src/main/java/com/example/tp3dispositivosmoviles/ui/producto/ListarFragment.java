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

import com.example.tp3dispositivosmoviles.databinding.FragmentListarBinding;

public class ListarFragment extends Fragment {

  private FragmentListarBinding binding;
  private ProductoViewModel vm;

  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    binding= FragmentListarBinding.inflate(inflater, container, false);
    vm= new ViewModelProvider(requireActivity()).get(ProductoViewModel.class);
    View root = binding.getRoot();



    /*Config recyclerWiew*/
    vm.getMutableProducto().observe(getViewLifecycleOwner(), productos -> {
      ProductoAdapter adapter= new ProductoAdapter(productos, getLayoutInflater());
      binding.idLista.setAdapter(adapter);
    });

    binding.idLista.setLayoutManager(new LinearLayoutManager(getContext()));
    // Observar si la lista está vacia y mostrar/ocultar el TextView
    vm.getListaVacia().observe(getViewLifecycleOwner(), vacia -> {
      if (vacia) {
        binding.TvVacia.setVisibility(View.VISIBLE);
        binding.TvVacia.setText("La lista está vacía");
        binding.idLista.setVisibility(View.GONE);
      } else {
        binding.TvVacia.setVisibility(View.GONE);
        binding.idLista.setVisibility(View.VISIBLE);
      }
    });


    return root;
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binding = null;
  }
}