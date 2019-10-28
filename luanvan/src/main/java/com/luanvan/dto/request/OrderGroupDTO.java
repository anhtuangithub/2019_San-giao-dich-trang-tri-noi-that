package com.luanvan.dto.request;

import java.util.List;

import com.luanvan.model.OrderGroup;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class OrderGroupDTO {

	private OrderGroup orderGroup;
	private List<OrderDTO> orderDTO;
}
