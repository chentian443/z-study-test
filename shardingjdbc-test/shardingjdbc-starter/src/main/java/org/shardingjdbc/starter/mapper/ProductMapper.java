package org.shardingjdbc.starter.mapper;

import org.shardingjdbc.starter.entity.Product;

public interface ProductMapper {
    int deleteByPrimaryKey(Long productId);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Long productId);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);
}