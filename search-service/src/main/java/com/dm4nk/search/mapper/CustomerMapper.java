package com.dm4nk.search.mapper;

import com.dm4nk.clients.search.CustomerResponse;
import com.dm4nk.search.config.MapStructConfig;
import com.dm4nk.search.domain.Customer;
import customer.public$.customer.Value;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapStructConfig.class)
public interface CustomerMapper {
    Customer toValue(Value from);

    void updateCustomer(@MappingTarget Customer to, Value from);

    default String map(CharSequence value) {
        return value.toString();
    }

    CustomerResponse toResponse(Customer customer);
}
