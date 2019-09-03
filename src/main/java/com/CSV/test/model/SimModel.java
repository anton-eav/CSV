package com.CSV.test.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
public class SimModel {

    UUID id;                            // unit.id / sim_cards.id
    String codeOperator;                // sim_cards.code_operator
    String ipSim;                       // sim_cards.ip_sim
    String msisdn;                      // sim_cards.msisdn
    String imsi;                        // sim_cards.imsi
    String iccid;                       // sim_cards.iccid
    String dateManufacture;             // unit.date_manufacture


}
