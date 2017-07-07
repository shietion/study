package edu.development.common.base;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import edu.development.common.db.DataSourceEnum;
import edu.development.common.db.DynamicDataSource;

/**
 * 实现BaseService抽象类 Created by atminer on 2017/01/07.
 */
public abstract class BaseServiceImpl<Mapper, Record> implements BaseService<Record> {

    public Mapper mapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
	try {
	    DynamicDataSource.setDataSource(DataSourceEnum.MASTER.getName());
	    Method deleteByPrimaryKey = mapper.getClass().getDeclaredMethod("deleteByPrimaryKey", id.getClass());
	    Object result = deleteByPrimaryKey.invoke(mapper, id);
	    return Integer.parseInt(String.valueOf(result));
	} catch (Exception e) {
	    e.printStackTrace();
	}
	DynamicDataSource.clearDataSource();
	return 0;
    }

    @Override
    public int insert(Record record) {
	try {
	    DynamicDataSource.setDataSource(DataSourceEnum.MASTER.getName());
	    Method insert = mapper.getClass().getDeclaredMethod("insert", record.getClass());
	    Object result = insert.invoke(mapper, record);
	    return Integer.parseInt(String.valueOf(result));
	} catch (Exception e) {
	    e.printStackTrace();
	}
	DynamicDataSource.clearDataSource();
	return 0;
    }

    @Override
    public int insertSelective(Record record) {
	try {
	    DynamicDataSource.setDataSource(DataSourceEnum.MASTER.getName());
	    Method insertSelective = mapper.getClass().getDeclaredMethod("insertSelective", record.getClass());
	    Object result = insertSelective.invoke(mapper, record);
	    return Integer.parseInt(String.valueOf(result));
	} catch (Exception e) {
	    e.printStackTrace();
	}
	DynamicDataSource.clearDataSource();
	return 0;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Record> selectByExample(Record example) {
	try {
	    DynamicDataSource.setDataSource(DataSourceEnum.SLAVE.getName());
	    Method selectByExample = mapper.getClass().getDeclaredMethod("selectByExample", example.getClass());
	    Object result = selectByExample.invoke(mapper, example);
	    return (List<Record>) result;
	} catch (Exception e) {
	    e.printStackTrace();
	}
	DynamicDataSource.clearDataSource();
	return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Record selectByPrimaryKey(Long id) {
	try {
	    DynamicDataSource.setDataSource(DataSourceEnum.SLAVE.getName());
	    Method selectByPrimaryKey = mapper.getClass().getDeclaredMethod("selectByPrimaryKey", id.getClass());
	    Object result = selectByPrimaryKey.invoke(mapper, id);
	    return (Record) result;
	} catch (Exception e) {
	    e.printStackTrace();
	}
	DynamicDataSource.clearDataSource();
	return null;
    }

    @Override
    public int updateByPrimaryKeySelective(Record record) {
	try {
	    DynamicDataSource.setDataSource(DataSourceEnum.MASTER.getName());
	    Method updateByPrimaryKeySelective = mapper.getClass().getDeclaredMethod("updateByPrimaryKeySelective", record.getClass());
	    Object result = updateByPrimaryKeySelective.invoke(mapper, record);
	    return Integer.parseInt(String.valueOf(result));
	} catch (Exception e) {
	    e.printStackTrace();
	}
	DynamicDataSource.clearDataSource();
	return 0;
    }

    @Override
    public int updateByPrimaryKey(Record record) {
	try {
	    DynamicDataSource.setDataSource(DataSourceEnum.MASTER.getName());
	    Method updateByPrimaryKey = mapper.getClass().getDeclaredMethod("updateByPrimaryKey", record.getClass());
	    Object result = updateByPrimaryKey.invoke(mapper, record);
	    return Integer.parseInt(String.valueOf(result));
	} catch (Exception e) {
	    e.printStackTrace();
	}
	DynamicDataSource.clearDataSource();
	return 0;
    }

    @Override
    public int deleteByPrimaryKeys(String ids) {
	try {
	    if (StringUtils.isBlank(ids)) {
		return 0;
	    }
	    DynamicDataSource.setDataSource(DataSourceEnum.MASTER.getName());
	    String[] idArray = ids.split("-");
	    int count = 0;
	    for (String idStr : idArray) {
		if (StringUtils.isBlank(idStr)) {
		    continue;
		}
		Integer id = Integer.parseInt(idStr);
		Method deleteByPrimaryKey = mapper.getClass().getDeclaredMethod("deleteByPrimaryKey", id.getClass());
		Object result = deleteByPrimaryKey.invoke(mapper, id);
		count += Integer.parseInt(String.valueOf(result));
	    }
	    return count;
	} catch (Exception e) {
	    e.printStackTrace();
	}
	DynamicDataSource.clearDataSource();
	return 0;
    }

    @Override
    public void initMapper() {
	this.mapper = SpringContextUtil.getBean(getMapperClass());
    }

    /**
     * 获取类泛型class
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    public Class<Mapper> getMapperClass() {
	return (Class<Mapper>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

}