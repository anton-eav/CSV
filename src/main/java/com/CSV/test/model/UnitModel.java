package com.CSV.test.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "units")
public class UnitModel {
    @Id
    UUID id;                            // unit.id / sim_cards.id
    Date dateManufacture;             // unit.date_manufacture
    String codeNomenclature = "SIM";
    String codePredefined = "SIM";
}
