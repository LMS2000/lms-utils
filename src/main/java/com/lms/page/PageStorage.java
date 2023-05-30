package com.lms.page;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PageStorage {
    private Long pageNum;
    private Long pageSize;
    private Long totalPageNum;
    private Long totalRecordNum;
    private List<Object> data;

    protected static void exposePageInfo(IPage page) {
        PageStorage storage=new PageStorage();
        storage.setPageSize(page.getSize());
        storage.setPageNum(page.getCurrent());
        storage.setTotalPageNum(page.getPages());
        storage.setTotalRecordNum(page.getTotal());
        storage.setData(page.getRecords());

    }
}
