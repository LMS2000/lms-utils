package ${packageName}.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lms.common.BaseResponse;
import com.lms.lmscommon.common.ResultUtils;
import ${packageName}.dto.Create${classTypeName}DTO;
import ${packageName}.dto.Query${classTypeName}DTO;
import ${packageName}.dto.Update${classTypeName}DTO;
import ${packageName}.vo.${classTypeName}VO;
import ${packageName}.service.${classTypeName}Service;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
* ${classComment} 控制层
* @author ${author}
* @since ${createDate}
*/
@RestController
@RequestMapping("/${className}")
public class ${classTypeName}Controller {

@Resource
private ${classTypeName}Service ${className}Service;

/**
* 创建
* @param dto
* @return
*/
@PostMapping("/add")
public BaseResponse<Long> create(@RequestBody Create${classTypeName}DTO dto){
    return ResultUtils.success( ${className}Service.create(dto));
    }

/**
* 分页查询
* @param dto
* @return
*/
@PostMapping("/page")
public BaseResponse<IPage<${classTypeName}VO>> page(@RequestBody Query${classTypeName}DTO dto){
   return ResultUtils.success(${className}Service.selectRecordPage(dto));
  }

/**
* 查询列表
* @param dto
* @return
*/
@PostMapping("/list")
public BaseResponse<List<${classTypeName}VO>> list(@RequestBody Query${classTypeName}DTO dto){
   return ResultUtils.success(${className}Service.selectRecordList(dto));
}

/**
* 修改
* @param dto
* @return
*/
@PostMapping("/update")
public BaseResponse<Boolean> update(@RequestBody Update${classTypeName}DTO dto){
    return ResultUtils.success(${className}Service.update(dto));
}

/**
* 删除
* @param id
* @return
*/
@PostMapping("/delete/{id}")
public BaseResponse<Boolean> delete(@PathVariable Long id){
    return ResultUtils.success(${className}Service.deleteById(id));
}

/**
* 查询详情
* @param id
* @return
*/
@GetMapping("/{id}")
public BaseResponse<${classTypeName}VO> selectById(@PathVariable Long id){
    return ResultUtils.success(${className}Service.selectById(id));
}

}
