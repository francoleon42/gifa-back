package com.gifa_api.service.impl;

import com.gifa_api.model.ItemDeInventario;
import com.gifa_api.model.Pedido;
import com.gifa_api.repository.IPedidoRepository;
import com.gifa_api.repository.ItemDeInventarioRepository;
import com.gifa_api.service.IPedidoService;
import com.gifa_api.utils.enums.EstadoPedido;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements IPedidoService {
    private final IPedidoRepository pedidoRepository;
    private final ItemDeInventarioRepository itemDeInventarioRepository;


    @Override
    public void createPedido(ItemDeInventario itemDeInventario, Integer cantidad) {
            Pedido pedido = Pedido
                    .builder()
                    .estadoPedido(EstadoPedido.PENDIENTE)
                    .item(itemDeInventario)
                    .cantidad(cantidad)
                    .fecha(LocalDate.now())
                    .motivo("Solcitud de stock ")
                    .build();
        pedidoRepository.save(pedido);
    }

    @Scheduled(fixedRate = 86400000)  // Ejecuta cada 24 horas (86400000 milisegundos)
    public void hacerPedidos() {
        List<ItemDeInventario> itemsDeInventario = itemDeInventarioRepository.findAll();
        for (ItemDeInventario item : itemsDeInventario) {
            if(item.getUmbral() >  item.getStock()  ){
                int cantidadMinima = (item.getUmbral() - item.getStock()) + item.getStock();;
                createPedido(item,cantidadMinima + 50 );
            }
        }

    }

    

}
