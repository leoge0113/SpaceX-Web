package com.cainiao.web;

import com.cainiao.dto.BaseResult;
import com.cainiao.entity.Goods;
import com.cainiao.enums.ResultEnum;
import com.cainiao.exception.BizException;
import com.cainiao.service.GoodsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/goods")
public class GoodsController {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GoodsService goodsService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model, Integer offset, Integer limit) {
        LOG.info("invoke----------/goods/list");
        LOG.info("offset = ", offset);
        //offset = offset == null ? 0 : offset;//默认便宜0
        //limit = limit == null ? 50 : limit;//默认展示50条
        // Optional.ofNullable(offset).map(offset1 -> offset.intValue()).orElse(0);
        //Optional.ofNullable(limit).map(limit1 -> limit1.intValue()).orElse(50);
        List<Goods> list = goodsService.getGoodsList(Optional.ofNullable(offset).map(offset1 -> offset1.intValue()).orElse(0), Optional.ofNullable(limit).map(limit1 -> limit1.intValue()).orElse(50));
        model.addAttribute("goodslist", list);
        return "goodslist";
    }

    @RequestMapping(value = "/{goodsId}/buy", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    //Form表单提交的时候，只在前端做数据校验是不够安全的，所以有时候我们需要在后端同样做数据的校验
    //http://blog.csdn.net/xzmeasy/article/details/76098188
    public BaseResult<Object> buy(@CookieValue(value = "userPhone", required = false) Long userPhone,
        /*@PathVariable("goodsId") Long goodsId*/ @Valid Goods goods, BindingResult result) {
        LOG.info("invoke----------/" + goods.getGoodsId() + "/buy userPhone:" + userPhone);
        if (userPhone == null) {
            return new BaseResult<Object>(false, ResultEnum.INVALID_USER.getMsg());
        }
        //Valid 参数验证(这里注释掉，采用AOP的方式验证,见BindingResultAop.java)
        try {
            goodsService.buyGoods(userPhone, goods.getGoodsId(), false);
        } catch (BizException e) {
            return new BaseResult<Object>(false, e.getMessage());
        } catch (Exception e) {
            return new BaseResult<Object>(false, ResultEnum.INNER_ERROR.getMsg());
        }
        return new BaseResult<Object>(true, null);
    }
}
