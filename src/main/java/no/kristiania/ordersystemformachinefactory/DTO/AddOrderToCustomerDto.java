package no.kristiania.ordersystemformachinefactory.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class AddOrderToCustomerDto {
    private Date orderDate;
    private Long customerId;

    public AddOrderToCustomerDto(Date orderDate, Long customerId){
        this.customerId = customerId;
        this.orderDate = orderDate;
    }
}
