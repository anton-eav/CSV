package com.CSV.test.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sim_cards")
public class SimModel {
    @Id
    UUID id;                            // unit.id / sim_cards.id
    String codeOperator;                // sim_cards.code_operator
    String ipSim;                       // sim_cards.ip_sim
    String msisdn;                      // sim_cards.msisdn
    String imsi;                        // sim_cards.imsi
    String iccid;                       // sim_cards.iccid
}
