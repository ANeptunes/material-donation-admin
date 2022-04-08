package com.qyt.material.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qyt
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageVo<T> {
    private Long total;
    private List<T> data = new ArrayList<>();
}
