package com.operator.clients.customer;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class ChangeTarifPlanRequest {
    UUID customerId;
    String tarifPlan;
}
