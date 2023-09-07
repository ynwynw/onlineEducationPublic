package top.qiudb.service.course;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.qiudb.mapper.ResourceListMapper;
import top.qiudb.pojo.ResourceList;

import java.util.List;
@Service
public class ResourceListServiceImpl implements ResourceListService {
    @Autowired
    private ResourceListMapper resourceListMapper;

    @Override
    public List<ResourceList> queryPageResourceList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return resourceListMapper.queryPageResourceList();
    }

    @Override
    public List<ResourceList> queryTotalResource() {
        return resourceListMapper.queryTotalResource();
    }

    @Override
    public List<ResourceList> searchResource(ResourceList resourceList,int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return resourceListMapper.searchResource(resourceList);
    }

    @Override
    public List<ResourceList> totalSearchResource(ResourceList resourceList) {
        return resourceListMapper.totalSearchResource(resourceList);
    }

    @Override
    public ResourceList queryResourceById(int resourceId) {
        return resourceListMapper.queryResourceById(resourceId);
    }

    @Override
    public int addResource(ResourceList resourceList) {
        return resourceListMapper.addResource(resourceList);
    }

    @Override
    public int updateResource(ResourceList resourceList) {
        return resourceListMapper.updateResource(resourceList);
    }

    @Override
    public int updateResourceState(int resourceId,String uploader) {
        return resourceListMapper.updateResourceState(resourceId,uploader);
    }

    @Override
    public int updateAuditState(int resourceId, int auditState) {
        return resourceListMapper.updateAuditState(resourceId,auditState);
    }

    @Override
    public int deleteResourceById(int resourceId) {
        return resourceListMapper.deleteResourceById(resourceId);
    }

    @Override
    public String queryFileUrlById(int resourceId) {
        return resourceListMapper.queryFileUrlById(resourceId);
    }

    @Override
    public List<ResourceList> queryAllPublishAuditResource() {
        return resourceListMapper.queryAllPublishAuditResource();
    }
}

