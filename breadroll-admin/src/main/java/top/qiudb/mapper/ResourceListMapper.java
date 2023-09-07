package top.qiudb.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import top.qiudb.pojo.ResourceList;

import java.util.List;

@Component
@Mapper
public interface ResourceListMapper {
    //分页查询所有未删除的资料
    List<ResourceList> queryPageResourceList();
    //查询所有未删除的资料  统计数量
    List<ResourceList> queryTotalResource();
    //搜索资料
    List<ResourceList> searchResource(ResourceList resourceList);
    List<ResourceList> totalSearchResource(ResourceList resourceList);
    //根据ResourceId查询资料
    ResourceList queryResourceById(int resourceId);
    //添加资料
    int addResource(ResourceList resourceList);
    //编辑资料
    int updateResource(ResourceList resourceList);
    //修改资料发布状态
    int updateResourceState(@Param("resourceId") int resourceId,@Param("uploader")String uploader);
    //改变资料的审核状态
    int updateAuditState(@Param("resourceId")int resourceId, @Param("auditState")int auditState);
    //根据资料Id删除
    int deleteResourceById(int resourceId);
    //根据资料Id获得资料路径
    String queryFileUrlById(int resourceId);
    //查询所有发布且通过审核的资料
    List<ResourceList> queryAllPublishAuditResource();
}

