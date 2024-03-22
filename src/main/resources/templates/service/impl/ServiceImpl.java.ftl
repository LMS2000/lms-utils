package ${packageName}.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lms.contants.HttpCode;
import ${packageName}.constant.SqlConstant;
import com.lms.exception.BusinessException;
import ${packageName}.dto.Create${classTypeName}DTO;
import ${packageName}.dto.Query${classTypeName}DTO;
import ${packageName}.dto.Update${classTypeName}DTO;
import ${packageName}.entity.${classTypeName};
import ${packageName}.vo.${classTypeName}VO;
import ${packageName}.mapper.${classTypeName}Mapper;
import ${packageName}.service.${classTypeName}Service;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

import static ${packageName}.factory.${classTypeName}Factory.${classTypeName?upper_case}_CONVERTER;
/**
* ${classComment} 服务实现类
*
* @author ${author}
* @since ${createDate}
*/
@Service
public class ${classTypeName}ServiceImpl extends ServiceImpl<${classTypeName}Mapper, ${classTypeName}> implements ${classTypeName}Service {
/**
* 根据id获取明细
* @param id
* @return
*/
@Override
public ${classTypeName}VO selectById(Long id) {
return ${classTypeName?upper_case}_CONVERTER.to${classTypeName}VO(baseMapper.selectById(id));
}

/***
*   根据参数 查询数据
*   @param dto
*  @return
*/
@Override
public Page<${classTypeName}VO> selectRecordPage(Query${classTypeName}DTO dto) {
    long current = dto.getCurrent();
    long size = dto.getPageSize();
    // 限制爬虫
    BusinessException.throwIf(size > 20, HttpCode.PARAMS_ERROR);
    Page<${classTypeName}> ${className}Page = this.page(new Page<>(current, size),
        getQueryWrapper(dto));
        List<${classTypeName}VO> ${className}VOList = ${classTypeName?upper_case}_CONVERTER.toList${classTypeName}VO(${className}Page.getRecords());
            Page<${classTypeName}VO> ${className}VOPage=new Page<>(current,size);
            ${className}VOPage.setRecords(${className}VOList);
            ${className}VOPage.setTotal(${className}VOList.size());
                return ${className}VOPage;
                }

/***
*  根据参数 分页查询数据
*  @param dto
*  @return
*/
@Override
public List<${classTypeName}VO> selectRecordList(Query${classTypeName}DTO dto) {
   ${classTypeName} ${className}=new ${classTypeName}();
   BeanUtil.copyProperties(dto,${className});
   List<${classTypeName}> ${className}List= baseMapper.selectList(new QueryWrapper<>(${className}));
   return ${classTypeName?upper_case}_CONVERTER.toList${classTypeName}VO(${className}List);
   }

/**
* 根据参数修改数据
* @param dto
* @return
*/
@Override
public Boolean update(Update${classTypeName}DTO dto) {
    ${classTypeName}VO ${className}VO = selectById(dto.getId());
    BusinessException.throwIf(ObjectUtil.isEmpty(${className}VO),"修改内容不存在");
    ${classTypeName} ${className}=new  ${classTypeName}();
    BeanUtil.copyProperties(dto, ${className});
    return updateById(${className});
    }

/**
* 删除数据
* @param id
* @return
*/
@Override
public Boolean deleteById(Long id) {
    ${classTypeName}VO ${className}VO = selectById(id);
    BusinessException.throwIf(ObjectUtil.isEmpty(${className}VO),"删除内容不存在");
    return deleteById(id);
    }

/**
* 创建数据
* @param dto
* @return
*/
@Override
public Long create(Create${classTypeName}DTO dto) {
    ${classTypeName} ${className}=new  ${classTypeName}();
    BeanUtil.copyProperties(dto, ${className});
    save( ${className});
    return  ${className}.getId();
    }

/**
* 封装查询参数
* @param dto
* @return
*/
private QueryWrapper<${classTypeName}> getQueryWrapper(Query${classTypeName}DTO dto) {
     BusinessException.throwIf(dto == null, HttpCode.NOT_FOUND_ERROR);
     ${classTypeName} ${classTypeName}Query = new ${classTypeName}();
     BeanUtils.copyProperties(dto, ${classTypeName}Query);
     String sortField = dto.getSortField();
     String sortOrder = dto.getSortOrder();
     QueryWrapper<${classTypeName}> wrapper = new QueryWrapper<>(${classTypeName}Query);
     wrapper.orderBy(StringUtils.isNotBlank(sortField)
     , sortOrder.equals(SqlConstant.SORT_ORDER_ASC), sortField);
     return wrapper;
     }
}
