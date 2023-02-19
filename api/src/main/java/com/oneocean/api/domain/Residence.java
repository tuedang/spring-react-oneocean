package com.oneocean.api.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "residence")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Residence {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotBlank(message = "CEP is required")
    @Size(min = 8, max = 8, message = "CEP must have 8 characters")
    private String cep;

    @NotNull(message = "Number is required")
    private Integer number;

    @NotNull(message = "Quantity Residents is required")
    @Min(value = 1, message = "Quantity Residents should not be less than 1")
    @Column(name = "QT_RESIDENTS")
    private Integer qtResidents;

    private float latitude;

    private float longitude;
}
