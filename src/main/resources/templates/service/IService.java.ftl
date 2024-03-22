package ${packageName}.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import ${packageName}.vo.${classTypeName}VO;
import ${packageName}.dto.Query${classTypeName}DTO;
import ${packageName}.dto.Create${classTypeName}DTO;
import ${packageName}.dto.Update${classTypeName}DTO;
import java.util.List;
/**
* ${classComment}
* @author ${author}
* @since ${createDate}
*/
public interface ${classTypeName}Service {
/**
* 根据主键 查询详情
*
* @param id
* @return
*/
${classTypeName}VO selectById(Long id);

/***
*   根据参数 查询数据
*   分页
*   @param dto
*  @return
*/
IPage<${classTypeName}VO> selectRecordPage(Query${classTypeName}DTO dto);

/***
*   根据参数 查询数据
*   @param dto
*  @return
*/
List<${classTypeName}VO> selectRecordList(Query${classTypeName}DTO dto);
/***
*   根据主键 更新数据
*   查询不到数据 BusinessException 异常
*   @param dto
*  @return
*/
Boolean update(Update${classTypeName}DTO dto);


/***
*   根据主键 删除数据
*   查询不到数据 BusinessException 异常
*   @param id
*  @return
*/
Boolean deleteById(Long id);

/***
*   插入数据
*   新检查数据是否传 ，存在返回BusinessException 异常
*   vo 对象检查必填是否有数据
*   @param dto
*  @return
*/
Long create(Create${classTypeName}DTO dto);

}