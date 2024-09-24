package com.dm4nk.customer.mapper;

import com.dm4nk.clients.customer.CustomerCreationRequest;
import com.dm4nk.clients.customer.CustomerResponse;
import com.dm4nk.customer.db.model.Customer;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    List<CustomerResponse> toCustomerResponse(List<Customer> from);

    CustomerResponse toCustomerResponse(Customer from);

    Customer toCustomer(CustomerCreationRequest from);
}
