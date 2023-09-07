package top.qiudb.controller.marketing;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import top.qiudb.pojo.Vip;
import top.qiudb.service.marketing.VipService;
import top.qiudb.util.tools.ResultVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/vip")
@Api(tags="vip信息操作接口")
public class VipManageController {
    @Autowired
    private VipService vipService;
    @GetMapping("/pageList")
    @ResponseBody
    @ApiOperation("分页查询所有VIP")
    public ResultVO pageAllList(@RequestParam int pageNum, @RequestParam int pageSize){
        List<Vip> pageVip=vipService.queryPageVip(pageNum,pageSize);
        List<Vip> totalVip=vipService.queryAllVip();
        Map<String,Object> data=new HashMap<>();
        data.put("total",totalVip.size());
        data.put("list",pageVip);
        if(totalVip.size()!=0){
            return ResultVO.success("查询成功",data);
        }
        return ResultVO.error("查询失败",data);
    }

    @GetMapping("/goToEditVip")
    @ApiOperation("跳转到添加/编辑Vip")
    public String goToEditMessage(@ApiParam("VipId") int vipId, Model model){
        if (vipId!=0){
            Vip vip= vipService.queryByVipId(vipId);
            model.addAttribute("vip",vip);
        }
        return "marketing/add-edit-vip";
    }

    @PostMapping("/addVIP")
    @ResponseBody
    @ApiOperation("添加VIP")
    public ResultVO addVIP(@ApiParam("VIP信息")Vip vip){
        int n= vipService.addVip(vip);
        if (n == 1) {
            return ResultVO.success("添加成功");
        }
        return ResultVO.error("添加失败");
    }

    @PostMapping("/editVIP")
    @ResponseBody
    @ApiOperation("编辑VIP")
    public ResultVO editVIP(@ApiParam("VIP信息")Vip vip){
        int n= vipService.updateVip(vip);
        if (n == 1) {
            return ResultVO.success("修改成功");
        }
        return ResultVO.error("修改失败");
    }

    @GetMapping("/updateVipState")
    @ResponseBody
    @ApiOperation("修改VIP状态")
    public ResultVO updateVIPState(@ApiParam("VIPId")int vipId){
        int n=vipService.updateVipState(vipId);
        if(n==1){
            return ResultVO.success("修改成功");
        }
        return ResultVO.error("修改失败");
    }

    @GetMapping("/deleteVip")
    @ResponseBody
    @ApiOperation("删除VIP")
    public ResultVO deleteVip(@ApiParam("VIPId")int vipId){
        int n=vipService.deleteVip(vipId);
        if(n==1){
            return ResultVO.success("删除成功");
        }
        return ResultVO.error("删除失败");
    }
}
