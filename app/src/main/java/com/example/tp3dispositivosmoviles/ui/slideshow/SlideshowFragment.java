//ui/slideshow/SlideshowFragment
package com.example.tp3dispositivosmoviles.ui.slideshow;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tp3dispositivosmoviles.databinding.FragmentSlideshowBinding;

public class SlideshowFragment extends Fragment {

  private FragmentSlideshowBinding binding;

  public View onCreateView(@NonNull LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState) {
    SlideshowViewModel slideshowViewModel =
            new ViewModelProvider(this).get(SlideshowViewModel.class);

    binding = FragmentSlideshowBinding.inflate(inflater, container, false);
    View root = binding.getRoot();

    final TextView textView = binding.textSlideshow;
    slideshowViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);


    return root;
  }

  @Override
  public void onResume(){
    super.onResume();
    salir();
  }

  private void salir(){
    new AlertDialog.Builder(requireContext())
            .setTitle("Cerrar app")
            .setMessage("Salir?")
            .setPositiveButton("Si", (dialog, which) -> {
              //salir
              requireActivity().finishAffinity();
            })
            .setNegativeButton("No", (dialog, which) -> {
             dialog.dismiss();
            })
            .show();
  }

  /*ESTE FRAGMENT ES DEL SALIR*/

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binding = null;
  }
}