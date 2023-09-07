package top.qiudb.service.resource;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.qiudb.mapper.ResourceListMapper;
import top.qiudb.pojo.resource.ResourceList;

import java.util.List;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/5/16 8:25
 * @description 描述
 */
@Service
public class ResourceListServiceImpl implements ResourceListService {
    @Autowired
    ResourceListMapper resourceListMapper;

    @Override
    public List<ResourceList> queryAllResource(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return resourceListMapper.selectAllResource();
    }

    @Override
    public Integer queryCount() {
        return resourceListMapper.queryCount();
    }

    @Override
    public List<ResourceList> queryAllResourceByName(String resourceName,int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return resourceListMapper.selectAllResourceByName(resourceName);
    }

    @Override
    public Integer queryCountByName(String resourceName) {
        return resourceListMapper.queryCountByName(resourceName);
    }

    @Override
    public ResourceList queryResourceById(Integer resourceId) {
        return resourceListMapper.selectResourceById(resourceId);
    }
}
