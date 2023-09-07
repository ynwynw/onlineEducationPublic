package top.qiudb.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;
import top.qiudb.pojo.banner.Banner;
import top.qiudb.pojo.resource.ResourceList;

import java.util.List;

/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/5/16 8:17
 * @description 学习资源
 */
@Repository
@Mapper
public interface ResourceListMapper {
    //查询所有学习资源
    public List<ResourceList> selectAllResource();

    //查询学习资源总数
    public Integer queryCount();

    //根据学习资源名字查询
    public List<ResourceList> selectAllResourceByName(String resourceName);

    //根据学习资源名字查询总数
    public Integer queryCountByName(String resourceName);

    //根据资源id查询
    public ResourceList selectResourceById(Integer resourceId);
}
