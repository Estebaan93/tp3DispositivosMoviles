//ui/producto/listar/ProductoAdapter
package com.example.tp3dispositivosmoviles.ui.producto.listar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tp3dispositivosmoviles.R;
import com.example.tp3dispositivosmoviles.modelo.Producto;


import java.util.List;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder>{

  private List<Producto> productos;
  private LayoutInflater layoutInflater;


  public ProductoAdapter(List<Producto> productos, LayoutInflater layoutInflater){
    this.productos= productos;
    this.layoutInflater= layoutInflater;
    //notifyDataSetChanged();
  }

  @NonNull
  @Override
  public ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View itemView= layoutInflater.inflate(R.layout.item_producto, parent, false);

    return new ProductoViewHolder(itemView);
  }

  @Override
  public void onBindViewHolder(@NonNull ProductoViewHolder holder, int position) {
    Producto producto= productos.get(position);
    holder.tvCodigo.setText("Codigo: " +producto.getCodigo());
    holder.tvDescripcion.setText("Descripcion: "+ producto.getDescripcion());
    holder.tvPrecio.setText("Precio $ "+producto.getPrecio());
  }

  @Override
  public int getItemCount() {
    return productos.size();
  }


  public class ProductoViewHolder extends RecyclerView.ViewHolder{
    TextView tvCodigo, tvDescripcion, tvPrecio;
    public ProductoViewHolder(@NonNull View itemView){
      super(itemView);
      tvCodigo= itemView.findViewById(R.id.tvCodigo);
      tvDescripcion= itemView.findViewById(R.id.tvDescripcion);
      tvPrecio= itemView.findViewById(R.id.tvPrecio);

    }

  }

}
