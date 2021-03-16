package top.inson.springboot.base;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface ITkBaseMapper<T> extends Mapper<T>, MySqlMapper<T> {


}
