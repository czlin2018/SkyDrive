package com.web.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "resourceMapping")
public class ResourceMapping implements Serializable {
    private Integer id;

    @Column(name = "mapping_id")
    private String mappingId;

    @Column(name = "resource_id")
    private String resourceId;

    @Column(name = "take_code")
    private String takeCode;

    @Column(name = "update_time")
    private Date updateTime;


}