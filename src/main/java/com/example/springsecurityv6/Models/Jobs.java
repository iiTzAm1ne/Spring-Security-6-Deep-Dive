package com.example.springsecurityv6.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Jobs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jobId;
    private String jobOfferTittle;
    private String jobOfferPost;
    private String jobOfferOrganisation;
    @Column(length = 99999)
    private String jobOfferDescription;

}
