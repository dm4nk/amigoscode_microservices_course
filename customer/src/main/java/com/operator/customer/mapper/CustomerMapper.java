package com.operator.customer.mapper;

import com.operator.clients.customer.CustomerRequest;
import com.operator.clients.customer.CustomerResponse;
import com.operator.customer.db.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    List<CustomerResponse> toCustomerResponse(List<Customer> from);

    CustomerResponse toCustomerResponse(Customer from);

    Customer toCustomer(CustomerRequest from);

    void updateCustomer(@MappingTarget Customer to, CustomerRequest from);
}
