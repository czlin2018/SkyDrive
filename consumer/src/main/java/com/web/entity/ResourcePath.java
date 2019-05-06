package com.web.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "resourcePath")
public class ResourcePath implements Serializable{
    private Integer id;

    @Id
    @Column(name = "node_id")
    private String nodeId;

    @Column(name = "node_name")
    private String nodeName;

    @Column(name = "node_path")
    private String nodePath;
    @Column(name = "father_node")
    private String fatherNode;

    @Column(name = "is_frist")
    private Integer isFrist;

    @Column(name = "update_time")
    private Date updateTime;
}