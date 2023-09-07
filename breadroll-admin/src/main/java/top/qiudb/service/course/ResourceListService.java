package top.qiudb.service.course;

import org.apache.ibatis.annotations.Param;
import top.qiudb.mapper.ResourceListMapper;
import top.qiudb.pojo.ResourceList;

import java.util.List;

public interface ResourceListService {
    //分页查询所有未删除的资料
    List<ResourceList> queryPageResourceList(int pageNum, int pageSize);
    //查询所有未删除的资料  统计数量
    List<ResourceList> queryTotalResource();
    //搜索资料
    List<ResourceList> searchResource(ResourceList resourceList,int pageNum, int pageSize);
    List<ResourceList> totalSearchResource(ResourceList resourceList);
    //根据ResourceId查询资料
    ResourceList queryResourceById(int resourceId);
    //添加资料
    int addResource(ResourceList resourceList);
    //编辑资料
    int updateResource(ResourceList resourceList);
    //修改资料发布状态
    int updateResourceState(int resourceId,String uploader);
    //改变资料的审核状态
    int updateAuditState(int resourceId,int auditState);
    //根据资料Id删除
    int deleteResourceById(int resourceId);
    //根据资料Id获得资料路径
    String queryFileUrlById(int resourceId);
    //查询所有发布且通过审核的资料
    List<ResourceList> queryAllPublishAuditResource();
}
